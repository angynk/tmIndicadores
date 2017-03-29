package com.tmIndicadores.view;

import com.tmIndicadores.model.entity.Programacion;
import org.primefaces.model.chart.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("ChartGenerator")
public class ChartGenerator {

    public ChartGenerator() {
    }

    public BarChartModel crearGraficaBarraBuses(List<Programacion> programacion) {
        BarChartModel barBuses = new BarChartModel();
        barBuses = initBarModel(programacion);

        barBuses.setTitle("Buses Programados");
        barBuses.setLegendPosition("ne");

        Axis xAxis = barBuses.getAxis(AxisType.X);
        xAxis.setLabel("Fecha");

        Axis yAxis = barBuses.getAxis(AxisType.Y);
        yAxis.setLabel("Buses");
        yAxis.setMin(0);
        yAxis.setMax(programacion.get(0).getBuses()+20);

        return barBuses;

    }

    public BarChartModel initBarModel(List<Programacion> programacion) {
        BarChartModel model = new BarChartModel();
        ChartSeries busesDEF = new ChartSeries();
        busesDEF.setLabel("Buses");
        for(Programacion prog:programacion){
            busesDEF.set(prog.getFecha(), prog.getBuses());
        }

        model.addSeries(busesDEF);

        return model;
    }

    public BubbleChartModel crearGraficaPuntosBuses(List<Programacion> programacion){
        BubbleChartModel bubbleModel1 = initBubbleModel(programacion);
        bubbleModel1.setTitle("Bubble Chart");
        bubbleModel1.getAxis(AxisType.X).setLabel("Price");
        Axis yAxis = bubbleModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(250);
        yAxis.setLabel("Labels");

        return bubbleModel1;

    }

    private BubbleChartModel initBubbleModel(List<Programacion> programacion){
        BubbleChartModel model = new BubbleChartModel();

        model.add(new BubbleChartSeries("Acura", 70, 183,2));
        model.add(new BubbleChartSeries("Alfa Romeo", 45, 92, 2));
        model.add(new BubbleChartSeries("AM General", 24, 104, 2));
        model.add(new BubbleChartSeries("Bugatti", 50, 123, 2));
        model.add(new BubbleChartSeries("BMW", 15, 89, 2));
        model.add(new BubbleChartSeries("Audi", 40, 180, 2));
        model.add(new BubbleChartSeries("Aston Martin", 70, 70, 2));

        return model;
    }

    public LineChartModel crearGraficaLineBuses(List<Programacion> programacion) {

        LineChartModel lineModel2 = initCategoryModel(programacion);
        lineModel2.setTitle("Category Chart");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        Axis  yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(programacion.get(0).getBuses()+20);

        return lineModel2;
    }


    private LineChartModel initCategoryModel(List<Programacion> programacion) {
        LineChartModel model = new LineChartModel();

        ChartSeries buses = new ChartSeries();
        buses.setLabel("Buses");

        for(Programacion prog:programacion){
            buses.set(prog.getFecha().toString(), prog.getBuses());
        }

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 90);
        girls.set("2008", 120);

        model.addSeries(buses);
       // model.addSeries(girls);

        return model;
    }
}
