function renderChart(divId, chartType, chartTitle, chartData, categories){
    console.log("entre");
    console.log(chartData);
    if(chartData!=null){
        var options = createOption(divId, chartType, chartTitle, categories);
        options.series = $.parseJSON(chartData);
        console.log(chartData);
        var series = document.getElementById("form:hidden").value;
        console.log(series);
        console.log("NOOO");
        console.log( document.getElementById("form:hidden"));
        var chart = new Highcharts.Chart(divId,options);
    }

}

function createOption(divId, chartType, chartTitle, categories){
    var options = {
        xAxis: {
            min: -0.5,
            max: 5.5
        },
        yAxis: {
            min: 0
        },
        title: {
            text: 'Scatter plot with regression line'
        },
        series: []
    };

    return options;
};

function renderChartLine(divId, chartType, chartTitle, chartData, categories,tipoGrafica){
    if(tipoGrafica == 'Lineas'){
        var options = createOptionLine();
        var series = document.getElementById("form:hiddenForLine").value;
        console.log(series);
        options.series = $.parseJSON(series);
        var chart = new Highcharts.Chart(divId,options);
    }else if (tipoGrafica == 'Tendencia'){
        var options = createOption(divId, chartType, chartTitle, categories);
        var series = document.getElementById("form:hidden").value;
        options.series = $.parseJSON(series);
        var chart = new Highcharts.Chart(divId,options);
    }else if(tipoGrafica == 'Barras'){
        var options = createOptionBar();
        var series = document.getElementById("form:hiddenSForBar").value;
        console.log(series);
        options.series = $.parseJSON(series);
        var categories = document.getElementById("form:hiddenCForBar").value;
        options.xAxis.categories = $.parseJSON(categories);
        var chart = new Highcharts.Chart(divId,options);
    }

    console.log(tipoGrafica);

}

function createOptionBar(){
    var options = {
        chart: {
            type: 'column'
        },
        title: {
            text: 'Monthly Average Rainfall'
        },
        subtitle: {
            text: 'Source: WorldClimate.com'
        },
        xAxis: {
            categories: [],
            crosshair: true
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Rainfall (mm)'
            }
        },
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
            '<td style="padding:0"><b>{point.y:.1f} mm</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: []
    };

    return options;
}

function createOptionLine(){
    var options = {
        title: {
            text: 'Solar Employment Growth by Sector, 2010-2016'
        },

        subtitle: {
            text: 'Source: thesolarfoundation.com'
        },

        yAxis: {
            title: {
                text: 'Number of Employees'
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },

        plotOptions: {
            series: {
                pointStart: 2010
            }
        },

        series: []};

    return options;
};
