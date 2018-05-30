package com.tmIndicadores.controller;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;


public class Util {

    public static HttpSession getSession(){
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest(){
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static List<String> listaDePeriocidad(){
        List<String> periocidad = new ArrayList<>();
        periocidad.add("Habil");
        periocidad.add("Festivo");
        periocidad.add("Sabado");
        return periocidad;
    }

    public static List<String> listaDeTipologia(){
        List<String> tipologia = new ArrayList<>();
        tipologia.add("ART");
        tipologia.add("BI");
        tipologia.add("DEF");
        return tipologia;
    }

    public static List<String> listaDeTipoGrafica(){
        List<String> tipoGrafica = new ArrayList<>();
        tipoGrafica.add("Con Saltos");
        tipoGrafica.add("Con Repeticiones");
        return tipoGrafica;
    }

    public static String convertirAString(Date fecha) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        return sdfDate.format(fecha);
    }

    public static String convertirModo(String modo) {
        if (Modos.TRONCAL.equals(modo)){
            return "T";
        }else if (Modos.ALIMENTADOR.equals(modo)){
            return "A";
        }
        return "D";
    }

    public static String md5(String data) {
        // Get the algorithm:
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // Calculate Message Digest as bytes:
        byte[] digest = md5.digest(data.getBytes(UTF_8));
        // Convert to 32-char long String:
        return DatatypeConverter.printHexBinary(digest);
    }

    public static List<String> listaTipoIndicadores() {
        List<String> tipoGrafica = new ArrayList<>();
        tipoGrafica.add(TipoIndicador.GOAL_BUS);
        tipoGrafica.add(TipoIndicador.EXPEDICIONES);
        return tipoGrafica;

    }

    public static List<ListObject> listaIndicadoresGoalBus() {
        List<ListObject> lista = new ArrayList<>();
        lista.add(new ListObject("Numero de Buses","NB"));
        lista.add(new ListObject("KM Comerciales","KC"));
        lista.add(new ListObject("KM Vacios","KV"));
        lista.add(new ListObject("EXP Comercial","EC"));
        lista.add(new ListObject("% Vacios","PB"));
        lista.add(new ListObject("Líneas GoalBus","LC"));
        lista.add(new ListObject("Número de Servicios","NS"));
        lista.add(new ListObject("Velocidad Comercial","VC"));
        lista.add(new ListObject("Horas Por Buses","HB"));
        return lista;
    }


    public static List<ListObject> listaIndicadoresExpediciones() {
        List<ListObject> lista = new ArrayList<>();
        lista.add(new ListObject("Numero de Buses","NB"));
        lista.add(new ListObject("KM Comerciales","KC"));
        lista.add(new ListObject("KM Vacios","KV"));
        lista.add(new ListObject("KM Vacios VPA","KP"));
        lista.add(new ListObject("KM Vacios VEX","KE"));
        lista.add(new ListObject("KM Vacios VH","KH"));
        lista.add(new ListObject("% Vacios","PB"));
        return lista;

    }
}
