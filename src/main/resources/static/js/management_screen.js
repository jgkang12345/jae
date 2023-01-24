function managementScreenInit() {
    let s_date = new Date();
    const s_year = s_date.getFullYear();
    let s_month = '0' + s_date.getMonth()+1;
    if (s_month.length > 2)
        s_month = s_month.substring(1);
    let s_day = '0' + s_date.getDate();
    if (s_day.length > 2)
        s_day = s_day.substring(1);
    $("#m_end_dt").val(s_year + '-' + s_month + '-' + s_day);

    let e_date = new Date();
    e_date.setDate(s_date.getDate() - 7);
    const year = e_date.getFullYear();
    let month = '0' + e_date.getMonth()+1;
    if (month.length > 2)
        month = month.substring(1);
    let day = '0' + e_date.getDate();
    if (day.length > 2)
        day = day.substring(1);

    $("#m_start_dt").val(year + '-' + month + '-' + day);
    mg_getItem();
    m_doSearch();
}
let mg_main_list = [];
async function m_doSearch()
{
    mg_main_list = [];
    var obj = {};
    for (let i = 0; i < $(".mg.form-control").length; i++) {
        const id = $(".mg.form-control")[i].id;
        obj[id] = $("#" + id).val();
    }

    const response = await post("../management/doSearch",obj);

    if (!response.result) {
        alert("검색 실패 하였습니다");
    } else {
        console.log(response);
        mg_main_list = [...response.data];
        $("#mg_table").empty();
        $("#mg_table").append(make_table(response.data));
    }
}

function make_table(list) {
    var table = "";
    list.forEach((item) => {
        table += `<tr class="${tradeCdRender(item.trade_cd)}">
                    <td>${item.transactionOkDate}</td>
                    <td>${item.title}</td>
                    <td>${comma(uncomma(item.amount))}</td>
                    <td><button class="btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#mg_modal" onclick="mg_modal_init(${item.seq})" >상세</button></td>
                    </tr>`
    });
    return table;
}
function mg_modal_reset() {
    $("#mg_modal_seq").val("");
    $("#mg_modal_tr_date").val("");
    $("#mg_modal_reg_date").val("");
    $("#mg_modal_amount").val("");
    $("#mg_modal_comment").val("");
}

function mg_modal_set(detail) {
    $("#mg_modal_seq").val(detail.seq);
    $("#mg_modal_tr_date").val(detail.transactionOkDate);
    $("#mg_modal_reg_date").val(detail.regDt);
    $("#mg_modal_item_id").val(detail.itemId);
    $("#mg_modal_amount").val(comma(uncomma(detail.amount)));
    $("#mg_modal_comment").val(detail.comment);
}

function mg_modal_init(seq) {
    try {
        mg_modal_reset();
        const detail = mg_main_list.filter((item) => (item.seq === seq))[0];
        mg_modal_set(detail);
    }
    catch (e) {
        alert(e);
    }
}
async function mg_delete () {
    const seq = Number($("#mg_modal_seq").val());
    const ok = confirm("정말로 삭제하시겠습니까?");

    if (ok === false)
        return;

    const detail = mg_main_list.filter((item) => (item.seq === seq))[0];
    const response = await post("../management/deleteItem",detail);
    if (!response.result) {
        alert("삭제 실패");
    } else {
        mg_main_list = [...mg_main_list.filter((item) => (item.seq !== seq))];
        $("#mg_table").empty();
        $("#mg_table").append(make_table(mg_main_list));
        $("#mg_close").click();
    }
}

async function mg_update() {

    const seq = $("#mg_modal_seq").val();
    const item_id = $("#mg_modal_item_id").val();
    const amount = $("#mg_modal_amount").val().replaceAll(",", "").trim();
    const comment = $("#mg_modal_comment").val();
    const obj = {
        seq : seq,
        amount : amount,
        comment : comment,
        item_id : item_id
    };

    const response = await post("../management/updateItem",obj);
    if (!response.result) {
        alert("수정 실패");
    } else {
        await m_doSearch();
        $("#mg_close").click();
    }
}

async function mg_getItem() {
    const response = await selectItemSetting();
    if (response.result) {
        let str = "";
        response.data.forEach((item) => {
            str += `<option value="${item.item_id}">${item.title}</option>`
        });
        $("#mg_modal_item_id").empty();
        $("#mg_modal_item_id").append(str);
    }
}

async function mg_excelDownload() {
    const obj = {};
    obj.list = mg_main_list

    const response = await fetch("../management/mg_excelDownload", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(obj),
    });

    const myblob = await response.blob();

    var a = document.createElement("a");
    var url = URL.createObjectURL(myblob);
    a.href = url;
    a.download = "전표관리.xlsx";
    document.body.appendChild(a);
    a.click();
    window.URL.revokeObjectURL(url);
}