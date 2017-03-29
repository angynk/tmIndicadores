package com.tmIndicadores.view;

import com.tmIndicadores.controller.Util;
import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.Programacion;
import org.primefaces.model.chart.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import java.util.ArrayList;
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
    private BubbleChartModel puntosBuses;

    private boolean visibleBarBuses;
    private boolean visibleGrafica;
    private boolean visibleLineBuses;
    private boolean visiblePuntosBuses;

    @ManagedProperty(value="#{ChartGenerator}")
    private ChartGenerator chartGenerator;

    @ManagedProperty(value="#{ProgramacionServicios}")
    private ProgramacionServicios programacionServicios;

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
        visiblePuntosBuses =false;
        lineBuses = new LineChartModel();
        puntosBuses = new BubbleChartModel();

    }


    public void generar(){
        if(genracionValida()){
            List<Programacion> programacion=programacionServicios.getProgramacionbyAttributes(fechaInicio,fechaFin,periocidad,tipologia);
            barBuses = chartGenerator.crearGraficaBarraBuses(programacion);
            lineBuses = chartGenerator.crearGraficaLineBuses(programacion);
            puntosBuses =chartGenerator.crearGraficaPuntosBuses(programacion);
            visibleGrafica = true;
            visibleLineBuses = false;
            visibleBarBuses = true;
            visiblePuntosBuses =false;
        }

    }

    private boolean genracionValida() {
        if(fechaInicio!= null && fechaFin!=null && tipologia!=null && periocidad!=null ){
            return true;
        }
        return false;
    }

    public void cambioDeGrafica(ValueChangeEvent event){
        if(grafica!=null){
            grafica = (String) event.getNewValue();
            if(grafica.equals("Barras")){
                visibleBarBuses = true;
                visibleLineBuses = false;
                visiblePuntosBuses =false;

            }else if(grafica.equals("Líneas")){
                visibleBarBuses = false;
                visibleLineBuses = true;
                visiblePuntosBuses =false;

            }else if(grafica.equals("Tendencia")){
                visibleBarBuses = false;
                visibleLineBuses = false;
                visiblePuntosBuses =true;

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


    public void setCambioDeGrafica(String cambioDeGrafica) {
        this.cambioDeGrafica = cambioDeGrafica;
    }

    public LineChartModel getLineBuses() {
        return lineBuses;
    }

    public void setLineBuses(LineChartModel lineBuses) {
        this.lineBuses = lineBuses;
    }

    public BubbleChartModel getPuntosBuses() {
        return puntosBuses;
    }

    public void setPuntosBuses(BubbleChartModel puntosBuses) {
        this.puntosBuses = puntosBuses;
    }

    public boolean isVisiblePuntosBuses() {
        return visiblePuntosBuses;
    }

    public void setVisiblePuntosBuses(boolean visiblePuntosBuses) {
        this.visiblePuntosBuses = visiblePuntosBuses;
    }

    public ProgramacionServicios getProgramacionServicios() {
        return programacionServicios;
    }

    public void setProgramacionServicios(ProgramacionServicios programacionServicios) {
        this.programacionServicios = programacionServicios;
    }
}
