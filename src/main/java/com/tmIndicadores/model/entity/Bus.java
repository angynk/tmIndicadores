package com.tmIndicadores.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="dh_bus")
public class Bus {

    @Id
    @Column(name = "numero")
    private String numero;

    @Column(name = "fecha_creacion")
    private Date fechaCreacion;

    @Column(name = "modo")
    private String modo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bus")
    private Set<BusRegistro> busesLista = new HashSet<BusRegistro>(0);


    public Bus() {
    }

    public Bus(String numero, Date fechaCreacion, String modo, Set<BusRegistro> busesLista) {
        this.numero = numero;
        this.fechaCreacion = fechaCreacion;
        this.modo = modo;
        this.busesLista = busesLista;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public Set<BusRegistro> getBusesLista() {
        return busesLista;
    }

    public void setBusesLista(Set<BusRegistro> busesLista) {
        this.busesLista = busesLista;
    }
}
