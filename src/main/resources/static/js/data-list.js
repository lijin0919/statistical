
$("#data-list-export-btn").click(function () {
    var mDate1 = $("#date1").data("datetimepicker").getDate();
    var strDate1 = mDate1.getFullYear()+"-"+(mDate1.getMonth()+1)+"-"+mDate1.getDate();
    var mDate2 = $("#date2").data("datetimepicker").getDate();
    var strDate2 = mDate2.getFullYear()+"-"+(mDate2.getMonth()+1)+"-"+mDate2.getDate();
    alert(strDate2);
});



function sum() {
    if (confirm("确认提交？")) {

        return true;
    } else {
        return false;
    }
}