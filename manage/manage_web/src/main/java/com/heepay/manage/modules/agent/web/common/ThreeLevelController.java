/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.web.common;

import com.heepay.manage.modules.agent.web.rpc.ThreeLevelClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.heepay.manage.modules.agent.web.rpc.ThreeLevelClient;

/**
 * 名称：省市县三级联动
 * <p>
 * 创建者  B.HJ
 * 创建时间 2016-08-23-11:20
 * 创建描述：省市县三级联动
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
@RequestMapping("user")
public class ThreeLevelController {

    @Autowired
    private ThreeLevelClient threeLevelClient;

    /**
     * 定义全局日志
     */
    private static final Logger logger = LogManager.getLogger();

    /**
     * 三级联动获取数据
     *
     * @param id       id
     * @param callback callback
     * @param role     role
     * @return str
     * @throws TException
     */
    @RequestMapping(value = "/select")
    @ResponseBody
    public String select(String id, String callback, String role) throws TException{

        return threeLevelClient.getStr(id, callback, role);
    }

    /**
     * 选择银行
     * @param id            id
     * @param callback      callback
     * @param role          role
     * @param bankNo        bankNo
     * @return
     * @throws TException
     */
    @RequestMapping(value = "/selectBank")
    @ResponseBody
    public String selectBank(String id, String callback, String role,String bankNo) throws TException{

    	return threeLevelClient.getBank(id, callback, role, bankNo);
    }

    /**
     *
     * @return
     * @throws TException
     */
    @RequestMapping(value = "/bankList")
    @ResponseBody
    public String bankList() throws TException{
    	
    	return threeLevelClient.getBankList();
    }
}
