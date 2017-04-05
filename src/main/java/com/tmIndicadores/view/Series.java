package com.tmIndicadores.view;


import java.util.List;

public class Series {
    private String name;
    private String type;
//    private boolean regression;
//    private RegressionSettings regressionSettings;
    private List<List<Object>> data;

    public Series() {
    }

    public Series(String name, List<List<Double>> data,String type) {
        this.name = name;
//        this.data = data;
        this.type = type;
    }

//    public Series(String name, List<List<Double>>data) {
//        this.name = name;
////        this.data = data;
//    }

    public Series(String name, List<List<Object>>data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}