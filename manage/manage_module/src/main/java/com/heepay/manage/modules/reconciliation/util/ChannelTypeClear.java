package com.heepay.manage.modules.reconciliation.util;

import com.heepay.enums.TransType;
import com.heepay.manage.common.utils.DictList;
import com.heepay.manage.common.utils.EnumBean;

import java.util.List;
import java.util.function.Function;

/***
 *
 *
 * 描    述：
 *
 * 创 建 者： wangl
 * 创建时间：  2017-08-2510:05
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
public class ChannelTypeClear {


    /**
     * @方法说明：通道业务类型
     * @时间： 2017-08-25 10:08
     * @创建人：wangl
     */
    private static List<EnumBean> list = DictList.channelType();

    static {
        EnumBean de = new EnumBean();
        de.setName(TransType.REAL_NAME.getContent());//"实名认证"
        de.setValue(TransType.REAL_NAME.getValue());//RENAME
        list.add(de);
    }

    /**
     * @方法说明：根据名称翻译
     * @时间： 2017-08-25 10:14
     * @创建人：wangl
     */
    public static String labelOf(String name) {
        String value = "";
        for (int i = 0, length = list.size(); i < length; i++) {
            EnumBean enumBean = list.get(i);
            if (enumBean.getValue().equals(name)) {
                value = enumBean.getName();
                break;
            }
        }
        return value;
    }

    public static List<EnumBean> getList() {
        return list;
    }
}
