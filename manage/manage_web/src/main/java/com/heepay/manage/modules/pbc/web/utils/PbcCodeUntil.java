package com.heepay.manage.modules.pbc.web.utils;

import java.util.Random;


/***
 * 
* 
* 描    述：传输报文流水号编码规则
*
* 创 建 者：wangl
* 创建时间：  Dec 13, 201611:51:16 AM
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
*
*
*
*
 */
public class PbcCodeUntil {

   
	/**
	 * 
	 * @方法说明：业务类型编码：
	 * 业务类型编码：
	 * 0101-止付支付账号                         
	 * 0102-止付支付账号的反馈                      
	 * 0103-已止付支付账号的延期/解除                  
	 * 0104-已止付支付账号的延期/解除反馈                
	 * 0105-单笔交易止付                         
	 * 0106-单笔交易止付反馈                       
	 * 0107-单笔交易的止付延期/解除                   
	 * 0108-单笔交易的止付延期/解除的反馈                
	 * 0201-冻结支付账号                         
	 * 0202-冻结支付账号的反馈                      
	 * 0203-已冻结支付账号的延期/解除                  
	 * 0204-已冻结支付账号的延期/解除反馈                
	 * 0205-单笔交易冻结                         
	 * 0206-单笔交易冻结反馈                       
	 * 0207-单笔交易的冻结延期/解除                   
	 * 0208-单笔交易的冻结延期/解除的反馈                
	 * 0301-账户交易明细查询                       
	 * 0302-账户交易明细查询反馈                     
	 * 0303-账户主体详情查询                       
	 * 0304-账户主体详情查询反馈                     
	 * 0305-账户动态查询                         
	 * 0306-账户动态查询反馈                       
	 * 0307-账户动态查询解除                       
	 * 0308-账户动态查询解除反馈                     
	 * 0309-关联全支付账号查询                      
	 * 0310-关联全支付账号查询反馈                    
	 * 0311-按交易流水号查询银行卡/支付帐号               
	 * 0312-按交易流水号查询银行卡/支付帐号反馈             
	 * 0313-按POS编号查询收单明细                   
	 * 0314-按POS编号查询收单明细反馈                 
	 * 0401-案件举报(暂不开通)                     
	 * 0402-案件举报反馈(暂不开通)                   
	 * 0403-可疑名单上报-异常消费支付账户                
	 * 0404-可疑名单上报-涉案账户                    
	 * 0405-可疑名单上报-涉案账户历史明细上报              
	 * 0406-可疑交易上报-单笔交易上报                  

     *
	 * @时间：Dec 13, 2016
	 * @创建人：wangl
	 */
    public String  getReqHeader(String code){
       
        
        String serialNo= code+getRandomNumber(32);
        
        return serialNo;
    }

    public static String getRandomNumber(int digCount){
        Random rndnew = new Random();
        StringBuilder sb = new StringBuilder(digCount);
        for (int i = 0; i < digCount; i++){
            sb.append(rndnew.nextInt(10));
        }
        return sb.toString();
    }


}
