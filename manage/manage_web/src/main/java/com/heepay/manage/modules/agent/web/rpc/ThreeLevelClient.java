/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.agent.web.rpc;

import com.heepay.common.util.Constants;
import com.heepay.manage.rpc.service.ThreeLinkageService;
import com.heepay.rpc.client.BaseClient;
import com.heepay.rpc.client.ClientThreadLocal;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

/**
 * 名称：三级联动的client
 * <p/>
 * 创建者  B.HJ
 * 创建时间 2016-08-23-15:49
 * 创建描述：三级联动的client
 * <p/>
 * 审核者：
 * 审核时间：
 * 审核描述：
 * <p/>
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Service
public class ThreeLevelClient extends BaseClient {

    private static final String SERVICENAME = "threeLevelServiceImpl";
    private static final String NODENAME = Constants.RPCNodeName.MERCHANTRPC;

    private ThreeLinkageService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new ThreeLinkageService.Client(ClientThreadLocal.getInstance().getProtocol());
    }

    /**
     * 三级联动
     *
     * @param id       id
     * @param callback 回调
     * @param role     角色
     * @return 字符串
     * @throws TException
     */
    public String getStr(String id, String callback, String role) throws TException {
        try {
            return getClient().select(id, callback, role);
        } finally {
            this.close();
        }
    }

    public String getBank(String id, String callback, String role, String bankNo) throws TException {
        try {
            return getClient().selectBank(id, callback, role, bankNo);
        } finally {
            this.close();
        }
    }


    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODENAME);
    }

    public String getBankList() throws TException {
        try {
            return getClient().getBankList();
        } finally {
            this.close();
        }
    }

    /**
     * @param area
     * @param bankNo
     * @param openbankName
     * @return
     * @throws TException
     * @discription 根据城市名称和银行ID查询银行列表
     * @author yanxb
     * @created 2016年9月26日 下午2:13:24
     */
    public String selectByAreaNameAndBankId(String area, String bankNo, String openbankName) throws TException {
        try {
            return getClient().selectAreaName(area, bankNo, openbankName);
        } finally {
            this.close();
        }
    }
}
