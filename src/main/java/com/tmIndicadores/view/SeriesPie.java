package com.tmIndicadores.view;

import java.util.List;


public class SeriesPie {

    private String name;
    private List<DataComposed> data;

    public SeriesPie() {
    }

    public SeriesPie(String name, List<DataComposed> data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataComposed> getData() {
        return data;
    }

    public void setData(List<DataComposed> data) {
        this.data = data;
    }
}
