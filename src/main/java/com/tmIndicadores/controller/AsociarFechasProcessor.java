package com.tmIndicadores.controller;

import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.FechaAsociada;
import com.tmIndicadores.model.entity.Programacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("AsociarFechasProcessor")
public class AsociarFechasProcessor {

    @Autowired
    private ProgramacionServicios programacionServicios;
    private boolean asociacionValida;


    public List<Programacion> getAllProgramacionbyModo(String modo) {
        return programacionServicios.getAllProgramacionbyModo(modo);
    }

    public boolean existeAsociacionesEnEsaFecha(String fechas, String modo) {
        List<Date> fechasRecords = ProcessorUtils.convertirAfechas(fechas);
        if(fechasRecords.size()>0){
            for(Date fecha:fechasRecords){
                List<FechaAsociada>  fechaAsociadas = programacionServicios.getFechasAsociadas(fecha);
                for(FechaAsociada fechaAsociada:fechaAsociadas){
                    if (fechaAsociada.getProgramacion().getModo().equals(modo)){
                        return true;
                    }
                }
            }


        }
        return false;
    }

    public List<LogDatos> asociarProgramacion(Date fechaAsociar, String fechas, String modo) {
        List<LogDatos> logDatos = new ArrayList<>();
        List<Date> fechasRecords = ProcessorUtils.convertirAfechas(fechas);
        eliminarAsociacionFechas(fechasRecords,modo,logDatos);
        asociarFechas(fechaAsociar,modo,fechasRecords,logDatos);
        return logDatos;
    }

    private void asociarFechas(Date fechaAsociar, String modo, List<Date> fechasRecords, List<LogDatos> logDatos) {
        List<Programacion> programaciones = programacionServicios.getProgramacionbyFechaModo(fechaAsociar, modo);
        for(Programacion prog:programaciones){
            for(Date fecha:fechasRecords){
                FechaAsociada fechaAsociada = new FechaAsociada();
                fechaAsociada.setFecha(fecha);
                fechaAsociada.setProgramacion(prog);
                programacionServicios.addFechaAsociada(fechaAsociada);
                logDatos.add(new LogDatos("Programacion  ("+prog.getCuadro()+") Asociada a fecha: "+fecha.toString(), TipoLog.INFO));
            }

        }
        asociacionValida = true;

    }

    private void eliminarAsociacionFechas( List<Date> fechasRecords, String modo, List<LogDatos> logDatos) {

        if(fechasRecords.size()>0){
            for(Date fecha:fechasRecords){
                List<FechaAsociada>  fechaAsociadas = programacionServicios.getFechasAsociadas(fecha);
                for(FechaAsociada fechaAsociada:fechaAsociadas){
                    if (fechaAsociada.getProgramacion().getModo().equals(modo)){
                        programacionServicios.deleteFechaAsociada(fechaAsociada);
                        logDatos.add(new LogDatos(fecha.toString()+" Desasociada a Programacion ("+fechaAsociada.getProgramacion().getCuadro()+")",TipoLog.INFO));
                    }
                }
            }


        }
    }

    public ProgramacionServicios getProgramacionServicios() {
        return programacionServicios;
    }

    public void setProgramacionServicios(ProgramacionServicios programacionServicios) {
        this.programacionServicios = programacionServicios;
    }

    public boolean isAsociacionValida() {
        return asociacionValida;
    }

    public void setAsociacionValida(boolean asociacionValida) {
        this.asociacionValida = asociacionValida;
    }
}
