package com.tmIndicadores.view;

import com.tmIndicadores.controller.Util;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
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

    private List<String> listaPeriocidad;
    private List<String> listaTipologia;
    private List<String> listaTipoGrafica;

    private BarChartModel barBuses;
    private boolean visibleBarBuses;

    public BusesProgramadosBean() {
    }

    @PostConstruct
    public void init(){
        listaPeriocidad = Util.listaDePeriocidad();
        listaTipologia = Util.listaDeTipologia();
        listaTipoGrafica = Util.listaDeTipoGrafica();
        barBuses = new BarChartModel();
        visibleBarBuses = false;
    }


    public void generar(){
        crearGraficaBuses();
        visibleBarBuses = true;
    }

    private void crearGraficaBuses() {
        barBuses = initBarModel();

        barBuses.setTitle("Buses Programados");
        barBuses.setLegendPosition("ne");

        Axis xAxis = barBuses.getAxis(AxisType.X);
        xAxis.setLabel("Fecha");

        Axis yAxis = barBuses.getAxis(AxisType.Y);
        yAxis.setLabel("Buses");
        yAxis.setMin(0);
        yAxis.setMax(200);

    }

    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries busesDEF = new ChartSeries();
        busesDEF.setLabel("Buses");
        busesDEF.set("2004", 120);
        busesDEF.set("2005", 100);
        busesDEF.set("2006", 44);
        busesDEF.set("2007", 150);
        busesDEF.set("2008", 25);


        model.addSeries(busesDEF);

        return model;
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
}
