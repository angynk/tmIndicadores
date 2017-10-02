package com.tmIndicadores.view;

import com.tmIndicadores.controller.IndicadoresGoalProcessor;
import com.tmIndicadores.controller.LogDatos;
import com.tmIndicadores.controller.TipoLog;
import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.Programacion;
import org.primefaces.model.DualListModel;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;

@ManagedBean(name = "cargarBean")
@ViewScoped
public class CargarIndicadoresBean {

    private String tipoGeneracion;
    private String razonProgramacion;
    private String lineasCargadas;
    private String periocidad;
    private String tipologia;
    private String cuadro;
    private String cuadroDef;
    private Date fechaProgramacion;
    private Date fechaProgramacionDef;
    private Date fechaIniBusquedaDef;
    private Date fechaFinBusquedaDef;
    private UploadedFile traceLog;
    private List<LogDatos> logDatos;
    private DualListModel<Programacion> progList;
    private Map<String,Programacion> progMap;
    private boolean resultadosVisibles;
    private String tipoCarga;
    private boolean cargaArchivo;
    private boolean generarDEF;
    private boolean listaProg;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;


    @ManagedProperty("#{GoalProcessor}")
    private IndicadoresGoalProcessor idProcessor;

    @ManagedProperty(value="#{ProgramacionServicios}")
    private ProgramacionServicios programacionServicios;

    public CargarIndicadoresBean() {
    }


    @PostConstruct
    public void init() {
        tipoGeneracion ="1";
        periocidad ="HABIL";
        tipologia = "ART";
        resultadosVisibles = false;
        tipoCarga = "NC";
        cargaArchivo = false;
        generarDEF = false;
        progList = new DualListModel<Programacion>();
        logDatos = new ArrayList<LogDatos>();
    }

    public void habilitarTipoCarga(){
        if(tipoCarga.equals("NC")){
            cargaArchivo = true;
            generarDEF =false;
        }else{
            cargaArchivo =false;
            generarDEF = true;
        }

    }

