package com.tmIndicadores.controller.servicios;

import com.tmIndicadores.model.dao.ProgramacionDao;
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

    public void addProgramacion(Programacion programacion) {
       programacionDao.addProgramacion(programacion);

    }

    public void deleteProgramacion(Programacion programacion) {
        programacionDao.deleteProgramacion(programacion);
    }


    public void updateProgramacion(Programacion programacion) {
        programacionDao.updateProgramacion(programacion);
    }


    public List<Programacion> getProgramacionbyAttributes(Date fechaInicio, Date fechaFin, String periocidad, String tipologia,String tipoDatos){
        return programacionDao.getProgramacionbyAttributes(fechaInicio,fechaFin,periocidad,tipologia,tipoDatos);
    }

    public List<Programacion> getAll(){
        return programacionDao.getAll();
    }

    public List<Programacion> getProgramacionbyAttributes(Date fechaInicio, Date fechaFin, String periocidad,String tipoDatos){
        return programacionDao.getProgramacionbyAttributes(fechaInicio,fechaFin,periocidad,tipoDatos);
    }

    public List<Programacion> getProgramacionbyAttributes(Date fechaInicio, Date fechaFin){
        return programacionDao.getProgramacionbyAttributes(fechaInicio,fechaFin);
    }

    public List<Programacion> getProgramacionesUltimoMes(String periocidad){
        Date hoy = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hoy);
        calendar.add(Calendar.DAY_OF_YEAR, -30);
        return programacionDao.getProgramacionesUltimoMes(periocidad,calendar.getTime(),hoy);
    }

    public boolean isCuadroAlready(String cuadro){
        return programacionDao.isCuadroAlready(cuadro);
    }

    public List<Programacion> getProgramacionbyFecha(Date fecha){
        return programacionDao.getProgramacionbyFecha(fecha);
    }

    public List<Programacion> getProgramacionbyFechaTipologiaPeriocidad(Date fecha,String tipologia, String periocidad ){
        return programacionDao.getProgramacionbyFechaTipologiaPeriocidad(fecha,tipologia,periocidad);
    }

    public boolean isDEFAlready(Date fecha){
        return  programacionDao.isDEFAlready(fecha);
    }


}
