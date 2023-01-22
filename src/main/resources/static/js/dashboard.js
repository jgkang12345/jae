let myChart;
function dashbaordScreenInit() {
    // todo 초기화
    let e_date = new Date();
    const year = e_date.getFullYear();
    let month = '0' + e_date.getMonth()+1;
    if (month.length > 2)
        month = month.substring(1);
    dashboard_tab();
    $("#ds_month").val(year + '-' + month);
    renderChart(year,month, "간식비");
}

function renderChart(year,month, title) {
    const len = fn_DayOfMonth(year, month);
    const labels = [];
    for (let i = 1; i <= len; i++) {
        labels.push(i);
    }

    const data = {
        labels: labels,
        datasets: [{
            label: title,
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(255, 99, 132)',
            data: [0, 10, 5, 2, 20, 30, 45],
        }]
    };

    const config = {
        type: 'line',
        data: data,
        options: {}
    };

    if (myChart !== undefined)
        myChart.destroy();

    myChart = new Chart(
        document.getElementById('myChart'),
        config
    );
}

function fn_DayOfMonth(year, month) {
    return 32 - new Date(year, month-1, 32).getDate();
}

function dashboard_tab(flag) {
    if (flag) {
        $("#tableTab").show();
        $("#chartTab_a").removeClass("active");
        $("#tableTab_a").addClass("active");
        $("#chartTab").hide();
    }
    else {
        $("#chartTab").show();
        $("#chartTab_a").addClass("active");
        $("#tableTab_a").removeClass("active");
        $("#tableTab").hide();
    }
}