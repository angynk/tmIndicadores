package com.tmIndicadores.view;

import org.primefaces.model.chart.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


@Service("ChartGenerator")
public class ChartGenerator {

    public ChartGenerator() {
    }

    public BarChartModel crearGraficaBarraBuses() {
        BarChartModel barBuses = new BarChartModel();
        barBuses = initBarModel();

        barBuses.setTitle("Buses Programados");
        barBuses.setLegendPosition("ne");

        Axis xAxis = barBuses.getAxis(AxisType.X);
        xAxis.setLabel("Fecha");

        Axis yAxis = barBuses.getAxis(AxisType.Y);
        yAxis.setLabel("Buses");
        yAxis.setMin(0);
        yAxis.setMax(200);

        return barBuses;

    }

    public BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();

        ChartSeries busesDEF = new ChartSeries();
        busesDEF.setLabel("Buses");
        busesDEF.set("2004", 120);
        busesDEF.set("2005", 100);
        busesDEF.set("2006", 44);
        busesDEF.set("2007", 150);
        busesDEF.set("2008", 25);
        model.addSeries(busesDEF);

        return model;
    }

    public LineChartModel crearGraficaLineBuses() {
        LineChartModel lineModel1 =initLinearModel();
        lineModel1.setTitle("Linear Chart");
        lineModel1.setLegendPosition("e");
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);

        LineChartModel lineModel2 = initCategoryModel();
        lineModel2.setTitle("Category Chart");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        yAxis = lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200);

        return lineModel2;
    }

    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();

        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");

        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);

        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");

        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);

        model.addSeries(series1);
        model.addSeries(series2);

        return model;
    }

    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();

        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 120);
        boys.set("2005", 100);
        boys.set("2006", 44);
        boys.set("2007", 150);
        boys.set("2008", 25);

        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 110);
        girls.set("2007", 90);
        girls.set("2008", 120);

        model.addSeries(boys);
        model.addSeries(girls);

        return model;
    }
}
