package com.tmIndicadores.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "duplicarBean")
@ViewScoped
public class DuplicarIndicadoresBean {

    private String razonProgramacion;
    private List<Date> fechasADuplicar;

    public DuplicarIndicadoresBean() {
    }

    public void duplicar(){

    }

    public String getRazonProgramacion() {
        return razonProgramacion;
    }

    public void setRazonProgramacion(String razonProgramacion) {
        this.razonProgramacion = razonProgramacion;
    }

    public List<Date> getFechasADuplicar() {
        return fechasADuplicar;
    }

    public void setFechasADuplicar(List<Date> fechasADuplicar) {
        this.fechasADuplicar = fechasADuplicar;
    }
}
