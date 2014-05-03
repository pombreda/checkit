function minusSeconds(time, seconds) {
    var regex = /(\d{4})-(\d{2})-(\d{2}) (\d{2}):(\d{2}):(\d{2})/;
    var partTime = time.match(regex);
    var date = new Date(partTime[1], partTime[2] - 1, partTime[3], partTime[4], partTime[5], partTime[6] - seconds);

    var year = date.getFullYear();
    var month = date.getMonth() + 1; 
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds(); 

    if (month < 10) {
        month = '0' + month;
    }
    if (day < 10) {
        day = '0' + day;
    }   
    if (hour < 10) {
        hour = '0' + hour;
    }
    if (minute < 10) {
        minute = '0' + minute;
    }
    if (second < 10) {
        second = '0' + second;
    }   

    return year + '-' + month + '-' + day + ' ' + hour + ':' + minute + ':' + second;
}

$(document).ready(function(){ 
    var pieOptions = {
        grid: {
            drawBorder: false, 
            drawGridlines: false,
            background: '#ffffff',
            shadow:false
        },
        axesDefaults: {
        },
        seriesDefaults:{
            renderer:$.jqplot.PieRenderer,
            rendererOptions: {
                showDataLabels: true,
                dataLabelFormatString: '%.2f %',
                highlightMouseOver: false,
                highlightMouseDown: false,
                highlightColor: null,
                dataLabelThreshold: 1
            }
        },
        seriesColors: ["#97df71", "#dc6b6b", "#b6b6b6"],
        legend: {
            show: true,
            rendererOptions: {
                numberRows: 1
            },
            location: 's'
        }
    };
    
    var lastDayOptions = {
        grid: {
            drawBorder: false,
            shadow: false,
            background: 'rgba(0,0,0,0)'
        },
        seriesDefaults: { 
            shadowAlpha: 0.1,
            shadowDepth: 2,
            fillToZero: true,
            rendererOptions: {
                highlightMouseOver: false,
                highlightMouseDown: false,
                highlightColor: null
            }
        },
        series: [
            {
                color: '#cccccc',
                fillColor: '#97df71',
                negativeColor: '#dc6b6b',
                showMarker: false,
                showLine: true,
                lineWidth: 1,
                fill: true,
                fillAndStroke: true,
                rendererOptions: {
                    smooth: true
                }
            }
        ],
        axesDefaults: {
            rendererOptions: {
                drawBaseline: false
            },
            pad: 0
        },
        axes: {       
            xaxis: {
                renderer: $.jqplot.DateAxisRenderer
            },
            yaxis: {
                min: -1,
                max: 1,
                tickOptions: {
                    show: false,
                    showLabel: false
                }
            }
        }
    };
    
    var pieWeek = $.jqplot('pieWeek', [pieWeekSlices], pieOptions); 
    var pieMonth = $.jqplot('pieMonth', [pieMonthSlices], pieOptions);
    var pieAll = $.jqplot('pieAll', [pieAllSlices], pieOptions); 
    
    var lastDayData = new Array();
    var status, time;
    var lastStatus = 0;
    for (var i=0; i<lastDayBasicData.length; i++) {
        if (lastDayBasicData[i].status === "U") status = 1;
        else if (lastDayBasicData[i].status === "D") status = -1;
        else status = 0;
        time = lastDayBasicData[i].time.split(".").shift();
        
        if (i > 0) {
            lastDayData.push(new Array(minusSeconds(time, 5), lastStatus)); //-5 seconds = jqPlot bugfix
        }        
        if (i !== lastDayBasicData.length-1) {  
            lastDayData.push(new Array(time, status));
        }
        
        lastStatus = status;
    }
    if (lastDayData.length < 3 && lastDayData.length > 0) lastDayData.push(lastDayData[0]); //jqPlot bugfix

    var allTime = $.jqplot('allTime', [lastDayData], lastDayOptions);
});