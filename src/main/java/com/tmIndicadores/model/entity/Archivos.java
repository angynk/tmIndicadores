package com.tmIndicadores.model.entity;

import javax.persistence.*;

@Entity
@Table(name="dh_archivo")
public class Archivos {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="ArchivoGenerator")
    @SequenceGenerator(name="ArchivoGenerator", sequenceName = "dh_archivo_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "grupo")
    private String grupo;

    @Column(name = "adjuntado")
    private boolean adjuntado;


    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cuadro", nullable = false)
    private Cuadro cuadro;

    public Archivos() {
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
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

    public boolean isAdjuntado() {
        return adjuntado;
    }

    public void setAdjuntado(boolean adjuntado) {
        this.adjuntado = adjuntado;
    }
}
