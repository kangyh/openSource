/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 描述：
 * <p>
 * 创建者: yangzd
 * 创建时间: 2017-04-10-16:41
 * 创建描述: 签名类
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
public class SignTools {

    private static final Logger log = LogManager.getLogger();

    /**
     * 对数据进行签名
     *
     * @param formparams
     * @param key
     * @return MD5签名值 (小写)
     */
    public static String signData(List<NameValuePair> formparams, String key) throws Exception {
        formparams.sort(Comparator.comparing(NameValuePair::getName));
        StringBuffer sb = new StringBuffer();
        for (NameValuePair nvp : formparams) {
            sb.append(nvp.getName());
            sb.append("=");
            sb.append(nvp.getValue());
            sb.append("&");
        }
        sb.append("key=").append(key);

        //签名
        String sign = Md5Tools.MD5(sb.toString());
        if (sign.length() > 0) {
            log.info("签名串:{}, 签名值:{}", sb.toString(), sign.toLowerCase());
            return sign.toLowerCase();
        } else {
            throw new Exception("签名失败");
        }
    }
    public static String signData(String productKey, CustomRequestNotifyVO customResponseVO) throws Exception {
        List<NameValuePair> formparams = new ArrayList<>();
        formparams.add(new BasicNameValuePair("merchantId", customResponseVO.getMerchantId()));
        formparams.add(new BasicNameValuePair("merchantBatchNo", customResponseVO.getMerchantBatchNo()));
        formparams.add(new BasicNameValuePair("importBatchNo", customResponseVO.getImportBatchNo()));
        formparams.add(new BasicNameValuePair("customDetails", customResponseVO.getCustomDetails()));
        return signData(formparams, productKey);
    }
}
