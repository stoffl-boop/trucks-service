$(document).ready(function () {
    monthStatistics();
    hoursStatistics();
    loadStatistics();
    statusStatistics();
});

function hoursStatistics() {
    var posting = $.get("/getStatisticsForHoursPercent");
    posting.done(function (data) {
        var arrHours = [],
            arrPercents = [];
        Object.keys(data).forEach(key => {
            arrHours.push(key);
            arrPercents.push(data[key]);
        });
        arrPercents.push(100);
        var ctxL = document.getElementById("hoursChart").getContext('2d');
        var gradientFill = ctxL.createLinearGradient(0, 0, 0, 290);
        gradientFill.addColorStop(0, "rgba(86, 118, 200, 1)");
        gradientFill.addColorStop(1, "rgba(86, 118, 200, 0.1)");
        var myLineChart = new Chart(ctxL, {
            type: 'bar',
            data: {
                labels: arrHours,
                datasets: [{
                    label: "Процент замовлень",
                    data: arrPercents,
                    backgroundColor: gradientFill,
                    borderColor: [
                        'rgba(86, 118, 200, 1)',
                    ],
                    borderWidth: 2,
                    //pointBorderColor: "rgba(163, 86, 254, 1)",
                    //pointBackgroundColor: "rgba(163, 86, 254, 1)",
                }]
            },
            options: {
                responsive: true,
                legend: {
                    display: true,
                    labels: {
                        //fontColor: 'rgba(163, 86, 254, 1)'
                    }
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            //fontColor: "rgba(163, 86, 254, 1)",
                            fontSize: 12,
                            stepSize: 20,
                            beginAtZero: true
                        }
                    }],
                    xAxes: [{
                        ticks: {
                            //fontColor: "rgba(163, 86, 254, 1)",
                            fontSize: 12,
                            stepSize: 20,
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    });
}

function monthStatistics() {
    var posting = $.get("/getStatisticsForMonth");
    posting.done(function (data) {
        var arrDates = [],
            arrCounts = [];
        data.forEach(responseItem => {
            arrDates.push(responseItem.createDate);
            arrCounts.push(responseItem.count);
        });
        var ctxL = document.getElementById("monthChart").getContext('2d');
        var gradientFill = ctxL.createLinearGradient(0, 0, 0, 290);
        gradientFill.addColorStop(0, "rgba(86, 200, 108, 1)");
        gradientFill.addColorStop(1, "rgba(86, 200, 108, 0.1)");
        var myLineChart = new Chart(ctxL, {
            type: 'line',
            data: {
                labels: arrDates,
                datasets: [{
                    label: "Замовлення",
                    data: arrCounts,
                    backgroundColor: gradientFill,
                    borderColor: "#3cba9f",
                    borderWidth: 2,
                    //pointBorderColor: "#212529",
                    pointBackgroundColor: "white",
                }]
            },
            options: {
                responsive: true,
                legend: {
                    display: true,
                    labels: {
                        //fontColor: '#3cba9f'
                    }
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            //fontColor: "#3cba9f",
                            fontSize: 12,
                            stepSize: 2,
                            beginAtZero: true
                        }
                    }],
                    xAxes: [{
                        ticks: {
                            //fontColor: "#3cba9f",
                            fontSize: 12,
                            stepSize: 20,
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    });
}

function loadStatistics() {
    var posting = $.get("/getStatisticsForDaysPercent");
    posting.done(function (data) {
        var arrDates = [],
            arrLoad = [];
        data.forEach(responseItem => {
            arrDates.push(responseItem.createDate);
            arrLoad.push(responseItem.loadInMinutes);
        });
        arrLoad.push(100);
        var ctxL = document.getElementById("loadChart").getContext('2d');
        var gradientFill = ctxL.createLinearGradient(0, 0, 0, 290);
        gradientFill.addColorStop(0, "rgba(200, 86, 86, 1)");
        gradientFill.addColorStop(1, "rgba(200, 86, 86, 0.1)");
        var myLineChart = new Chart(ctxL, {
            type: 'bar',
            data: {
                labels: arrDates,
                datasets: [{
                    label: "Процент завантаженості ресурсів",
                    data: arrLoad,
                    backgroundColor: gradientFill,
                    borderColor: [
                        'rgba(200, 86, 86, 1)',
                    ],
                    borderWidth: 2,
                    //pointBorderColor: "rgba(163, 86, 254, 1)",
                    //pointBackgroundColor: "rgba(163, 86, 254, 1)",
                }]
            },
            options: {
                responsive: true,
                legend: {
                    display: true,
                    labels: {
                        //fontColor: 'rgba(163, 86, 254, 1)'
                    }
                },
                scales: {
                    yAxes: [{
                        ticks: {
                            //fontColor: "rgba(163, 86, 254, 1)",
                            fontSize: 12,
                            stepSize: 20,
                            beginAtZero: true
                        }
                    }],
                    xAxes: [{
                        ticks: {
                            //fontColor: "rgba(163, 86, 254, 1)",
                            fontSize: 12,
                            stepSize: 20,
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    });
}

function statusStatistics() {
    var posting = $.get("/getStatusStatisticsPercent");
    posting.done(function (data) {
        var arrCounts = [];
        data.forEach(responseItem => {
            arrCounts.push(responseItem.count);
        });
        var ctx = document.getElementById("statusChart").getContext('2d');
        var myChart = new Chart(ctx, {
          type: 'pie',
          data: {
            labels: ["Відмовлено", "Виконано",],
            datasets: [{
              backgroundColor: [
                "#e74c3c",
                "#2ecc71"
              ],
              data: arrCounts
            }]
          },
          tooltips: {
               enabled: false
          },
          plugins: {
              datalabels: {
                  formatter: (value, ctx) => {

                    let sum = 0;
                    let dataArr = ctx.chart.data.datasets[0].data;
                    dataArr.map(data => {
                        sum += data;
                    });
                    let percentage = (value*100 / sum).toFixed(2)+"%";
                    return percentage;


                  },
                  color: '#fff',
              }
          }
        });
    });
}
