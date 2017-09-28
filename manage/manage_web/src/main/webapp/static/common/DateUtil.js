/**
 * Created by Zhangjx on 2016/8/29.
 */
    //Date转字符串
    function convertDateToStr(srcDate) {
        var year = srcDate.getFullYear();
        var month = srcDate.getMonth() + 1;
        var date = srcDate.getDate();
        var hour =  srcDate.getHours();
        var minute =  srcDate.getMinutes();
        var second =  srcDate.getSeconds();
        return year
            + (month < 10 ? '0' + month : month)
            +  (date < 10 ? '0' + date : date)
            + (hour < 10 ? '0' + hour : hour)
            + (minute < 10 ? '0' + minute : minute)
            + (second < 10 ? '0' + second : second);
    }
    function convertDateToJoinStr(srcDate) {
        srcDate = srcDate.split('-').join('');
        srcDate = srcDate.split(':').join('');
        srcDate = srcDate.split(' ').join('');
      return srcDate;
    }
    //比较时间大小
    function compareDate(fromDate,toDate){
        //fromDate = fromDate.substring(0, 4) + "-" + fromDate.substring(4, 6) + "-" + fromDate.substring(6, 8)
        //+fromDate.substring(8, 10) + fromDate.substring(10, 12) + fromDate.substring(12, 14);
        //toDate = toDate.substring(0, 4) + "-" + toDate.substring(4, 6) + "-" + toDate.substring(6, 8)
        //    +toDate.substring(8, 10) + toDate.substring(10, 12) + toDate.substring(12, 14);
        if(parseInt(fromDate) - parseInt(toDate) == 0)
            return 0;
        if(parseInt(fromDate) - parseInt(toDate) > 0)
            return 1;
        return -1;
    }

    //比较时间范围是否超过n年
    function compareYear(fromDate, toDate , maxYear){
        var fromDate_year = parseInt(fromDate.substring(0, 4));
        var fromDate_month = parseInt(fromDate.substring(4, 6));
        var fromDate_day = parseInt(fromDate.substring(6, 8));
        var toDate_year = parseInt(toDate.substring(0, 4));
        var toDate_month = parseInt(toDate.substring(4, 6));
        var toDate_day = parseInt(toDate.substring(6, 8));
        var year = toDate_year - fromDate_year;
        if(fromDate_month == toDate_month && fromDate_day < (toDate_day + 1)){
            year = year+1;
            if(year > maxYear){
                return -1;
            }
        }
        if(fromDate_month < toDate_month){
            year = year+1;
            if(year > maxYear){
                return -1;
            }
        }
        if(fromDate_month > toDate_month){
            year = year - 1;
            if(year >= maxYear){
                return -1;
            }
        }
        return 1;
    }
