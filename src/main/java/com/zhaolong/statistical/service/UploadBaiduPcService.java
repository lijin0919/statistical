package com.zhaolong.statistical.service;

import com.zhaolong.statistical.entity.BaiduPcExcel;
import com.zhaolong.statistical.util.ExcelImportUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
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
public class UploadBaiduPcService {

    @Autowired
    private HttpSession session;
    /**
     * 上传excel文件到临时目录后并开始解析
     * @param fileName
     * @return
     */
    public void batchImport(String fileName, MultipartFile mfile){

        File uploadDir = new  File("E:\\fileupload");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!uploadDir.exists()) uploadDir.mkdirs();
        //新建一个文件
        File tempFile = new File("E:\\fileupload\\" + new Date().getTime() + ".xlsx");
        //初始化输入流
        InputStream is = null;
        try{
            //将上传的文件写入新建的文件中
            mfile.transferTo(tempFile);

            //根据新建的文件实例化输入流
            is = new FileInputStream(tempFile);

            //根据版本选择创建Workbook的方式
            Workbook wb = null;
            //根据文件名判断文件是2003版本还是2007版本
            if(ExcelImportUtils.isExcel2007(fileName)){
                wb = new XSSFWorkbook(is);
            }else{
                wb = new HSSFWorkbook(is);
            }
            //根据excel里面的内容读取知识库信息
            chooseExcelMethod(wb,tempFile);
        }catch(Exception e){
            e.printStackTrace();
        } finally{
            if(is !=null)
            {
                try{
                    is.close();
                }catch(IOException e){
                    is = null;
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 选择解析方式
     * @param wb
     * @param tempFile
     */
    public void chooseExcelMethod(Workbook wb,File tempFile) throws ParseException {
        String channel = (String) session.getAttribute("channel");
        switch (channel){
            case "百度PC":
                readExcelValue(wb,tempFile);
                break;
            case "百度移动":
                break;
            case "360PC":
                break;
            case "360移动":
                break;
            case "搜狗PC":
                break;
            case "搜狗移动":
                break;
            case "神马":
                break;
            case "商务通":
                break;
        }
    }


    /**
     * 解析百度PCExcel里面的数据
     * @param wb
     * @return
     */
    private void readExcelValue(Workbook wb,File tempFile) throws ParseException {

        //错误信息接收器
        String errorMsg = "";
        //得到第一个shell
        Sheet sheet=wb.getSheetAt(0);
        //得到Excel的行数
        int totalRows=sheet.getPhysicalNumberOfRows();
        //总列数
        int totalCells = 0;
        //得到Excel的列数(前提是有行数)，从第二行算起
        if(totalRows>=2 && sheet.getRow(8) != null){
            totalCells=sheet.getRow(8).getPhysicalNumberOfCells();
        }
        List<BaiduPcExcel> baiduPcExcelList=new ArrayList<>();
        BaiduPcExcel baiduPcExcel;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数
        //循环Excel行数,从第二行开始。标题不入库
        for(int r=9;r<totalRows;r++){

            Row row = sheet.getRow(r);

            baiduPcExcel = new BaiduPcExcel();

            //循环Excel的列
            for(int c = 0; c <totalCells; c++){
                Cell cell = row.getCell(c);
                if (null != cell){
                    switch (c){
                        case 0:
                            Date d = cell.getDateCellValue();
                            baiduPcExcel.setDate(d);
                            break;
                        case 1:
                            String key = cell.getStringCellValue();
                            baiduPcExcel.setKeyWords(key);
                            break;
                        case 4://展现

                            Double d1 = cell.getNumericCellValue();
                            baiduPcExcel.setShow(Integer.valueOf(df.format(d1)));
                            break;
                        case 5://点击
                            Double d2 = cell.getNumericCellValue();
                            baiduPcExcel.setClick(Integer.valueOf(df.format(d2)));
                            break;
                        case 6://消费
                            Double d3= cell.getNumericCellValue();
                            baiduPcExcel.setSpend(Double.valueOf(d3.toString()));
                            break;
                    }

                }
            }


            baiduPcExcelList.add(baiduPcExcel);

        }

        //删除上传的临时文件
//        if(tempFile.exists()){
////            tempFile.delete();
////        }
        //全部验证通过才导入到数据库
        System.out.println(baiduPcExcelList);
    }
}
