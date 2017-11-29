package com.tmIndicadores.view;

import com.tmIndicadores.controller.AsociarFechasProcessor;
import com.tmIndicadores.controller.ListObject;
import com.tmIndicadores.controller.LogDatos;
import com.tmIndicadores.controller.ModosUtil;
import com.tmIndicadores.model.entity.Programacion;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name = "asociarBean", eager = true)
@ViewScoped
public class AsociarFechasBean {

    private String modo;
    private List<ListObject> modos;
    private boolean visibleAsociacion;
    private String fechas;
    private Programacion selectedProg;
    private List<String> progDateList;
    private String fechaSelected;
    private Date fechaAsociar;

    private List<LogDatos> logDatos;
    private boolean resultadosVisibles;

    @ManagedProperty("#{AsociarFechasProcessor}")
    private AsociarFechasProcessor asociarFechasProcessor;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public AsociarFechasBean() {
    }

    @PostConstruct
    public void init() {
        modo = "TRO";
        modos = ModosUtil.cargarListaModos();
        visibleAsociacion = false;
        resultadosVisibles = false;
    }

    public void asociar(){
        //Obtener fechas
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        fechas = ec.getRequestParameterMap().get("fechas");
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            fechaAsociar = format.parse(fechaSelected);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(asociarFechasProcessor.existeAsociacionesEnEsaFecha(fechas,modo)){
            RequestContext.getCurrentInstance().execute("PF('reemplazarDuDialog').show();");
        }else{
           asociarFechas();
        }

    }

    private void asociarFechas(){
        logDatos = asociarFechasProcessor.asociarProgramacion(fechaAsociar,fechas,modo);
        resultadosVisibles = true;
        if(asociarFechasProcessor.isAsociacionValida()){
            messagesView.info(Messages.MENSAJE_CARGA_EXITOSA,"");
        }else{
            messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,Messages.VALIDE_LOG);
        }
    }

    public void continuarAsociacion(){
            asociarFechas();
    }

    public void finalizarAsociacion(){
        messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,"La programación no fue asociada a las fechas definidas");
    }

    public void updateProgList(){
        List<Programacion> allProgramacionbyModo = asociarFechasProcessor.getAllProgramacionbyModo(modo);
        progDateList = convertirFechasLista(allProgramacionbyModo);
        visibleAsociacion = true;
    }

    private List<String> convertirFechasLista(List<Programacion> allProgramacionbyModo) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        List<String> fechas = new ArrayList<>();
        for(Programacion programacion:allProgramacionbyModo){
            if(!fechas.contains(sdfDate.format(programacion.getFecha()))){

                fechas.add(sdfDate.format(programacion.getFecha()));
            }

        }
        return fechas;
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

    public boolean isVisibleAsociacion() {
        return visibleAsociacion;
    }

    public void setVisibleAsociacion(boolean visibleAsociacion) {
        this.visibleAsociacion = visibleAsociacion;
    }

    public Programacion getSelectedProg() {
        return selectedProg;
    }

    public void setSelectedProg(Programacion selectedProg) {
        this.selectedProg = selectedProg;
    }

    public List<String> getProgDateList() {
        return progDateList;
    }

    public void setProgDateList(List<String> progDateList) {
        this.progDateList = progDateList;
    }

    public AsociarFechasProcessor getAsociarFechasProcessor() {
        return asociarFechasProcessor;
    }

    public void setAsociarFechasProcessor(AsociarFechasProcessor asociarFechasProcessor) {
        this.asociarFechasProcessor = asociarFechasProcessor;
    }

    public String getFechaSelected() {
        return fechaSelected;
    }

    public void setFechaSelected(String fechaSelected) {
        this.fechaSelected = fechaSelected;
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

    public String getFechas() {
        return fechas;
    }

    public void setFechas(String fechas) {
        this.fechas = fechas;
    }

    public Date getFechaAsociar() {
        return fechaAsociar;
    }

    public void setFechaAsociar(Date fechaAsociar) {
        this.fechaAsociar = fechaAsociar;
    }

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }
}
