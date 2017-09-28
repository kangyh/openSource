package com.heepay.manage.common.utils;

import com.google.common.collect.Lists;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.modules.sys.utils.DictUtils;

import java.util.List;

/**
 * 描述：获取字典list
 * <p>
 * 创建者  ly
 * 创建时间 2017-02-23-15:38
 * 创建描述：
 * <p>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
public class DictList {

    /**
     * @author ly
     * @return
     * 字典值ChannelPartner转成list
     */
    public static List<EnumBean> channelPartner() {
        List<Dict> dictList = DictUtils.getDictList(Constants.CHANNEL_PARTNER);
        List<EnumBean> list = getEnumBeen(dictList);
        return list;
    }

    /**
     * @author ly
     * @return
     * 字典值ChannelType转成list
     */
    public static List<EnumBean> channelType() {
        List<Dict> dictList = DictUtils.getDictList(Constants.CHANNEL_TYPE);
        List<EnumBean> list = getEnumBeen(dictList);
        return list;
    }

    /**
     * @author ly
     * @return
     * 字典值ByProject转成list
     */
    public static List<EnumBean> byProject() {
        List<Dict> dictList = DictUtils.getDictList(Constants.BY_PROJECT);
        List<EnumBean> list = getEnumBeen(dictList);
        return list;
    }

    /**
     * @author ly
     * @return
     * 字典值byCompany转成list
     */
    public static List<EnumBean> byCompany() {
        List<Dict> dictList = DictUtils.getDictList(Constants.BY_COMPANY);
        List<EnumBean> list = getEnumBeen(dictList);
        return list;
    }

    /**
     * @author ly
     * @return
     * 字典值onlineProduct转成list
     */
    public static List<EnumBean> onlineProduct() {
        List<Dict> dictList = DictUtils.getDictList(Constants.ONLINE_PRODUCT);
        List<EnumBean> list = getEnumBeen(dictList);
        return list;
    }

    /**
     * 将字典list转成EnumBean
     * @author ly
     */
    private static List<EnumBean> getEnumBeen(List<Dict> dictList) {
        List<EnumBean> list = Lists.newArrayList();
        if(null != dictList && !dictList.isEmpty()){
            for(Dict dict : dictList){
                EnumBean enumBean = new EnumBean();
                enumBean.setName(dict.getLabel());
                enumBean.setValue(dict.getValue());
                list.add(enumBean);
            }
        }
        return list;
    }

    /**
     * 获取综合通道产品code
     * @author ly
     */
    public static String comprehensiveCode() {
        String comprehensiveCode = "";
        List<Dict> dictList = DictUtils.getDictList(Constants.COMPREHENSIVE_CODE);
        if(null != dictList && !dictList.isEmpty()){
            for(Dict dict : dictList){
                comprehensiveCode += dict.getValue() + "','";
            }
            comprehensiveCode = comprehensiveCode.substring(0,comprehensiveCode.length()-3);
        }
        return comprehensiveCode;
    }


    /**
     * 字典值merchantFlag(商户标识)
     * @return
     */
    public static List<EnumBean> merchantFlag() {
        List<Dict> dictList = DictUtils.getDictList(Constants.MERCHANT_FLAG);
        List<EnumBean> list = getEnumBeen(dictList);
        return list;
    }
}
