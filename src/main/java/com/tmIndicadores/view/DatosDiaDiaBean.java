package com.tmIndicadores.view;

import com.tmIndicadores.controller.ListObject;
import com.tmIndicadores.controller.ModosUtil;
import com.tmIndicadores.controller.PathFiles;
import com.tmIndicadores.controller.ReportesGoalBusProcessor;
import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.model.entity.Programacion;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ManagedBean(name="DDiaDiaBean")
@ViewScoped
public class DatosDiaDiaBean implements Serializable {

    private boolean visibleDescarga;
    private Date fechaInicio;
    private Date fechaFin;
    private String tipologia;
    private String modo;
    private List<ListObject> modos;
    private List<ListObject> tipologias;



    @ManagedProperty(value="#{ReportesGoalProcessor}")
    private ReportesGoalBusProcessor reportesGoalBusProcessor;

    @ManagedProperty("#{MessagesView}")
    private MessagesView messagesView;

    @PostConstruct
    public void init() {
        visibleDescarga = false;
        modo = "TRO";
        tipologia = "DEF";
        cargarListaModos();
        cargarListaTipologiaTroncal();
    }

    public void exportarDatos(){

        if(genracionValida()){
           boolean resultado= reportesGoalBusProcessor.exportarDatosDiaADia(fechaInicio,fechaFin,modo,tipologia);
           if(resultado) visibleDescarga = true;
        }else{
            messagesView.error(Messages.MENSAJE_CARGA_FALLIDA,
                    "Información Incompleta");
        }

    }

    public void cargarListaModos(){
        modos = ModosUtil.cargarListaModos();
    }

    private void cargarListaTipologiaTroncal() {
        tipologias = ModosUtil.cargarListaTipologiaTroncal();
    }

    private void cargarListaTipologiaDual() {
        tipologias = ModosUtil.cargarListaTipologiaDual();
    }

    public void updateTipologias(){
        if(modo.equals("TRO")){
            cargarListaTipologiaTroncal();
        }else{
            cargarListaTipologiaDual();
        }
    }



    public void  download ()throws IOException {


        String filename = "resultado.xls";

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
        File file = new File(PathFiles.PATH+""+PathFiles.DIA_A_DIA_FILE);
        file.createNewFile();
        FileInputStream fileIn = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=datosDiaADia.xls");


        byte[] outputByte = new byte[4096];
        while(fileIn.read(outputByte, 0, 4096) != -1)
        {
            out.write(outputByte, 0, 4096);
        }
        fileIn.close();
        out.flush();
        out.close();
        out.flush();
        try {
            if (out != null) {
                out.close();
            }
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException e) {

        }

    }

    private boolean genracionValida() {
        if(fechaInicio!= null && fechaFin!=null && tipologia!=null && modo!=null ){
            return true;
        }
        return false;
    }


    public boolean isVisibleDescarga() {
        return visibleDescarga;
    }

    public void setVisibleDescarga(boolean visibleDescarga) {
        this.visibleDescarga = visibleDescarga;
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

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
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

    public MessagesView getMessagesView() {
        return messagesView;
    }

    public void setMessagesView(MessagesView messagesView) {
        this.messagesView = messagesView;
    }

    public ReportesGoalBusProcessor getReportesGoalBusProcessor() {
        return reportesGoalBusProcessor;
    }

    public void setReportesGoalBusProcessor(ReportesGoalBusProcessor reportesGoalBusProcessor) {
        this.reportesGoalBusProcessor = reportesGoalBusProcessor;
    }
}
