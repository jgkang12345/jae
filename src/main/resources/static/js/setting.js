function settingScreenInit() {
    // todo 초기화
    s_doSearch();
}
let s_main_list = [];
async function s_doSearch() {
    s_main_list = [];
    var obj = {};
    for (let i = 0; i < $(".sg.form-control").length; i++) {
        const id = $(".sg.form-control")[i].id;
        obj[id] = $("#" + id).val();
    }

    const response = await post("../setting/doSearch",obj);

    if (!response.result) {
        alert("검색 실패 하였습니다");
    } else {
        console.log(response);
        s_main_list = [...response.data];
        $("#s_table").empty();
        $("#s_table").append(s_make_table(response.data));
    }
}

function s_make_table(list) {
    var table = "";
    list.forEach((item) => {
        table += `<tr>
                    <td>${item.title}</td>
                    <td>${tradeGbRender(item.trade_cd)}</td>
                    <td>${fixGbRender(item.fix_gb)}</td>
                    <td><button class="btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#s_modal" onclick="s_modal_update_open(${item.seq})" >상세</button></td>
                    </tr>`
    });
    return table;
}

async function s_doSave() {
    var obj = {};
    obj.seq = $("#s_modal_seq").val()
    obj.title = $("#s_modal_item_title").val();
    obj.item_id = $("#s_modal_item_id").val();
    obj.trade_cd = $("#s_modal_trad_cd").val();

    const response = await post("../setting/doSave",obj);

    if (!response.result) {
        alert("계정과목 등록 실패");
    } else {
        await s_doSearch();
        $("#s_modal_close").click();
    }
}

function s_modal_init() {
    $("#s_modal_seq").val("")
    $("#s_modal_item_id").val("");
    $("#s_modal_item_title").val("");
    $("#s_modal_trad_cd").val("I");
}

function s_modal_set(detail) {
    $("#s_modal_seq").val(detail.seq)
    $("#s_modal_item_id").val(detail.item_id);
    $("#s_modal_item_title").val(detail.title);
    $("#s_modal_trad_cd").val(detail.trade_cd);
}

function s_modal_update_open(seq) {
    s_modal_init();
    const detail = s_main_list.filter((item) => (item.seq === seq))[0];
    s_modal_set(detail);
}

async function s_delete() {
    const seq = Number($("#s_modal_seq").val());
    const ok = confirm("정말로 삭제하시겠습니까?");

    if (ok === false)
        return;

    const detail = s_main_list.filter((item) => (item.seq === seq))[0];
    const response = await post("../setting/doDelete",detail);
    if (!response.result) {
        alert("삭제 실패");
    } else {
        s_main_list = [...s_main_list.filter((item) => (item.seq !== seq))];
        $("#s_table").empty();
        $("#s_table").append(s_make_table(s_main_list));
        $("#s_modal_close").click();
    }
}