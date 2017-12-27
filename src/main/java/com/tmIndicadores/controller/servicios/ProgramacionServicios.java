package com.tmIndicadores.controller.servicios;

import com.tmIndicadores.model.dao.FechaAsociadaDao;
import com.tmIndicadores.model.dao.ProgramacionDao;
import com.tmIndicadores.model.entity.FechaAsociada;
import com.tmIndicadores.model.entity.Programacion;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("ProgramacionServicios")
public class ProgramacionServicios {

    @Autowired
    private ProgramacionDao programacionDao;

    @Autowired
    private FechaAsociadaDao fechaAsociadaDao;

    public void addProgramacion(Programacion programacion) {
       programacionDao.addProgramacion(programacion);

    }

    public Programacion getProgramacionbyID(long id){
        return programacionDao.getProgramacionbyID(id);
    }

    public void deleteProgramacion(Programacion programacion) {
        programacionDao.deleteProgramacion(programacion);
    }


    public void updateProgramacion(Programacion programacion) {
        programacionDao.updateProgramacion(programacion);
    }

    public List<Programacion> getAllProgramacionbyModo(String modo){
        return programacionDao.getAllProgramacionbyModo(modo);
    }

    public List<Programacion> getAllProgramacionbyModo(String modo,String tipologia){
        return programacionDao.getAllProgramacionbyModo(modo,tipologia);
    }

    public List<Programacion> getProgramacionbyAttributes(Date fechaInicio, Date fechaFin, String periocidad, String tipologia,String tipoDatos){
        return programacionDao.getProgramacionbyAttributes(fechaInicio,fechaFin,periocidad,tipologia,tipoDatos);
    }

    public List<Programacion> getProgramacionBaseForReport(Date fechaInicio, Date fechaFin, String modo,String tipologia){
        return programacionDao.getProgramacionBaseForReport(fechaInicio,fechaFin,modo,tipologia);
    }

    public List<Programacion> getAll(){
        return programacionDao.getAll();
    }

    public List<Programacion> getProgramacionbyAttributesWithModo(Date fechaInicio, Date fechaFin, String periocidad,String tipoDatos,String modo){
        return programacionDao.getProgramacionbyAttributesWithModo(fechaInicio,fechaFin,periocidad,tipoDatos,modo);
    }

    public List<Programacion> getProgramacionbyAttributes(Date fechaInicio, Date fechaFin){
        return programacionDao.getProgramacionbyAttributes(fechaInicio,fechaFin);
    }

    public List<Programacion> getProgramacionesUltimoMes(String periocidad,Date fechaUltimaProg,String tipologia){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaUltimaProg);
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        return programacionDao.getProgramacionesUltimoMes(periocidad,calendar.getTime(),fechaUltimaProg,tipologia);
    }

    public Date getLastProgramacionFecha(String modo, String tipologia,String periodicidad) {
        return programacionDao.getLastProgramacionFecha(modo,tipologia,periodicidad);
    }

    public Date getLastProgramacionFecha(String modo, String tipologia) {
        return programacionDao.getLastProgramacionFecha(modo,tipologia);
    }

    public boolean isCuadroAlready(String cuadro){
        return programacionDao.isCuadroAlready(cuadro);
    }

    public List<Programacion> getProgramacionbyFecha(Date fecha,String modo){
        return programacionDao.getProgramacionbyFecha(fecha,modo);
    }

    public Programacion getProgramacionbyFechaModoTipo(Date fecha,String modo,String tipologia){
        return programacionDao.getProgramacionbyFechaModoTipo(fecha,modo,tipologia);
    }

    public List<Programacion> getProgramacionbyFechaModo(Date fecha,String modo){
        return programacionDao.getProgramacionbyFechaModo(fecha,modo);
    }

    public List<Programacion> getProgramacionbyFechaTipologiaPeriocidad(Date fecha,String tipologia, String periocidad ){
        return programacionDao.getProgramacionbyFechaTipologiaPeriocidad(fecha,tipologia,periocidad);
    }

    public boolean isDEFAlready(Date fecha,String tipoDEF){
        return  programacionDao.isDEFAlready(fecha,tipoDEF);
    }

    public Programacion getDEFAlready(Date fecha,String tipoDEF){
        return programacionDao.getDEFAlready(fecha,tipoDEF);
    }


    public List<FechaAsociada> getFechasAsociadas(Date date) {

        return fechaAsociadaDao.getFechasAsociadas(date);
    }

    public void deleteFechaAsociada(FechaAsociada fechaAsociada) {
        fechaAsociadaDao.deleteFechaAsociada(fechaAsociada);
    }

    public void addFechaAsociada(FechaAsociada fechaAsociada) {
        fechaAsociadaDao.addFechaAsociada(fechaAsociada);
    }
}
