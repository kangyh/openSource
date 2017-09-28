/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.rpc.service.impl;

import com.heepay.manage.modules.sys.dao.DictDao;
import com.heepay.manage.modules.sys.entity.Dict;
import com.heepay.manage.rpc.service.ChannelPatternAndChannelTypeThrift;
import com.heepay.rpc.service.RpcService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 描述：根据type查询ChannelPatternAndChannelType字典值
 *
 * 创建者  B.HJ
 * 创建时间 2017-05-22-10:40
 * 创建描述：根据type查询ChannelPatternAndChannelType字典值
 *
 * 审核者：
 * 审核时间：
 * 审核描述：
 *
 * 修改者：
 * 修改时间：
 * 修改内容：
 */
@Component
@RpcService(name = "channelPatternAndChannelTypeServiceImpl", processor = com.heepay.manage.rpc.service.ChannelPatternAndChannelTypeService.Processor.class)
public class ChannelPatternAndChannelTypeService implements com.heepay.manage.rpc.service.ChannelPatternAndChannelTypeService.Iface{

    @Autowired
    private DictDao dictDao;

    @Override
    public List<ChannelPatternAndChannelTypeThrift> queryListByType(String type) throws TException {
        Dict searchDict = new Dict();
        searchDict.setType(type);
        List<Dict> list = dictDao.findList(searchDict);
        List<ChannelPatternAndChannelTypeThrift> returnList = new ArrayList<>();
        //无结果
        if(list.isEmpty()){
            return returnList;
        }else {
            for (Dict dict : list) {
                ChannelPatternAndChannelTypeThrift thrift = new ChannelPatternAndChannelTypeThrift();
                thrift.setLabel(dict.getLabel());
                thrift.setValue(dict.getValue());
                returnList.add(thrift);
            }
            return returnList;
        }
    }

    @Override
    public String queryChannelPatternChannelTypeByType(String type, String label) throws TException {
        Dict valueByLabel = dictDao.getValueByLabel(type, label);
        return valueByLabel == null ? "" : valueByLabel.getValue();
    }
}
