/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.web;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.enums.AuthenticationResult;
import com.heepay.enums.InterfaceStatus;
import com.heepay.manage.modules.merchant.rpc.client.EnterprisesCertificationClient;
import com.heepay.manage.modules.merchant.rpc.client.MobileAuthenticationClient;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import com.heepay.rpc.payment.model.AuthRes;
import com.heepay.rpc.payment.model.EnterprisesCertificationModel;
import com.heepay.rpc.payment.model.InternalAuthReq;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 描述：企业实名认证controller
 *
 * 创建者  B.HJ
 * 创建时间 2017-02-07-15:52
 * 创建描述：企业实名认证controller
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Controller
@RequestMapping(value = "${adminPath}/merchant/corporateIdentities")
public class CorporateIdentitiesContrller {

    @Autowired
    private EnterprisesCertificationClient enterprisesCertificationClient;


    @Autowired
    private MobileAuthenticationClient mobileAuthenticationClient;

    @Autowired
    private MerchantCService merchantCService;

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * 企业实名认证
     *
     * entName  公司名称
     * entRegNo 工商注册号
     * repIdNo  法定代表人身份证号
     * repName  法人代表
     */
    @RequestMapping("/auth")
    @ResponseBody
    public String corporateIdentities(String merchantId) {
        Merchant merchant = merchantCService.get(merchantId);
        logger.info("用户{}进行企业实名认证。", merchantId);
        EnterprisesCertificationModel model = new EnterprisesCertificationModel();
        model.setMerchantId(Long.valueOf(merchantId));
        model.setEntName(merchant.getCompanyName());
        model.setEntRegNo(merchant.getBusinessLicenseNo());
        model.setRepIdNo(merchant.getLegalIdcard());
        model.setRepName(merchant.getLegalRepresentative());
        String s = enterprisesCertificationClient.corporateIndentities(model);
        Map<String,String> map = JsonMapperUtil.nonEmptyMapper().fromJson(s,Map.class);
        if(s.contains("\"result\":\"1000\"")){
            logger.info("{}企业实名认证成功!",merchantId);
            merchantCService.updateAuth(merchantId,"SUCCES","");
            return "认证成功";
        }else{
            logger.info("{}企业实名认证失败!{}",merchantId,s);
            merchantCService.updateAuth(merchantId,"FAILED",map.get("errorMsg"));
            return "认证失败,"+map.get("errorMsg");
        }
    }

    /**
     * @方法说明：联系人手机号认证
     * @时间： 19/07/2017 10:51
     * @创建人：wangl
     */
    @RequestMapping("/phoneAuth")
    @ResponseBody
    public String phoneAuth(String merchantId) {

        Merchant merchant = merchantCService.get(merchantId);

        logger.info("用户进行手机实名认证--->{}", merchant.getUserId().toString());
        InternalAuthReq req = new InternalAuthReq();
        req.setMerchantId(merchant.getUserId());//商户ID
        req.setOperator(UserUtils.getUser().getName());//操作员
        req.setName(merchant.getContactor());//联系人
        req.setIdCardNo(merchant.getContactorIdcardNo());//身份证号
        req.setMobileNo(merchant.getContactorPhone());//手机号

        /**
         * 查询当前是那种验证方式
         * dictValue 岂安 = 1 ， 考拉手机二三四要素鉴权 = 2 ，考拉通用鉴权 = 3
         */
        String dictValue = DictUtils.getDictValue("MobileAuthentication", "MobileAuthentication", "");
        AuthRes authRes = null;
        /*if("1".equals(dictValue)){
            authRes = mobileAuthenticationClient.internalMobileAuthQiAn(req);
        }else if("2".equals(dictValue)){
            authRes = mobileAuthenticationClient.mobileAuthKaola(req);
        }else{
            //result = "认证失败,服务调取异常";
        }*/
        switch(dictValue){
            case "1":
                authRes = mobileAuthenticationClient.internalMobileAuthQiAn(req);
                break;
            case "2":
                authRes = mobileAuthenticationClient.mobileAuthKaola(req);
                break;
        }

        String result = "";
        if(null != authRes){
            logger.info("成功调取交易手机实名认证--->{}",authRes);
            int retCode = authRes.getRetCode();
            String authResult = authRes.getAuthResult();
            if(retCode == InterfaceStatus.SUCCESS.getValue()){//调取服务成功
                if(authResult.equals(AuthenticationResult.SUCCES.getValue())){//鉴权成功
                    merchantCService.updatePhoneAuth(merchant.getUserId().toString(),"SUCCESS",authRes.getRetMsg());
                    result = "认证成功";
                }else {
                    merchantCService.updatePhoneAuth(merchant.getUserId().toString(),"FAILED","鉴权失败");
                    result = "鉴权失败";
                }
            }else {//调取服务失败
                merchantCService.updatePhoneAuth(merchant.getUserId().toString(),"FAILED",authRes.getRetMsg());
                result = "认证失败,"+authRes.getRetMsg();
            }
        }else{
            logger.info("认证失败,服务调取异常");
            result = "认证失败,服务调取异常";
        }
        return result;
    }
}