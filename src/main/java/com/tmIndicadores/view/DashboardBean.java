package com.tmIndicadores.view;

import com.google.gson.Gson;
import com.tmIndicadores.controller.ListObject;
import com.tmIndicadores.controller.ModosUtil;
import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.Programacion;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "dashBean")
@ViewScoped
public class DashboardBean {

    private String progHabil;
    private String progFestivo;
    private String progSabado;
    private String progTotal;
    private String hiddenChartLine;
    private String hiddenChartPie;
    private String hiddenChartPieSabado;
    private String hiddenAreaFestivo;
    private String tituloGrapKMComerciales;
    private String srcImagenModo;

    private String modo;
    private String tipologia;
    private  List<ListObject> modos;

   private List<Programacion>  ultimasProgramaciones;


    @ManagedProperty(value="#{ProgramacionServicios}")
    private ProgramacionServicios programacionServicios;

    public DashboardBean() {
    }

    @PostConstruct
    public void init(){
        modos = ModosUtil.cargarListaModos();
        modo = "TRO";
        tipologia = "DEF";
        srcImagenModo = "/resources/images/troncal.png";

        updateCharts();

    }

    public void updateCharts(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaUltimaProg = programacionServicios.getLastProgramacionFecha(modo,tipologia);
        List<Programacion> habil = programacionServicios.getProgramacionesUltimoMes("HABIL",fechaUltimaProg,tipologia);
        List<Programacion> festivo = programacionServicios.getProgramacionesUltimoMes("FESTIVO",fechaUltimaProg,tipologia);
        List<Programacion> sabado = programacionServicios.getProgramacionesUltimoMes("SABADO",fechaUltimaProg,tipologia);
        progHabil = habil.size()+"";
        progFestivo = festivo.size()+"";
        progSabado = sabado.size()+"";
        progTotal = habil.size()+festivo.size()+sabado.size()+"";
        tituloGrapKMComerciales = "Últimos 30 días desde " + sdfDate.format(fechaUltimaProg);
        List<Series> series = SeriesForLine(habil, festivo, sabado);
        setHiddenChartLine(new Gson().toJson(series));
        ultimasProgramacionesTabla(habil, festivo, sabado);

        List<Series> seriesPie = SeriesForLine(habil);
        setHiddenChartPie(new Gson().toJson(seriesPie));

        List<Series> seriesPieSabado = SeriesForLine(sabado);
        setHiddenChartPieSabado(new Gson().toJson(seriesPieSabado));

        List<Series> seriesAreaFestivo = SeriesForLine(festivo);
        setHiddenAreaFestivo(new Gson().toJson(seriesAreaFestivo));

    }

    public void updateDashboard(){
        if(modo.equals("TRO")){
            tipologia = "DEF";
            srcImagenModo = "/resources/images/troncal.png";
        }else{
            tipologia = "DU-DEF";
            srcImagenModo = "/resources/images/dual.png";
        }

        updateCharts();

//        refreshPage();

    }

