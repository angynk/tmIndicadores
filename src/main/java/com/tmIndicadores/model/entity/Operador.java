package com.tmIndicadores.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="dh_operador")
public class Operador {

    @Id
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "letra")
    private String letra;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operador")
    private Set<BusRegistro> busesLista = new HashSet<BusRegistro>(0);

    public Operador() {
    }

    public Operador(Integer codigo, String letra, String nombre) {
        this.codigo = codigo;
        this.letra = letra;
        this.nombre = nombre;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<BusRegistro> getBusesLista() {
        return busesLista;
    }

    public void setBusesLista(Set<BusRegistro> busesLista) {
        this.busesLista = busesLista;
    }
}
