package com.zhaolong.statistical.service;

import com.zhaolong.statistical.entity.BusinessInfo;
import com.zhaolong.statistical.entity.ExcelInfo;
import com.zhaolong.statistical.entity.KeyWordsExcelInfo;
import com.zhaolong.statistical.entity.KeywordsCode;
import com.zhaolong.statistical.repository.KeyWordsRepository;
import com.zhaolong.statistical.util.ExcelImportUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@Service
public class KeyWordsCodeService {

    @Autowired
    private KeyWordsRepository keyWordsRepository;

    /**
     * 保存关键词
     *
     * @param keywords
     * @param keycode
     * @param keychannel
     */
    public void saveKeyWords(String keywords, String keycode, String keychannel) {
        KeywordsCode keywordsCode = new KeywordsCode();
        keywordsCode.setCode(keycode);
        keywordsCode.setKeyWords(keywords);
        keywordsCode.setSearchEngine(keychannel);

        keyWordsRepository.save(keywordsCode);
    }

    public List<KeywordsCode> getKeywordsCodeList(Pageable pageable) {
//        Page<KeywordsCode> list = keyWordsRepository.findAll(pageable);
        Page<KeywordsCode> list = keyWordsRepository.findByState(1, pageable);
        List<KeywordsCode> keywordsCodes = new ArrayList<>();
        int i = 1;
        for (KeywordsCode key : list) {
            key.setNo(i);
            keywordsCodes.add(key);
            i++;
        }


        return keywordsCodes;
    }

    public void deleteKey(Integer id) {
        KeywordsCode keywordsCode = keyWordsRepository.findOne(id);
        keywordsCode.setState(0);
        keyWordsRepository.save(keywordsCode);
    }

    public void uploadKey(String fileName, MultipartFile mfile) {
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
        read(wb, 0, 0, 1);//百度PC
        read(wb, 1, 0, 1);//搜狗
        read(wb, 2, 0, 1);//360
        read(wb, 3, 0, 1);//360
        read(wb, 4, 0, 1);//360
        read(wb, 5, 0, 1);//360

    }

    private void read(Workbook wb, int sheetNo, int key, int url) {

        Sheet sheet = wb.getSheetAt(sheetNo);
        //得到Excel的行数
        int totalRows = sheet.getPhysicalNumberOfRows();
        //总列数
        int totalCells = 0;
        //得到Excel的列数(前提是有行数)，从第二行算起
        if (totalRows >= 2 && sheet.getRow(1) != null) {
            totalCells = sheet.getRow(1).getPhysicalNumberOfCells();
        }
        List<KeyWordsExcelInfo> baiduPcExcelList = new ArrayList<>();
        KeyWordsExcelInfo excelInfo;
        for (int r = 1; r < totalRows; r++) {
            Row row = sheet.getRow(r);
            excelInfo = new KeyWordsExcelInfo();
            Cell cell = row.getCell(key);//关键词
            String value = cell.getStringCellValue();
            excelInfo.setKey(value);
            cell = row.getCell(url);//url
            value = cell.getStringCellValue();
            excelInfo.setUrl(value);
            int n = value.indexOf("?");
            if (n >= 0) {
                String s = value.substring(n + 1, value.length());
                String[] strings = s.split("-");
                if (strings.length == 4) {
                    String channel = "";
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
                    if (strings[1].equalsIgnoreCase("pc") && !channel.equals("神马")) {
                        channel += "PC";
                    } else if(!channel.equals("神马")){
                        channel += "移动";
                    }

                    excelInfo.setChannel(channel);
                    excelInfo.setCode(strings[3]);

                    saveKeyWords(excelInfo.getKey(), excelInfo.getCode(), excelInfo.getChannel());
                }

            }


        }
    }
}
