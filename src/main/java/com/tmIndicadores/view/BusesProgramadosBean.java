package com.tmIndicadores.view;

import com.google.gson.Gson;
import com.tmIndicadores.controller.ListObject;
import com.tmIndicadores.controller.ModosUtil;
import com.tmIndicadores.controller.Util;
import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.Programacion;
import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.io.IOException;
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
    private String indicador;
    private String grafica;
    private String representacion;
    private String titulo;
    private String tituloSabado;
    private String tituloFestivo;
    private String tituloEjeX;
    private String tipoDatos;

    private String modo;
    private List<ListObject> modos;
    private List<ListObject> tipologias;

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
    private String chartSeriesSabado;
    private String chartSeriesFestivo;
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
        tipologia = "ART";
        modo = "TRO";
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
            generarChartSeries();
            visibleGrafica = true;
            grafica = "Seleccione la grafica";
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
            if (chartSeries != null )
            return chartSeries;
        return "[]";
    }

    public void generarChartSeries(){
        if(!periocidad.equals("TODOS")){
            List<Programacion> programacion = programacionServicios.getProgramacionbyAttributes(fechaInicio,fechaFin,periocidad,tipologia,tipoDatos);
            List<Series> series = generarRegressionChart(programacion,periocidad);
            setChartSeries(new Gson().toJson(series));
            generarLineasChart(programacion);
            generarBarrasChart(programacion,indicador);
        }else{
            List<Programacion> programacionHabil = programacionServicios.getProgramacionbyAttributes(fechaInicio,fechaFin,"HABIL",tipologia,tipoDatos);
            List<Programacion> programacionSabado = programacionServicios.getProgramacionbyAttributes(fechaInicio,fechaFin,"SABADO",tipologia,tipoDatos);
            List<Programacion> programacionFestivo = programacionServicios.getProgramacionbyAttributes(fechaInicio,fechaFin,"FESTIVO",tipologia,tipoDatos);
            generarLineasChartPara(programacionHabil,programacionSabado,programacionFestivo);
            generarBarrasChartPara(programacionHabil,programacionSabado,programacionFestivo);
            List<Series> series = generarRegressionChart(programacionHabil,"HABIL");
            setChartSeries(new Gson().toJson(series));
            List<Series> seriesS = generarRegressionChart(programacionSabado,"SABADO");
            tituloSabado = definirTituloGrafica(indicador)+" - Sabado";
            setChartSeriesSabado(new Gson().toJson(seriesS));
            List<Series> seriesF = generarRegressionChart(programacionFestivo,"FESTIVO");
            tituloFestivo = definirTituloGrafica(indicador)+" - Festivo";
            titulo = definirTituloGrafica(indicador);
            setChartSeriesFestivo(new Gson().toJson(seriesF));
        }

    }

    public void generarLineasChartPara(List<Programacion> programacionHabil, List<Programacion> programacionSabado, List<Programacion> programacionFestivo){
        titulo = definirTituloGrafica(indicador);
        tituloEjeX = definirTituloX(indicador);
        List<Series> series = new ArrayList<Series>();
        series.add(transformarASerieParaLineas(programacionHabil,"Habil",indicador));
        series.add(transformarASerieParaLineas(programacionSabado,"Sabado",indicador));
        series.add(transformarASerieParaLineas(programacionFestivo,"Festivo",indicador));
        setChartSeriesForLine(new Gson().toJson(series));
    }

    public void generarBarrasChartPara(List<Programacion> programacionHabil, List<Programacion> programacionSabado, List<Programacion> programacionFestivo){
        titulo = definirTituloGrafica(indicador);
        tituloEjeX = definirTituloX(indicador);
        List<Series> series = new ArrayList<Series>();
        series.add(transformarASerieParaLineas(programacionHabil,"Habil",indicador));
        series.add(transformarASerieParaLineas(programacionSabado,"Sabado",indicador));
        series.add(transformarASerieParaLineas(programacionFestivo,"Festivo",indicador));
        setChartSeriesForBar(new Gson().toJson(series));
    }


    public void generarLineasChart(List<Programacion> programacion){
        titulo = definirTituloGrafica(indicador);
        tituloEjeX = definirTituloX(indicador);
        List<Series> series = new ArrayList<Series>();
        Series serie = transformarASerieParaLineas(programacion,periocidad,indicador);
        series.add(serie);
        setChartSeriesForLine(new Gson().toJson(series));
    }

    private String definirTituloGrafica(String tipoIndicador) {
        String titulo = "Gráfica ";
        if(tipoIndicador.equals(IndicadorEnum.NUMERO_BUSES.toString())){
            titulo = titulo +IndicadorEnum.NUMERO_BUSES.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.KM_COMERCIALES.toString()) ){
            titulo = titulo +IndicadorEnum.KM_COMERCIALES.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.KM_VACIO.toString()) ){
            titulo = titulo +IndicadorEnum.KM_VACIO.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.EXP_COMERCIAL.toString()) ){
            titulo = titulo +IndicadorEnum.EXP_COMERCIAL.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.POR_VACIOS.toString()) ){
            titulo = titulo +IndicadorEnum.POR_VACIOS.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.LINEA_CARGADA.toString()) ){
            titulo = titulo +IndicadorEnum.LINEA_CARGADA.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.VELOCIDAD_COMERCIAL.toString()) ){
            titulo = titulo +IndicadorEnum.VELOCIDAD_COMERCIAL.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.HORAS_BUSES.toString()) ){
            titulo = titulo +IndicadorEnum.HORAS_BUSES.getNombre();
        }
        return titulo;
    }

    private String definirTituloX(String tipoIndicador) {
        String titulo = "";
        if(tipoIndicador.equals(IndicadorEnum.NUMERO_BUSES.toString())){
            titulo = titulo +IndicadorEnum.TI_NUMERO_BUSES.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.KM_COMERCIALES.toString()) ){
            titulo = titulo +IndicadorEnum.TI_KM_COMERCIALES.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.KM_VACIO.toString()) ){
            titulo = titulo +IndicadorEnum.TI_KM_VACIO.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.EXP_COMERCIAL.toString()) ){
            titulo = titulo +IndicadorEnum.TI_EXP_COMERCIAL.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.POR_VACIOS.toString()) ){
            titulo = titulo +IndicadorEnum.TI_POR_VACIOS.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.LINEA_CARGADA.toString()) ){
            titulo = titulo +IndicadorEnum.TI_LINEA_CARGADA.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.VELOCIDAD_COMERCIAL.toString()) ){
            titulo = titulo +IndicadorEnum.TI_VELOCIDAD_COMERCIAL.getNombre();
        }else if ( tipoIndicador.equals(IndicadorEnum.HORAS_BUSES.toString()) ){
            titulo = titulo +IndicadorEnum.TI_HORAS_BUSES.getNombre();
        }
        return titulo;
    }

    public Series transformarASerieParaLineas(List<Programacion> programacion,String nombre,String tipoIndicador){
       List<List<Object>> dataPoints = new ArrayList<>();
       Series serie = null;
       for(Programacion prog: programacion){
           Object valor = null;
           if(tipoIndicador.equals(IndicadorEnum.NUMERO_BUSES.toString())){
               valor = (double)prog.getBuses();
           }else if ( tipoIndicador.equals(IndicadorEnum.KM_COMERCIALES.toString()) ){
               valor = prog.getKmComercialFin();
           }else if ( tipoIndicador.equals(IndicadorEnum.KM_VACIO.toString()) ){
               valor =  prog.getKmVacioFin();
           }else if ( tipoIndicador.equals(IndicadorEnum.EXP_COMERCIAL.toString()) ){
               valor = (double) prog.getExpedicionComercial();
           }else if ( tipoIndicador.equals(IndicadorEnum.POR_VACIOS.toString()) ){
               valor = prog.getPorcentajeVacioFinal()*100;
           }else if ( tipoIndicador.equals(IndicadorEnum.LINEA_CARGADA.toString()) ){
               valor = prog.getLineasCargadas();
           }else if ( tipoIndicador.equals(IndicadorEnum.VELOCIDAD_COMERCIAL.toString()) ){
               valor = prog.getVelocidadComercial();
           }else if ( tipoIndicador.equals(IndicadorEnum.HORAS_BUSES.toString()) ){
               valor = prog.getHorasPorBuses();
           }
           dataPoints.add(new ArrayList<Object>(Arrays.asList(prog.getFecha(),valor)));
       }
       serie = new Series(nombre, dataPoints);
       return serie;
   }


    public void generarBarrasChart(List<Programacion> programacion, String tipoIndicador){
            List<String> categorias = new ArrayList<String>();
            categorias.add("Jan");
            categorias.add("Feb");
            categorias.add("Mar");
            categorias.add("Apr");
            setChartCategoriesForBar(new Gson().toJson(categorias));

        titulo = definirTituloGrafica(indicador);
        tituloEjeX = definirTituloX(indicador);
        List<Series> series = new ArrayList<Series>();
        List<List<Object>> dataPoints = new ArrayList<>();
        for(Programacion prog: programacion){
            Object valor = null;
            if(tipoIndicador.equals(IndicadorEnum.NUMERO_BUSES.toString())){
                valor = (double)prog.getBuses();
            }else if ( tipoIndicador.equals(IndicadorEnum.KM_COMERCIALES.toString()) ){
                valor = prog.getKmComercialFin();
            }else if ( tipoIndicador.equals(IndicadorEnum.KM_VACIO.toString()) ){
                valor =  prog.getKmVacioFin();
            }else if ( tipoIndicador.equals(IndicadorEnum.EXP_COMERCIAL.toString()) ){
                valor = (double) prog.getExpedicionComercial();
            }else if ( tipoIndicador.equals(IndicadorEnum.POR_VACIOS.toString()) ){
                valor = prog.getPorcentajeVacioFinal()*100;
            }else if ( tipoIndicador.equals(IndicadorEnum.LINEA_CARGADA.toString()) ){
                valor = prog.getLineasCargadas();
            }else if ( tipoIndicador.equals(IndicadorEnum.VELOCIDAD_COMERCIAL.toString()) ){
                valor = prog.getVelocidadComercial();
            }else if ( tipoIndicador.equals(IndicadorEnum.HORAS_BUSES.toString()) ){
                valor = prog.getHorasPorBuses();
            }
            dataPoints.add(new ArrayList<Object>(Arrays.asList(prog.getFecha(),valor)));
        }
         series.add(new Series(periocidad, dataPoints));
        setChartSeriesForBar(new Gson().toJson(series));

    }


    public  List<Series> generarRegressionChart(List<Programacion> programacion, String period){

        titulo = definirTituloGrafica(indicador);
        titulo = titulo +" - "+period;


        tituloEjeX = definirTituloX(indicador);
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

        Series serie = transformarASerieParaLineas(programacion,period,indicador);
        serie.setType("scatter");
        series.add(serie);

        return series;
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

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getChartSeriesSabado() {
        return chartSeriesSabado;
    }

    public void setChartSeriesSabado(String chartSeriesSabado) {
        this.chartSeriesSabado = chartSeriesSabado;
    }

    public String getChartSeriesFestivo() {
        return chartSeriesFestivo;
    }

    public void setChartSeriesFestivo(String chartSeriesFestivo) {
        this.chartSeriesFestivo = chartSeriesFestivo;
    }

    public String getTituloSabado() {
        return tituloSabado;
    }

    public void setTituloSabado(String tituloSabado) {
        this.tituloSabado = tituloSabado;
    }

    public String getTituloFestivo() {
        return tituloFestivo;
    }

    public void setTituloFestivo(String tituloFestivo) {
        this.tituloFestivo = tituloFestivo;
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
}
