package com.tmIndicadores.view;

import com.google.gson.Gson;
import com.tmIndicadores.controller.Util;
import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.Programacion;
import org.primefaces.model.chart.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
    private String representacion;
    private String titulo;
    private String tituloEjeX;

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

    private String chartSeries;
    private String chartSeriesForLine;
    private String chartCategoriesForLine;
    private String chartSeriesForBar;
    private String chartCategoriesForBar;

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
            generarChartSeries();
            visibleGrafica = true;
        }else{
            addMessage(FacesMessage.SEVERITY_INFO,"Complete los datos para generar la grafica", "");
        }


    }
    public void addMessage(FacesMessage.Severity severity , String summary, String detail) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
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

    public String getChartSeries() {
//        if(genracionValida()){
            //generarChartSeries();
            if (chartSeries != null )
            return chartSeries;
//       }
        return "[]";
    }

    public void generarChartSeries(){
        List<Programacion> programacion = programacionServicios.getProgramacionbyAttributes(fechaInicio,fechaFin,periocidad,tipologia);
        generarRegressionChart(programacion);
        generarLineasChart(programacion);
        generarBarrasChart(programacion);
    }

    public void generarLineasChart(List<Programacion> programacion){
        List<Series> series = new ArrayList<Series>();
        List<List<Object>> dataPoints = new ArrayList<>();
        List<List<String>> categorias = new ArrayList<>();
        for(Programacion prog: programacion){
            dataPoints.add(new ArrayList<Object>(Arrays.asList((double)prog.getBuses())));
            categorias.add(new ArrayList<String>(Arrays.asList(formatoFecha(prog.getFecha()))));
        }
        titulo = "Gráfica Buses Programados";
        tituloEjeX = "Número de Buses";
        series.add(new Series("Buses", dataPoints));
        setChartSeriesForLine(new Gson().toJson(series));
        setChartCategoriesForLine(new Gson().toJson(categorias));
    }

    private String formatoFecha(Date fecha) {
        SimpleDateFormat   format = new SimpleDateFormat("yyyy-MM-dd");
       return format.format(fecha);
    }

    public void generarBarrasChart(List<Programacion> programacion){
            List<String> categorias = new ArrayList<String>();
            categorias.add("Jan");
            categorias.add("Feb");
            categorias.add("Mar");
            categorias.add("Apr");
            setChartCategoriesForBar(new Gson().toJson(categorias));

        List<Series> series = new ArrayList<Series>();
        List<List<Double>> dataPoints = new ArrayList<>();
        dataPoints.add(new ArrayList<Double>(Arrays.asList(49.9)));
        dataPoints.add(new ArrayList<Double>(Arrays.asList(71.5)));
        dataPoints.add(new ArrayList<Double>(Arrays.asList(106.4)));
        dataPoints.add(new ArrayList<Double>(Arrays.asList(129.2)));
//        series.add(new Series("Tokyo", dataPoints));
        setChartSeriesForBar(new Gson().toJson(series));

    }


    public void generarRegressionChart(List<Programacion> programacion){
        List<Series> series = new ArrayList<Series>();

        List<List<Double>> dataPoints = new ArrayList<>();
        List<Double> point1= new ArrayList<>();
        List<Double> point2= new ArrayList<>();

        point1.add( 0.0);
        point1.add(1.11);

        point2.add( 5.0);
        point2.add( 4.51);

        dataPoints.add(point1);
        dataPoints.add(point2);


        List<List<Double>> dataRegressionLine = new ArrayList<>();
        List<Double> point= new ArrayList<Double>(Arrays.asList(1.0));
        List<Double> point7= new ArrayList<Double>(Arrays.asList(1.5));
        List<Double> point3= new ArrayList<Double>(Arrays.asList(2.8));
        List<Double> point4= new ArrayList<Double>(Arrays.asList(3.5));
        List<Double> point5= new ArrayList<Double>(Arrays.asList(3.9));
        List<Double> point6= new ArrayList<Double>(Arrays.asList(4.2));


        dataRegressionLine.add(point);
        dataRegressionLine.add(point7);
        dataRegressionLine.add(point3);
        dataRegressionLine.add(point4);
        dataRegressionLine.add(point5);
        dataRegressionLine.add(point6);

        series.add(new Series("Regression Line", dataPoints,"line"));
        series.add(new Series("Observations", dataRegressionLine,"scatter"));

        setChartSeries(new Gson().toJson(series));
    }

    public void setChartSeries(String chartSeries) {
        this.chartSeries = chartSeries;
    }


    public String getChartSeriesForLine() {
//        if(genracionValida()){
            return chartSeriesForLine;
//        }
//        return "[]";
    }

    public void setChartSeriesForLine(String chartSeriesForLine) {
        this.chartSeriesForLine = chartSeriesForLine;
    }

    public String getChartSeriesForBar() {
//        if(genracionValida()){
            return chartSeriesForBar;
//        }
//        return "[]";
    }

    public void setChartSeriesForBar(String chartSeriesForBar) {
        this.chartSeriesForBar = chartSeriesForBar;
    }

    public String getChartCategoriesForBar() {

//        if(genracionValida()){
            return chartCategoriesForBar;
//        }
//        return "[]";
    }

    public void setChartCategoriesForBar(String chartCategoriesForBar) {
        this.chartCategoriesForBar = chartCategoriesForBar;
    }

    public String getRepresentacion() {
        return representacion;
    }

    public void setRepresentacion(String representacion) {
        this.representacion = representacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTituloEjeX() {
        return tituloEjeX;
    }

    public void setTituloEjeX(String tituloEjeX) {
        this.tituloEjeX = tituloEjeX;
    }

    public String getChartCategoriesForLine() {
        return chartCategoriesForLine;
    }

    public void setChartCategoriesForLine(String chartCategoriesForLine) {
        this.chartCategoriesForLine = chartCategoriesForLine;
    }
}
