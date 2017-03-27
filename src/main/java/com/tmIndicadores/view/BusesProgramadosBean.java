package com.tmIndicadores.view;

import com.tmIndicadores.controller.Util;
import org.primefaces.model.chart.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "bPBean")
@ViewScoped
public class BusesProgramadosBean {

    private Date fechaInicio;
    private Date fechaFin;

    private String periocidad;
    private String tipologia;
    private String tipoGrafica;
    private String grafica;

    private String cambioDeGrafica;

    private List<String> listaPeriocidad;
    private List<String> listaTipologia;
    private List<String> listaTipoGrafica;

    private BarChartModel barBuses;
    private LineChartModel lineBuses;

    private boolean visibleBarBuses;
    private boolean visibleGrafica;
    private boolean visibleLineBuses;

    @ManagedProperty(value="#{ChartGenerator}")
    private ChartGenerator chartGenerator;

    public BusesProgramadosBean() {
    }

    @PostConstruct
    public void init(){
        listaPeriocidad = Util.listaDePeriocidad();
        listaTipologia = Util.listaDeTipologia();
        listaTipoGrafica = Util.listaDeTipoGrafica();
        barBuses = new BarChartModel();
        visibleBarBuses = false;
        visibleGrafica = false;
        lineBuses = new LineChartModel();
    }


    public void generar(){
        barBuses = chartGenerator.crearGraficaBarraBuses();
        lineBuses = chartGenerator.crearGraficaLineBuses();
        visibleGrafica = true;
        visibleLineBuses = false;
        visibleBarBuses = true;
    }

    public void cambioDeGrafica(ValueChangeEvent event){
        if(grafica!=null){
            grafica = (String) event.getNewValue();
            if(grafica.equals("Barras")){
                visibleBarBuses = true;
                visibleLineBuses = false;

            }else if(grafica.equals("Líneas")){
                visibleBarBuses = false;
                visibleLineBuses = true;

            }else if(grafica.equals("Tendencia")){
                visibleBarBuses = false;
                visibleLineBuses = false;

            }
        }


    }

    public void changeEvent() {
        System.out.println("entre");
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

    public String getTipoGrafica() {
        return tipoGrafica;
    }

    public void setTipoGrafica(String tipoGrafica) {
        this.tipoGrafica = tipoGrafica;
    }

    public List<String> getListaPeriocidad() {
        return listaPeriocidad;
    }

    public void setListaPeriocidad(List<String> listaPeriocidad) {
        this.listaPeriocidad = listaPeriocidad;
    }

    public List<String> getListaTipologia() {
        return listaTipologia;
    }

    public void setListaTipologia(List<String> listaTipologia) {
        this.listaTipologia = listaTipologia;
    }

    public List<String> getListaTipoGrafica() {
        return listaTipoGrafica;
    }

    public void setListaTipoGrafica(List<String> listaTipoGrafica) {
        this.listaTipoGrafica = listaTipoGrafica;
    }

    public BarChartModel getBarBuses() {
        return barBuses;
    }

    public void setBarBuses(BarChartModel barBuses) {
        this.barBuses = barBuses;
    }

    public boolean isVisibleBarBuses() {
        return visibleBarBuses;
    }

    public void setVisibleBarBuses(boolean visibleBarBuses) {
        this.visibleBarBuses = visibleBarBuses;
    }

    public String getGrafica() {
        return grafica;
    }

    public void setGrafica(String grafica) {
        this.grafica = grafica;
    }

    public boolean isVisibleGrafica() {
        return visibleGrafica;
    }

    public void setVisibleGrafica(boolean visibleGrafica) {
        this.visibleGrafica = visibleGrafica;
    }



    public boolean isVisibleLineBuses() {
        return visibleLineBuses;
    }

    public void setVisibleLineBuses(boolean visibleLineBuses) {
        this.visibleLineBuses = visibleLineBuses;
    }

    public ChartGenerator getChartGenerator() {
        return chartGenerator;
    }

    public void setChartGenerator(ChartGenerator chartGenerator) {
        this.chartGenerator = chartGenerator;
    }

//    public String getCambioDeGrafica() {
//        if(grafica!=null){
//            if(grafica.equals("Barras")){
//                visibleBarBuses = true;
//                visibleLineBuses = false;
//
//            }else if(grafica.equals("Líneas")){
//                visibleBarBuses = false;
//                visibleLineBuses = true;
//
//            }else if(grafica.equals("Tendencia")){
//                visibleBarBuses = false;
//                visibleLineBuses = false;
//
//            }
//        }
//
//        return cambioDeGrafica;
//    }

    public void setCambioDeGrafica(String cambioDeGrafica) {
        this.cambioDeGrafica = cambioDeGrafica;
    }

    public LineChartModel getLineBuses() {
        return lineBuses;
    }

    public void setLineBuses(LineChartModel lineBuses) {
        this.lineBuses = lineBuses;
    }
}