    public void reiniciar (){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(ec.getRequestContextPath()
                    + "/secured/cargarIdicadores.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buscar(){
        listaProg = true;

        if(fechaIniBusquedaDef!=null && fechaFinBusquedaDef!=null){
            List<Programacion> listaSource = programacionServicios.getProgramacionbyAttributes(fechaIniBusquedaDef,fechaFinBusquedaDef);
            progMap = crearMapaProg(listaSource);
            List<Programacion> listaTarget =  new ArrayList<Programacion>();
            progList = new DualListModel<Programacion>(listaSource, listaTarget);
        }
        generarDEF = false;

    }

    private Map<String, Programacion> crearMapaProg(List<Programacion> listaSource) {
        progMap = new HashMap<String,Programacion>();
        for(Programacion prog:listaSource){
            progMap.put(prog.toString(),prog);
        }
        return progMap;
    }

    public void calcularDEF(){
        logDatos.add(new LogDatos("<<Inicio Calculo Indicador Definitivo>>", TipoLog.INFO));
        if(programacionServicios.isDEFAlready(fechaProgramacionDef)){
            logDatos.add(new LogDatos("La fecha seleccionada ya tiene una programaciòn definitiva", TipoLog.ERROR));
            messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,"La fecha seleccionada ya tiene una programaciòn definitiva");
        }else{
            if(progList.getTarget().size()>0 && cuadroDef!=null && fechaProgramacionDef!=null){
                List<Programacion> programaciones = progList.getTarget();
                Programacion nueva = new Programacion();
                nueva.setTipologia("DEF");
                nueva.setCuadro(cuadroDef);
                nueva.setFecha(fechaProgramacionDef);
                copiarInformacionBase(programaciones, nueva);
                nueva = calcularValorProgramacion(programaciones,nueva);
                programacionServicios.addProgramacion(nueva);
                logDatos.add(new LogDatos("Nueva Programacion DEF ", TipoLog.INFO));
                messagesView.info(Messages.MENSAJE_CARGA_EXITOSA,"Nuevo Indicador Definitivo");

            }else{
                messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,"Complete todos los campos");
                logDatos.add(new LogDatos("Complete todos los campos", TipoLog.ERROR));
            }


        }
        resultadosVisibles = true;
        logDatos.add(new LogDatos("<<Fin Calculo Indicador Definitivo>>", TipoLog.INFO));
    }

    private void copiarInformacionBase(List<Programacion> programaciones, Programacion nueva) {
        Programacion programacion = progMap.get(programaciones.get(0));
        if(programacion!=null){
            nueva.setPeriodicidad(programacion.getPeriodicidad());
            nueva.setRazonCambio(programacion.getRazonCambio());
            nueva.setTipoProgramacion(programacion.getTipoProgramacion());
        }
    }

    private Programacion calcularValorProgramacion(List<Programacion> programaciones,Programacion nueva) {
        int numBuses = 0;
        double kmComercialesFin = 0;
        double kmVacioFin = 0;
        double kmComerialesInicio = 0;
        double kmVacioInicio = 0;
        int lineasCargas = 0;
        int expComercial = 0;
        for(int z=0;z<programaciones.size();z++){
            Programacion prog = progMap.get(programaciones.get(z));
            numBuses = numBuses + prog.getBuses();
            kmComercialesFin = kmComercialesFin + prog.getKmComercialFin();
            kmVacioFin = kmVacioFin + prog.getKmVacioFin();
            kmComerialesInicio = kmComerialesInicio + validarDoubleNulo(prog.getKmComercialIncio());
            kmVacioInicio = kmVacioInicio + validarDoubleNulo(prog.getKmVacioInicio());
            expComercial = expComercial + validarIntNulo(prog.getExpedicionComercial());
            lineasCargas = lineasCargas + prog.getLineasCargadas();
        }

        nueva.setBuses(numBuses);
        nueva.setKmComercialFin(kmComercialesFin);
        nueva.setKmVacioFin(kmVacioFin);
        nueva.setKmVacioInicio(kmVacioInicio);
        nueva.setKmComercialIncio(kmComerialesInicio);
        nueva.setLineasCargadas(lineasCargas);
        nueva.setExpedicionComercial(expComercial);
        nueva.setPorcentajeVacioFinal(nueva.getKmVacioFin()/nueva.getKmComercialFin());
        if(kmComerialesInicio!= 0){
            nueva.setPorcentajeVacioInicio(kmVacioInicio/kmComerialesInicio);
        }
        return nueva;
    }

    public Double validarDoubleNulo(Double valor){
        if(valor == null){
            return 0.0;
        }
        return valor;
    }

    public Integer validarIntNulo(Integer valor){
        if(valor == null){
            return 0;
        }
        return valor;
    }

    public void cargarArchivo(){
        if(valid()){
            try {
                logDatos  = idProcessor.processDataFromFile(traceLog.getFileName(), traceLog.getInputstream(), fechaProgramacion, razonProgramacion, tipologia, periocidad, lineasCargadas,cuadro);
                resultadosVisibles = true;
                if(logDatos.size()>2){
                    messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,Messages.ACCION_INDICADORES_REVISAR);
                }else{
                    messagesView.info(Messages.MENSAJE_CARGA_EXITOSA,Messages.ACCION_INDICADORES_ALMACENADOS);
                }

            } catch (IOException e) {
                messagesView.error(Messages.MENSAJE_ARCHIVO_NO_EXCEL,Messages.ACCION_ARCHIVO_NO_EXCEL);
            }
        }else{
            messagesView.error(Messages.MENSAJE_CAMPOS_INCOMPLETOS,Messages.ACCION_CAMPOS_INCOMPLETOS);
        }
    }

    private boolean valid() {
        if(fechaProgramacion!= null && razonProgramacion!=null && tipologia!=null && periocidad!=null && lineasCargadas!=null){
            return true;
        }
        return false;
    }


    public String getTipoGeneracion() {
        return tipoGeneracion;
    }


    public void setTipoGeneracion(String tipoGeneracion) {
        this.tipoGeneracion = tipoGeneracion;
    }

    public Date getFechaProgramacion() {
        return fechaProgramacion;
    }

    public void setFechaProgramacion(Date fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public UploadedFile getTraceLog() {
        return traceLog;
    }

    public void setTraceLog(UploadedFile traceLog) {
        this.traceLog = traceLog;
    }

    public String getRazonProgramacion() {
        return razonProgramacion;
    }

    public void setRazonProgramacion(String razonProgramacion) {
        this.razonProgramacion = razonProgramacion;
    }

    public IndicadoresGoalProcessor getIdProcessor() {
        return idProcessor;
    }

    public void setIdProcessor(IndicadoresGoalProcessor idProcessor) {
        this.idProcessor = idProcessor;
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


    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public String getLineasCargadas() {
        return lineasCargadas;
    }

    public void setLineasCargadas(String lineasCargadas) {
        this.lineasCargadas = lineasCargadas;
    }

    public String getCuadro() {
        return cuadro;
    }

    public void setCuadro(String cuadro) {
        this.cuadro = cuadro;
    }

    public List<LogDatos> getLogDatos() {
        return logDatos;
    }

    public void setLogDatos(List<LogDatos> logDatos) {
        this.logDatos = logDatos;
    }

    public boolean isResultadosVisibles() {
        return resultadosVisibles;
    }

    public void setResultadosVisibles(boolean resultadosVisibles) {
        this.resultadosVisibles = resultadosVisibles;
    }

    public String getTipoCarga() {
        return tipoCarga;
    }

    public void setTipoCarga(String tipoCarga) {
        this.tipoCarga = tipoCarga;
    }

    public boolean isCargaArchivo() {
        return cargaArchivo;
    }

    public void setCargaArchivo(boolean cargaArchivo) {
        this.cargaArchivo = cargaArchivo;
    }

    public String getCuadroDef() {
        return cuadroDef;
    }

    public void setCuadroDef(String cuadroDef) {
        this.cuadroDef = cuadroDef;
    }

    public Date getFechaProgramacionDef() {
        return fechaProgramacionDef;
    }

    public void setFechaProgramacionDef(Date fechaProgramacionDef) {
        this.fechaProgramacionDef = fechaProgramacionDef;
    }

    public Date getFechaIniBusquedaDef() {
        return fechaIniBusquedaDef;
    }

    public void setFechaIniBusquedaDef(Date fechaIniBusquedaDef) {
        this.fechaIniBusquedaDef = fechaIniBusquedaDef;
    }

    public Date getFechaFinBusquedaDef() {
        return fechaFinBusquedaDef;
    }

    public void setFechaFinBusquedaDef(Date fechaFinBusquedaDef) {
        this.fechaFinBusquedaDef = fechaFinBusquedaDef;
    }

    public boolean isListaProg() {
        return listaProg;
    }

    public void setListaProg(boolean listaProg) {
        this.listaProg = listaProg;
    }

    public DualListModel<Programacion> getProgList() {
        return progList;
    }

    public void setProgList(DualListModel<Programacion> progList) {
        this.progList = progList;
    }

    public ProgramacionServicios getProgramacionServicios() {
        return programacionServicios;
    }

    public void setProgramacionServicios(ProgramacionServicios programacionServicios) {
        this.programacionServicios = programacionServicios;
    }

    public boolean isGenerarDEF() {
        return generarDEF;
    }

    public void setGenerarDEF(boolean generarDEF) {
        this.generarDEF = generarDEF;
    }
}
