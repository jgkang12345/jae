
function inputNumberFormat(obj) {
    obj.value = comma(uncomma(obj.value));
}

function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
}

function tradeGbRender(gb) {
    if (gb === "I")
        return "수입";
    else
        return "지출";
}

function fixGbRender(gb) {
    if (gb === "Y")
        return "고정항목";
    else
        return "변동항목";
}