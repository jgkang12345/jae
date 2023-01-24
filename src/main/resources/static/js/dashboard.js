let myChart;
async function dashbaordScreenInit() {
    // todo 초기화
    let e_date = new Date();
    const year = e_date.getFullYear();
    let month = '0' + e_date.getMonth()+1;
    if (month.length > 2)
        month = month.substring(1);
    dashboard_tab();
    $("#ds_month").val(year + '-' + month);
    $("#ds_tab2_month").val(year + '-' + month);

    await ds_getItem();
    await ds_getChartData();
    await ds_getAlldata();
}

function renderChart(year,month, title,list) {
    const len = fn_DayOfMonth(year, month);
    const labels = [];
    const datas = [];
    for (let i = 1; i <= len; i++) {
        labels.push(i);
        datas.push(0);
    }

    list.forEach((item) => {
        datas[item.ok_date] = item.amount;
    })

    const data = {
        labels: labels,
        datasets: [{
            label: title,
            backgroundColor: 'rgb(255, 99, 132)',
            borderColor: 'rgb(255, 99, 132)',
            data: datas,
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

async function ds_getItem() {
    const response = await selectItemSetting();
    if (response.result) {
        let str = "";
        response.data.forEach((item) => {
            str += `<option value="${item.item_id}">${item.title}</option>`
        });
        $("#ds_item_id").empty();
        $("#ds_item_id").append(str);
    }
}

async function ds_getChartData() {
    var obj = {};
    obj.item_id = $("#ds_item_id").val();
    obj.month = $("#ds_month").val();
    const response = await post("../dashboard/ds_getChartData",obj);
    console.log(response);
    if (!response.result) {
        alert("검색 실패 하였습니다");
    } else {
        const title =  $("#ds_item_id option:checked").text();
        const dateList = $("#ds_month").val().split("-");
        renderChart(dateList[0],dateList[1],title, response.data);
    }
}

async function ds_getAlldata() {
    var obj = {};
    obj.month = $("#ds_tab2_month").val();
    const response = await post("../dashboard/ds_getAlldata",obj);
    console.log(response);
    if (!response.result) {
        alert("검색 실패 하였습니다");
    } else {
        const i = response.data.filter(item => (item.trade_cd === "I"));
        const o = response.data.filter(item => (item.trade_cd === "O"));
        $("#ds_i_table").empty();
        $("#ds_i_table").append(ds_make_table(i));
        sumFooter(i,"I");
        $("#ds_o_table").empty();
        $("#ds_o_table").append(ds_make_table(o));
        sumFooter(o,"O");
    }
}

function ds_make_table(list) {
    var table = "";
    list.forEach((item) => {
        table += `<tr class="${tradeCdRender(item.trade_cd)}">
                    <td>${item.title}</td>
                    <td>${comma(uncomma(item.amount))}</td>
                    <td></td> 
                   </tr>`
    });
    return table;
}


function sumFooter(list, gb) {
    let sum = 0;

    list.forEach((item) => {
        sum += item.amount;
    })

    if (gb === "I") {
        $("#i_sum").text(comma(uncomma(sum)));
    }
    else {
        $("#o_sum").text(comma(uncomma(sum)));
    }
}