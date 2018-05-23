package com.tmIndicadores.model.entity;

import javax.persistence.*;

@Entity
@Table(name="dh_indicadores")
public class Indicadores {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="IndicadorGenerator")
    @SequenceGenerator(name="IndicadorGenerator", sequenceName = "dh_indicadores_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cuadro_id", nullable = false)
    private Cuadro cuadro;

    @Column(name = "numero_buses")
    private Integer numBuses;

    @Column(name = "km_comerciales")
    private Double kmComerciales;

    @Column(name = "km_vacio_total")
    private Double kmVacioTotal;

    @Column(name = "km_vex")
    private Double kmVacioVEX;

    @Column(name = "km_vh")
    private Double kmVacioVH;

    @Column(name = "km_vpa")
    private Double kmVacioVPA;

    @Column(name = "porcentaje_vacio")
    private Double porcentajeVacio;


    public Indicadores() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cuadro getCuadro() {
        return cuadro;
    }

    public void setCuadro(Cuadro cuadro) {
        this.cuadro = cuadro;
    }

    public Integer getNumBuses() {
        return numBuses;
    }

    public void setNumBuses(Integer numBuses) {
        this.numBuses = numBuses;
    }

    public Double getKmComerciales() {
        return kmComerciales;
    }

    public void setKmComerciales(Double kmComerciales) {
        this.kmComerciales = kmComerciales;
    }

    public Double getKmVacioTotal() {
        return kmVacioTotal;
    }

    public void setKmVacioTotal(Double kmVacioTotal) {
        this.kmVacioTotal = kmVacioTotal;
    }

    public Double getKmVacioVEX() {
        return kmVacioVEX;
    }

    public void setKmVacioVEX(Double kmVacioVEX) {
        this.kmVacioVEX = kmVacioVEX;
    }

    public Double getKmVacioVH() {
        return kmVacioVH;
    }

    public void setKmVacioVH(Double kmVacioVH) {
        this.kmVacioVH = kmVacioVH;
    }

    public Double getKmVacioVPA() {
        return kmVacioVPA;
    }

    public void setKmVacioVPA(Double kmVacioVPA) {
        this.kmVacioVPA = kmVacioVPA;
    }

    public Double getPorcentajeVacio() {
        return porcentajeVacio;
    }

    public void setPorcentajeVacio(Double porcentajeVacio) {
        this.porcentajeVacio = porcentajeVacio;
    }
}
