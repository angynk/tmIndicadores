package com.tmIndicadores.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="dh_programacion")
public class DhProgramacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ProgramacionGenerator")
    @SequenceGenerator(name="ProgramacionGenerator", sequenceName = "dh_programacion_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "jornada")
    private String jornada;

    @Column(name = "identificador")
    private String identificador;

    @Column(name = "tipo_dia")
    private String tipoDia;

    @Column(name = "modo")
    private String modo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "dias_aplica")
    private Integer diasAplica;

    @Column(name = "tipo_programacion")
    private String tipoProgramacion;


    @Column(name = "fecha_duplicado")
    private Date fechaDuplicado;



    public DhProgramacion(Date fecha, String jornada, String identificador) {
        this.fecha = fecha;
        this.jornada = jornada;
        this.identificador = identificador;
    }

    public DhProgramacion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTipoDia() {
        return tipoDia;
    }

    public void setTipoDia(String tipoDia) {
        this.tipoDia = tipoDia;
    }


    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Integer getDiasAplica() {
        return diasAplica;
    }

    public void setDiasAplica(Integer diasAplica) {
        this.diasAplica = diasAplica;
    }

    public String getTipoProgramacion() {
        return tipoProgramacion;
    }

    public void setTipoProgramacion(String tipoProgramacion) {
        this.tipoProgramacion = tipoProgramacion;
    }


    public Date getFechaDuplicado() {
        return fechaDuplicado;
    }

    public void setFechaDuplicado(Date fechaDuplicado) {
        this.fechaDuplicado = fechaDuplicado;
    }
}
