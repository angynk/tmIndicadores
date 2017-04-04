package com.tmIndicadores.controller.servicios;

import com.tmIndicadores.model.dao.ProgramacionDao;
import com.tmIndicadores.model.entity.Programacion;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
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


    public List<Programacion> getProgramacionbyAttributes(Date fechaInicio, Date fechaFin, String periocidad, String tipologia){
       return programacionDao.getProgramacionbyAttributes(fechaInicio,fechaFin,periocidad,tipologia);
    }


}
