package com.tmIndicadores.view;


import java.util.List;

public class Point {

    private List<Long> data;

    public Point(List<Long> data) {
        this.data = data;

    }

    public Point() {
    }

    public List<Long> getData() {
        return data;
    }

    public void setData(List<Long> data) {
        this.data = data;
    }
}
