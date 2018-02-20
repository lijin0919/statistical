package com.zhaolong.statistical.service;

import com.zhaolong.statistical.entity.BusinessInfo;
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

import javax.xml.crypto.Data;
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

@Service
public class BusinessService {

    @Autowired
    private KeyWordsRecordRepository keyWordsRecordRepository;

    @Autowired
    private KeyWordsRepository keyWordsRepository;

    public void batchImport(String fileName, MultipartFile mfile) {

        File uploadDir = new File("E:\\fileupload");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!uploadDir.exists()) uploadDir.mkdirs();
        //新建一个文件
        File tempFile = new File("E:\\fileupload\\" + new Date().getTime() + ".xlsx");
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
            readExcelValue(wb, tempFile);
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
    }


    private void readExcelValue(Workbook wb, File tempFile) throws ParseException {

        //错误信息接收器
        String errorMsg = "";
        //得到第一个shell
        Sheet sheet = wb.getSheetAt(0);
        //得到Excel的行数
        int totalRows = sheet.getPhysicalNumberOfRows();
        //总列数
        int totalCells = 0;
        //得到Excel的列数(前提是有行数)，从第二行算起
        if (totalRows >= 2 && sheet.getRow(4) != null) {
            totalCells = sheet.getRow(4).getPhysicalNumberOfCells();
        }
        List<ExcelInfo> baiduPcExcelList = new ArrayList<>();
        ExcelInfo baiduPcExcel;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数
        //循环Excel行数,从第二行开始。标题不入库
        for (int r = 3; r < totalRows; r++) {

            Row row = sheet.getRow(r);

            baiduPcExcel = new ExcelInfo();
            Cell cell = row.getCell(12);
            //循环Excel的列

            String value = cell.getStringCellValue();

            Cell dateCell = row.getCell(1);

            String str = dateCell.getStringCellValue();
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = simpleDateFormat1.parse(str);

            int n = value.indexOf("?");
            if (n >= 0) {
                String s = value.substring(n + 1, value.length());

                String[] strings = s.split("-");

                if (strings.length == 4) {
                    BusinessInfo businessInfo = new BusinessInfo();

                    String channel ="";
                    switch (strings[0]) {
                        case "baidu":
                            channel = "百度";
                            break;
                        case "sg":
                            channel = "搜狗";
                            break;
                        case "sm":
                            channel = "神马";
                            break;
                        case "360":
                            channel = "360";
                            break;

                    }

                    if(strings[1].equalsIgnoreCase("pc")&&!channel.equals("神马")){
                        channel+="PC";
                    }else {
                        channel+="移动";
                    }


                    businessInfo.setChannel(channel);

                    businessInfo.setKey(strings[3]);
                    businessInfo.setDate(date);
                    updateKeyRecord(businessInfo);
                }

            }


        }
    }


    public void updateKeyRecord(BusinessInfo businessInfo) {


//       List<KeywordsCode> list = keyWordsRepository.findByKeyWordsAndSearchEngine(businessInfo.getKey(),businessInfo.getChannel());
        List<KeywordsCode> list = keyWordsRepository.findByCodeAndSearchEngineAndState(businessInfo.getKey(), businessInfo.getChannel(),1);
        if (list.size() > 0) {
            List<KeywordsRecord> keywordsRecordList = keyWordsRecordRepository.findByKeywordsCodeAndSearchEngine(list.get(0), businessInfo.getChannel());

            if (keywordsRecordList.size() > 0) {
                for (KeywordsRecord keywordsRecord : keywordsRecordList) {

                    if ((keywordsRecord.getRecordDate().getYear() == businessInfo.getDate().getYear())
                            && (keywordsRecord.getRecordDate().getMonth() == businessInfo.getDate().getMonth())
                            && (keywordsRecord.getRecordDate().getDay() == businessInfo.getDate().getDay())) {
                        if (keywordsRecord.getDialogueCount() == null || keywordsRecord.getDialogueCount() == 0) {
                            keywordsRecord.setDialogueCount(1);
                        } else {
                            keywordsRecord.setDialogueCount(keywordsRecord.getDialogueCount() + 1);
                        }

                        keyWordsRecordRepository.save(keywordsRecord);
                    }
                }
            }
        }
    }

}






