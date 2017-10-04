package com.tmIndicadores.model.entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="programacion_goal")
public class Programacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="PGenerator")
    @SequenceGenerator(name="PGenerator", sequenceName = "programacion_goal_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "fecha_duplicado")
    private Date fechaDuplicada;

    @Column(name = "cuadro")
    private String cuadro;

    @Column(name = "buses")
    private Integer buses;

    @Column(name = "km_comerciales_inicio")
    private Double kmComercialIncio;

    @Column(name = "km_vacio_inicio")
    private Double kmVacioInicio;

    @Column(name = "km_comerciales_fin")
    private Double kmComercialFin;

    @Column(name = "km_vacio_fin")
    private Double kmVacioFin;

    @Column(name = "tiempo_exp")
    private String tiempoExpedicion;

    @Column(name = "tiempo_pro")
    private String tiempoProcesamiento;

    @Column(name = "exp_comercial")
    private Integer expedicionComercial;

    @Column(name = "por_vacio_final")
    private Double porcentajeVacioFinal;

    @Column(name = "por_vacio_inicio")
    private Double porcentajeVacioInicio;

    @Column(name = "lineas_cargadas")
    private Integer lineasCargadas;


    @Column(name = "razon_cambio")
    private String razonCambio;

    @Column(name = "tipo_programacion")
    private String tipoProgramacion;

    @Column(name = "periodicidad")
    private String periodicidad;

    @Column(name = "tipologia")
    private String tipologia;

    @Column(name = "modo")
    private String modo;

    @Transient
    private String fechaFormatted;
    @Transient
    private String tipoProgramacionFormatted;


    public Programacion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCuadro() {
        return cuadro;
    }

    public void setCuadro(String cuadro) {
        this.cuadro = cuadro;
    }

    public Integer getBuses() {
        return buses;
    }

    public void setBuses(Integer buses) {
        this.buses = buses;
    }

    public String getTiempoExpedicion() {
        return tiempoExpedicion;
    }

    public void setTiempoExpedicion(String tiempoExpedicion) {
        this.tiempoExpedicion = tiempoExpedicion;
    }

    public Integer getExpedicionComercial() {
        return expedicionComercial;
    }

    public void setExpedicionComercial(Integer expedicionComercial) {
        this.expedicionComercial = expedicionComercial;
    }



    public Integer getLineasCargadas() {
        return lineasCargadas;
    }

    public void setLineasCargadas(Integer lineasCargadas) {
        this.lineasCargadas = lineasCargadas;
    }

    public String getRazonCambio() {
        return razonCambio;
    }

    public void setRazonCambio(String razonCambio) {
        this.razonCambio = razonCambio;
    }

    public String getTipoProgramacion() {
        return tipoProgramacion;
    }

    public void setTipoProgramacion(String tipoProgramacion) {
        this.tipoProgramacion = tipoProgramacion;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaFormatted() {
        if(fecha!=null){
            SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
            return dt1.format(fecha);
        }
          return "";
    }


    public void setFechaFormatted(String fechaFormatted) {
        this.fechaFormatted = fechaFormatted;
    }

    public Double getKmComercialIncio() {
        return kmComercialIncio;
    }

    public void setKmComercialIncio(Double kmComercialIncio) {
        this.kmComercialIncio = kmComercialIncio;
    }

    public Double getKmVacioInicio() {
        return kmVacioInicio;
    }

    public void setKmVacioInicio(Double kmVacioInicio) {
        this.kmVacioInicio = kmVacioInicio;
    }

    public Double getKmComercialFin() {
        return kmComercialFin;
    }

    public void setKmComercialFin(Double kmComercialFin) {
        this.kmComercialFin = kmComercialFin;
    }

    public Double getKmVacioFin() {
        return kmVacioFin;
    }

    public void setKmVacioFin(Double kmVacioFin) {
        this.kmVacioFin = kmVacioFin;
    }

    public String getTiempoProcesamiento() {
        return tiempoProcesamiento;
    }

    public void setTiempoProcesamiento(String tiempoProcesamiento) {
        this.tiempoProcesamiento = tiempoProcesamiento;
    }

    public Double getPorcentajeVacioFinal() {
        return porcentajeVacioFinal;
    }

    public void setPorcentajeVacioFinal(Double porcentajeVacioFinal) {
        this.porcentajeVacioFinal = porcentajeVacioFinal;
    }

    public Double getPorcentajeVacioInicio() {
        return porcentajeVacioInicio;
    }

    public void setPorcentajeVacioInicio(Double porcentajeVacioInicio) {
        this.porcentajeVacioInicio = porcentajeVacioInicio;
    }

    public String getTipoProgramacionFormatted() {
        if(tipoProgramacion.equals("N")) return "Nueva";
        SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
        if(fechaDuplicada!=null) return dt1.format(fechaDuplicada);
        return "";
    }

    public Date getFechaDuplicada() {
        return fechaDuplicada;
    }

    public void setFechaDuplicada(Date fechaDuplicada) {
        this.fechaDuplicada = fechaDuplicada;
    }

    @Override
    public String toString() {
        SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
        return dt1.format(fecha)+"-"+tipologia;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }
}
