package com.tmIndicadores.controller;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ProcessorUtils {

    public static final String CALCULO_PROMEDIO = "Promedio";
    public static final String CALCULO_MODA = "Moda";
    public static final String CALCULO_MINIMO = "Minimo";
    public static final String CALCULO_MAXIMO = "Maximo";

    public void copyFile(String fileName, InputStream in, String destination) {
        try {

            destination= destination+fileName;
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Date fromStringToDate(String fechaString){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = formatter.parse(fechaString);
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void postProcessXLS(Object document) {
        HSSFWorkbook book = (HSSFWorkbook) document;
        HSSFSheet sheet = book.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = book.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);


        HSSFCellStyle decStyle = book.createCellStyle();
        decStyle.setDataFormat((short)2);



        for(int rowInd = 1; rowInd < sheet.getPhysicalNumberOfRows(); rowInd++) {
            HSSFRow row = sheet.getRow(rowInd);
            for(int cellInd = 1; cellInd < header.getPhysicalNumberOfCells(); cellInd++) {
                HSSFCell cell = row.getCell(cellInd);
                String strVal = cell.getStringCellValue();

                //Double
                try {
                    double dblVal = Double.valueOf(strVal);
                    cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue(dblVal);
                    cell.setCellStyle(decStyle);
                }catch (Exception e){

                }


            }
        }
    }
}
