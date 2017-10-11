package com.tmIndicadores.controller;

import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.Programacion;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("ReportesGoalProcessor")
public class ReportesGoalBusProcessor {

    @Autowired
    private ProgramacionServicios programacionServicios;

    private List<Programacion> programacionRecordsBase ;


    public boolean exportarDatosDiaADia(Date fechaInicio,Date fechaFin,String modo,String tipologia){

        programacionRecordsBase = programacionServicios.getProgramacionBaseForReport(fechaInicio,fechaFin,modo,tipologia);
        try {
            createExcelDiaADia();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    private void createExcelDiaADia() throws Exception {
        try {
            File file = new File(PathFiles.PATH+""+PathFiles.DIA_A_DIA_FILE);
            file.createNewFile();
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet worksheet = workbook.createSheet("Programaciones");
            crearRowsIniciales(worksheet);
            crearRowsContenido(worksheet,workbook);
            FileOutputStream outFile =new FileOutputStream(PathFiles.PATH+""+PathFiles.DIA_A_DIA_FILE);
            workbook.write(outFile);
            outFile.close();
        } catch (FileNotFoundException e) {
//            logDatos.add(new LogDatos(e.getMessage(), TipoLog.ERROR));
            e.fillInStackTrace();
            throw new Exception("No existe un archivo de Verificacion para ese Tipo Dia");
        } catch (IOException e) {
//            logDatos.add(new LogDatos(e.getMessage(), TipoLog.ERROR));
            throw new Exception(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private void crearRowsContenido(HSSFSheet worksheet, HSSFWorkbook workbook) {
        for (int x = 0; x<programacionRecordsBase.size();x++){
            if(x+1<programacionRecordsBase.size()){
                completarProgDesdeHasta(programacionRecordsBase.get(x),programacionRecordsBase.get(x+1),worksheet,x+1);
            }else{
                //Insertar último campo
            }
        }

    }

    private void completarProgDesdeHasta(Programacion programacionA, Programacion programacionB,HSSFSheet worksheet,int pos) {
        insertarProgramacion(worksheet,programacionA,pos);
    }

    private void insertarProgramacion(HSSFSheet worksheet, Programacion prog,int pos) {
        Row row = worksheet.createRow(pos);
        createCellResultados(row,prog.getFechaFormatted(),GoalReporteDEF.ID_FECHA);
        createCellResultados(row,prog.getCuadro(),GoalReporteDEF.ID_CUADRO);
        createCellResultados(row,prog.getTipologia(),GoalReporteDEF.ID_TIPOLOGIA);
        createCellResultados(row,prog.getBuses()+"",GoalReporteDEF.ID_BUSES);
        createCellResultados(row,prog.getKmComercialFin()+"",GoalReporteDEF.ID_KM_COMERCIAL_FIN);
        createCellResultados(row,prog.getTipoProgramacionFormatted(),GoalReporteDEF.ID_TIEMPO_EXP);
        createCellResultados(row,prog.getKmVacioFin()+"",GoalReporteDEF.ID_KM_VACIO_FIN);
        createCellResultados(row,prog.getPorcentajeVacioFinal()+"",GoalReporteDEF.ID_POR_VACIO_FIN);
        createCellResultados(row,prog.getLineasCargadas()+"",GoalReporteDEF.ID_LINEAS);
        createCellResultados(row,prog.getTipoProgramacionFormatted(),GoalReporteDEF.ID_TIPO);
        createCellResultados(row,prog.getRazonCambio(),GoalReporteDEF.ID_RAZON);
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
}
