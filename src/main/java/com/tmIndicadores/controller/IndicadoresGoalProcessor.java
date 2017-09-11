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
    private String destination = PathFiles.PATH;

    public List<LogDatos> processDataFromFile(String fileName, InputStream in, Date fechaProgramacion,String razon,String tipologia, String periocidad,String lineasC){
        logDatos = new ArrayList<>();
        logDatos.add(new LogDatos("<<Inicio Indicadores Goal Bus con Archivo>>", TipoLog.INFO));
        log.info("<<Inicio Indicadores Goal Bus con Archivo>>");
        String [] cuadroInfo = fileName.split("_");
        String cuadroNuevo = cuadroInfo[2];
        if(!programacionServicios.isCuadroAlready(cuadroNuevo)){
            processorUtils.copyFile(fileName,in,destination);
            destination=destination+fileName;
            readExcelAndSaveData(destination,fechaProgramacion,razon,tipologia,periocidad,lineasC,fileName);
        }else{
            logDatos.add(new LogDatos("<<El cuadro de programación ya existe>>", TipoLog.ERROR));
        }

        logDatos.add(new LogDatos("<<Fin Indicadores Goal Bus con Archivo>>", TipoLog.INFO));

        return logDatos;
    }

    private void readExcelAndSaveData(String destination, Date fechaProgramacion, String razon,String tipologia, String periocidad,String lineasC,String filename) {
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
            String [] cuadroInfo = filename.split("_");
            String [] valores = line.split(cvsSplitBy);
            km_comer_inicial = Double.parseDouble(valores[TraceLogIndex.KM_LINEA]);
            km_vacio_inicial = Double.parseDouble(valores[TraceLogIndex.KM_VACIO]);
            while ((line = br.readLine()) != null){
                previousLine =line;
            }
            valores = previousLine.split(cvsSplitBy);
            Programacion programacion = new Programacion();
            programacion.setCuadro(cuadroInfo[2]);
            programacion.setFecha(fechaProgramacion);
            programacion.setRazonCambio(razon);
            programacion.setKmComercialIncio(km_comer_inicial);
            programacion.setKmVacioInicio(km_vacio_inicial);
            programacion.setPorcentajeVacioInicio(km_vacio_inicial/km_comer_inicial);
            programacion.setLineasCargadas(Integer.parseInt(lineasC));
            programacion.setKmComercialFin(Double.parseDouble(valores[TraceLogIndex.KM_LINEA]));
            programacion.setKmVacioFin(Double.parseDouble(valores[TraceLogIndex.KM_VACIO]));
            programacion.setPorcentajeVacioFinal(programacion.getKmVacioFin()/programacion.getKmComercialFin());
            programacion.setBuses(Integer.parseInt(valores[TraceLogIndex.NUM_BUSES]));
            programacion.setTiempoExpedicion(valores[TraceLogIndex.TOTAL_HORAS]);
            programacion.setExpedicionComercial(Integer.parseInt(valores[TraceLogIndex.NUM_VIAJES]));
            programacion.setTiempoProcesamiento(valores[TraceLogIndex.TIEMPO_SOLUCION]);
            programacion.setTipologia(tipologia);
            programacion.setPeriodicidad(periocidad);

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
