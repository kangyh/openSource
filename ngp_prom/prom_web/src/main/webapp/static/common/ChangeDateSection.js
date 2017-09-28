/**
 * Created by Tangwei on 2017/7/4.
 */
function checkDate(){
    var beginCreateTime = $("#beginCreateTime").val();
    var endCreateTime = $("#endCreateTime").val();
    if(beginCreateTime != '' && endCreateTime == ''){
        alert("请录入结束时间");
        return false;
    }

    if(beginCreateTime == '' && endCreateTime != ''){
        alert("请录入开始时间");
        return false;
    }

    var nowDate = getNowFormatDate();
    var lastDayOfDay = getLastDateOfDay();
    if(beginCreateTime > nowDate){
        alert("开始时间不能大于当前时间");
        return false;
    }
    if(endCreateTime > lastDayOfDay){
        alert("结束时间不能大于当前时间");
        return false;
    }

    if(beginCreateTime > endCreateTime){
        alert("开始时间不能大于结束时间");
        return false;
    }

    if(dateDiff(beginCreateTime,endCreateTime)>185){
        alert("结束时间跟开始时间不能相差6个月");
        return false;
    }

    return true;
}


//获取当前时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (hours >= 0 && hours <= 9) {
        hours = "0" + hours;
    }
    if (minutes >= 0 && minutes <= 9) {
        minutes = "0" + minutes;
    }
    if (seconds >= 0 && seconds <= 9) {
        seconds = "0" + seconds;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " " + hours + seperator2 + minutes
        + seperator2 + seconds;
    return currentdate;
}


//获取当天最后的时间
function getLastDateOfDay() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
        + " 23:59:59";
    return currentdate;
}


function getDateStr(addDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate() + addDayCount);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth() + 1;//获取当前月份的日期
    var d = dd.getDate();
    if(m < 10){
        m = "0" + m;
    }
    if(d < 10){
        d = "0" + d;
    }
    return y + "-" + m + "-" + d;
}


function getYearStr(addYearCount) {
    var dd = new Date();
    var y = dd.getFullYear()  + addYearCount;
    return y;
}


//获得本月的开始日期
function getMonthStartDate(){
    var now = new Date(); //当前日期
    var nowMonth = now.getMonth(); //当前月
    var nowYear = now.getYear(); //当前年
    nowYear += (nowYear < 2000) ? 1900 : 0;
    var monthStartDate = new Date(nowYear, nowMonth, 1);
    return formatDate(monthStartDate);
}

//获得本月的结束日期
function getMonthEndDate(){
    var now = new Date(); //当前日期
    var nowMonth = now.getMonth(); //当前月
    var nowYear = now.getYear(); //当前年
    nowYear += (nowYear < 2000) ? 1900 : 0;
    var monthEndDate = new Date(nowYear, nowMonth, getMonthDays(nowYear, nowMonth));
    return formatDate(monthEndDate);
}

//获得上月开始时间
function getPreMonthStartDate(){
    var now = new Date(); //当前日期
    var nowMonth = now.getMonth(); //当前月
    var nowYear = now.getYear(); //当前年
    nowYear += (nowYear < 2000) ? 1900 : 0;
    var lastMonthDate = new Date(); //上月日期
    lastMonthDate.setDate(1);
    lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
    var lastMonth = lastMonthDate.getMonth();
    var lastMonthStartDate = new Date(nowYear, lastMonth, 1);
    return formatDate(lastMonthStartDate);
}

//获得前6个月的开始时间
function getSixPreMonthStartDate(){
    var now = new Date(); //当前日期
    var nowMonth = now.getMonth(); //当前月
    var nowYear = now.getYear(); //当前年
    nowYear += (nowYear < 2000) ? 1900 : 0;
    var lastMonthDate = new Date(); //上月日期
    lastMonthDate.setDate(1);
    lastMonthDate.setMonth(lastMonthDate.getMonth()-5);
    var lastMonth = lastMonthDate.getMonth();
    var lastMonthStartDate = new Date(nowYear, lastMonth, 1);
    return formatDate(lastMonthStartDate);
}

