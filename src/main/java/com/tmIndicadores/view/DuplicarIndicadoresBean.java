package com.tmIndicadores.view;

import com.tmIndicadores.controller.DuplicarProgramacionProcessor;
import com.tmIndicadores.controller.LogDatos;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "duplicarBean")
@ViewScoped
public class DuplicarIndicadoresBean {

    private String razonProgramacion;
    private String fechas;
    private String modo;
    private Date fechaADuplicar;
    private List<LogDatos> logDatos;
    private boolean resultadosVisibles;

    @ManagedProperty("#{DuplicarProcessor}")
    private DuplicarProgramacionProcessor duplicarProgramacionProcessor;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public DuplicarIndicadoresBean() {
    }

    @PostConstruct
    public void init() {
        resultadosVisibles = false;
    }

    public void duplicar(){
        //Obtener fechas
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        fechas = ec.getRequestParameterMap().get("fechas");
        logDatos = duplicarProgramacionProcessor.duplicarProgramacion(fechaADuplicar,fechas,modo);
        resultadosVisibles = true;
        if(duplicarProgramacionProcessor.isDuplicacionValida()){
            messagesView.info(Messages.MENSAJE_CARGA_EXITOSA,"");
        }else{
            messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,Messages.VALIDE_LOG);
        }
    }

    public String getRazonProgramacion() {
        return razonProgramacion;
    }

    public void setRazonProgramacion(String razonProgramacion) {
        this.razonProgramacion = razonProgramacion;
    }

    public String getFechas() {
        return fechas;
    }

    public void setFechas(String fechas) {
        this.fechas = fechas;
    }

    public Date getFechaADuplicar() {
        return fechaADuplicar;
    }

    public void setFechaADuplicar(Date fechaADuplicar) {
        this.fechaADuplicar = fechaADuplicar;
    }

    public DuplicarProgramacionProcessor getDuplicarProgramacionProcessor() {
        return duplicarProgramacionProcessor;
    }

    public void setDuplicarProgramacionProcessor(DuplicarProgramacionProcessor duplicarProgramacionProcessor) {
        this.duplicarProgramacionProcessor = duplicarProgramacionProcessor;
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

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }
}
