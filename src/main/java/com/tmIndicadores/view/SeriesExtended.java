package com.tmIndicadores.view;

import java.util.List;


public class SeriesExtended extends Series{
    private String name;
    private String type;
    private List<List<Double>> data;

    public SeriesExtended() {
    }

    public SeriesExtended(String name, List<List<Double>> data,String type) {
        this.name = name;
        this.data = data;
        this.type = type;
    }

    public SeriesExtended(String name, List<List<Double>>data) {
        this.name = name;
        this.data = data;
    }
}
