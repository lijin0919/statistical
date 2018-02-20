package com.zhaolong.statistical.service;

import com.zhaolong.statistical.entity.ExcelInfo;
import com.zhaolong.statistical.entity.ExportInfo;
import com.zhaolong.statistical.entity.KeywordsRecord;
import com.zhaolong.statistical.repository.DataListRepository;
import com.zhaolong.statistical.repository.ExportRepository;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExportService {

    @Autowired
    private DataListRepository dataListRepository;

    @Autowired
    private ExportRepository exportRepository;

    public String exportExcl(Date start, Date end) throws IOException {
        List<KeywordsRecord> list = dataListRepository.findByRecordDateBetween(start, end);

        //1、创建workbook，对应一个excel
        HSSFWorkbook wb = new HSSFWorkbook();

//1.5、生成excel中可能用到的单元格样式
//首先创建字体样式
        HSSFFont font = wb.createFont();//创建字体样式
        font.setFontName("宋体");//使用宋体
        font.setFontHeightInPoints((short) 10);//字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
//然后创建单元格样式style
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setFont(font);//将字体注入
        style1.setWrapText(true);// 自动换行
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        style1.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());// 设置单元格的背景颜色
        style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style1.setBorderTop((short) 1);// 边框的大小
        style1.setBorderBottom((short) 1);
        style1.setBorderLeft((short) 1);
        style1.setBorderRight((short) 1);

//2、生成一个sheet，对应excel的sheet，参数为excel中sheet显示的名字
        HSSFSheet sheet = wb.createSheet("精准课程词");//3、设置sheet中每列的宽度，第一个参数为第几列，0为第一列；第二个参数为列的宽度，可以设置为0。//Test中有三个属性，因此这里设置三列，第0列设置宽度为0，第1~3列用以存放数据
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 20 * 256);//4、生成sheet中一行，从0开始
        HSSFRow row = sheet.createRow(0);//设置行数。从0开始
        row.setHeight((short) 800);// 设定行的高度//5、创建row中的单元格，从0开始
        HSSFCell cell = row.createCell(0);//我们第一列设置宽度为0，不会显示，因此第0个单元格不需要设置样式
        cell = row.createCell(0);//从第1个单元格开始，设置每个单元格样式
        cell.setCellValue("核心词");//设置单元格中内容
        cell.setCellStyle(style1);//设置单元格样式
        cell = row.createCell(1);//第二个单元格
        cell.setCellValue("费用");
        cell.setCellStyle(style1);
        cell = row.createCell(2);//第三个单元格
        cell.setCellValue("点击");
        cell.setCellStyle(style1);
        cell = row.createCell(3);//第四个单元格
        cell.setCellValue("对话");
        cell.setCellStyle(style1);
        cell = row.createCell(4);//第五个单元格
        cell.setCellValue("信息");
        cell.setCellStyle(style1);
        cell = row.createCell(5);//第6个单元格
        cell.setCellValue("信息");
        cell.setCellStyle(style1);
//6、输入数据
        List<ExportInfo> exportInfos = exportRepository.getAll(start, end);
        System.out.println(exportInfos);
        for (int i = 0; i < exportInfos.size(); i++) {
            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(exportInfos.get(i).getKey());
            cell = row.createCell(1);
            cell.setCellValue(exportInfos.get(i).getTotalMoney());
            cell = row.createCell(2);
            cell.setCellValue(exportInfos.get(i).getClick());
            cell = row.createCell(3);
            cell.setCellValue(exportInfos.get(i).getDialog());
            cell = row.createCell(4);
            cell.setCellValue(exportInfos.get(i).getPhone());
            cell = row.createCell(5);
            cell.setCellValue(exportInfos.get(i).getVisit());
            cell = row.createCell(6);
            cell.setCellValue(exportInfos.get(i).getRegister());

        }
//7、如果需要单元格合并，有两种方式


//        sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 1));//参数为(第一行，最后一行，第一列，最后一列)
//8、输入excel
        String path = "E:\\test.xls";
        FileOutputStream os = new FileOutputStream("E:\\test.xls");
        wb.write(os);
        os.close();

        return path;

//        response.setContentType("multipart/form-data");
//        //设置Content-Disposition
//        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
//
//        OutputStream out = response.getOutputStream();
//
//        InputStream in = new FileInputStream(new File(path));
//
//        int b;
//        while((b=in.read())!=-1){
//            out.write(b);
//        }
//        in.close();
//        out.close();
    }

    public List<ExportInfo> getExportList(List<KeywordsRecord> list) {

        List<ExportInfo> exportInfos = new ArrayList<>();


        for (KeywordsRecord key : list) {
            for (ExportInfo ex : exportInfos) {
                if (ex != null) {
                    if (ex.getKey().equals(key.getKeywordsCode().getKeyWords())) {
                        ex.setClick(ex.getClick() + key.getClickCount());
                        ex.setDialog(ex.getDialog() + key.getDialogueCount());
                        ex.setPhone(ex.getPhone() + key.getPhoneCount());
                        ex.setRegister(ex.getRegister() + key.getRegisteredCount());
                        ex.setTotalMoney(ex.getTotalMoney() + key.getSpendMoney());
                        ex.setVisit(ex.getVisit() + key.getVisitCount());
                    }
                }
            }
            ExportInfo exportInfo = new ExportInfo();
            exportInfo.setKey(key.getKeywordsCode().getKeyWords());
            exportInfo.setClick(key.getClickCount());
            exportInfo.setDialog(key.getDialogueCount());
            exportInfo.setPhone(key.getPhoneCount());
            exportInfo.setRegister(key.getRegisteredCount());
            exportInfo.setTotalMoney(key.getSpendMoney());
            exportInfo.setVisit(key.getVisitCount());
            exportInfos.add(exportInfo);


        }


        return null;
    }


}
