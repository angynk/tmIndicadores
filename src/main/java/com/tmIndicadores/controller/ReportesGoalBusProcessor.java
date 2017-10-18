package com.tmIndicadores.controller;

import com.tmIndicadores.controller.servicios.FechasAsociadasServicios;
import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.FechaAsociada;
import com.tmIndicadores.model.entity.Programacion;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("ReportesGoalProcessor")
public class ReportesGoalBusProcessor {

    @Autowired
    private ProgramacionServicios programacionServicios;

    @Autowired
    private FechasAsociadasServicios fechasAsociadasServicios;

    private List<FechaAsociada> fechaAsociadasBase ;

    private static Logger log = Logger.getLogger(ReportesGoalBusProcessor.class);


    public boolean exportarDatosDiaADia(Date fechaInicio,Date fechaFin,String modo,String tipologia){
        log.info("Proceso de Exportar Datos de Programaciones Día a Día");
        fechaAsociadasBase = fechasAsociadasServicios.getFechasBaseForReport(fechaInicio,fechaFin,modo,tipologia);
        try {
            createExcelDiaADia(modo,tipologia,fechaInicio,fechaFin);
            return true;
        } catch (Exception e) {
           log.error(e.getMessage());
        }
        return false;

    }

    private void createExcelDiaADia(String modo,String tipologia,Date fechaInicio,Date fechaFin){
        try {
            File file = new File(PathFiles.PATH+""+PathFiles.DIA_A_DIA_FILE);
            file.createNewFile();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Programaciones");
            crearRowsIniciales(worksheet);
            crearRowsContenido(worksheet,workbook,modo,tipologia,fechaInicio,fechaFin);
            FileOutputStream outFile =new FileOutputStream(PathFiles.PATH+""+PathFiles.DIA_A_DIA_FILE);
            workbook.write(outFile);
            outFile.close();
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
           log.error(e.getMessage());
        }
    }

    private void crearRowsContenido(HSSFSheet worksheet, HSSFWorkbook workbook,String modo,String tipologia,Date fechaInicio,Date fechaFin) {
        Date diaAPlus1 = fechaInicio;
        int x = 0;
        int pos = 1;
        boolean continuar = true;
        while(diaAPlus1.before(fechaFin) || diaAPlus1.compareTo(fechaFin)==0){
            if(x < fechaAsociadasBase.size()){
                FechaAsociada fecha = fechaAsociadasBase.get(x);
                if(fechasIguales(fecha.getFecha(),diaAPlus1)) { // Si la fecha existe
                    fecha.getProgramacion().setFecha(fecha.getFecha());
                    insertarProgramacion(worksheet,fecha.getProgramacion(),pos);
                    x++;
                }else{ // registro vacio
                    insertarRegistroVacio(worksheet,diaAPlus1,pos);
                }
            }else{
                insertarRegistroVacio(worksheet,diaAPlus1,pos);
            }
            diaAPlus1 = getSiguienteDia(diaAPlus1);
            pos++;

        }

    }

