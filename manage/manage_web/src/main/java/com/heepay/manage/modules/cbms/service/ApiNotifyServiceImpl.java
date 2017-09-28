package com.heepay.manage.modules.cbms.service;
import com.heepay.cbms.rpc.cbmsweb.module.CbmsNotifyRecordModel;
import com.heepay.manage.common.utils.JsonMapper;
import com.heepay.manage.modules.cbms.entity.CbmsOrderSum;
import com.heepay.manage.modules.cbms.entity.CbmsOrderform;
import com.heepay.manage.modules.cbms.producer.CbmsQueuePorducerSender;
import com.heepay.manage.modules.cbms.util.CustomRequestNotifyVO;
import com.heepay.manage.modules.cbms.util.CustomStatus;
import com.heepay.manage.modules.cbms.util.SignTools;
import com.heepay.manage.modules.merchant.service.MerchantProductRateCService;
import com.heepay.manage.modules.merchant.vo.MerchantProductRate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述：
 * <p>
 * 创建者: 牛俊鹏
 * 创建时间: 2017/7/27
 * 创建描述: 申报信息异步通知
 * <p>
 * 审核者:
 * 审核时间:
 * 审核描述:
 * <p>
 * 修改者:
 * 修改时间:
 * 修改内容:
 */
@Component
public class ApiNotifyServiceImpl {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private MerchantProductRateCService merchantProductRateCService;
    @Autowired
    private CbmsQueuePorducerSender queuePorducerSender;
    @Autowired
    private NotifyRecordServiceClient notifyRecordServiceClient;

        public void customNotifyUtil(CbmsOrderSum cbmsOrderSum,List<CbmsOrderform> updateList){
        if(cbmsOrderSum.getNotifyUrl()==null){
            return;
        }
        if(!cbmsOrderSum.getNotifyUrl().startsWith("http") && !cbmsOrderSum.getNotifyUrl().startsWith("https")){
            logger.info("通知地址不合法,汇总信息:{}",cbmsOrderSum.toString());
            return;
        }
            // 获取商户产品
            MerchantProductRate merchantProductRate=  new MerchantProductRate();
            merchantProductRate.setProductCode("CP20");
            merchantProductRate.setMerchantId(cbmsOrderSum.getMerchantNo());
            MerchantProductRate productVO = merchantProductRateCService.exist(merchantProductRate);
            if(productVO==null){
                logger.error("通过商户ID:{}, 产品编码:{},获取产品key失败", cbmsOrderSum.getMerchantNo(), "CP20");
                return;
            }

            List<CustomStatus> list=new ArrayList<>();
            for( CbmsOrderform cbmsOrderform :updateList){
                CustomStatus customStatus=new CustomStatus();
                customStatus.setStatus(cbmsOrderform.getCustomStatus());
                customStatus.setMessage(cbmsOrderform.getCausesFailure()!=null?cbmsOrderform.getCausesFailure():null);
                customStatus.setOrderFormNo(cbmsOrderform.getOrderFormNo());
                list.add(customStatus);
            }
            //组装信息
            CustomRequestNotifyVO customRequestNotifyVO=new CustomRequestNotifyVO();
            customRequestNotifyVO.setMerchantId(cbmsOrderSum.getMerchantNo());//汇付宝id
            customRequestNotifyVO.setMerchantBatchNo(cbmsOrderSum.getMerchantBatchNo());//商户批次号
            customRequestNotifyVO.setImportBatchNo(cbmsOrderSum.getImportBatchNumber());//导入批次号
            customRequestNotifyVO.setNotifyUrl(cbmsOrderSum.getNotifyUrl());//设置异步通知地址
            customRequestNotifyVO.setCustomDetails(JsonMapper.toJsonString(list));//申报明细
            try {
                String signString = SignTools.signData(productVO.getAutographKey(),customRequestNotifyVO);//签名字符串
                customRequestNotifyVO.setSignString(signString);
            } catch (Exception e) {
                logger.error("签名失败{}",e);
                return;
            }
            //插入队列
            String notifyStr=JsonMapper.toJsonString(customRequestNotifyVO);
            logger.info("异步通知json字符串是:{}",notifyStr);
            Date date=new Date();
            /**插入通知记录, 若已存在进行更新*/
            CbmsNotifyRecordModel CbmsNotifyRecordModel=new CbmsNotifyRecordModel();
            CbmsNotifyRecordModel.setImportBatchNo(cbmsOrderSum.getImportBatchNumber());
            CbmsNotifyRecordModel.setMerchantId(cbmsOrderSum.getMerchantNo());
            CbmsNotifyRecordModel.setMerchantBatchNo(cbmsOrderSum.getMerchantBatchNo());
            CbmsNotifyRecordModel.setNotifyUrl(cbmsOrderSum.getNotifyUrl());
            CbmsNotifyRecordModel.setStatus("UNKNOW");
            CbmsNotifyRecordModel.setNotifyRequestParams(notifyStr);
            CbmsNotifyRecordModel.setNotifyNumber("0");
            CbmsNotifyRecordModel.setNotifyType("2");
            CbmsNotifyRecordModel.setNotifyTime(date.getTime());
            CbmsNotifyRecordModel.setCreateTime(date.getTime());
            CbmsNotifyRecordModel.setUpdateTime(date.getTime());
            boolean bool = notifyRecordServiceClient.insertOrUpdate(CbmsNotifyRecordModel);
            if(bool){
                queuePorducerSender.send(notifyStr);
            }else{
                logger.info("插入通知记录失败");
            }

    }
}
