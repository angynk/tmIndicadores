package com.tmIndicadores.controller;

import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.Programacion;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("GoalProcessor")
public class IndicadoresGoalProcessor {

    @Autowired
    private ProcessorUtils processorUtils;

    @Autowired
    private ProgramacionServicios programacionServicios;

    private List<LogDatos> logDatos;
    private static Logger log = Logger.getLogger(IndicadoresGoalProcessor.class);
    private String destination="C:\\temp\\";

    public List<LogDatos> processDataFromFile(String fileName, InputStream in, Date fechaProgramacion,String razon,String tipologia, String periocidad){
        logDatos = new ArrayList<>();
        logDatos.add(new LogDatos("<<Inicio Indicadores Goal Bus con Archivo>>", TipoLog.INFO));
        log.info("<<Inicio Indicadores Goal Bus con Archivo>>");
        processorUtils.copyFile(fileName,in,destination);
        destination=destination+fileName;
        readExcelAndSaveData(destination,fechaProgramacion,razon,tipologia,periocidad);
        logDatos.add(new LogDatos("<<Fin Indicadores Goal Bus con Archivo>>", TipoLog.INFO));
        return logDatos;
    }

    private void readExcelAndSaveData(String destination, Date fechaProgramacion, String razon,String tipologia, String periocidad) {
        BufferedReader br = null;
        String line = "";
        String previousLine ="";
        String cvsSplitBy = ";";
        double km_vacio_inicial ;
        double km_comer_inicial ;

        try {

            br = new BufferedReader(new FileReader(destination));
            br.readLine(); // Leer encabezados
            line = br.readLine();
            String [] valores = line.split(cvsSplitBy);
            km_comer_inicial = Double.parseDouble(valores[TraceLogIndex.KM_LINEA]);
            km_vacio_inicial = Double.parseDouble(valores[TraceLogIndex.KM_VACIO]);
            while ((line = br.readLine()) != null){
                previousLine =line;
            }
            valores = previousLine.split(cvsSplitBy);
            Programacion programacion = new Programacion();
            programacion.setFecha(fechaProgramacion);
            programacion.setRazonCambio(razon);
            programacion.setKmComercialIncio(km_comer_inicial);
            programacion.setKmVacioInicio(km_vacio_inicial);
            programacion.setKmComercialFin(Double.parseDouble(valores[TraceLogIndex.KM_LINEA]));
            programacion.setKmVacioFin(Double.parseDouble(valores[TraceLogIndex.KM_VACIO]));
            programacion.setBuses(Integer.parseInt(valores[TraceLogIndex.NUM_BUSES]));
            programacion.setTiempoExpedicion(valores[TraceLogIndex.TIEMPO_SOLUCION]);
            programacion.setTipologia(tipologia);
            programacion.setPeriodicidad(periocidad);
            programacion.setPorcentajeVacioFinal(20);

            programacionServicios.addProgramacion(programacion);

        } catch (FileNotFoundException e) {
           logDatos.add(new LogDatos(e.getMessage(), TipoLog.ERROR));
        } catch (IOException e) {
            logDatos.add(new LogDatos(e.getMessage(), TipoLog.ERROR));
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logDatos.add(new LogDatos(e.getMessage(), TipoLog.ERROR));
                }
            }
        }

    }

}
