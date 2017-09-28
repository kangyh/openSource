/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.utils;

import com.heepay.common.util.StringUtils;

/**
 * 名称：订单数字转换中文工具类
 *
 * 创建者  牛俊鹏
 * 创建时间 2017年8月22日
 * 创建描述：一些订单的公用switch转换方法
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class OrderFormUtils {
    private OrderFormUtils() {
    }

    /**
     * 转换交易状态
     * @param string
     * @return
     */
    public static String changeTransStatus(String string) {
       if(!StringUtils.isEmpty(string)){
           switch (string){
               case "1":string="手续费扣款成功"; break;
               case "2":string="手续费扣款失败"; break;
               case "3":string="已退款"; break;
               case "4":string="不允许退款"; break;
               default:string="";
           }
          return string;
       }else {
           return "";
       }
    }

    /**
     * 币种转换
     * @param string
     * @return
     */
    public static String changeCurrencyType(String string) {
        if(!StringUtils.isEmpty(string)){
            switch (string){
                case "CNY":string="人民币"; break;
                case "HKD":string="港币"; break;
                case "CAD":string="加拿大元"; break;
                case "EUR":string="欧元"; break;
                case "PHP":string="菲律宾比索"; break;
                case "KRW":string="韩元"; break;
                case "DKK":string="丹麦克朗"; break;
                case "FRF":string="法国法郎"; break;
                case "ESP":string="西班牙比塞塔"; break;
                case "FIM":string="芬兰马克"; break;
                case "SEK":string="瑞典克朗"; break;
                case "NZD":string="新西兰元"; break;
                case "GBP":string="GBP"; break;
                case "USD":string="美元"; break;
                case "AUD":string="澳大利亚元"; break;
                case "JPY":string="日元"; break;
                case "MOP":string="澳门元"; break;
                case "SGD":string="新加坡元"; break;
                case "THB":string="泰铢"; break;
                case "DEM":string="德国马克"; break;
                case "ITL":string="意大利里拉"; break;
                case "ATS":string="奥地利先令"; break;
                case "NOK":string="挪威克朗"; break;
                case "CHF":string="瑞士法郎"; break;
            }
            return string;
        }else {
            return "";
        }
    }
    public static String changePayErcertificateType(String string) {
        if(!StringUtils.isEmpty(string)){
            switch (string){
                case "01":string="居民身份证"; break;
                case "02":string="户口本"; break;
                case "03":string="军官证"; break;
                case "04":string="往来港澳"; break;
                case "05":string="台胞证"; break;
                case "06":string="护照"; break;
                case "07":string="临时身份证"; break;
                case "99":string="其他"; break;
            }
            return string;
        }else {
            return "";
        }
    }

    /**
     * 支付用途
     * @param string
     * @return
     */
    public static String changePayPurpose(String string) {
        if(!StringUtils.isEmpty(string)){
            switch (string){
                case "A":string="全款"; break;
                case "S":string="货款"; break;
                case "X":string="运费"; break;
                case "04":string="往来港澳"; break;
            }
            return string;
        }else {
            return "";
        }
    }

    /**
     * 支付交易状态
     * @param string
     * @return
     */
    public static String changePayStatus(String string) {
        if(!StringUtils.isEmpty(string)){
            switch (string){
                case "D":string="代扣"; break;
                case "S":string="实扣"; break;
                case "C":string="取消"; break;
                }
            return string;
        }else {
            return "";
        }
    }

    /**
     * 模式代码
     * @param string
     * @return
     */
    public static String changeModeCode(String string) {
        if(!StringUtils.isEmpty(string)){
            switch (string){
                case "1":string="一般模式"; break;
                case "2":string="保税模式"; break;
            }
            return string;
        }else {
            return "";
        }
    }
    public static String changeTransCode(String string) {
        if(!StringUtils.isEmpty(string)){
            switch (string){
                case "1":string="新增"; break;
                case "2":string="变更"; break;
                case "3":string="删除"; break;
            }
            return string;
        }else {
            return "";
        }
    }
}