    public void refreshPage(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<SeriesPie> SeriesPies(List<Programacion> habil) {
        int vacio = 100;
        if(habil!=null){
            if(habil.size()>0){
                vacio= (int) habil.get(0).getPorcentajeVacioFinal().intValue();
            }
        }

        List<SeriesPie> seriesPie = new ArrayList<SeriesPie>();
        List<DataComposed> dataPie = new ArrayList<DataComposed>();
        dataPie.add(new DataComposed("Km Vacios",vacio));
        dataPie.add(new DataComposed("Km Comerciales",100-vacio));
        seriesPie.add( new SeriesPie("%",dataPie));
        return seriesPie;
    }

    private void ultimasProgramacionesTabla(List<Programacion> habil, List<Programacion> festivo, List<Programacion> sabado) {
        ultimasProgramaciones = new ArrayList<>();
        if(habil.size()>0){
            ultimasProgramaciones.add(habil.get(0));
        }
        if(sabado.size()>0){
            ultimasProgramaciones.add(sabado.get(0));
        }
        if(festivo.size()>0){
            ultimasProgramaciones.add(festivo.get(0));
        }
    }

    private List<Series> SeriesForLine(List<Programacion> habil, List<Programacion> festivo, List<Programacion> sabado) {
        List<Series> series = new ArrayList<Series>();
        series.add(transformarASerieParaLineas(habil,"Habil","KC"));
        series.add(transformarASerieParaLineas(festivo,"Festivo","KC"));
        series.add(transformarASerieParaLineas(sabado,"Sabado","KC"));
        return series;
    }

    private List<Series> SeriesForLine(List<Programacion> habil){
        List<Series> series = new ArrayList<Series>();
        series.add(transformarASerieParaLineas(habil,"KM Comerciales",IndicadorEnum.KM_COMERCIALES.toString()));
        series.add(transformarASerieParaLineas(habil,"KM Vacio",IndicadorEnum.KM_VACIO.toString()));
        return series;
    }

    public Series transformarASerieParaLineas(List<Programacion> programacion, String nombre, String tipoIndicador){
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
                valor = prog.getPorcentajeVacioFinal();
            }else if ( tipoIndicador.equals(IndicadorEnum.LINEA_CARGADA.toString()) ){
                valor = prog.getLineasCargadas();
            }
            dataPoints.add(new ArrayList<Object>(Arrays.asList(prog.getFecha(),valor)));
        }
        serie = new Series(nombre, dataPoints);
        return serie;
    }

    public ProgramacionServicios getProgramacionServicios() {
        return programacionServicios;
    }

    public void setProgramacionServicios(ProgramacionServicios programacionServicios) {
        this.programacionServicios = programacionServicios;
    }

    public String getProgHabil() {
        return progHabil;
    }

    public void setProgHabil(String progHabil) {
        this.progHabil = progHabil;
    }

    public String getProgFestivo() {
        return progFestivo;
    }

    public void setProgFestivo(String progFestivo) {
        this.progFestivo = progFestivo;
    }

    public String getProgSabado() {
        return progSabado;
    }

    public void setProgSabado(String progSabado) {
        this.progSabado = progSabado;
    }

    public String getProgTotal() {
        return progTotal;
    }

    public void setProgTotal(String progTotal) {
        this.progTotal = progTotal;
    }

    public String getHiddenChartLine() {
        return hiddenChartLine;
    }

    public void setHiddenChartLine(String hiddenChartLine) {
        this.hiddenChartLine = hiddenChartLine;
    }

    public List<Programacion> getUltimasProgramaciones() {
        return ultimasProgramaciones;
    }

    public void setUltimasProgramaciones(List<Programacion> ultimasProgramaciones) {
        this.ultimasProgramaciones = ultimasProgramaciones;
    }

    public String getHiddenChartPie() {
        return hiddenChartPie;
    }

    public void setHiddenChartPie(String hiddenChartPie) {
        this.hiddenChartPie = hiddenChartPie;
    }

    public String getHiddenChartPieSabado() {
        return hiddenChartPieSabado;
    }

    public void setHiddenChartPieSabado(String hiddenChartPieSabado) {
        this.hiddenChartPieSabado = hiddenChartPieSabado;
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

    public String getTituloGrapKMComerciales() {
        return tituloGrapKMComerciales;
    }

    public void setTituloGrapKMComerciales(String tituloGrapKMComerciales) {
        this.tituloGrapKMComerciales = tituloGrapKMComerciales;
    }

    public String getSrcImagenModo() {
        return srcImagenModo;
    }

    public void setSrcImagenModo(String srcImagenModo) {
        this.srcImagenModo = srcImagenModo;
    }

    public String getHiddenAreaFestivo() {
        return hiddenAreaFestivo;
    }

    public void setHiddenAreaFestivo(String hiddenAreaFestivo) {
        this.hiddenAreaFestivo = hiddenAreaFestivo;
    }
}
