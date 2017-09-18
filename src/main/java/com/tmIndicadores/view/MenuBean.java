package com.tmIndicadores.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean(name="menu")
@SessionScoped
public class MenuBean {

    public void refreshBusesProgramados(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/busesChart.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshResumen(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/resumenIndicadores.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshIdiProgResumen(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/resumenIndicadoresProgramacion.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshCargaIndicadores(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/cargarIdicadores.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshDuplicarProgramacion(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/duplicarProgramacion.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshModProgramaciones(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/modProgramacion.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
