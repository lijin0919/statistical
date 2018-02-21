package com.zhaolong.statistical.service;

import com.zhaolong.statistical.entity.CustomerInfo;
import com.zhaolong.statistical.entity.ExcelInfo;
import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.entity.KeywordsRecord;
import com.zhaolong.statistical.repository.KeyWordsRecordRepository;
import com.zhaolong.statistical.repository.KeyWordsRepository;
import com.zhaolong.statistical.util.ExcelImportUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 上传客服数据
 */
@Service
public class CustomerService {

    @Autowired
    private KeyWordsRepository keyWordsRepository;
    @Autowired
    private KeyWordsRecordRepository keyWordsRecordRepository;

    /**
     * 读取客服数据
     *
     * @return
     */
    public List<CustomerInfo> readExcel(String fileName, MultipartFile mfile) {
        File uploadDir = new File("E:\\客服数据");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!uploadDir.exists()) uploadDir.mkdirs();
        //新建一个文件
        File tempFile = new File("E:\\客服数据\\" + new Date().getTime() + ".xlsx");
        //初始化输入流
        InputStream is = null;
        try {
            //将上传的文件写入新建的文件中
            mfile.transferTo(tempFile);

            //根据新建的文件实例化输入流
            is = new FileInputStream(tempFile);

            //根据版本选择创建Workbook的方式
            Workbook wb = null;
            //根据文件名判断文件是2003版本还是2007版本
            if (ExcelImportUtils.isExcel2007(fileName)) {
                wb = new XSSFWorkbook(is);
            } else {
                wb = new HSSFWorkbook(is);
            }
            //根据excel里面的内容读取知识库信息
            List<CustomerInfo> list = getInfo(wb, tempFile);

            updateRecord(list);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    private List<CustomerInfo> getInfo(Workbook wb, File tempFile) throws ParseException {

        //错误信息接收器
        String errorMsg = "";
        //得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        //得到Excel的行数
        int totalRows = sheet.getPhysicalNumberOfRows();
        //总列数
        int totalCells = 0;
        //得到Excel的列数(前提是有行数)，从第二行算起
        if (totalRows >= 1 && sheet.getRow(1) != null) {
            totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        }
        List<CustomerInfo> customerInfoList = new ArrayList<>();
        CustomerInfo customerInfo;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数
        //循环Excel行数,从第二行开始。标题不入库
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);

            customerInfo = new CustomerInfo();

            //循环Excel的列
            for (int c = 0; c < totalCells; c++) {
                Cell cell = row.getCell(c);
                if (null != cell) {
                    switch (c) {
                        case 0://时间
                            Date d = cell.getDateCellValue();
                            customerInfo.setDate(d);
                            break;
                        case 1://关键词
                            String key = cell.getStringCellValue();
                            customerInfo.setKey(key);
                            break;
                        case 2:
                            String s = cell.getStringCellValue();
                            customerInfo.setChannel(s);
                            break;
                        case 3://展现
                            Double d1 = cell.getNumericCellValue();
                            customerInfo.setPhone(Integer.valueOf(df.format(d1)));
                            break;
                        case 4://点击
                            Double d2 = cell.getNumericCellValue();
                            customerInfo.setVisit(Integer.valueOf(df.format(d2)));
                            break;
                        case 5://消费
                            Double d3 = cell.getNumericCellValue();
                            customerInfo.setRegister(Integer.valueOf(df.format(d3)));
                            break;
                    }

                }
            }
            customerInfoList.add(customerInfo);
        }

        System.out.println(customerInfoList);
        return customerInfoList;
    }

    public void updateRecord(List<CustomerInfo> list) {
        for (CustomerInfo c : list) {
            List<KeywordsCode> keywordsCodeList = keyWordsRepository.findByKeyWordsAndSearchEngine(c.getKey(), c.getChannel());
            if (keywordsCodeList.size() > 0) {
                List<KeywordsRecord> keywordsRecordList = keyWordsRecordRepository.findByKeywordsCodeAndSearchEngine(keywordsCodeList.get(0), c.getChannel());
                if (keywordsRecordList.size() > 0) {
                    for (KeywordsRecord keywordsRecord : keywordsRecordList) {

                        if ((keywordsRecord.getRecordDate().getYear() == c.getDate().getYear())
                                && (keywordsRecord.getRecordDate().getMonth() == c.getDate().getMonth())
                                && (keywordsRecord.getRecordDate().getDay() == c.getDate().getDay())) {
                            if (keywordsRecord.getPhoneCount() == null || keywordsRecord.getPhoneCount() == 0) {
                                keywordsRecord.setPhoneCount(c.getPhone());
                            } else {
                                keywordsRecord.setPhoneCount(keywordsRecord.getPhoneCount() + c.getPhone());
                            }

                            if (keywordsRecord.getVisitCount() == null || keywordsRecord.getVisitCount() == 0) {
                                keywordsRecord.setVisitCount(c.getVisit());
                            } else {
                                keywordsRecord.setVisitCount(keywordsRecord.getVisitCount() + c.getVisit());
                            }

                            if (keywordsRecord.getRegisteredCount() == null || keywordsRecord.getRegisteredCount() == 0) {
                                keywordsRecord.setRegisteredCount(c.getRegister());
                            } else {
                                keywordsRecord.setRegisteredCount(keywordsRecord.getRegisteredCount() + c.getRegister());
                            }

                            keyWordsRecordRepository.save(keywordsRecord);
                        }
                    }
                }

            }
        }
    }

}
