package com.tmIndicadores.controller;

import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("DuplicarProcessor")
public class DuplicarProgramacionProcessor {

    @Autowired
    private ProgramacionServicios programacionServicios;

    private List<LogDatos> logDatos;
    private static Logger log = Logger.getLogger(IndicadoresGoalProcessor.class);

    public List<LogDatos> duplicarProgramacion(Date fechaADuplicar, String fechas){
        logDatos = new ArrayList<>();
        logDatos.add(new LogDatos("<<Inicio Duplicacion programacion>>", TipoLog.INFO));

        return logDatos;
    }
}
