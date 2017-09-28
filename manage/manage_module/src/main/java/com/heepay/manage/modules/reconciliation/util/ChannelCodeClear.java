package com.heepay.manage.modules.reconciliation.util;

import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumBean;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.sys.utils.BusinessInfoUtils;

import java.util.List;

/***
 *
 *
 * 描    述：通道合作方 枚举类方法
 *
 * 创 建 者： wangl
 * 创建时间：  2017-09-0410:06
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
public class ChannelCodeClear {

    /**
     * @方法说明：通道合作方
     * @时间： 2017-08-25 10:08
     * @创建人：wangl
     */
    private static List<EnumBean> channelCode = DictList.channelPartner();

    /**
     * @方法说明：获取银行信息List
     * @时间： 2017-08-25 10:08
     * @创建人：wangl
     */
    private static List<BankInfo> bankNo = BusinessInfoUtils.getBankInfoList();

    /**
     * @方法说明：通道合作方根据名称翻译
     * @时间： 2017-08-25 10:14
     * @创建人：wangl
     */
    public static String labelOf(String name) {
        String value = "";
        for (int i = 0, length = channelCode.size(); i < length; i++) {
            EnumBean enumBean = channelCode.get(i);
            if (enumBean.getValue().equals(name)) {
                value = enumBean.getName();
                break;
            }
        }
        return value;
    }

    /**
     * @方法说明：获取银行信息根据名称翻译
     * @时间： 2017-08-25 10:14
     * @创建人：wangl
     */
    public static String labelOfBankNo(String name) {
        String value = "";
        for (int i = 0, length = bankNo.size(); i < length; i++) {
            BankInfo bankInfo = bankNo.get(i);
            if (bankInfo.getBankNo().equals(name)) {
                value = bankInfo.getBankName();
                break;
            }
        }
        return value;
    }


    public static List<EnumBean> getChannelCode() {
        return channelCode;
    }

    public static List<BankInfo> getBankNo() {
        return bankNo;
    }
}
