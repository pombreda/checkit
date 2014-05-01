function getDateTime(daysBack) {
    var now = new Date(); 
    now.setDate(now.getDate()-daysBack);
    var year = now.getFullYear();
    var month = now.getMonth() + 1; 
    var day = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds(); 

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
                dataLabelThreshold: 0.01,
                sliceMargin: 15
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
    
    var allTimeOptions = {
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
            tickOptions: {
                show: false,
                showLabel: false
            },
            pad: 0
        },
        axes: {       
            xaxis: {
                renderer: $.jqplot.DateAxisRenderer
            }
        }
    };
    
    var pieDay = $.jqplot('pieDay', [payDaySlices], pieOptions); 
    var pieWeek = $.jqplot('pieWeek', [payWeekSlices], pieOptions); 
    var pieMonth = $.jqplot('pieMonth', [payMonthSlices], pieOptions);
    
    var allTimeData = new Array();
    var status, time;
    var lastStatus = 0;
    for (var i=0; i<allTimeBasicData.length; i++) {
        if (allTimeBasicData[i].status === "U") status = 1;
        else if (allTimeBasicData[i].status === "D") status = -1;
        else status = 0;
        time = allTimeBasicData[i].time.split(".").shift();
        
        if (i > 0) {
            allTimeData.push(new Array(time + ".000", lastStatus)); // + .000 Chrome bug-fix
        }
        allTimeData.push(new Array(time + ".001", status)); // + .001 Chrome bug-fix
        
        lastStatus = status;
    }
    var date = getDateTime(0);
    allTimeData.push(new Array(date + ".000", lastStatus)); //Chrome bug-fix
    var allTime = $.jqplot('allTime', [allTimeData], allTimeOptions);
});