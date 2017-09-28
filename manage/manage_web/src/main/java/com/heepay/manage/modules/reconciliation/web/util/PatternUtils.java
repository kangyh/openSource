package com.heepay.manage.modules.reconciliation.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 *
 *
 * 描    述：检验是不是数字
 *
 * 创 建 者： wangl
 * 创建时间：  2017-05-2710:31 AM
 * 创建描述：
 *
 * 修 改 者：  
 * 修改时间： 
 * 修改描述： 
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
public class PatternUtils {

    public static boolean isNaN(String str){
        Pattern pattern = Pattern.compile("[1-9]+[0-9]*]*$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
