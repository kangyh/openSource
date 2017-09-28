package com.heepay.manage.modules.hgms.util;

import com.heepay.manage.modules.hgms.entity.HgmsSummaryResult;

/**
 * Created by 牛俊鹏 on 2017/3/21.
 */
public class SharmedUtils {

    /**
     * @param result
     * @return HgmsSummaryResult
     * @discription 如果查到的统计值为null 则页面显示为0/0.00
     * @author 牛俊鹏
     * @created 2017年3月21日
     */
    public static HgmsSummaryResult changeObject(HgmsSummaryResult result) {
        if (!"0".equals(result.getTranNumberCount())) {
        } else {
            result.setTranNumberCount("0");
            result.setTranNumberSuccess("0");
            result.setTranNumberMiddle("0");
            result.setTranNumberFailed("0");
            result.setTranMoneyCount("0.00");
            result.setTranMoneyMiddle("0.00");
            result.setTranMoneySuccess("0.00");
            result.setTranMoneyFailed("0.00");
            result.setFeeSuccess("0.00");
            result.setFeeFailed("0.00");
            result.setFeeMiddle("0.00");
            result.setFeeCount("0.00");
        }
        return result;
    }
}
