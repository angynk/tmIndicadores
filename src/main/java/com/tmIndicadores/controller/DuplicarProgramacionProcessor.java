package com.tmIndicadores.controller;

import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.Programacion;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("DuplicarProcessor")
public class DuplicarProgramacionProcessor {

    @Autowired
    private ProgramacionServicios programacionServicios;
    private boolean duplicacionValida = true;

    private List<LogDatos> logDatos;
    private static Logger log = Logger.getLogger(IndicadoresGoalProcessor.class);

    public List<LogDatos> duplicarProgramacion(Date fechaADuplicar, String fechas){
        logDatos = new ArrayList<>();
        duplicacionValida = true;
        logDatos.add(new LogDatos("<<Inicio Duplicacion programacion>>", TipoLog.INFO));
        List<Programacion> programaciones = encontrarProgramacionActual(fechaADuplicar);
        if(programaciones.size()>0){
            List<Date> fechasRecords = convertirAfechas(fechas);
            if(fechasRecords.size()>0){
                duplicarDatosProgramacion(fechasRecords,programaciones);
            }else{
                duplicacionValida = false;
                logDatos.add(new LogDatos("El formato de Fechas es Incorrecto",TipoLog.ERROR));
            }
        }else{
            duplicacionValida = false;
            logDatos.add(new LogDatos("No existe ningunaProgramacion Para la Fecha definida",TipoLog.ERROR));
        }
        logDatos.add(new LogDatos("<<Fin Duplicacion programacion>>", TipoLog.INFO));
        return logDatos;
    }

    private void duplicarDatosProgramacion(List<Date> fechasRecords, List<Programacion> programaciones) {
        for(Date fecha:fechasRecords){
            for(Programacion prog:programaciones){
                insertarProgramacion(fecha,prog);
                logDatos.add(new LogDatos("Programacion Duplicada ("+fecha.toString()+") del Cuadro: "+prog.getCuadro()+" ,Tipo Dia: "+prog.getPeriodicidad()
                        +" ,Tipologia: "+prog.getTipologia(), TipoLog.INFO));
            }
        }
    }



    private void insertarProgramacion(Date fecha, Programacion prog) {
        Programacion nuevaProgramacion = transpasarDatosObjetoProgramacion(prog);
        nuevaProgramacion.setFecha(fecha);
        programacionServicios.addProgramacion(nuevaProgramacion);
    }

    private Programacion transpasarDatosObjetoProgramacion(Programacion prog) {
        Programacion nuevo = new Programacion();
        nuevo.setBuses(prog.getBuses());
        nuevo.setCuadro(prog.getCuadro());
        nuevo.setExpedicionComercial(prog.getExpedicionComercial());
        nuevo.setKmComercialFin(prog.getKmComercialFin());
        nuevo.setKmComercialIncio(prog.getKmComercialIncio());
        nuevo.setKmVacioFin(prog.getKmVacioFin());
        nuevo.setKmVacioInicio(prog.getKmVacioInicio());
        nuevo.setLineasCargadas(prog.getLineasCargadas());
        nuevo.setPeriodicidad(prog.getPeriodicidad());
        nuevo.setPorcentajeVacioFinal(prog.getPorcentajeVacioFinal());
        nuevo.setPorcentajeVacioInicio(prog.getPorcentajeVacioInicio());
        nuevo.setRazonCambio(prog.getRazonCambio());
        nuevo.setTipologia(prog.getTipologia());
        nuevo.setTiempoExpedicion(prog.getTiempoExpedicion());
        nuevo.setTiempoProcesamiento(prog.getTiempoProcesamiento());
        nuevo.setTipoProgramacion("D");
        return nuevo;
    }

    private List<Date> convertirAfechas(String fechas) {
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


    private  List<Programacion> encontrarProgramacionActual(Date fechaProg) {
       return programacionServicios.getProgramacionbyFecha(fechaProg);
    }

    public boolean isDuplicacionValida() {
        return duplicacionValida;
    }

    public void setDuplicacionValida(boolean duplicacionValida) {
        this.duplicacionValida = duplicacionValida;
    }
}