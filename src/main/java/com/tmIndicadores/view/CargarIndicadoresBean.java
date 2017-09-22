package com.tmIndicadores.view;

import com.tmIndicadores.controller.IndicadoresGoalProcessor;
import com.tmIndicadores.controller.LogDatos;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "cargarBean")
@ViewScoped
public class CargarIndicadoresBean {

    private String tipoGeneracion;
    private String razonProgramacion;
    private String lineasCargadas;
    private String periocidad;
    private String tipologia;
    private String cuadro;
    private Date fechaProgramacion;
    private UploadedFile traceLog;
    private List<LogDatos> logDatos;
    private boolean resultadosVisibles;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;


    @ManagedProperty("#{GoalProcessor}")
    private IndicadoresGoalProcessor idProcessor;

    public CargarIndicadoresBean() {
    }


    @PostConstruct
    public void init() {
        tipoGeneracion ="1";
        periocidad ="HABIL";
        tipologia = "ART";
        resultadosVisibles = false;
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
}
