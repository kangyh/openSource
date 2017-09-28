package com.heepay.boss.client;

import com.heepay.manage.rpc.service.ChannelPatternAndChannelTypeService;
import com.heepay.manage.rpc.service.ChannelPatternAndChannelTypeThrift;
import com.heepay.rpc.client.BaseClientDistribute;
import com.heepay.rpc.client.ClientThreadLocal;
import com.heepay.rpc.client.ThriftClientProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描 述：manage查询字典信息
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/6/3 14:05
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@Component
public class ManageSeviceClient extends BaseClientDistribute {

    private static final String SERVICENAME = "channelPatternAndChannelTypeServiceImpl";

    private static final String NODENAME = "manager_server";

    private static final Logger log = LogManager.getLogger();

    @Resource(name = "manageClient")
    private ThriftClientProxy clientProxy;

    @Override
    public void setServiceName() {
        ClientThreadLocal.getInstance().setServiceName(SERVICENAME);
    }

    @Override
    public void setNodename() {
        ClientThreadLocal.getInstance().setNodename(NODENAME);
    }

    @Override
    public ThriftClientProxy getClientProxy() {
        return clientProxy;
    }

    public ChannelPatternAndChannelTypeService.Client getClient() {
        this.setServiceName();
        this.setNodename();
        this.setTMultiplexedProtocol();
        return new ChannelPatternAndChannelTypeService.Client(ClientThreadLocal
                .getInstance().getProtocol());
    }

    /**
     * @方法说明：查询指定的字典
     * @时间：2017年2月16日 上午10:31:51
     * @创建人：wangdong
     */
    public String queryChannelPatternChannelTypeByType(String type,String label) {
        ChannelPatternAndChannelTypeService.Client client = this.getClient();
        try {
            String dictInfo = client.queryChannelPatternChannelTypeByType(type,label);
            log.info("查询指定的字典 THRIFT服务,参数{},{},返回结果：{}", type,label,dictInfo);
            return dictInfo;
        } catch (TException e) {
            e.printStackTrace();
            log.error("查询指定的字典 THRIFT服务异常,参数{},{},异常信息：{}", type,label, e);
        } finally {
            this.close();
        }
        return null;
    }

    /**
     * @方法说明：查询该类型的所有字典
     * @时间： 2017/6/5 18:06
     * @创建人：wangdong
     */
    public Map<String, String> getChannelByChannelCodeList(String type){
        ChannelPatternAndChannelTypeService.Client client = this.getClient();
        try {
            Map<String, String> map = new HashMap<String, String>();
            List<ChannelPatternAndChannelTypeThrift> queryListByType = client.queryListByType(type);
            for (ChannelPatternAndChannelTypeThrift channelPatternAndChannelTypeThrift : queryListByType) {
                map.put(channelPatternAndChannelTypeThrift.getValue(), channelPatternAndChannelTypeThrift.getLabel());
            }
            log.info("查询该类型的所有字典 THRIFT服务,参数{},返回结果：{}", type,map);
            return map;
        } catch (TException e) {
            log.error("查询该类型的所有字典 THRIFT服务异常,参数{},异常信息：{}", type,e);
        } finally {
            this.close();
        }
        return null;
    }

}
