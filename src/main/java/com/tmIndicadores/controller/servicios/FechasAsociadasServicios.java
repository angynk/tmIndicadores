package com.tmIndicadores.controller.servicios;

import com.tmIndicadores.model.dao.FechaAsociadaDao;
import com.tmIndicadores.model.entity.FechaAsociada;
import com.tmIndicadores.model.entity.Programacion;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service("FechasAsociadasServicios")
public class FechasAsociadasServicios {

    @Autowired
    private FechaAsociadaDao fechaAsociadaDao;

    public void addFechaAsociada(FechaAsociada fechaAsociada) {
        fechaAsociadaDao.addFechaAsociada(fechaAsociada);
    }

    public void deleteFechaAsociada(FechaAsociada fechaAsociada) {
        fechaAsociadaDao.deleteFechaAsociada(fechaAsociada);
    }


    public void updateFechaAsociada(FechaAsociada fechaAsociada) {
        fechaAsociadaDao.updateFechaAsociada(fechaAsociada);
    }

    public List<FechaAsociada> getFechasAsociadasProgramacion(Programacion programacion){
        return fechaAsociadaDao.getFechasAsociadasProgramacion(programacion);
    }
}
