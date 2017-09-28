package com.heepay.manage.common.utils;

import com.google.common.collect.Lists;
import com.heepay.enums.*;
import com.heepay.enums.risk.RiskChannelReqResult;
import com.heepay.enums.tpds.InstallmentType;
import com.heepay.manage.common.enums.*;
import com.heepay.manage.common.enums.CertifyChannelPartner;

import java.util.List;

/**
 * @author ly
 * 枚举类转成list的工具类
 */
public class ListEnum {

    private ListEnum() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * @author ly
     * @return List
     * SettleType枚举类转成list
     */
    public static List<EnumBean> settleType() {
        List<EnumBean> list = Lists.newArrayList();
        for (SettleType e : SettleType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * 账户类型
     * @return
     */
    public static List<EnumBean> accountStatus() {
        List<EnumBean> list = Lists.newArrayList();
        for (AccountStatus e : AccountStatus.values()) {
            if (e.getValue().equals(AccountStatus.NORMAL.getValue())||e.getValue().equals(AccountStatus.FREEZED.getValue())){
                EnumBean enumBean = new EnumBean();
                enumBean.setName(e.getContent());
                enumBean.setValue(e.getValue());
                list.add(enumBean);
            }
        }
        return list;
    }

    /**
     * @author ly
     * @return List
     * AutographType枚举类转成list
     */
    public static List<EnumBean> autographType() {
        List<EnumBean> list = Lists.newArrayList();
        for (AutographType e : AutographType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author ly
     * @return
     * commonStatus枚举类转成list(只用禁用，启用)
     */
    public static List<EnumBean> commonStatus() {
        List<EnumBean> list = Lists.newArrayList();
        for (CommonStatus e : CommonStatus.values()) {
            if("ENABLE".equals(e.getValue()) || "DISABL".equals(e.getValue())){
                EnumBean enumBean = new EnumBean();
                enumBean.setName(e.getContent());
                enumBean.setValue(e.getValue());
                list.add(enumBean);
            }
        }
        return list;
    }

    /**
     * @author ly
     * @return
     * yesOrNo枚举类转成list(只用YES，NO)
     */
    public static List<EnumBean> yesOrNo() {
        List<EnumBean> list = Lists.newArrayList();
        for (CommonStatus e : CommonStatus.values()) {
            if("Y".equals(e.getValue()) || "N".equals(e.getValue())){
                EnumBean enumBean = new EnumBean();
                enumBean.setName(e.getContent());
                enumBean.setValue(e.getValue());
                list.add(enumBean);
            }
        }
        return list;
    }

    /**
     * @author ly
     * @return
     * remitStatus枚举类转成list
     */
    public static List<EnumBean>  remitStatus() {
        List<EnumBean> list = Lists.newArrayList();
        for (RemitStatus e : RemitStatus.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author ly
     * @return
     * routeStatus枚举类转成list
     */
    public static List<EnumBean>  routeStatus() {
        List<EnumBean> list = Lists.newArrayList();
        for (RouteStatus e : RouteStatus.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author ly
     * @return
     * authenticationStatus枚举类转成list
     */
    public static List<EnumBean>  authenticationStatus() {
        List<EnumBean> list = Lists.newArrayList();
        for (AuthenticationStatus e : AuthenticationStatus.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }



    /**
     * @author lm
     * @return
     * CostType枚举类转成list
     */
    public static List<EnumBean> costType() {
        List<EnumBean> list = Lists.newArrayList();
        for (CostType e : CostType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author lm
     * @return
     * ChannelPartner枚举类转成list
     */
    public static List<EnumBean> channelPartner() {
        List<EnumBean> list = Lists.newArrayList();
        for (ChannelPartner e : ChannelPartner.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author lm
     * @return
     * CertifyChannelPartner枚举类转成list
     */
    public static List<EnumBean> certifyChannelPartner() {
        List<EnumBean> list = Lists.newArrayList();
        for (CertifyChannelPartner e : CertifyChannelPartner.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }
    /**
     * @author lm
     * @return
     * ChannelType枚举类转成list
     */
    public static List<EnumBean> channelType() {
        List<EnumBean> list = Lists.newArrayList();
        for (ChannelType e : ChannelType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author lm
     * @return
     * BankcardType枚举类转成list
     */
    public static List<EnumBean> bankcardType() {
        List<EnumBean> list = Lists.newArrayList();
        for (BankcardType e : BankcardType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author lm
     * @return
     * SettlePeriod枚举类转成list
     */
    public static List<EnumBean> settlePeriod(){
        List<EnumBean> list = Lists.newArrayList();
        for (SettlePeriod e : SettlePeriod.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author lm
     * @return
     * AccountType枚举类转成list
     */
    public static List<EnumBean> accountType(){
        List<EnumBean> list = Lists.newArrayList();
        for (AccountType e : AccountType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author ly
     * @return
     * RateBusinessType枚举类转成list
     */
    public static List<EnumBean> rateBusinessType(){
        List<EnumBean> list = Lists.newArrayList();
        for (RateBusinessType e : RateBusinessType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author lm
     * @return
     * BusinessType枚举类转成list
     */
    public static List<EnumBean> businessType(){
        List<EnumBean> list = Lists.newArrayList();
        for (BusinessType e : BusinessType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author ly
     * @return
     * ChargeCollectionType枚举类转成list
     */
    public static List<EnumBean> chargeCollectionType(){
        List<EnumBean> list = Lists.newArrayList();
        for (ChargeCollectionType e : ChargeCollectionType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author ly
     * @return
     * ChargeDeductType枚举类转成list
     */
    public static List<EnumBean> chargeDeductType(){
        List<EnumBean> list = Lists.newArrayList();
        for (ChargeDeductType e : ChargeDeductType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author ly
     * @return
     * ChargeSource枚举类转成list
     */
    public static List<EnumBean> chargeSource(){
        List<EnumBean> list = Lists.newArrayList();
        for (ChargeSource e : ChargeSource.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author ly
     * @return
     * RateBankcardType枚举类转成list
     */
    public static List<EnumBean> rateBankcardType(){
        List<EnumBean> list = Lists.newArrayList();
        for (RateBankcardType e : RateBankcardType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author ly
     * @return
     * MerchantAutographType枚举类转成list
     */
    public static List<EnumBean> merchantAutographType(){
        List<EnumBean> list = Lists.newArrayList();
        for (MerchantAutographType e : MerchantAutographType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @author ly
     * @return
     * SettlementTo枚举类转成list
     */
    public static List<EnumBean> settlementTo(){
        List<EnumBean> list = Lists.newArrayList();
        for (SettlementTo e : SettlementTo.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * 科目状态
     * @return
     * SubjectStatus枚举类转成list
     */
    public static List<EnumBean> subjectStatus() {
        List<EnumBean> list = Lists.newArrayList();
        for (SubjectStatus e : SubjectStatus.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * 科目种类
     * @return
     * SubjectType枚举类转成list
     */
    public static List<EnumBean> subjectType() {
        List<EnumBean> list = Lists.newArrayList();
        for (SubjectType e : SubjectType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * 科目级别
     * @return
     * SubjectType枚举类转成list
     */
    public static List<EnumBean> subjectLevel() {
        List<EnumBean> list = Lists.newArrayList();
        for (SubjectLevel e : SubjectLevel.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * 科目级别
     * @return
     * SubjectType枚举类转成list
     */
    public static List<EnumBean> merchantAccountDirection() {
        List<EnumBean> list = Lists.newArrayList();
        for (MerchantAccountDirection e : MerchantAccountDirection.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * 内部账户类型
     * @return
     * InternalAccountType枚举类转成list
     */
    public static List<EnumBean> internalAccountType() {
        List<EnumBean> list = Lists.newArrayList();
        for (InternalAccountType e : InternalAccountType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            if(e.getValue().length() < 3 && Integer.parseInt(enumBean.getValue()) > 0){
                list.add(enumBean);
            }
        }
        return list;
    }


    /**
     * 内部账户详细类型--排除银行
     * @return
     * InternalAccountType枚举类转成list
     */
    public static List<EnumBean> internalAccountTypeNoBank() {
        List<EnumBean> list = Lists.newArrayList();
        for (InternalAccountType e : InternalAccountType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            if(e.getValue().length() == 3){
                list.add(enumBean);
            }
        }
        return list;
    }

    /**
     * @author ly
     * @param list
     * @param sep
     * @return String
     * 将list装成string中间添加sep
     */
    public static String listToString(List<String> list, String sep) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String person : list) {
            if (flag) {
                result.append(sep==null?"":sep);
            } else {
                flag = true;
            }
            result.append(person);
        }
        return result.toString();
    }

    /**
     * @author ly
     * @param arg
     * @return List<String>
     * 将String(带有,的字符串)装成List<String>
     */
    public static List<String> stringToList(String arg){
        List<String> list = Lists.newArrayList();
        if (arg != null && !"".equals(arg)) {
            String[] a = arg.split(",");
            for (String temp : a) {
                if (temp != null && temp.length() != 0) {
                    list.add(temp);
                }
            }
        }
        return list;
    }

    /**
     * @return CbmsOrderFormCStatus枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> orderFormStatus() {
        List<EnumBean> list = Lists.newArrayList();
        for (CbmsOrderFormCStatus e : CbmsOrderFormCStatus.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return CertificateType枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> certificateType() {
        List<EnumBean> list = Lists.newArrayList();
        for (CertificateType e : CertificateType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return MerchantType枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> merchantType() {
        List<EnumBean> list = Lists.newArrayList();
        for (MerchantType e : MerchantType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return AllowSystemType枚举类转成list
     * @author ly
     */
    public static List<EnumBean> allowSystemType() {
        List<EnumBean> list = Lists.newArrayList();
        for (AllowSystemType e : AllowSystemType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return CbmsEnterpriseType枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> cbmsEnterpriseType() {
        List<EnumBean> list = Lists.newArrayList();
        for (CbmsEnterpriseType e : CbmsEnterpriseType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return CbmsSupplierCategory枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> cbmsSupplierCategory() {
        List<EnumBean> list = Lists.newArrayList();
        for (CbmsSupplierCategory e : CbmsSupplierCategory.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return CbmsSupplierCategory枚举类转成list
     * @author 郭正新
     */
    public static List<EnumBean> cbmsTradeType() {
        List<EnumBean> list = Lists.newArrayList();
        for (CbmsTradeType e : CbmsTradeType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * @return CbmsSupplierCategory枚举类转成list
     * @author 马振
     */
    public static List<EnumBean> integraChannelPayType() {
        List<EnumBean> list = Lists.newArrayList();
        for (IntegraChannelPayType e : IntegraChannelPayType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * MerchantStatus枚举类转换list
     */
    public static List<EnumBean> merchantStatus() {
        List<EnumBean> list = Lists.newArrayList();
        for (MerchantStatus e : MerchantStatus.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * ContractType枚举类转换list
     */
    public static List<EnumBean> contractType() {
        List<EnumBean> list = Lists.newArrayList();
        for (ContractType e : ContractType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * MerchantStatus枚举类转换list
     */
    public static List<EnumBean> channelReqResult() {
        List<EnumBean> list = Lists.newArrayList();
        for (RiskChannelReqResult e : RiskChannelReqResult.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }

    /**
     * InstallmentType枚举类转换list
     */
    public static List<EnumBean> installmentType() {
        List<EnumBean> list = Lists.newArrayList();
        for (InstallmentType e : InstallmentType.values()) {
            EnumBean enumBean = new EnumBean();
            enumBean.setName(e.getContent());
            enumBean.setValue(e.getValue());
            list.add(enumBean);
        }
        return list;
    }
}