//获得上月结束时间
function getPreMonthEndDate(){
    var now = new Date(); //当前日期
    var nowMonth = now.getMonth(); //当前月
    var nowYear = now.getYear(); //当前年
    nowYear += (nowYear < 2000) ? 1900 : 0;
    var lastMonthDate = new Date(); //上月日期
    lastMonthDate.setDate(1);
    lastMonthDate.setMonth(lastMonthDate.getMonth()-1);
    var lastMonth = lastMonthDate.getMonth();
    var lastMonthEndDate = new Date(nowYear, lastMonth, getMonthDays(nowYear, lastMonth));
    return formatDate(lastMonthEndDate);
}

//获得某月的天数
function getMonthDays(nowYear, myMonth){
    var monthStartDate = new Date(nowYear, myMonth, 1);
    var monthEndDate = new Date(nowYear, myMonth + 1, 1);
    var days = (monthEndDate - monthStartDate)/(1000 * 60 * 60 * 24);
    return days;
}

//格式化日期：yyyy-MM-dd
function formatDate(date) {
    var myyear = date.getFullYear();
    var mymonth = date.getMonth()+1;
    var myweekday = date.getDate();

    if(mymonth < 10){
        mymonth = "0" + mymonth;
    }
    if(myweekday < 10){
        myweekday = "0" + myweekday;
    }
    return (myyear+"-"+mymonth + "-" + myweekday);
}

function changeDateSection(text){
    if(text == '全部'){
        $("#beginCreateTime").val('');
        $("#endCreateTime").val('');
    }else if(text == '今天'){
        var today = getDateStr(0);
        $("#beginCreateTime").val(today + " 00:00:00");
        $("#endCreateTime").val(getNowFormatDate());
    }else if(text == '当天'){
        var day = getDateStr(0);
        $("#beginCreateTime").val(day + " 00:00:00");
        $("#endCreateTime").val(day + " 23:59:59");
    }else if(text == '昨天'){
        var yesterday = getDateStr(-1);
        $("#beginCreateTime").val(yesterday + " 00:00:00");
        $("#endCreateTime").val(yesterday + " 23:59:59");
    }else if(text == '本月'){
        var startMonth = getMonthStartDate();
        var endMonth = getNowFormatDate();
        $("#beginCreateTime").val(startMonth + " 00:00:00");
        $("#endCreateTime").val(endMonth);
    }else if(text == '本年'){
        var year = getYearStr(0);
        var endYear = getNowFormatDate();
        $("#beginCreateTime").val(year + "-01-01 00:00:00");
        $("#endCreateTime").val(endYear);
    }else if(text == '上月'){
        var startMonth = getPreMonthStartDate();
        var endMonth = getPreMonthEndDate();
        $("#beginCreateTime").val(startMonth + " 00:00:00");
        $("#endCreateTime").val(endMonth + " 23:59:59");
    }else if(text == '上年'){
        var year = getYearStr(-1);
        $("#beginCreateTime").val(year + "-01-01 00:00:00");
        $("#endCreateTime").val(year + "-12-31 23:59:59");
    }else if(text == '半年'){
        var startMonth = getSixPreMonthStartDate();
        var endMonth = getNowFormatDate();
        $("#beginCreateTime").val(startMonth + " 00:00:00");
        $("#endCreateTime").val(endMonth);
    }
}

function dateDiff(date1, date2){
    var type1 = typeof date1;
    var type2 = typeof date2;
    if(type1 == 'string')
        date1 = stringToTime(date1);
    else if(date1.getTime)
        date1 = date1.getTime();
    if(type2 == 'string')
        date2 = stringToTime(date2);
    else if(date2.getTime)
        date2 = date2.getTime();
    return (date2 - date1) / 1000 / 60 / 60 / 24;//除1000是毫秒，不加是秒
}

function stringToTime(string){
    var f = string.split(' ', 2);
    var d = (f[0] ? f[0] : '').split('-', 3);
    var t = (f[1] ? f[1] : '').split(':', 3);
    return (new Date(
        parseInt(d[0], 10) || null,
        (parseInt(d[1], 10) || 1)-1,
        parseInt(d[2], 10) || null,
        parseInt(t[0], 10) || null,
        parseInt(t[1], 10) || null,
        parseInt(t[2], 10) || null)).getTime();
}