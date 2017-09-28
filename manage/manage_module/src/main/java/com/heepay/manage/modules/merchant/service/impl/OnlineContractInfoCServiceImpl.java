/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.service.impl;

import com.google.common.collect.Lists;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.MerchantAutographType;
import com.heepay.enums.RateBusinessType;
import com.heepay.enums.RouteStatus;
import com.heepay.manage.common.enums.ContractType;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.merchant.dao.*;
import com.heepay.manage.modules.merchant.service.OnlineContractInfoCService;
import com.heepay.manage.modules.merchant.vo.*;
import com.heepay.manage.modules.sys.utils.BusinessInfoUtils;
import com.heepay.vo.MerchantVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 * 描    述：线上签约Service
 *
 * 创 建 者： @author ly
 * 创建时间：
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
@Service
@Transactional(readOnly = true)
public class OnlineContractInfoCServiceImpl extends CrudService<OnlineContractInfoDao, OnlineContractInfo>  implements OnlineContractInfoCService {

    @Autowired
    private OnlineContractInfoDao onlineContractInfoDao;

    @Autowired
    private NormProductDao normProductDao;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private MerchantProductRateDao merchantProductRateDao;

    @Autowired
    private MerchantRateBankDao merchantRateBankDao;

    @Override
    public OnlineContractInfo selectOnlineContractInfo(String userId) {
        return onlineContractInfoDao.selectOnlineContractInfo(userId);
    }

    @Transactional(readOnly = false)
    public int status(OnlineContractInfo onlineContractInfo) {
        return onlineContractInfoDao.status(onlineContractInfo);
    }

    @Transactional(readOnly = false)
    public void legalAudit(OnlineContractInfo onlineContractInfo) {
        onlineContractInfo.preUpdate();
        if(RouteStatus.AUDIT_SUCCESS.getValue().equals(onlineContractInfo.getLegalAuditStatus())){
            onlineContractInfo.setRcAuditStatus(RouteStatus.AUDING.getValue());
        }else{
            onlineContractInfo.setStatus("FAILED");
            onlineContractInfo.setRejectTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        }
        onlineContractInfoDao.legalAudit(onlineContractInfo);
    }

    @Override
    public List<OnlineContractInfo> findProductList(OnlineContractInfo onlineContractInfo) {
        List<OnlineContractInfo> onlineContractInfos = onlineContractInfoDao.findProductList(onlineContractInfo);
        List<OnlineContractInfo> list = Lists.newArrayList();
        //获取产品信息
        Map<String, Product> productMap = BusinessInfoUtils.getProductMap();
        for(OnlineContractInfo onlineContractInfo1 : onlineContractInfos){
            //产品名称
            onlineContractInfo1.setProductName(
                    productMap.get(onlineContractInfo1.getNormProductCode().substring(2)).getName());
            //业务类型
            onlineContractInfo1.setBusinessType(
                    RateBusinessType.labelOf(productMap.get(
                            onlineContractInfo1.getNormProductCode().substring(2)).getBusinessType()));
            //合同地址
            if(StringUtils.isNotBlank(onlineContractInfo1.getOriginalFilePath())){
                onlineContractInfo1.setOriginalFilePath(RandomUtil.getFastDfs(onlineContractInfo1.getOriginalFilePath()));
            }
            list.add(onlineContractInfo1);
        }
        return list;
    }

    @Override
    public OnlineContractInfo queryProductsBybatchNoAndUserId(String batchNo, String userId) {
        OnlineContractInfo onlineContractInfo = onlineContractInfoDao.queryProductsBybatchNoAndUserId(batchNo,userId).get(0);
        onlineContractInfo.setEndCreateTime(com.heepay.manage.common.utils.DateUtil.getNowTimeAfterYear(onlineContractInfo.getCreateTime(),1));
        if(StringUtils.isNotBlank(onlineContractInfo.getLegalAuditStatus())){
            onlineContractInfo.setLegalAuditStatus(RouteStatus.labelOf(onlineContractInfo.getLegalAuditStatus()));
        }
        if(StringUtils.isNotBlank(onlineContractInfo.getRcAuditStatus())){
            onlineContractInfo.setRcAuditStatus(RouteStatus.labelOf(onlineContractInfo.getRcAuditStatus()));
        }
        return onlineContractInfo;
    }

    @Transactional(readOnly = false)
    public void rcAudit(OnlineContractInfo onlineContractInfo) {
        onlineContractInfo.preUpdate();
        if(RouteStatus.AUDIT_SUCCESS.getValue().equals(onlineContractInfo.getRcAuditStatus())){
            //开通产品(模拟给商户配置产品)
            boolean openProduct = openProduct(onlineContractInfo);
            if(openProduct){
                onlineContractInfo.setStatus("SUCCES");
                onlineContractInfo.setSuccessTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
            }else{
                //系统问题
            }
        }else{
            onlineContractInfo.setStatus("FAILED");
            onlineContractInfo.setRejectTime(DateUtil.getDate(DateUtil.DATETIME_FORMAT));
        }
        onlineContractInfoDao.rcAudit(onlineContractInfo);
    }

