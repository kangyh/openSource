package com.heepay.common.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.Logger;

import com.heepay.common.vo.B4ChargeRecordVo;

public class RiskBossLogUtil {
	
	public static void logB4Risk(Logger log,B4ChargeRecordVo b4ChargeRecordVo) throws ParseException{
		  
		  if(log==null||b4ChargeRecordVo==null){
		    log.info("风控日志记录异常:{},{}",log,b4ChargeRecordVo);
	      return;
	    }
		  log.info("{}风控日志记录开始",b4ChargeRecordVo.getTransNo().trim());
			StringBuffer sb = new StringBuffer();
			//以下顺序不能调换，因为是按正则表达式进行解析
			sb.append("risk_message:");
			sb.append("{merchantOrderNo:");    //必添
			sb.append(b4ChargeRecordVo.getMerchantOrderNo()==null? "null":b4ChargeRecordVo.getMerchantOrderNo().trim());
			sb.append(",transNo:"); //必添
			sb.append(b4ChargeRecordVo.getTransNo()==null? "null":b4ChargeRecordVo.getTransNo().trim());
			sb.append(",merchantId:");//必添
			sb.append(b4ChargeRecordVo.getMerchantId()==null? "null":b4ChargeRecordVo.getMerchantId().trim());
			sb.append(",requestAmount:");//必添
			sb.append(b4ChargeRecordVo.getRequestAmount()==null? "null":b4ChargeRecordVo.getRequestAmount());
			sb.append(",payAmount:");//必添
			sb.append(b4ChargeRecordVo.getPayAmount()==null? "null":b4ChargeRecordVo.getPayAmount());
			sb.append(",bankcardType:");//必添
			sb.append(b4ChargeRecordVo.getBankcardType()==null? "null":b4ChargeRecordVo.getBankcardType().trim());
			sb.append(",cardholderGender:");//必添
			sb.append(b4ChargeRecordVo.getCardholderGender()==null? "null":b4ChargeRecordVo.getCardholderGender().trim());
			sb.append(",channelCode:");//必添
			sb.append(b4ChargeRecordVo.getChannelCode()==null? "null":b4ChargeRecordVo.getChannelCode().trim());
			sb.append(",channelTransType:");//必添
			sb.append(b4ChargeRecordVo.getChannelTransType()==null? "null":b4ChargeRecordVo.getChannelTransType().trim());
			sb.append(",bankCode:");//必添
			sb.append(b4ChargeRecordVo.getBankCode()==null? "null":b4ChargeRecordVo.getBankCode().trim());
			sb.append(",requestStatus:");//必添
			sb.append(b4ChargeRecordVo.getRequestStatus()==null? "null":b4ChargeRecordVo.getRequestStatus().trim());
			sb.append(",requestIp:");//必添
			sb.append(b4ChargeRecordVo.getRequestIp()==null? "null":b4ChargeRecordVo.getRequestIp().trim());
			sb.append(",provinceId:");//必添
			sb.append(b4ChargeRecordVo.getProvinceId()==null? "null":b4ChargeRecordVo.getProvinceId().trim());
			sb.append(",requestTime:"); 
			sb.append(b4ChargeRecordVo.getRequestTime()==null? "null": b4ChargeRecordVo.getRequestTime().getTime());
			sb.append(",payFinishTime:"); //必添
			sb.append(b4ChargeRecordVo.getPayFinishTime()==null? "null": b4ChargeRecordVo.getPayFinishTime().getTime());
			sb.append(",settlePart:"); //必添
			sb.append(b4ChargeRecordVo.getSettlePart()==null? "null" :b4ChargeRecordVo.getSettlePart().trim());
			sb.append(",bankId:"); //必添
			sb.append(b4ChargeRecordVo.getBankId()==null? "null":b4ChargeRecordVo.getBankId().trim());
			sb.append(",bankShortName:"); //必添
			sb.append(b4ChargeRecordVo.getBankShortName()==null? "null":b4ChargeRecordVo.getBankShortName().trim());
			sb.append(",productCode:"); //必添
			sb.append(b4ChargeRecordVo.getProductCode()==null? "null":b4ChargeRecordVo.getProductCode().trim());
			sb.append(",productNumber:"); //必添
			sb.append(b4ChargeRecordVo.getProductNumber()==null? "null":b4ChargeRecordVo.getProductNumber().trim());
			sb.append(",productDescription:"); //必添
			sb.append(b4ChargeRecordVo.getProductDescription()==null? "null":b4ChargeRecordVo.getProductDescription().trim());
			sb.append(",settleAmount:"); //必添
			sb.append(b4ChargeRecordVo.getSettleAmount()==null? "null":b4ChargeRecordVo.getSettleAmount());
			sb.append(",settleStatus:"); //必添
			sb.append(b4ChargeRecordVo.getSettleStatus()==null? "null":b4ChargeRecordVo.getSettleStatus().trim());
			sb.append(",createPerson:"); //必添
			sb.append(b4ChargeRecordVo.getCreatePerson()==null? "null":b4ChargeRecordVo.getCreatePerson().trim());
			sb.append(",createTime:"); //必添
			sb.append(b4ChargeRecordVo.getCreateTime()==null? "null":b4ChargeRecordVo.getCreateTime().getTime());
			sb.append(",updatePerson:"); //必添
			sb.append(b4ChargeRecordVo.getUpdatePerson()==null? "null": b4ChargeRecordVo.getUpdatePerson().trim());
			sb.append(",updateTime:"); //必添
			sb.append(b4ChargeRecordVo.getUpdateTime()==null? "null": b4ChargeRecordVo.getUpdateTime().getTime());
			sb.append(",channleNo:"); //必添
			sb.append(b4ChargeRecordVo.getChannleNo()==null? "null":b4ChargeRecordVo.getChannleNo().trim());
			sb.append(",feeAmount:"); //必添
			sb.append(b4ChargeRecordVo.getFeeAmount()==null? "null":b4ChargeRecordVo.getFeeAmount());
			sb.append(",merchantName:"); //必添
			sb.append(b4ChargeRecordVo.getMerchantName()==null? "null":b4ChargeRecordVo.getMerchantName());
			sb.append(",transType:"); //必添
			sb.append(b4ChargeRecordVo.getTransType()==null? "null":b4ChargeRecordVo.getTransType());
			sb.append(",year:"); //必添
			sb.append(b4ChargeRecordVo.getYear()==null? "null":b4ChargeRecordVo.getYear().trim());
			sb.append(",month:"); //必添
			sb.append(b4ChargeRecordVo.getMonth()==null? "null":b4ChargeRecordVo.getMonth().trim());
			sb.append(",day:"); //必添
			sb.append(b4ChargeRecordVo.getDay()==null? "null":b4ChargeRecordVo.getDay().trim());
			
			sb.append("}");
			log.info(sb.toString());
		}
		public static String longDate2String(long date) throws ParseException
		{
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(date);
			Date datetime = c.getTime();
			return datetime.toString();
			
		}
		
		
}
