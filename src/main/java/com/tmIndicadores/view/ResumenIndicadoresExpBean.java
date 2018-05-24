package com.tmIndicadores.view;

import com.tmIndicadores.controller.ListObject;
import com.tmIndicadores.controller.ModosUtil;
import com.tmIndicadores.controller.ProcessorUtils;
import com.tmIndicadores.controller.Util;
import com.tmIndicadores.controller.servicios.IndicadoresExpServicio;
import com.tmIndicadores.model.entity.Indicadores;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "RIEBean")
@ViewScoped
public class ResumenIndicadoresExpBean {

    private Date fechaInicio;
    private Date fechaFin;

    private String periocidad;
    private String tipologia;
    private String tipoDatos;

    private String modo;
    private List<ListObject> modos;
    private List<ListObject> tipologias;

    private boolean visibleResumen;
    private List<Indicadores> programacionRecords ;
    private List<Indicadores> filteredProgramacionRecords ;



    @ManagedProperty(value="#{IndicadoresExpServicio}")
    private IndicadoresExpServicio indicadoresExpServicio;

    public ResumenIndicadoresExpBean() {
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
        programacionRecords = indicadoresExpServicio.getIndicadoresByFecha(fechaInicio,fechaFin,periocidad,tipologia,tipoDatos, Util.convertirModo(modo));
        cargarListaModos();
        cargarListaTipologiaTroncal();
    }


    private void cargarListaTipologiaTroncal() {
        tipologias = ModosUtil.cargarListaTipologiaTroncal();
    }

    private void cargarListaTipologiaDual() {
        tipologias = ModosUtil.cargarListaTipologiaDual();
    }

    public void cargarListaModos(){
        modos = ModosUtil.cargarListaModos();
    }


    public void updateTipologias(){
        if(modo.equals("TRO")){
            cargarListaTipologiaTroncal();
        }else{
            cargarListaTipologiaDual();
        }
    }

    public void generar(){
        if(genracionValida()){
            programacionRecords = indicadoresExpServicio.getIndicadoresByFecha(fechaInicio,fechaFin,periocidad,tipologia,tipoDatos, Util.convertirModo(modo));
            visibleResumen = true;
        }else{
            addMessage(FacesMessage.SEVERITY_INFO,"Complete los datos para generar la grafica", "");
        }
    }

    public void cargarTodo(){
        programacionRecords = indicadoresExpServicio.obtenerTodo();
        visibleResumen = true;
    }

    private boolean genracionValida() {
        if(fechaInicio!= null && fechaFin!=null && tipologia!=null && periocidad!=null ){
            return true;
        }
        return false;
    }

    public void addMessage(FacesMessage.Severity severity , String summary, String detail) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void postProcessXLS(Object document){
        ProcessorUtils.postProcessXLS(document);
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

    public List<ListObject> getTipologias() {
        return tipologias;
    }

    public void setTipologias(List<ListObject> tipologias) {
        this.tipologias = tipologias;
    }

    public boolean isVisibleResumen() {
        return visibleResumen;
    }

    public void setVisibleResumen(boolean visibleResumen) {
        this.visibleResumen = visibleResumen;
    }

    public List<Indicadores> getProgramacionRecords() {
        return programacionRecords;
    }

    public void setProgramacionRecords(List<Indicadores> programacionRecords) {
        this.programacionRecords = programacionRecords;
    }

    public List<Indicadores> getFilteredProgramacionRecords() {
        return filteredProgramacionRecords;
    }

    public void setFilteredProgramacionRecords(List<Indicadores> filteredProgramacionRecords) {
        this.filteredProgramacionRecords = filteredProgramacionRecords;
    }

    public IndicadoresExpServicio getIndicadoresExpServicio() {
        return indicadoresExpServicio;
    }

    public void setIndicadoresExpServicio(IndicadoresExpServicio indicadoresExpServicio) {
        this.indicadoresExpServicio = indicadoresExpServicio;
    }
}