    private boolean openProduct(OnlineContractInfo onlineContractInfo) {
        //查询一个批次开通的产品
        List<OnlineContractInfo> onlineContractInfos = onlineContractInfoDao.findProductList(onlineContractInfo);
        for(OnlineContractInfo info : onlineContractInfos){
            //根据标准产品查询开通产品信息
            List<NormProduct> normProducts = normProductDao.selectByNormProductCode(info.getNormProductCode());
            if(null != normProducts && !normProducts.isEmpty()){
                //开通产品
                saveMerchantRateBank(normProducts,onlineContractInfo);
            }
        }
        return true;
    }

    /**
     * @discription 转换MerchantProductRate
     * @author ly
     * @created 2017年5月16日16:29:36
     */
    private MerchantProductRate saveMerchantProductRate(NormProduct normProduct,OnlineContractInfo onlineContractInfo) {
        MerchantProductRate merchantProductRate = new MerchantProductRate();
        //获取商户信息
        MerchantVO merchantVo = merchantDao.getMerchantVo(onlineContractInfo.getUserId());
        if(null == merchantVo){
            return null;
        }
        merchantProductRate.setMerchantCompanyName(merchantVo.getMerchantCompany());
        merchantProductRate.setMerchantLoginName(merchantVo.getMerchantLoginName());
        //获取产品信息
        Map<String, Product> productMap = BusinessInfoUtils.getProductMap();
        merchantProductRate.setBusinessType(productMap.get(normProduct.getProductCode()).getBusinessType());
        merchantProductRate.setProductCode(normProduct.getProductCode());
        merchantProductRate.setProductName(productMap.get(normProduct.getProductCode()).getName());
        merchantProductRate.setChargeCollectionType(normProduct.getChargeCollectionType());
        merchantProductRate.setChargeDeductType(normProduct.getChargeDeductType());
        merchantProductRate.setChargeSource(normProduct.getChargeSource());
        merchantProductRate.setStatus(normProduct.getStatus());
        merchantProductRate.setSettlementTo(normProduct.getSettlementTo());
        merchantProductRate.setSettleType(normProduct.getSettleType());
        //获取签约信息
        merchantProductRate.setMerchantId(onlineContractInfo.getUserId());
        merchantProductRate.setRuleBeginTime(onlineContractInfo.getCreateTime());
        merchantProductRate.setRuleEndTime(
                com.heepay.manage.common.utils.DateUtil.getNowTimeAfterYear(onlineContractInfo.getCreateTime(),1));
        merchantProductRate.setIsRefundable(normProduct.getIsRefundable());
        merchantProductRate.setBackUrl(onlineContractInfo.getBackUrl());
        merchantProductRate.setNotifyUrl(onlineContractInfo.getNotifyUrl());
        merchantProductRate.setIpDomain(onlineContractInfo.getIpDomain());
        merchantProductRate.setAutographType(MerchantAutographType.MD5.getValue());
        merchantProductRate.setAutographKey(RandomUtil.getKey(onlineContractInfo.getUserId(),
                normProduct.getProductCode(),MerchantAutographType.MD5.getValue()));
        merchantProductRate.setContractType(ContractType.ONLINE.getValue());
        merchantProductRateDao.insert(merchantProductRate);
        return merchantProductRate;
    }

    /**
     * @discription 保存merchant_rate_bank表
     * @author ly
     * @created 2017年5月16日16:56:14
     */
    private void saveMerchantRateBank(List<NormProduct> normProducts,OnlineContractInfo onlineContractInfo) {
        String id = saveMerchantProductRate(normProducts.get(0), onlineContractInfo).getId();
        for(NormProduct normProduct : normProducts){
            MerchantRateBank merchantRateBank = new MerchantRateBank();
            merchantRateBank.setRateId(id);
            if(normProducts.size()>1){
                merchantRateBank.setBankNo(Constants.MERCHANT_RATE_ALL_BANK);
            }
            merchantRateBank.setChargeFee(normProduct.getChargeFee());
            merchantRateBank.setChargeRatio(normProduct.getChargeRatio());
            merchantRateBank.setChargeType(normProduct.getChargeType());
            merchantRateBank.setMaxFee(normProduct.getMaxFee());
            merchantRateBank.setMinFee(normProduct.getMinFee());
            merchantRateBank.setBankCardType(normProduct.getBankCardType());
            merchantRateBank.setUserOrDate();
            merchantRateBankDao.insert(merchantRateBank);
        }
    }
}