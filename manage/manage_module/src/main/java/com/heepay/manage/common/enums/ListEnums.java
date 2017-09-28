package com.heepay.manage.common.enums;

import com.google.common.collect.Lists;
import com.heepay.manage.common.utils.EnumBean;

import java.util.List;

/**
 * 描述：枚举类转成list的工具类（用于页面下拉框使用）
 * <p>
 * 创建者: guozx@9186.com
 * 创建时间: 2017-06-27 17:29:36
 * 创建描述: 结算状态    "ENABLE":"启用"	 "DISABL":"禁用"
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
public class ListEnums {

    private ListEnums() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * @return HgmsCompanyType(资金归集账户类型)枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> hgmsCompanyType() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsCompanyType e : HgmsCompanyType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return IndustryTypes(资金归集商户的事业类型)枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> industryTypes() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsIndustryTypes e : HgmsIndustryTypes.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return HgmsBankcardOwnerType(资金归集商户的银行卡持卡人类型)枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> bankcardOwnerType() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsBankcardOwnerType e : HgmsBankcardOwnerType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return HgmsBankcardType(资金归集商户的银行卡类型)枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> bankcardType() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsBankcardType e : HgmsBankcardType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return HgmsCreditLevel(资金归集商户的信用评级)枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> creditLevel() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsCreditLevel e : HgmsCreditLevel.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return HgmsRiskLevel(资金归集商户的风险类型)枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> riskLevel() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsRiskLevel e : HgmsRiskLevel.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return PayTransStatus(资金归集商户的风险类型)枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> payTransStatus() {
        List<EnumBean> list = Lists.newArrayList();
        for (PayTransStatus e : PayTransStatus.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return HgmsContractStatus(合同的启用和禁用)枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> hgmsContractStatus() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsContractStatus e : HgmsContractStatus.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return HgmsRuleBuildType(合同的启用和禁用)枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> hgmsRuleBuildType() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsRuleBuildType e : HgmsRuleBuildType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return HgmsOrderTaskType(任务类型)枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> hgmsOrderTaskType() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsOrderTaskType e : HgmsOrderTaskType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return HgmsTaskStatus(定时任务执行状态)枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> hgmsTaskStatus() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsTaskStatus e : HgmsTaskStatus.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return HgmsOrderSource(汇聚财订单来源)枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> hgmsOrderSource() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsOrderSource e : HgmsOrderSource.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return TimeIntervalType(商户添加页面时间区间)枚举类转成list
     * @author niujunpeng
     */
    public static List<EnumBean> timeIntervalType() {
        List<EnumBean> list = Lists.newArrayList();
        for (TimeIntervalType e : TimeIntervalType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return HgmsBusinessType(商户添加页面时间区间)枚举类转成list
     * @author niujunpeng
     */
    public static List<EnumBean> hgmsBusinessType() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsBusinessType e : HgmsBusinessType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return HgmsRuleForms(规则形式)枚举类转成list
     * @author niujunpeng
     */
    public static List<EnumBean> hgmsRuleForms() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsRuleForms e : HgmsRuleForms.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return HgmsRuleMode(规则模式)枚举类转成list
     * @author niujunpeng
     */
    public static List<EnumBean> hgmsRuleMode() {
        List<EnumBean> list = Lists.newArrayList();
        for (HgmsRuleMode e : HgmsRuleMode.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return TransWay(交易方式)枚举类转成list
     * @author niujunpeng
     */
    public static List<EnumBean> hgmsTransWay() {
        List<EnumBean> list = Lists.newArrayList();
        for (TransWay e : TransWay.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }
}
