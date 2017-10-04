package com.tmIndicadores.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 04/10/2017.
 */
public class ModosUtil {

    public static  List<ListObject> cargarListaTipologiaTroncal() {
        List<ListObject> tipologias = new ArrayList<ListObject>();
        tipologias.add(new ListObject("Articulado","ART"));
        tipologias.add(new ListObject("Biarticulado","BI"));
        tipologias.add(new ListObject("Definitivo Troncal","DEF"));
        return tipologias;
    }

    public static List<ListObject> cargarListaTipologiaDual() {
        List<ListObject> tipologias = new ArrayList<ListObject>();
        tipologias.add(new ListObject("Dual Hibrído","DU-HI"));
        tipologias.add(new ListObject("Dual Disel","DU-DI"));
        tipologias.add(new ListObject("Definitivo Dual","DU-DEF"));
        return tipologias;
    }

    public static List<ListObject> cargarListaModos(){
        List<ListObject> modos =  new ArrayList<ListObject>();
        modos.add(new ListObject("Troncal","TRO"));
        modos.add(new ListObject("Dual","DUA"));
        return modos;
    }

    public static String getModoPorDEF(String DEF){
        if(DEF.equals("DU-DEF")) return "DUA";
        return "TRO";
    }
}
