function registerScreenInit() {
    let date = new Date();
    const year = date.getFullYear();
    let month = '0' + date.getMonth()+1;
    if (month.length > 2)
        month = month.substring(1);
    let day = '0' + date.getDate();
    if (day.length > 2)
        day = day.substring(1);
    $("#transaction_ok_date").val(year + '-' + month + '-' + day);
    getItem();

}
async function registerSave() {
    var obj = {};
    for (let i = 0; i < $(".reg.form-control").length; i++) {
        const id = $(".reg.form-control")[i].id;
        obj[id] = $("#" + id).val();
        if (id === "amount") {
            obj[id] = $("#" + id).val().replaceAll(",", "").trim();
        }
    }
    if ($("#amount").val().trim() === "") {
        alert("금액은 필수 입력값입니다");
        return;
    }
    obj.title = $("#item_id option:checked").text();
    const response = await post("../register/registerSave",obj);

    if (!response.result) {
        alert("전표 등록에 실패 하였습니다");
    } else {
        alert("전표를 등록하였습니다");
        registerInit();
    }
}
function registerInit() {
    for (let i = 0; i < $(".reg.form-control").length; i++) {
        const id = $(".form-control")[i].id;
        if (id === "transaction_ok_date" || id === "item_id") {
            continue;
        }
        $("#" + id).val("");
    }
}

async function getItem() {
    const response = await selectItemSetting();
    if (response.result) {
        let str = "";
        response.data.forEach((item) => {
            str += `<option value="${item.item_id}">${item.title}</option>`
        });
        $("#item_id").empty();
        $("#item_id").append(str);
    }
}