package com.tmIndicadores.controller;

import com.tmIndicadores.controller.servicios.FechasAsociadasServicios;
import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.FechaAsociada;
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

    @Autowired
    private FechasAsociadasServicios fechasAsociadasServicios;

    private List<LogDatos> logDatos;
    private static Logger log = Logger.getLogger(IndicadoresGoalProcessor.class);
    private String destination = PathFiles.PATH;

    public String copyFile(String fileName, InputStream in){
        processorUtils.copyFile(fileName,in,destination);
        destination=destination+fileName;
        return destination;
    }

    public List<LogDatos>processDataFromFile(String fileName,Date fechaProgramacion,String razon,String tipologia,
                                              String periocidad,String lineasC,String cuadro,String modo,String fechas,String filePath, Integer numServicios){
        logDatos = new ArrayList<>();
        logDatos.add(new LogDatos("<<Inicio Indicadores Goal Bus con Archivo>>", TipoLog.INFO));
        log.info("<<Inicio Indicadores Goal Bus con Archivo>>");
        if(eliminarDatosParaLaFecha(fechaProgramacion,tipologia,periocidad)){
            if(!programacionServicios.isCuadroAlready(cuadro)){
                List<Date> fechasRecords = ProcessorUtils.convertirAfechas(fechas);
//                processorUtils.copyFile(fileName,in,destination);

                readExcelAndSaveData(filePath,fechaProgramacion,razon,tipologia,periocidad,lineasC,fileName,cuadro,modo,fechasRecords,numServicios);
            }else{
                logDatos.add(new LogDatos("El cuadro de programación "+cuadro+" ya existe", TipoLog.ERROR));
            }


        }else{
            logDatos.add(new LogDatos("Ya existe una programación para el dia "+periocidad+" "+fechaProgramacion.toString()+" Tipologia  "+tipologia, TipoLog.ERROR));
        }

        logDatos.add(new LogDatos("<<Fin Indicadores Goal Bus con Archivo>>", TipoLog.INFO));
        destination = PathFiles.PATH;
        return logDatos;
    }

    public boolean eliminarDatosParaLaFecha(Date fechaProgramacion, String tipologia, String periocidad) {
        List<Programacion> programaciones = programacionServicios.getProgramacionbyFechaTipologiaPeriocidad(fechaProgramacion, tipologia, periocidad);
        try{
            for(Programacion prog:programaciones){
                programacionServicios.deleteProgramacion(prog);
            }

            List<FechaAsociada>  fechaAsociadas = programacionServicios.getFechasAsociadas(fechaProgramacion);
            for(FechaAsociada fechaAsociada:fechaAsociadas){
                if (fechaAsociada.getProgramacion().getTipologia().equals(tipologia)
                        && fechaAsociada.getProgramacion().getPeriodicidad().equals(periocidad)){
                    programacionServicios.deleteFechaAsociada(fechaAsociada);
                }
            }

        }catch (Exception e){
            return false;
        }
        return true;

    }

    private boolean elCuadroProgramacionNoExiste(String cuadro) {
        return false;
    }

    private void readExcelAndSaveData(String destination, Date fechaProgramacion, String razon,String tipologia, String periocidad,
                                      String lineasC,String filename,String cuadro,String modo,List<Date> fechasRecords,Integer numServicios) {
        BufferedReader br = null;
        String line = "";
        String previousLine ="";
        try {
            String cvsSplitBy = encontrarTipoArchivo(filename);
            int diffFiles = diferenciaIndexArchivos(cvsSplitBy);
            double km_vacio_inicial ;
            double km_comer_inicial ;

            br = new BufferedReader(new FileReader(destination));
            br.readLine(); // Leer encabezados
            line = br.readLine();
            String [] valores = line.split(cvsSplitBy);
            km_comer_inicial = Double.parseDouble(valores[TraceLogIndex.KM_LINEA-diffFiles]);
            km_vacio_inicial = Double.parseDouble(valores[TraceLogIndex.KM_VACIO-diffFiles]);
            while ((line = br.readLine()) != null){
                previousLine =line;
            }
            if(previousLine.length()>1){
                valores = previousLine.split(cvsSplitBy);
            }

            Programacion programacion = new Programacion();
            programacion.setCuadro(cuadro);
            programacion.setFecha(fechaProgramacion);
            programacion.setRazonCambio(razon);
            programacion.setKmComercialIncio(km_comer_inicial);
            programacion.setKmVacioInicio(km_vacio_inicial);
            programacion.setPorcentajeVacioInicio(km_vacio_inicial/km_comer_inicial);
            programacion.setLineasCargadas(Integer.parseInt(lineasC));
            programacion.setNumeroServicios(numServicios);
            programacion.setKmComercialFin(Double.parseDouble(valores[TraceLogIndex.KM_LINEA-diffFiles]));
            programacion.setKmVacioFin(Double.parseDouble(valores[TraceLogIndex.KM_VACIO-diffFiles]));
            programacion.setPorcentajeVacioFinal(programacion.getKmVacioFin()/programacion.getKmComercialFin());
            programacion.setBuses(Integer.parseInt(valores[TraceLogIndex.NUM_BUSES-diffFiles]));
            programacion.setTiempoExpedicion(valores[TraceLogIndex.TOTAL_HORAS-diffFiles]);
            programacion.setExpedicionComercial(Integer.parseInt(valores[TraceLogIndex.NUM_VIAJES-diffFiles]));
            programacion.setTiempoProcesamiento(valores[TraceLogIndex.TIEMPO_SOLUCION-diffFiles]);
            programacion.setTipologia(tipologia);
            programacion.setPeriodicidad(periocidad);
            programacion.setTipoProgramacion("N");
            programacion.setModo(modo);
            programacion.setTiempoVacio(valores[TraceLogIndex.HORAS_VACIO-diffFiles]);
            programacion.setNumCambioLinea(Integer.parseInt(valores[TraceLogIndex.NUM_CAMBIOS_LINEA-diffFiles]));
            programacion.setVelocidadComercial(calcularVelocidadComercial(programacion.getKmComercialFin(),programacion.getTiempoExpedicion()));
            programacion.setHorasPorBuses(calcularHorasPorBuses(programacion.getTiempoExpedicion(),programacion.getBuses(),valores[TraceLogIndex.HORAS_VACIO-diffFiles]));
            programacionServicios.addProgramacion(programacion);

            //Asociar Programación a fechas
            asociarProgramacionAFechas(programacion,fechasRecords);

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

    private void asociarProgramacionAFechas(Programacion programacion, List<Date> fechasRecords) {

        //Añadir las otras fechas seleccionadas
        for(Date fecha: fechasRecords){
            if(fechaNoExiste(fecha,programacion)){
                FechaAsociada fechaAsociada = new FechaAsociada(fecha,programacion);
                fechasAsociadasServicios.addFechaAsociada(fechaAsociada);
            }

        }
    }

    private boolean fechaNoExiste(Date fecha, Programacion nueva) {
        List<FechaAsociada>  fechaAsociadas = programacionServicios.getFechasAsociadas(fecha);
        for(FechaAsociada fechaAsociada:fechaAsociadas){
            if (fechaAsociada.getProgramacion().getModo().equals(nueva.getModo())
                    && fechaAsociada.getProgramacion().getTipologia().equals(nueva.getTipologia())
                    && fechaAsociada.getProgramacion().getPeriodicidad().equals(nueva.getPeriodicidad())){
                programacionServicios.deleteFechaAsociada(fechaAsociada);
            }
        }
        return true;
    }

    private Double calcularHorasPorBuses(String tiempoExpedicion, Integer buses, String valores) {
        Double totalHoras = ProcessorUtils.convertirFormatoHoraADouble(tiempoExpedicion);
        Double horasVacio = ProcessorUtils.convertirFormatoHoraADouble(valores);
        Double horasBus = totalHoras+horasVacio;
        return ProcessorUtils.round(horasBus/buses,2);
    }

    private Double calcularVelocidadComercial(Double kmComercialFin, String tiempoExpedicion) {
        Double horasTotales =  ProcessorUtils.convertirFormatoHoraADouble(tiempoExpedicion);
        return ProcessorUtils.round(kmComercialFin/horasTotales,2);
    }



    private int diferenciaIndexArchivos(String cvsSplitBy) {
        if(cvsSplitBy.equals(";")){
            return 0;
        }
        return 1;
    }

    private String encontrarTipoArchivo(String filename) throws FileNotFoundException {
        String[] fileNameSplit = filename.split("\\.");
        if(fileNameSplit.length>1){
            if(fileNameSplit[1].equals("csv")){
                return ";";
            }
            return "\t";
        }
       throw new FileNotFoundException("No se adjunto ningun archivo");
    }

    public boolean guardarDatosManual(Programacion programacion, String fechasManual) {
        if(eliminarDatosParaLaFecha(programacion.getFecha(),programacion.getTipologia(),programacion.getPeriodicidad())){
            if(!programacionServicios.isCuadroAlready(programacion.getCuadro())){
                List<Date> fechasRecords = ProcessorUtils.convertirAfechas(fechasManual);
                programacion.setPorcentajeVacioFinal(programacion.getKmVacioFin()/programacion.getKmComercialFin());
                programacion.setTipoProgramacion("N");
                programacionServicios.addProgramacion(programacion);

                //Asociar Programación a fechas
                asociarProgramacionAFechas(programacion,fechasRecords);
            }
            return true;
        }

        return false;
    }
}
