/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.common.utils;

import com.heepay.common.util.StringUtils;
import com.heepay.enums.CostType;
import com.heepay.enums.RateBankcardType;
import com.heepay.manage.modules.merchant.vo.MerchantRateBank;
import com.heepay.manage.modules.merchant.vo.MerchantRateNew;

import java.util.List;

/**
 *
 * 描述：构建批量修改费率时的email内容工具类
 *
 * 创建者  B.HJ
 * 创建时间 2017-07-07-13:51
 * 创建描述：构建批量修改费率时的email内容工具类
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class BuildUpdateRateEmailContentOnBatch {

    private static String BANKNAME = "</br>银行名称：";

    private BuildUpdateRateEmailContentOnBatch() {
    }

    /**
     * 判断调用哪种实现
     * @return
     */
    public static String getTable(List<MerchantRateBank> originals,MerchantRateBank after){
        if(originals.size() == 1){
           return judgeType(originals.get(0).getChargeType(),after.getChargeType(),originals.get(0),after).replace("null","");
        }else{
            StringBuilder table = new StringBuilder("");
            for (MerchantRateBank original:originals) {
                table.append(judgeType(original.getChargeType(),after.getChargeType(),original,after).replace("null",""));
            }
            return table.toString();
        }
    }

    /**
     * 判断类型，来选择调用哪个方法
     * @param originalType      修改前的类型
     * @param afterType         修改后的类型
     * @return                  table
     */
    public static String judgeType(String originalType,String afterType,MerchantRateBank original,MerchantRateBank after){
        // 按比例 ---> 按比例
        if(originalType.equals(afterType) && CostType.RATE.getValue().equals(originalType)){
            return ratiodToRatiod(original,after);
            // 按笔数---->按笔数
        }else if(originalType.equals(afterType) && CostType.COUNT.getValue().equals(originalType)){
            return countdToCountd(original,after);
            // 按笔数---->按比例
        }else if(!originalType.equals(afterType) && CostType.COUNT.getValue().equals(originalType)){
            return countdToRatiod(original,after);
        }else{
            // 按比例---->按笔数
            return ratiodToCountd(original,after);
        }
    }

    /**
     * 按笔数---->按笔数
     * @return
     */
    public static String countdToCountd(MerchantRateBank original,MerchantRateBank after){

        return "</br>计费类型：按笔数 ---> 按笔数</br>银行卡类型：" + getBankCardType(original) + BANKNAME + original.getBankName() +
                "<table border=\"1\" cellspacing=\"0\">\n" +
                "  <tr>\n" +
                "    <th></th>\n" +
                "    <th>修改前</th>\n" +
                "    <th>修改后</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用</td>\n" +
                "    <td>区间" + original.getChargeMin() + "-" + original.getChargeMax() + "值" + original.getChargeFee() + "</td>\n" +
                "    <td>区间" + after.getChargeMin() + "-" + after.getChargeMax() + "值" + after.getChargeFee() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用2</td>\n" +
                "    <td>区间" + original.getChargeMin2() + "-" + original.getChargeMax2() + "值" + original.getChargeFee2() + "</td>\n" +
                "    <td>区间" + after.getChargeMin2() + "-" + after.getChargeMax2() + "值" + after.getChargeFee2() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用3</td>\n" +
                "    <td>区间" + original.getChargeMin3() + "-" + original.getChargeMax3() + "值" + original.getChargeFee3() + "</td>\n" +
                "    <td>区间" + after.getChargeMin3() + "-" + after.getChargeMax3() + "值" + after.getChargeFee3() + "</td>\n" +
                "  </tr>\n" +
                "</table>";
    }

    /**
     * 按比例--->按比例
     * @return
     */
    public static String ratiodToRatiod(MerchantRateBank original,MerchantRateBank after){
        return "</br>计费类型：按笔数 ---> 按笔数</br>银行卡类型：" + getBankCardType(original) + BANKNAME + original.getBankName() +
                "<table border=\"1\" cellspacing=\"0\">\n" +
                "  <tr>\n" +
                "    <th></th>\n" +
                "    <th>修改前</th>\n" +
                "    <th>修改后</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用</td>\n" +
                "    <td>区间" + original.getChargeMin() + "-" + original.getChargeMax() + "值" + original.getChargeRatio() + "</td>\n" +
                "    <td>区间" + after.getChargeMin() + "-" + after.getChargeMax() + "值" + after.getChargeRatio() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用2</td>\n" +
                "    <td>区间" + original.getChargeMin2() + "-" + original.getChargeMax2() + "值" + original.getChargeRatio2() + "</td>\n" +
                "    <td>区间" + after.getChargeMin2() + "-" + after.getChargeMax2() + "值" + after.getChargeRatio2() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用3</td>\n" +
                "    <td>区间" + original.getChargeMin3() + "-" + original.getChargeMax3() + "值" + original.getChargeRatio3() + "</td>\n" +
                "    <td>区间" + after.getChargeMin3() + "-" + after.getChargeMax3() + "值" + after.getChargeRatio3() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>最小值</td>\n" +
                "    <td>" + original.getMinFee() + "</td>\n" +
                "    <td>" + after.getMinFee() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>最大值</td>\n" +
                "    <td>" + original.getMaxFee() + "</td>\n" +
                "    <td>" + after.getMaxFee() + "</td>\n" +
                "  </tr>\n" +
                "</table>";
    }

    /**
     * 按笔数---->按比例
     * @return
     */
    public static String countdToRatiod(MerchantRateBank original,MerchantRateBank after){

        return "</br>计费类型：按笔数 ---> <font color=\"#FF0000\">按比例</font></br>银行卡类型：" + getBankCardType(original) + BANKNAME + original.getBankName() +
                "<table border=\"1\" cellspacing=\"0\">\n" +
                "  <tr>\n" +
                "    <th></th>\n" +
                "    <th>修改前</th>\n" +
                "    <th>修改后</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用</td>\n" +
                "    <td>区间" + original.getChargeMin() + "-" + original.getChargeMax() + "值" + original.getChargeFee() + "</td>\n" +
                "    <td>区间" + after.getChargeMin() + "-" + after.getChargeMax() + "值" + after.getChargeRatio() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用2</td>\n" +
                "    <td>区间" + original.getChargeMin2() + "-" + original.getChargeMax2() + "值" + original.getChargeFee2() + "</td>\n" +
                "    <td>区间" + after.getChargeMin2() + "-" + after.getChargeMax2() + "值" + after.getChargeRatio2() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用3</td>\n" +
                "    <td>区间" + original.getChargeMin3() + "-" + original.getChargeMax3() + "值" + original.getChargeFee3() + "</td>\n" +
                "    <td>区间" + after.getChargeMin3() + "-" + after.getChargeMax3() + "值" + after.getChargeRatio3() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>最小值</td>\n" +
                "    <td>" + original.getMinFee() + "</td>\n" +
                "    <td>" + after.getMinFee() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>最大值</td>\n" +
                "    <td>" + original.getMaxFee() + "</td>\n" +
                "    <td>" + after.getMaxFee() + "</td>\n" +
                "  </tr>\n" +
                "</table>";
    }

    /**
     * 按比例---->按笔数
     * @return
     */
    public static String ratiodToCountd(MerchantRateBank original,MerchantRateBank after){

        return "</br>计费类型：按比例 ---> <font color=\"#FF0000\">按笔数</font></br>银行卡类型：" + getBankCardType(original) + BANKNAME + original.getBankName() +
                "<table border=\"1\" cellspacing=\"0\">\n" +
                "  <tr>\n" +
                "    <th></th>\n" +
                "    <th>修改前</th>\n" +
                "    <th>修改后</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用</td>\n" +
                "    <td>区间" + original.getChargeMin() + "-" + original.getChargeMax() + "值" + original.getChargeRatio() + "</td>\n" +
                "    <td>区间" + after.getChargeMin() + "-" + after.getChargeMax() + "值" + after.getChargeFee() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用2</td>\n" +
                "    <td>区间" + original.getChargeMin2() + "-" + original.getChargeMax2() + "值" + original.getChargeRatio2() + "</td>\n" +
                "    <td>区间" + after.getChargeMin2() + "-" + after.getChargeMax2() + "值" + after.getChargeFee2() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用3</td>\n" +
                "    <td>区间" + original.getChargeMin3() + "-" + original.getChargeMax3() + "值" + original.getChargeRatio3() + "</td>\n" +
                "    <td>区间" + after.getChargeMin3() + "-" + after.getChargeMax3() + "值" + after.getChargeFee3() + "</td>\n" +
                "  </tr>\n" +
                "</table>";
    }

    /**
     * 获取银行卡类型,做转换。
     * @param original      original
     * @return              银行卡类型中文
     */
    private static String getBankCardType(MerchantRateBank original){
        // 获取银行卡类型，如果有就转换中文，没有就显示""
        String bankCardType = "";
        if(StringUtils.isNotBlank(original.getBankCardType())){
            bankCardType = RateBankcardType.labelOf(original.getBankCardType());
        }
        return bankCardType;
    }

    /**
     * 批量按卡类型修改费率调用
     * @param originals
     * @param after
     * @return
     */
    public static String getTableByCardType(List<MerchantRateNew> originals,MerchantRateBank after){
        if(originals.size() == 1){
            return judgeTypeByCardType(originals.get(0).getChargeType(),after.getChargeType(),originals.get(0),after).replace("null","");
        }else{
            StringBuilder table = new StringBuilder("");
            for (MerchantRateNew original:originals) {
                table.append(judgeTypeByCardType(original.getChargeType(),after.getChargeType(),original,after).replace("null",""));
            }
            return table.toString();
        }
    }

    /**
     * 判断类型，来选择调用哪个方法
     * @param originalType      修改前的类型
     * @param afterType         修改后的类型
     * @return                  table
     */
    public static String judgeTypeByCardType(String originalType,String afterType,MerchantRateNew original,MerchantRateBank after){
        // 按比例 ---> 按比例
        if(originalType.equals(afterType) && CostType.RATE.getValue().equals(originalType)){
            return ratiodToRatiodByCardType(original,after);
            // 按笔数---->按笔数
        }else if(originalType.equals(afterType) && CostType.COUNT.getValue().equals(originalType)){
            return countdToCountdByCardType(original,after);
            // 按笔数---->按比例
        }else if(!originalType.equals(afterType) && CostType.COUNT.getValue().equals(originalType)){
            return countdToRatiodByCardType(original,after);
        }else{
            // 按比例---->按笔数
            return ratiodToCountdByCardType(original,after);
        }
    }
    /**
     * 按笔数---->按笔数
     * @return
     */
    public static String countdToCountdByCardType(MerchantRateNew original,MerchantRateBank after){

        return "</br>计费类型：按笔数 ---> 按笔数</br>银行卡类型：" + getBankCardTypeByCardType(original) + BANKNAME + original.getBankName() +
                "<table border=\"1\" cellspacing=\"0\">\n" +
                "  <tr>\n" +
                "    <th></th>\n" +
                "    <th>修改前</th>\n" +
                "    <th>修改后</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用</td>\n" +
                "    <td>区间" + original.getChargeMin() + "-" + original.getChargeMax() + "值" + original.getChargeFee() + "</td>\n" +
                "    <td>区间" + after.getChargeMin() + "-" + after.getChargeMax() + "值" + after.getChargeFee() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用2</td>\n" +
                "    <td>区间" + original.getChargeMin2() + "-" + original.getChargeMax2() + "值" + original.getChargeFee2() + "</td>\n" +
                "    <td>区间" + after.getChargeMin2() + "-" + after.getChargeMax2() + "值" + after.getChargeFee2() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用3</td>\n" +
                "    <td>区间" + original.getChargeMin3() + "-" + original.getChargeMax3() + "值" + original.getChargeFee3() + "</td>\n" +
                "    <td>区间" + after.getChargeMin3() + "-" + after.getChargeMax3() + "值" + after.getChargeFee3() + "</td>\n" +
                "  </tr>\n" +
                "</table>";
    }

    /**
     * 按比例--->按比例
     * @return
     */
    public static String ratiodToRatiodByCardType(MerchantRateNew original,MerchantRateBank after){
        return "</br>计费类型：按笔数 ---> 按笔数</br>银行卡类型：" + getBankCardTypeByCardType(original) + BANKNAME + original.getBankName() +
                "<table border=\"1\" cellspacing=\"0\">\n" +
                "  <tr>\n" +
                "    <th></th>\n" +
                "    <th>修改前</th>\n" +
                "    <th>修改后</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用</td>\n" +
                "    <td>区间" + original.getChargeMin() + "-" + original.getChargeMax() + "值" + original.getChargeRatio() + "</td>\n" +
                "    <td>区间" + after.getChargeMin() + "-" + after.getChargeMax() + "值" + after.getChargeRatio() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用2</td>\n" +
                "    <td>区间" + original.getChargeMin2() + "-" + original.getChargeMax2() + "值" + original.getChargeRatio2() + "</td>\n" +
                "    <td>区间" + after.getChargeMin2() + "-" + after.getChargeMax2() + "值" + after.getChargeRatio2() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用3</td>\n" +
                "    <td>区间" + original.getChargeMin3() + "-" + original.getChargeMax3() + "值" + original.getChargeRatio3() + "</td>\n" +
                "    <td>区间" + after.getChargeMin3() + "-" + after.getChargeMax3() + "值" + after.getChargeRatio3() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>最小值</td>\n" +
                "    <td>" + original.getMinFee() + "</td>\n" +
                "    <td>" + after.getMinFee() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>最大值</td>\n" +
                "    <td>" + original.getMaxFee() + "</td>\n" +
                "    <td>" + after.getMaxFee() + "</td>\n" +
                "  </tr>\n" +
                "</table>";
    }

    /**
     * 按笔数---->按比例
     * @return
     */
    public static String countdToRatiodByCardType(MerchantRateNew original,MerchantRateBank after){

        return "</br>计费类型：按笔数 ---> <font color=\"#FF0000\">按比例</font></br>银行卡类型：" + getBankCardTypeByCardType(original) + BANKNAME + original.getBankName() +
                "<table border=\"1\" cellspacing=\"0\">\n" +
                "  <tr>\n" +
                "    <th></th>\n" +
                "    <th>修改前</th>\n" +
                "    <th>修改后</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用</td>\n" +
                "    <td>区间" + original.getChargeMin() + "-" + original.getChargeMax() + "值" + original.getChargeFee() + "</td>\n" +
                "    <td>区间" + after.getChargeMin() + "-" + after.getChargeMax() + "值" + after.getChargeRatio() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用2</td>\n" +
                "    <td>区间" + original.getChargeMin2() + "-" + original.getChargeMax2() + "值" + original.getChargeFee2() + "</td>\n" +
                "    <td>区间" + after.getChargeMin2() + "-" + after.getChargeMax2() + "值" + after.getChargeRatio2() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用3</td>\n" +
                "    <td>区间" + original.getChargeMin3() + "-" + original.getChargeMax3() + "值" + original.getChargeFee3() + "</td>\n" +
                "    <td>区间" + after.getChargeMin3() + "-" + after.getChargeMax3() + "值" + after.getChargeRatio3() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>最小值</td>\n" +
                "    <td>" + original.getMinFee() + "</td>\n" +
                "    <td>" + after.getMinFee() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>最大值</td>\n" +
                "    <td>" + original.getMaxFee() + "</td>\n" +
                "    <td>" + after.getMaxFee() + "</td>\n" +
                "  </tr>\n" +
                "</table>";
    }

    /**
     * 按比例---->按笔数
     * @return
     */
    public static String ratiodToCountdByCardType(MerchantRateNew original,MerchantRateBank after){

        return "</br>计费类型：按比例 ---> <font color=\"#FF0000\">按笔数</font></br>银行卡类型：" + getBankCardTypeByCardType(original) + BANKNAME + original.getBankName() +
                "<table border=\"1\" cellspacing=\"0\">\n" +
                "  <tr>\n" +
                "    <th></th>\n" +
                "    <th>修改前</th>\n" +
                "    <th>修改后</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用</td>\n" +
                "    <td>区间" + original.getChargeMin() + "-" + original.getChargeMax() + "值" + original.getChargeRatio() + "</td>\n" +
                "    <td>区间" + after.getChargeMin() + "-" + after.getChargeMax() + "值" + after.getChargeFee() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用2</td>\n" +
                "    <td>区间" + original.getChargeMin2() + "-" + original.getChargeMax2() + "值" + original.getChargeRatio2() + "</td>\n" +
                "    <td>区间" + after.getChargeMin2() + "-" + after.getChargeMax2() + "值" + after.getChargeFee2() + "</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>手续费费用3</td>\n" +
                "    <td>区间" + original.getChargeMin3() + "-" + original.getChargeMax3() + "值" + original.getChargeRatio3() + "</td>\n" +
                "    <td>区间" + after.getChargeMin3() + "-" + after.getChargeMax3() + "值" + after.getChargeFee3() + "</td>\n" +
                "  </tr>\n" +
                "</table>";
    }

    /**
     * 获取银行卡类型,做转换。
     * @param original      original
     * @return              银行卡类型中文
     */
    private static String getBankCardTypeByCardType(MerchantRateNew original){
        // 获取银行卡类型，如果有就转换中文，没有就显示""
        String bankCardType = "";
        if(StringUtils.isNotBlank(original.getBankCardType())){
            bankCardType = RateBankcardType.labelOf(original.getBankCardType());
        }
        return bankCardType;
    }
}
