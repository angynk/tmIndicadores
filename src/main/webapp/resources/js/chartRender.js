//function renderChart(divId, chartType, chartTitle, chartData, categories){
//    console.log("entre");
//    console.log(chartData);
//    if(chartData!=null){
//        var options = createOption(divId, chartType, chartTitle, categories);
//        options.series = $.parseJSON(chartData);
//        console.log(chartData);
//        var series = document.getElementById("form:hidden").value;
//        console.log(series);
//        console.log("NOOO");
//        console.log( document.getElementById("form:hidden"));
//        var chart = new Highcharts.Chart(divId,options);
//    }
//
//}
var highchartsOptions = Highcharts.setOptions({
        lang: {
            loading: 'Espere...',
            months: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre'],
            shortMonths: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'],
            weekdays: ['Domingo','Lunes', 'Martes', 'Miercoles', 'Jueves', 'Viernes', 'Sabado'],
            shortWeekdays: ['Sun', 'Mån', 'Tys', 'Ons', 'Tors', 'Fre', 'Laur'],
            exportButtonTitle: "Exportar",
            printButtonTitle: "Imprimir",
            downloadPNG: 'Descargar imagen PNG',
            downloadJPEG: 'Descargar imagen JPEG',
            downloadPDF: 'Descargar documento PDF',
            downloadSVG: 'Descargar imagen SVG',
            downloadCSV: 'Descargar archivo CSV',
            downloadXLS: 'Descargar archivo XLS',
            printChart: 'Imprimir Gráfica'

        }
    }
);
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
        tooltip: {
            headerFormat: '<b>{point.key}</b><br/>',
            pointFormat: '{point.y}'
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

function renderChartLine(divId, chartType, chartTitle, chartData, categories,tipoGrafica,period){

    document.getElementById("containerSabado").style.visibility = 'hidden';
    document.getElementById("containerFestivo").style.visibility = 'hidden';
    if(tipoGrafica == 'Lineas'){
        var titulo = document.getElementById("form:hiddenTittle").value;
        var tituloX = document.getElementById("form:hiddenTittleX").value;
        var options = createOptionLine(titulo,tituloX);
        var series = $.parseJSON(document.getElementById("form:hiddenForLine").value);
        series = convertDateToUTC(series);
        options.series = series;
        options.tooltip.pointFormat = options.tooltip.pointFormat+" "+tituloX;
        var chart = new Highcharts.Chart(divId,options);
    }else if (tipoGrafica == 'Tendencia'){
        var titulo = document.getElementById("form:hiddenTittle").value ;
        var ejeX = document.getElementById("form:hiddenTittleX").value;
        var options = createOption(titulo,ejeX);
        var series =  $.parseJSON(document.getElementById("form:hidden").value);
        series = convertDateToUTC(series);
        options.series[0].data =series[0].data;
        options.tooltip.pointFormat = options.tooltip.pointFormat+" "+ejeX;
        var chart = new Highcharts.Chart(divId,options);
        if(period == 'TODOS'){
            document.getElementById("containerSabado").style.visibility  = 'visible';
            document.getElementById("containerFestivo").style.visibility  = 'visible';
            var optionsSabado = createOption(document.getElementById("form:hiddenTittleSabado").value,ejeX);
            var seriesSabado =  $.parseJSON(document.getElementById("form:hiddenSabado").value);
            seriesSabado = convertDateToUTC(seriesSabado);
            console.log(seriesSabado);
            optionsSabado.series[0].data =seriesSabado[0].data;
            optionsSabado.tooltip.pointFormat = options.tooltip.pointFormat+" "+ejeX;
            var chartSabado = new Highcharts.Chart('containerSabado',optionsSabado);

            var optionsFestivo = createOption(document.getElementById("form:hiddenTittleFestivo").value,ejeX);
            var seriesFestivo =  $.parseJSON(document.getElementById("form:hiddenFestivo").value);
            seriesFestivo = convertDateToUTC(seriesFestivo);
            optionsFestivo.series[0].data =seriesFestivo[0].data;
            optionsFestivo.tooltip.pointFormat = options.tooltip.pointFormat+" "+ejeX;
            var chartFestivo = new Highcharts.Chart('containerFestivo',optionsFestivo);

        }

    }else if(tipoGrafica == 'Barras'){
        var titulo = document.getElementById("form:hiddenTittle").value;
        var ejeX = document.getElementById("form:hiddenTittleX").value;
        var options = createOptionBar(titulo,ejeX);
        var series = $.parseJSON(document.getElementById("form:hiddenSForBar").value);
        series = convertDateToUTC(series);
        options.series = series;
        options.tooltip.pointFormat = options.tooltip.pointFormat+" "+ejeX;
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
            headerFormat: '<b>{point.key}</b><br/>',
            pointFormat: '{point.y:.1f}'
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
    }
        ,
        tooltip: {
            headerFormat: '<b>{point.key}</b><br/>',
            pointFormat:  '{point.y:.1f}',
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

function createOptionArea(titulo,tituloX){
    var options = {
        chart: {
            type: 'areaspline'
        },
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
        tooltip: {
            crosshairs: true,
            formatter: function () {
                var s = '<b>' + Highcharts.dateFormat('%e - %b - %Y',
                        new Date(this.x))  + '</b>';
                var comer = 0;
                var vacio = 1;

                $.each(this.points, function () {
                    s += '<br/>' + this.series.name + ': ' +
                        Highcharts.numberFormat(this.y, 2)+ ' Km';
                    if(this.series.name == 'KM Comerciales'){
                        comer = this.y;
                    }else{
                        vacio = this.y;
                    }
                });
                s += '<br/>' +"% Vacío: "+  Highcharts.numberFormat((vacio/comer)*100, 2) +' %';
                return s;
            },
            shared: true
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

$(".container").each(function() {
    var jThis = $(this),
        chartSelector = jThis.data("chartSelector"),
        chart = $(chartSelector).highcharts();

    $("*[data-type]", this).each(function() {
        var jThis = $(this),
            type = jThis.data("type");
        if(Highcharts.exporting.supports(type)) {
            jThis.click(function() {
                chart.exportChartLocal({ type: type });
            });
        }
        else {
            jThis.attr("disabled", "disabled");
        }
    });
});

function myfunction() {
    var container = document.getElementById("contain");
    var options = createOptionLine("Kilometros comerciales","KM");
    var series = $.parseJSON(document.getElementById("myForm:hiddenChartLine").value);
    series = convertDateToUTC(series);
    options.series = series;
    console.log(JSON.stringify(options));
    var chart = new Highcharts.Chart(container.id,options);
}
function myfunctionAreaHabil() {
    var container = document.getElementById("containArea");
    var options = createOptionArea("KM Comerciales Vs Vacio","KM");
    var series = $.parseJSON(document.getElementById("myForm:hiddenChartPie").value);
    series = convertDateToUTC(series);
    options.series = series;
    console.log(JSON.stringify(options));
    var chart = new Highcharts.Chart(container.id,options);

}

function myfunctionAreaSabado() {
    var container = document.getElementById("containAreaS");
    var options = createOptionArea("KM Comerciales Vs Vacio","KM");
    var series = $.parseJSON(document.getElementById("myForm:hiddenChartPieS").value);
    series = convertDateToUTC(series);
    options.series = series;
    console.log(JSON.stringify(options));
    var chart = new Highcharts.Chart(container.id,options);

}

function myfunctionAreaFestivo() {
    var container = document.getElementById("containAreaF");
    var options = createOptionArea("KM Comerciales Vs Vacio","KM");
    var series = $.parseJSON(document.getElementById("myForm:hiddenAreaFestivo").value);
    series = convertDateToUTC(series);
    options.series = series;
    console.log(JSON.stringify(options));
    var chart = new Highcharts.Chart(container.id,options);

}

function myfunctionPieSabado() {
    var containerS = document.getElementById("containPieS");
    var optionsS = createOptionPie("Sabado");
    var seriesS = $.parseJSON(document.getElementById("hiddenChartPieS").value);
    console.log(seriesS);
    optionsS.series = seriesS;
    console.log(JSON.stringify(optionsS));
    var chartS = new Highcharts.Chart(containerS.id,optionsS);
}

function createOptionPie(titulo){
    var options = {
        chart: {
            type: 'pie'
        },
        title: {
            text: titulo
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        }
        ,
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: []};

    return options;
};