    private boolean fechasIguales(Date fecha, Date diaAPlus1) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        String fechaA = sdfDate.format(fecha);
        String fechaB = sdfDate.format(diaAPlus1);
        if(fechaA.equals(fechaB)){
            return true;
        }
        return false;
    }

    private void completarProgDesdeHasta(Programacion programacionA, Programacion programacionB,HSSFSheet worksheet,int pos) {

        //Información Base
        Date diaA = programacionA.getFecha();
        Date diaAPlus1 = getSiguienteDia(diaA);
        Date diaB = programacionB.getFecha();

        // Insertar Programación A
        insertarProgramacion(worksheet,programacionA,pos);

        if(diaAPlus1.compareTo(diaB)!=0){ // El siguiente día tiene programación asignada

        }
    }

    private Date getSiguienteDia(Date diaA) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(diaA);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    private void insertarProgramacion(HSSFSheet worksheet, Programacion prog,int pos) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Row row = worksheet.createRow(pos);
        createCellResultados(row,sdfDate.format(prog.getFecha()),GoalReporteDEF.ID_FECHA);
        createCellResultados(row,prog.getCuadro(),GoalReporteDEF.ID_CUADRO);
        createCellResultados(row,prog.getTipologia(),GoalReporteDEF.ID_TIPOLOGIA);
        createCellNumberResultados(row,prog.getBuses(),GoalReporteDEF.ID_BUSES);
        createCellNumberResultados(row,prog.getKmComercialFin(),GoalReporteDEF.ID_KM_COMERCIAL_FIN);
        createCellResultados(row,prog.getTipoProgramacionFormatted(),GoalReporteDEF.ID_TIEMPO_EXP);
        createCellNumberResultados(row,prog.getKmVacioFin(),GoalReporteDEF.ID_KM_VACIO_FIN);
        createCellNumberResultados(row,prog.getPorcentajeVacioFinal(),GoalReporteDEF.ID_POR_VACIO_FIN);
        createCellNumberResultados(row,prog.getLineasCargadas(),GoalReporteDEF.ID_LINEAS);
        createCellResultados(row,prog.getTipoProgramacionFormatted(),GoalReporteDEF.ID_TIPO);
        createCellResultados(row,prog.getRazonCambio(),GoalReporteDEF.ID_RAZON);
    }

    private void insertarRegistroVacio(HSSFSheet worksheet, Date fecha,int pos) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        Row row = worksheet.createRow(pos);
        createCellResultados(row,sdfDate.format(fecha),GoalReporteDEF.ID_FECHA);
        createCellResultados(row,"Informacion no encontrada",GoalReporteDEF.ID_CUADRO);
    }

    private void crearRowsIniciales(HSSFSheet worksheet) {
        Row row = worksheet.createRow(0);
        createCellResultados(row,GoalReporteDEF.TI_FECHA,GoalReporteDEF.ID_FECHA);
        createCellResultados(row,GoalReporteDEF.TI_CUADRO,GoalReporteDEF.ID_CUADRO);
        createCellResultados(row,GoalReporteDEF.TI_TIPOLOGIA,GoalReporteDEF.ID_TIPOLOGIA);
        createCellResultados(row,GoalReporteDEF.TI_BUSES,GoalReporteDEF.ID_BUSES);
        createCellResultados(row,GoalReporteDEF.TI_KM_COMERCIAL_FIN,GoalReporteDEF.ID_KM_COMERCIAL_FIN);
        createCellResultados(row,GoalReporteDEF.TI_TIEMPO_EXP,GoalReporteDEF.ID_TIEMPO_EXP);
        createCellResultados(row,GoalReporteDEF.TI_KM_VACIO_FIN,GoalReporteDEF.ID_KM_VACIO_FIN);
        createCellResultados(row,GoalReporteDEF.TI_POR_VACIO_FIN,GoalReporteDEF.ID_POR_VACIO_FIN);
        createCellResultados(row,GoalReporteDEF.TI_LINEAS,GoalReporteDEF.ID_LINEAS);
        createCellResultados(row,GoalReporteDEF.TI_TIPO,GoalReporteDEF.ID_TIPO);
        createCellResultados(row,GoalReporteDEF.TI_RAZON,GoalReporteDEF.ID_RAZON);
    }

    private void createCellResultados(Row row, String valor, int num) {
        Cell resultadoHoraIni= row.createCell(num);
        resultadoHoraIni.setCellValue(valor);
        resultadoHoraIni.setCellType(Cell.CELL_TYPE_STRING);
        resultadoHoraIni.setCellValue(valor);
    }

    private void createCellNumberResultados(Row row, double valor,int num) {
        Cell resultadoHoraIni= row.createCell(num);
        resultadoHoraIni.setCellValue(ProcessorUtils.round(valor,2));
        resultadoHoraIni.setCellType(Cell.CELL_TYPE_NUMERIC);
        resultadoHoraIni.setCellValue(valor);
    }

    private void createCellNumberResultados(Row row, int valor,int num) {
        Cell resultadoHoraIni= row.createCell(num);
        resultadoHoraIni.setCellValue(valor);
        resultadoHoraIni.setCellType(Cell.CELL_TYPE_NUMERIC);
        resultadoHoraIni.setCellValue(valor);
    }
}
