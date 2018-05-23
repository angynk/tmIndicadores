package com.tmIndicadores.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="dh_bus_registro")
public class BusRegistro {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="BusRegistroGenerator")
    @SequenceGenerator(name="BusRegistroGenerator", sequenceName = "dh_bus_registro_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bus", nullable = false)
    private Bus bus;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "operador", nullable = false)
    private Operador operador;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cuadro", nullable = false)
    private Cuadro cuadro;

    @Column(name = "fecha")
    private Date fecha;



    public BusRegistro() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Cuadro getCuadro() {
        return cuadro;
    }

    public void setCuadro(Cuadro cuadro) {
        this.cuadro = cuadro;
    }


    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
