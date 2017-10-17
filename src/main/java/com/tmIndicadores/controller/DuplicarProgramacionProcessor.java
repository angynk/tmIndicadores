package com.tmIndicadores.controller;

import com.tmIndicadores.controller.servicios.FechasAsociadasServicios;
import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.FechaAsociada;
import com.tmIndicadores.model.entity.Programacion;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("DuplicarProcessor")
public class DuplicarProgramacionProcessor {

    @Autowired
    private ProgramacionServicios programacionServicios;
    @Autowired
    private FechasAsociadasServicios fechasAsociadasServicios;
    private boolean duplicacionValida = true;

    private List<LogDatos> logDatos;
    private static Logger log = Logger.getLogger(IndicadoresGoalProcessor.class);

    public List<Programacion> getAll(){
        return programacionServicios.getAll();
    }

    public Programacion getProgramacionbyID(long id){
        return programacionServicios.getProgramacionbyID(id);
    }

    public List<Programacion> getAllProgramacionbyModo(String modo){
        return programacionServicios.getAllProgramacionbyModo(modo);
    }

    public List<LogDatos> duplicarProgramacion(Date fecha, String fechas,String modo){
        logDatos = new ArrayList<>();
        duplicacionValida = true;
        logDatos.add(new LogDatos("<<Inicio Duplicacion programacion>>", TipoLog.INFO));
        List<Programacion> programaciones = encontrarProgramacionActual(fecha,modo);
        if(programaciones.size()>0){
            List<Date> fechasRecords = ProcessorUtils.convertirAfechas(fechas);
            if(fechasRecords.size()>0){
                duplicarDatosProgramacion(fechasRecords,programaciones);
            }else{
                duplicacionValida = false;
                logDatos.add(new LogDatos("El formato de Fechas es Incorrecto",TipoLog.ERROR));
            }
        }else{
            duplicacionValida = false;
            logDatos.add(new LogDatos("No existe ninguna Programacion Para la Fecha definida",TipoLog.ERROR));
        }
        logDatos.add(new LogDatos("<<Fin Duplicacion programacion>>", TipoLog.INFO));
        return logDatos;
    }

    private void duplicarDatosProgramacion(List<Date> fechasRecords, List<Programacion> programaciones) {
        for(int x=0;x<fechasRecords.size();x++){
            Date fecha =fechasRecords.get(x);
            if(fechaNoTieneProgramacion(fecha)){
                for(Programacion prog:programaciones){
                   Programacion nueva = insertarProgramacion(fecha,prog);
                    logDatos.add(new LogDatos("Programacion Duplicada ("+fecha.toString()+") del Cuadro: "+prog.getCuadro()+" ,Tipo Dia: "+prog.getPeriodicidad()
                            +" ,Tipologia: "+prog.getTipologia(), TipoLog.INFO));
                    asociarFechasAProgramacion(fechasRecords,nueva);
                }
                break;
            }else{
                logDatos.add(new LogDatos("Programacion No Duplicada ("+fecha.toString()+"), para esa " +
                        "fecha ya hay una programación asociada ", TipoLog.ERROR));
                duplicacionValida = false;
            }

        }
    }

    private void asociarFechasAProgramacion(List<Date> fechasRecords, Programacion nueva) {
        for(int y=0;y<fechasRecords.size();y++){
            Date fecha= fechasRecords.get(y);
            FechaAsociada fechaAsociada = new FechaAsociada();
            fechaAsociada.setFecha(fecha);
            fechaAsociada.setProgramacion(nueva);
            fechasAsociadasServicios.addFechaAsociada(fechaAsociada);
            logDatos.add(new LogDatos("Programacion  ("+nueva.getCuadro()+") Asociada a fecha: "+fecha.toString(), TipoLog.INFO));
        }
    }

    private boolean fechaNoTieneProgramacion(Date fecha) {
        List<Programacion> programacionbyFecha = programacionServicios.getProgramacionbyFecha(fecha);
        if(programacionbyFecha.size()>0){
            return false;
        }
        return true;

    }


    private Programacion insertarProgramacion(Date fecha, Programacion prog) {
        Programacion nuevaProgramacion = transpasarDatosObjetoProgramacion(prog);
        nuevaProgramacion.setFecha(fecha);
        programacionServicios.addProgramacion(nuevaProgramacion);
        return nuevaProgramacion;
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
        nuevo.setFechaDuplicada(prog.getFecha());
        nuevo.setModo(prog.getModo());
        nuevo.setTiempoVacio(prog.getTiempoVacio());
        nuevo.setHorasPorBuses(prog.getHorasPorBuses());
        nuevo.setVelocidadComercial(prog.getVelocidadComercial());
        nuevo.setNumCambioLinea(prog.getNumCambioLinea());
        nuevo.setFechaPadre(encontrarFechaPadre(prog));
        return nuevo;
    }

    private Date encontrarFechaPadre(Programacion prog) {

        if(prog.getTipoProgramacion().equals("N")){
            return prog.getFecha();
        }else{
            Programacion nuevaProg = programacionServicios.getProgramacionbyFechaModoTipo(prog.getFechaDuplicada(), prog.getModo(), prog.getTipologia());
            if(nuevaProg!=null){
                while(!nuevaProg.getTipoProgramacion().equals("N")){
                    nuevaProg = programacionServicios.getProgramacionbyFechaModoTipo(nuevaProg.getFechaDuplicada(), nuevaProg.getModo(), nuevaProg.getTipologia());
                }
                return nuevaProg.getFecha();
            }

        }

        return prog.getFecha();

    }


    private  List<Programacion> encontrarProgramacionActual(Date fechaProg,String modo) {
       return programacionServicios.getProgramacionbyFecha(fechaProg,modo);
    }

    public boolean isDuplicacionValida() {
        return duplicacionValida;
    }

    public void setDuplicacionValida(boolean duplicacionValida) {
        this.duplicacionValida = duplicacionValida;
    }
}
