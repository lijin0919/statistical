package com.zhaolong.statistical.service;

import com.zhaolong.statistical.entity.ExcelInfo;
import com.zhaolong.statistical.entity.KeywordsRecord;
import com.zhaolong.statistical.repository.DataListRepository;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Date;
import java.util.List;

@Service
public class ExportService {

    @Autowired
    private DataListRepository dataListRepository;

    public void exportExcl(Date start,Date end) throws IOException {
//        List<KeywordsRecord> list = dataListRepository.findByRecordDateBetween(start,end);

        File file = makeExcel();
        InputStream is = new FileInputStream(file);
        Workbook wb = new XSSFWorkbook(is);
        //得到第一个shell
        Sheet sheet=wb.getSheetAt(0);
        //得到Excel的行数
        int totalRows=sheet.getPhysicalNumberOfRows();
        //总列数
        int totalCells = 0;
        //得到Excel的列数(前提是有行数)，从第二行算起
        if(totalRows>=2 && sheet.getRow(2) != null){
            totalCells=sheet.getRow(2).getPhysicalNumberOfCells();
        }

        Row row = sheet.getRow(3);

        Cell cell = row.getCell(5);
        cell.setCellValue("aaa");
        //循环Excel的列

        System.out.println(totalCells);
    }

    public File makeExcel() throws IOException {
        File uploadDir = new  File("E:\\export");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!uploadDir.exists()) uploadDir.mkdirs();
        //新建一个文件
        File from = new File("E:\\推广日报.xlsx");
        File tempFile = new File("E:\\export\\" + new Date().getTime() + ".xlsx");
        copyFile(from,tempFile);
        return tempFile;
    }

    public void copyFile(File fromFile, File toFile) throws IOException {
        FileInputStream ins = new FileInputStream(fromFile);
        FileOutputStream out = new FileOutputStream(toFile);
        byte[] b = new byte[1024];
        int n=0;
        while((n=ins.read(b))!=-1){
            out.write(b, 0, n);
        }

        ins.close();
        out.close();
    }
}
