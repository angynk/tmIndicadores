package com.tmIndicadores.view;

import com.tmIndicadores.controller.DuplicarProgramacionProcessor;
import com.tmIndicadores.controller.ListObject;
import com.tmIndicadores.controller.LogDatos;
import com.tmIndicadores.controller.ModosUtil;
import com.tmIndicadores.model.entity.Programacion;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@ManagedBean(name = "duplicarBean", eager = true)
@ViewScoped
public class DuplicarIndicadoresBean {

    private boolean visibleDuplicacion;
    private String razonProgramacion;
    private String tituloPanel;
    private String fechas;
    private String fechaSelected;
    private String modo;
    private List<ListObject> modos;
    private Date fechaADuplicar;
    private List<LogDatos> logDatos;
    private boolean resultadosVisibles;

    private Programacion selectedProg;
    private List<String> progDateList;

    @ManagedProperty("#{DuplicarProcessor}")
    private DuplicarProgramacionProcessor duplicarProgramacionProcessor;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    public DuplicarIndicadoresBean() {
    }

    @PostConstruct
    public void init() {
        resultadosVisibles = false;
        modo = "TRO";
        modos = ModosUtil.cargarListaModos();
//        List<Programacion> allProgramacionbyModo = duplicarProgramacionProcessor.getAllProgramacionbyModo(modo);
//        progDateList = convertirFechasLista(allProgramacionbyModo);
        visibleDuplicacion = false;
    }

    public void duplicar(){
        //Obtener fechas
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        fechas = ec.getRequestParameterMap().get("fechas");
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        try {
            fechaADuplicar = format.parse(fechaSelected);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        logDatos = duplicarProgramacionProcessor.duplicarProgramacion(fechaADuplicar,fechas,modo);
        resultadosVisibles = true;
        if(duplicarProgramacionProcessor.isDuplicacionValida()){
            messagesView.info(Messages.MENSAJE_CARGA_EXITOSA,"");
        }else{
            messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,Messages.VALIDE_LOG);
        }
    }

    public void updateProgList(){
        List<Programacion> allProgramacionbyModo = duplicarProgramacionProcessor.getAllProgramacionbyModo(modo);
        progDateList = convertirFechasLista(allProgramacionbyModo);
        visibleDuplicacion = true;
        tituloPanel = "Duplicar Indicadores Goal Bus -  "+modo;
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

    public Programacion getProgramacionbyID(long id){
        return duplicarProgramacionProcessor.getProgramacionbyID(id);
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




    public Programacion getSelectedProg() {
        return selectedProg;
    }

    public void setSelectedProg(Programacion selectedProg) {
        this.selectedProg = selectedProg;
    }

    public List<ListObject> getModos() {
        return modos;
    }

    public void setModos(List<ListObject> modos) {
        this.modos = modos;
    }

    public String getFechaSelected() {
        return fechaSelected;
    }

    public void setFechaSelected(String fechaSelected) {
        this.fechaSelected = fechaSelected;
    }

    public boolean isVisibleDuplicacion() {
        return visibleDuplicacion;
    }

    public void setVisibleDuplicacion(boolean visibleDuplicacion) {
        this.visibleDuplicacion = visibleDuplicacion;
    }

    public List<String> getProgDateList() {
        return progDateList;
    }

    public void setProgDateList(List<String> progDateList) {
        this.progDateList = progDateList;
    }

    public String getTituloPanel() {
        return tituloPanel;
    }

    public void setTituloPanel(String tituloPanel) {
        this.tituloPanel = tituloPanel;
    }
}
