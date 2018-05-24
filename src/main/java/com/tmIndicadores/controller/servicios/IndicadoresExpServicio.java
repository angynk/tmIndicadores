package com.tmIndicadores.controller.servicios;

import com.tmIndicadores.model.dao.CuadroDao;
import com.tmIndicadores.model.dao.DhProgramacionDao;
import com.tmIndicadores.model.dao.IndicadoresDao;
import com.tmIndicadores.model.entity.Cuadro;
import com.tmIndicadores.model.entity.DhProgramacion;
import com.tmIndicadores.model.entity.Indicadores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("IndicadoresExpServicio")
public class IndicadoresExpServicio {

    @Autowired
    IndicadoresDao indicadoresDao;

    @Autowired
    DhProgramacionDao dhProgramacionDao;

    @Autowired
    CuadroDao cuadroDao;

    public List<Indicadores> getIndicadoresByFecha(Date fechaInicio, Date fechaFin, String periocidad, String tipologia, String tipoDatos,String modo) {

        List<Indicadores> indicadores= new ArrayList<>();

        //Encontrar Programaciones para  las fechas, periodicidad y tipo de datos
        List<DhProgramacion> programaciones = dhProgramacionDao.getProgramaciones(fechaInicio,fechaFin,periocidad,modo,tipoDatos);

        if(programaciones.size()>0){
            // Encontrar los cuadros para la tipologia
            List<Cuadro> cuadros = cuadroDao.getCuadros(programaciones,tipologia);

            if(cuadros.size()>0){
                //Encontrar los indicadores asociados a los cuadros
                indicadores = indicadoresDao.getIndicadores(cuadros);
            }
        }



        return indicadores;
    }

    public List<Indicadores> obtenerTodo() {
        return indicadoresDao.getAllIndicadores();
    }
}
