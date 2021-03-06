package com.tmIndicadores.view;


import com.tmIndicadores.controller.ListObject;
import com.tmIndicadores.controller.ModosUtil;
import com.tmIndicadores.controller.ProcessorUtils;
import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.Programacion;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "MPBean")
@ViewScoped
public class ModProgramacionBean {

    private Date fechaInicio;
    private Date fechaFin;

    private String periocidad;
    private String tipologia;
    private String tipoDatos;

    private boolean visibleResumen;
    private List<Programacion> programacionRecords ;
    private List<Programacion> programacionRecordsFiltered ;
    private Programacion selectedProg;

    private String modo;
    private List<ListObject> modos;

    @ManagedProperty(value="#{ProgramacionServicios}")
    private ProgramacionServicios programacionServicios;


    public ModProgramacionBean() {
    }

    @PostConstruct
    public void init(){
        visibleResumen = true;
        fechaFin = new Date();
        tipologia = "DEF";
        periocidad = "HABIL";
        Calendar c = Calendar.getInstance();
        c.setTime(fechaFin);
        c.add(Calendar.MONTH, -6);
        fechaInicio = c.getTime();
        tipoDatos = "D";
        modo = "TRO";
        modos = ModosUtil.cargarListaModos();
        programacionRecords = programacionServicios.getProgramacionbyAttributesWithModo(fechaInicio,fechaFin,periocidad,tipoDatos,modo);
    }



    public void generar(){
        if(genracionValida()){
            programacionRecords = programacionServicios.getProgramacionbyAttributesWithModo(fechaInicio,fechaFin,periocidad,tipoDatos,modo);
            visibleResumen = true;
        }else{
            addMessage(FacesMessage.SEVERITY_INFO,"Complete los datos para generar la grafica", "");
        }
    }

    public void cargarTodo(){
        programacionRecords = programacionServicios.getAll();
        visibleResumen = true;
    }

    public void eliminar(){
        programacionServicios.deleteProgramacion(selectedProg);
//        programacionRecords = programacionServicios.getProgramacionbyAttributes(fechaInicio,fechaFin,periocidad);
      //  refreshModProgramaciones();
        programacionRecords.remove(selectedProg);
        addMessage(FacesMessage.SEVERITY_INFO,"Programación eliminada", "");
       // refreshModProgramaciones();
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


    public void actualizar(){
        programacionServicios.updateProgramacion(selectedProg);
        addMessage(FacesMessage.SEVERITY_INFO,"Programación actualizada", "");
    }

    public void cancelar(){

    }


    private boolean genracionValida() {
        if(fechaInicio!= null && fechaFin!=null && periocidad!=null ){
            return true;
        }
        return false;
    }

    public void inicioReturn(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addMessage(FacesMessage.Severity severity , String summary, String detail) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getPeriocidad() {
        return periocidad;
    }

    public void setPeriocidad(String periocidad) {
        this.periocidad = periocidad;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getTipoDatos() {
        return tipoDatos;
    }

    public void setTipoDatos(String tipoDatos) {
        this.tipoDatos = tipoDatos;
    }

    public boolean isVisibleResumen() {
        return visibleResumen;
    }

    public void setVisibleResumen(boolean visibleResumen) {
        this.visibleResumen = visibleResumen;
    }

    public List<Programacion> getProgramacionRecords() {
        return programacionRecords;
    }

    public void setProgramacionRecords(List<Programacion> programacionRecords) {
        this.programacionRecords = programacionRecords;
    }

    public ProgramacionServicios getProgramacionServicios() {
        return programacionServicios;
    }

    public void setProgramacionServicios(ProgramacionServicios programacionServicios) {
        this.programacionServicios = programacionServicios;
    }

    public Programacion getSelectedProg() {
        return selectedProg;
    }

    public void setSelectedProg(Programacion selectedProg) {
        this.selectedProg = selectedProg;
    }

    public void postProcessXLS(Object document) {
        ProcessorUtils.postProcessXLS(document);
    }

    public List<Programacion> getProgramacionRecordsFiltered() {
        return programacionRecordsFiltered;
    }

    public void setProgramacionRecordsFiltered(List<Programacion> programacionRecordsFiltered) {
        this.programacionRecordsFiltered = programacionRecordsFiltered;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public List<ListObject> getModos() {
        return modos;
    }

    public void setModos(List<ListObject> modos) {
        this.modos = modos;
    }
}
