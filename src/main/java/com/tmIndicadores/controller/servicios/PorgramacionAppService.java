package com.tmIndicadores.controller.servicios;

import com.tmIndicadores.model.entity.Programacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name="appService", eager = true)
@Service
@ApplicationScoped
public class PorgramacionAppService {

    @Autowired
    private ProgramacionServicios programacionServicios;


    public PorgramacionAppService() {
    }

    public Programacion getProgramacionbyID(long id){
        return programacionServicios.getProgramacionbyID(id);
    }

    public ProgramacionServicios getProgramacionServicios() {
        return programacionServicios;
    }

    public void setProgramacionServicios(ProgramacionServicios programacionServicios) {
        this.programacionServicios = programacionServicios;
    }
}
