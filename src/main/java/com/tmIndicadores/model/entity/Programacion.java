package com.tmIndicadores.model.entity;

import javax.persistence.*;

@Entity
@Table(name="usuario")
public class Programacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="UsuarioGenerator")
    @SequenceGenerator(name="UsuarioGenerator", sequenceName = "usuario_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "cuadro")
    private String cuadro;

    @Column(name = "buses")
    private Integer buses;

    @Column(name = "km_comercial")
    private Integer kmComercial;

    @Column(name = "km_vacio")
    private Integer kmVacio;

    @Column(name = "tiempo_exp")
    private Integer tiempoExpedicion;

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

    public Integer getKmComercial() {
        return kmComercial;
    }

    public void setKmComercial(Integer kmComercial) {
        this.kmComercial = kmComercial;
    }

    public Integer getKmVacio() {
        return kmVacio;
    }

    public void setKmVacio(Integer kmVacio) {
        this.kmVacio = kmVacio;
    }

    public Integer getTiempoExpedicion() {
        return tiempoExpedicion;
    }

    public void setTiempoExpedicion(Integer tiempoExpedicion) {
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
}
