package com.tmIndicadores.view;

import com.tmIndicadores.controller.servicios.ProgramacionServicios;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "dashBean")
@ViewScoped
public class DashboardBean {

    private String progHabil;
    private String progFestivo;
    private String progSabado;
    private String progTotal;

    @ManagedProperty(value="#{ProgramacionServicios}")
    private ProgramacionServicios programacionServicios;

    public DashboardBean() {
    }

    @PostConstruct
    public void init(){
        int habil = programacionServicios.getProgramacionesUltimoMes("HABIL").size();
        int festivo = programacionServicios.getProgramacionesUltimoMes("FESTIVO").size();
        int sabado = programacionServicios.getProgramacionesUltimoMes("SABADO").size();
        progHabil = habil+"";
        progFestivo = festivo+"";
        progSabado = sabado+"";
        progTotal = habil+festivo+sabado+"";
    }

    public ProgramacionServicios getProgramacionServicios() {
        return programacionServicios;
    }

    public void setProgramacionServicios(ProgramacionServicios programacionServicios) {
        this.programacionServicios = programacionServicios;
    }

    public String getProgHabil() {
        return progHabil;
    }

    public void setProgHabil(String progHabil) {
        this.progHabil = progHabil;
    }

    public String getProgFestivo() {
        return progFestivo;
    }

    public void setProgFestivo(String progFestivo) {
        this.progFestivo = progFestivo;
    }

    public String getProgSabado() {
        return progSabado;
    }

    public void setProgSabado(String progSabado) {
        this.progSabado = progSabado;
    }

    public String getProgTotal() {
        return progTotal;
    }

    public void setProgTotal(String progTotal) {
        this.progTotal = progTotal;
    }
}
