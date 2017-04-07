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

function createOption(titulo,ejeX){
    var options = {
        chart: {
            type: 'scatter'
        },
        xAxis: {
            type: 'datetime',
            title: {
                text: 'Fecha'
            },
            startOnTick: true,
            endOnTick: true,
        },
        yAxis: {
            title: {
                text: ejeX
            }
        },
        title: {
            text: titulo
        },
        series: [{
            regression: true ,
            "regressionSettings": {"type": 'linear'},
            name: 'habil',
            type :'scatter',
            data :[]}]
        //data:[  [1, 1],
        //    [2, 3],
        //    [3, 9]]}]
    };

    return options;
};

function convertDateToUTC(series){
    for(i = 0; i < series.length; i++){
        var data = series[i].data;
        for (j = 0; j < data.length; j++){
            var date = new Date(data[j][0]);
            data[j][0]= Date.UTC(date.getFullYear(),date.getMonth(),date.getDate())
        }
    }

    return series;
}

function renderChartLine(divId, chartType, chartTitle, chartData, categories,tipoGrafica){
    if(tipoGrafica == 'Lineas'){
        var titulo = document.getElementById("form:hiddenTittle").value;
        var tituloX = document.getElementById("form:hiddenTittleX").value;
        var options = createOptionLine(titulo,tituloX);
        var series = $.parseJSON(document.getElementById("form:hiddenForLine").value);
        series = convertDateToUTC(series);
        options.series = series;
        var chart = new Highcharts.Chart(divId,options);
    }else if (tipoGrafica == 'Tendencia'){
        var titulo = document.getElementById("form:hiddenTittle").value;
        var ejeX = document.getElementById("form:hiddenTittleX").value;
        var options = createOption(titulo,ejeX);
        var series =  $.parseJSON(document.getElementById("form:hidden").value);
        series = convertDateToUTC(series);
        options.series[0].data =series[0].data;
        var chart = new Highcharts.Chart(divId,options);
    }else if(tipoGrafica == 'Barras'){
        var titulo = document.getElementById("form:hiddenTittle").value;
        var ejeX = document.getElementById("form:hiddenTittleX").value;
        var options = createOptionBar(titulo,ejeX);
        var series = $.parseJSON(document.getElementById("form:hiddenSForBar").value);
        series = convertDateToUTC(series);
        options.series = series;
        var categories = document.getElementById("form:hiddenCForBar").value;
        var chart = new Highcharts.Chart(divId,options);
    }

    console.log(tipoGrafica);

}

function createOptionBar(titulo,ejeX){
    var options = {
        chart: {
            type: 'column'
        },
        title: {
            text: titulo
        },
        xAxis: {
            type: 'datetime',
            title: {
                text: 'Fecha'
            }
        },
        yAxis: {
            min: 0,
            title: {
                text: ejeX
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

function createOptionLine(titulo,tituloX){
    var options = {
        title: {
            text: titulo
        }
        ,
        xAxis: {
        type: 'datetime',
            title: {
            text: 'Fecha'
        }
    },

        yAxis: {
            title: {
                text: tituloX
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },

        series: []};

    return options;
};
