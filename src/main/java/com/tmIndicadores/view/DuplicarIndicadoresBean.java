package com.tmIndicadores.view;

import com.tmIndicadores.controller.DuplicarProgramacionProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "duplicarBean")
@ViewScoped
public class DuplicarIndicadoresBean {

    private String razonProgramacion;
    private String fechas;
    private Date fechaADuplicar;

    @Autowired
    private DuplicarProgramacionProcessor duplicarProgramacionProcessor;

    public DuplicarIndicadoresBean() {
    }

    public void duplicar(){
        //Obtener fechas
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        fechas = ec.getRequestParameterMap().get("fechas");

    }

    public String getRazonProgramacion() {
        return razonProgramacion;
    }

    public void setRazonProgramacion(String razonProgramacion) {
        this.razonProgramacion = razonProgramacion;
    }

    public String getFechas() {
        return fechas;
    }

    public void setFechas(String fechas) {
        this.fechas = fechas;
    }

    public Date getFechaADuplicar() {
        return fechaADuplicar;
    }

    public void setFechaADuplicar(Date fechaADuplicar) {
        this.fechaADuplicar = fechaADuplicar;
    }

    public DuplicarProgramacionProcessor getDuplicarProgramacionProcessor() {
        return duplicarProgramacionProcessor;
    }

    public void setDuplicarProgramacionProcessor(DuplicarProgramacionProcessor duplicarProgramacionProcessor) {
        this.duplicarProgramacionProcessor = duplicarProgramacionProcessor;
    }
}
