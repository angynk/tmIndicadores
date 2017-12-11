package com.tmIndicadores.controller;

import com.tmIndicadores.controller.servicios.ProgramacionServicios;
import com.tmIndicadores.controller.servicios.UsuarioServicios;
import com.tmIndicadores.model.entity.Tarea;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component("myBean")
public class ScheduledJob {

    public String emailTroncal = "german.ramirez@transmilenio.gov.co";
   public String emailEnvio = "appsfortm@gmail.com";

    @Autowired
    private ProgramacionServicios programacionServicios;

    @Autowired
    private UsuarioServicios usuarioServicios;

    @Autowired
    private MailMail mailMail;

    public void printMessage() {
        emailEnvio = encontrarEmailSuperUsuario();
        validarCargaDatosTroncal();
        validarCargaDatosDual();

    }

    private String encontrarEmailSuperUsuario() {
        return usuarioServicios.getEmailSuperUsuario();
    }

    private void validarCargaDatosDual() {
        Date lastFecha = programacionServicios.getLastProgramacionFecha("DUA", "DU-DEF","HABIL");
        Date hoy = new Date();
        int diferencia = diferenciaEnDias(hoy,lastFecha);
        if(diferencia>15){
            System.out.println("Hace 15 días no actualiza la información");
        }
    }

    private void validarCargaDatosTroncal() {
        if(datosActualizados("TRO","DEF","HABIL")){
            if(datosActualizados("TRO","DEF","SABADO")){
                if(!datosActualizados("TRO","DEF","FESTIVO")){
                    enviarEmail("TRO");
                }
            }else {
                enviarEmail("TRO");
            }
        }else{
            enviarEmail("TRO");
        }
    }

    private boolean datosActualizados(String modo, String tipologia, String dia){
        Date lastFecha = programacionServicios.getLastProgramacionFecha(modo, tipologia,dia);
        Date hoy = new Date();
        int diferencia = diferenciaEnDias(hoy,lastFecha);
        if(diferencia>10){
                return false;
        }

        return true;
    }

    private void enviarEmail(String modo) {
        emailTroncal = validarEmail(modo);
        System.out.println("Hace 10 días no actualiza la información");
        mailMail.sendMail(emailEnvio,
                emailTroncal,
                "Indicadores BRT",
                "Hola! \n\n La información de la programaciòn Troncal se encuentra desactualizada" +
                        " \n\n Para actualizarla ingresa a :    http://192.168.100.121:8000/tmIndicadores-1.0-SNAPSHOT/login.xhtml" +
                        "\n\n  Muchas Gracias");
    }

    private String validarEmail(String modo) {
        if(modo.equals("TRO")){
            Tarea tarea = usuarioServicios.encontrarTareaByNombre("Cargar Indicadores Troncal");
            emailTroncal = tarea.getUsuario().getEmail();
        }
        return emailTroncal;
    }

    private int diferenciaEnDias(Date hoy, Date lastFecha) {
        long diferenciaEn_ms = hoy.getTime() - lastFecha.getTime();
        long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);
        return (int) dias;
    }

    public String getEmailTroncal() {
        return emailTroncal;
    }

    public void setEmailTroncal(String emailTroncal) {
        this.emailTroncal = emailTroncal;
    }

    public ProgramacionServicios getProgramacionServicios() {
        return programacionServicios;
    }

    public void setProgramacionServicios(ProgramacionServicios programacionServicios) {
        this.programacionServicios = programacionServicios;
    }

    public UsuarioServicios getUsuarioServicios() {
        return usuarioServicios;
    }

    public void setUsuarioServicios(UsuarioServicios usuarioServicios) {
        this.usuarioServicios = usuarioServicios;
    }

    public MailMail getMailMail() {
        return mailMail;
    }

    public void setMailMail(MailMail mailMail) {
        this.mailMail = mailMail;
    }
}
