package com.tmIndicadores.controller;

import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("myBean")
public class ScheduledJob {

    public String emailTroncal = "asig.natalycz@gmail.com";

    @Autowired
    private ProgramacionServicios programacionServicios;
    @Autowired
    private MailMail mailMail;

    public void printMessage() {
        validarCargaDatosTroncal();
        validarCargaDatosDual();

    }

    private void validarCargaDatosDual() {
        Date lastFecha = programacionServicios.getLastProgramacionFecha("DUA", "DU-DEF");
        Date hoy = new Date();
        int diferencia = diferenciaEnDias(hoy,lastFecha);
        if(diferencia>15){
            System.out.println("Hace 15 días no actualiza la información");
        }
    }

    private void validarCargaDatosTroncal() {
        Date lastFecha = programacionServicios.getLastProgramacionFecha("TRO", "DEF");
        Date hoy = new Date();
        int diferencia = diferenciaEnDias(hoy,lastFecha);
        if(diferencia>15){
            System.out.println("Hace 15 días no actualiza la información");
            enviarEmail("TRO");
        }
    }

    private void enviarEmail(String modo) {
        mailMail.sendMail("appsfortm@gmail.com",
                emailTroncal,
                "Indicadores BRT",
                "Hola! \n\n La información de la programaciòn Troncal se encuentra desactualizada" +
                        " \n\n Para actualizarla ingresa a :  " +
                        "\n\n  Muchas Gracias");
    }

    private int diferenciaEnDias(Date hoy, Date lastFecha) {
        long diferenciaEn_ms = hoy.getTime() - lastFecha.getTime();
        long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
        return (int) dias;
    }

}
