package com.heepay.tpds.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *
 * 描 述：网银跳转
 *
 * 创 建 者：wangdong
 * 创建时间：2017/7/23 0:22
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
@RequestMapping("b2cCharge")
@Controller
public class B2CChargeController{

    private static Logger log = LogManager.getLogger();

    @RequestMapping(value = "b2cCharge", method = RequestMethod.POST ,produces = {"application/json;charset=UTF-8"})
    public String test(HttpServletRequest request, HttpServletResponse response) throws TException, IOException {

        log.info("跳转网银支付支付页面参数个数：{}",request.getParameterMap().size());
        log.info("跳转网银支付支付页面路径：{}",request.getParameter("unionPayUrl"));
        request.setAttribute("unionPayUrl", request.getParameter("unionPayUrl"));
        request.setAttribute("reqHyTime",request.getParameter("reqHyTime"));
        request.setAttribute("callBackUrl",request.getParameter("callBackUrl"));
        request.setAttribute("bankCardType",request.getParameter("bankCardType"));
        request.setAttribute("description",request.getParameter("description"));
        request.setAttribute("bankName",request.getParameter("bankName"));
        request.setAttribute("merchantOrderNo",request.getParameter("merchantOrderNo"));
        request.setAttribute("version",request.getParameter("version"));
        request.setAttribute("requestTime",request.getParameter("requestTime"));
        request.setAttribute("bankId",request.getParameter("bankId"));
        request.setAttribute("productCode",request.getParameter("productCode"));
        request.setAttribute("payAmount",request.getParameter("payAmount"));
        request.setAttribute("onlineType",request.getParameter("onlineType"));
        request.setAttribute("merchantId",request.getParameter("merchantId"));
        request.setAttribute("clientIp",request.getParameter("clientIp"));
        request.setAttribute("notifyUrl",request.getParameter("notifyUrl"));
        request.setAttribute("merchantUserId",request.getParameter("merchantUserId"));
        request.setAttribute("signString",request.getParameter("signString"));
        return "tpds/b2cCharge";
    }
}
