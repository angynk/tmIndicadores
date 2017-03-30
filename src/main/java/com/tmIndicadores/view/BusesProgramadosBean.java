package com.tmIndicadores.view;

import com.google.gson.Gson;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "bPBean")
@ViewScoped
public class BusesProgramadosBean {

    private String fechaInicio;
    private String fechaFin;

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

    private String chartSeries;
    private String chartSeriesForLine;
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
        visibleGrafica = true;
//        if(genracionValida()){
//            List<Programacion> programacion=programacionServicios.getProgramacionbyAttributes(fechaInicio,fechaFin,periocidad,tipologia);
//            barBuses = chartGenerator.crearGraficaBarraBuses(programacion);
//            lineBuses = chartGenerator.crearGraficaLineBuses(programacion);
//            puntosBuses =chartGenerator.crearGraficaPuntosBuses(programacion);
//            visibleGrafica = true;
//            visibleLineBuses = false;
//            visibleBarBuses = true;
//            visiblePuntosBuses =false;
//        }

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

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getChartSeries() {
        generarChartSeries();
        return chartSeries;
    }

    public void generarChartSeries(){
        generarRegressionChart();
        generarLineasChart();
        generarBarrasChart();
    }

    public void generarLineasChart(){
        List<Series> series = new ArrayList<Series>();
        List<List<Double>> dataPoints = new ArrayList<>();
        dataPoints.add(new ArrayList<Double>(Arrays.asList(43934.0)));
        dataPoints.add(new ArrayList<Double>(Arrays.asList(52503.0)));
        dataPoints.add(new ArrayList<Double>(Arrays.asList(57177.0)));
        dataPoints.add(new ArrayList<Double>(Arrays.asList(69658.0)));
        dataPoints.add(new ArrayList<Double>(Arrays.asList(97031.0)));
        dataPoints.add(new ArrayList<Double>(Arrays.asList(119931.0)));
        dataPoints.add(new ArrayList<Double>(Arrays.asList(137133.0)));
        series.add(new Series("Instalacion", dataPoints));
        setChartSeriesForLine(new Gson().toJson(series));
    }

    public void generarBarrasChart(){
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
        series.add(new Series("Tokyo", dataPoints));
        setChartSeriesForBar(new Gson().toJson(series));

    }


    public void generarRegressionChart(){
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
        return chartSeriesForLine;
    }

    public void setChartSeriesForLine(String chartSeriesForLine) {
        this.chartSeriesForLine = chartSeriesForLine;
    }

    public String getChartSeriesForBar() {
        return chartSeriesForBar;
    }

    public void setChartSeriesForBar(String chartSeriesForBar) {
        this.chartSeriesForBar = chartSeriesForBar;
    }

    public String getChartCategoriesForBar() {
        return chartCategoriesForBar;
    }

    public void setChartCategoriesForBar(String chartCategoriesForBar) {
        this.chartCategoriesForBar = chartCategoriesForBar;
    }
}
