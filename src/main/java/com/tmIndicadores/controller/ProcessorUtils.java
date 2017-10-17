package com.tmIndicadores.controller;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
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

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Double convertirFormatoHoraADouble(String tiempo){
        Double hora = 1.0;
        if(tiempo != null){
            String[] tiempoTotal = tiempo.split(":");
            try{
                hora = Double.parseDouble(tiempoTotal[0]+"."+tiempoTotal[1]);
            }catch (Exception e){

            }
        }


        return hora;
    }

    public static String convertirDoubleaFormatoHora(Double valor){
        valor = ProcessorUtils.round(valor,2);
        String hora = valor.toString();
        hora = hora.replace(".",":");
        return hora+":00";
    }

    public static List<Date> convertirAfechas(String fechas) {
        List<Date> fechasFormat = new ArrayList<>();
        String [] fechaRecords = fechas.split(",");
        for(int x = 0; x < fechaRecords.length; x++){
            Date date = ProcessorUtils.fromStringToDate(fechaRecords[x]);
            if(date==null){
                break;
            }else{
                fechasFormat.add(date);
            }
        }

        return fechasFormat;
    }
}
