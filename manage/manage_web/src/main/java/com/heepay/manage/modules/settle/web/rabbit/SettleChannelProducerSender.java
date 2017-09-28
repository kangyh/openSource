package com.heepay.manage.modules.settle.web.rabbit;

import com.heepay.common.util.JsonMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettleChannelProducerSender {

    private Logger logger = LoggerFactory.getLogger(SettleChannelProducerSender.class);
    
    @Autowired
    private AmqpTemplate hyBillingSettleChannelRecordDetailMqTemplate;

    @Autowired
    private AmqpTemplate hyBillingSettleMerchantDetailMqTemplate;

    /**
     * @方法说明：通道侧结算单据发送
     * @时间： 2017/7/10 11:03
     * @创建人：wangl
     */
    public void sendChannelDataToQueue(Object param) {
        String json = JsonMapperUtil.nonDefaultMapper().toJson(param);
        logger.info(" ---sendChannelDataToQueue---- {}", json);
        hyBillingSettleChannelRecordDetailMqTemplate.convertAndSend(json);
    }

    /**
     * @方法说明：商户侧结算单据发送
     * @时间： 2017/7/10 11:03
     * @创建人：wangl
     */
    public void sendMerchantDataToQueue(Object param){
        String json = JsonMapperUtil.nonDefaultMapper().toJson(param);
        logger.info(" ---sendMerchantDataToQueue---- {}", json);
        hyBillingSettleMerchantDetailMqTemplate.convertAndSend(json);
    }

}