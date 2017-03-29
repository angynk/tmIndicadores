package com.tmIndicadores.model.entity;

import javax.persistence.*;
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

    @Column(name = "cuadro")
    private String cuadro;

    @Column(name = "buses")
    private Integer buses;

    @Column(name = "km_comerciales")
    private Double kmComercial;

    @Column(name = "km_vacio")
    private Double kmVacio;

    @Column(name = "tiempo_exp")
    private String tiempoExpedicion;

    @Column(name = "exp_comercial")
    private Integer expedicionComercial;

    @Column(name = "por_vacio_final")
    private Integer porcentajeVacioFinal;

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

    public Double getKmComercial() {
        return kmComercial;
    }

    public void setKmComercial(Double kmComercial) {
        this.kmComercial = kmComercial;
    }

    public Double getKmVacio() {
        return kmVacio;
    }

    public void setKmVacio(Double kmVacio) {
        this.kmVacio = kmVacio;
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

    public Integer getPorcentajeVacioFinal() {
        return porcentajeVacioFinal;
    }

    public void setPorcentajeVacioFinal(Integer porcentajeVacioFinal) {
        this.porcentajeVacioFinal = porcentajeVacioFinal;
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
}
