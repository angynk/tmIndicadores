package com.tmIndicadores.view;


import java.util.List;

public class Series {
    private String name;
    private String type;
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

}