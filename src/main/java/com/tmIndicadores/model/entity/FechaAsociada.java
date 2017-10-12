package com.tmIndicadores.model.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="programacion_fecha_asociada")
public class FechaAsociada {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="PGeneratorFecha")
    @SequenceGenerator(name="PGeneratorFecha", sequenceName = "programacion_fecha_asociada_id_seq",allocationSize=1)
    @Column(name = "id")
    private long id;

    @Column(name = "fecha")
    private Date fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "programacion", nullable = false)
    private Programacion programacion;

    public FechaAsociada() {
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

    public Programacion getProgramacion() {
        return programacion;
    }

    public void setProgramacion(Programacion programacion) {
        this.programacion = programacion;
    }
}
