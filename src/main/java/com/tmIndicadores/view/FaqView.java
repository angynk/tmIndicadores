package com.tmIndicadores.view;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.InputStream;

@ManagedBean(name = "FAQBean")
@ViewScoped
public class FaqView {

    private StreamedContent fileResumen;
    private StreamedContent fileIndicadores;
    private StreamedContent visualizarIndicadores;
    private StreamedContent reporteDatos;
    private StreamedContent agregarProg;
    private StreamedContent duplicarProg;
    private StreamedContent modificarProg;

    public FaqView() {
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/static/video2.mp4");
        fileIndicadores = new DefaultStreamedContent(stream, "video/mp4", "Resumen Aplicativo Indicadores.mp4");

        InputStream streamResumen = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/static/Resumen Indicdores.mp4");
        fileResumen = new DefaultStreamedContent(streamResumen, "video/mp4", "Obtener Resumen Indicadores.mp4");


        InputStream streamVisu = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/static/video3.mp4");
        visualizarIndicadores = new DefaultStreamedContent(streamVisu, "video/mp4", "Visualizar Indicadores.mp4");

        InputStream streamReporte = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/static/video4.mp4");
        reporteDatos = new DefaultStreamedContent(streamReporte, "video/mp4", "Reporte Datos.mp4");

        InputStream streamAgregar = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/static/video5.mp4");
        agregarProg = new DefaultStreamedContent(streamAgregar, "video/mp4", "Agregar Programacion.mp4");

        InputStream streamDuplicar = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/static/video6.mp4");
        duplicarProg = new DefaultStreamedContent(streamDuplicar, "video/mp4", "Duplicar Programacion.mp4");

        InputStream streamModificar = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/static/video7.mp4");
        modificarProg = new DefaultStreamedContent(streamModificar, "video/mp4", "Modificar Programacion.mp4");
    }

    public StreamedContent getFileResumen() {
        return fileResumen;
    }

    public void setFileResumen(StreamedContent fileResumen) {
        this.fileResumen = fileResumen;
    }

    public StreamedContent getFileIndicadores() {
        return fileIndicadores;
    }

    public void setFileIndicadores(StreamedContent fileIndicadores) {
        this.fileIndicadores = fileIndicadores;
    }

    public StreamedContent getVisualizarIndicadores() {
        return visualizarIndicadores;
    }

    public void setVisualizarIndicadores(StreamedContent visualizarIndicadores) {
        this.visualizarIndicadores = visualizarIndicadores;
    }

    public StreamedContent getReporteDatos() {
        return reporteDatos;
    }

    public void setReporteDatos(StreamedContent reporteDatos) {
        this.reporteDatos = reporteDatos;
    }

    public StreamedContent getAgregarProg() {
        return agregarProg;
    }

    public void setAgregarProg(StreamedContent agregarProg) {
        this.agregarProg = agregarProg;
    }

    public StreamedContent getDuplicarProg() {
        return duplicarProg;
    }

    public void setDuplicarProg(StreamedContent duplicarProg) {
        this.duplicarProg = duplicarProg;
    }

    public StreamedContent getModificarProg() {
        return modificarProg;
    }

    public void setModificarProg(StreamedContent modificarProg) {
        this.modificarProg = modificarProg;
    }
}
