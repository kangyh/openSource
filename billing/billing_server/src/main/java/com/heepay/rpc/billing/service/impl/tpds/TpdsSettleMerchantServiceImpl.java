/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年2月13日上午11:40:49
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
package com.heepay.rpc.billing.service.impl.tpds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.SettleMerchantRecordMapper;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.SettleMerchantRecord;
import com.heepay.common.util.DateUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.date.DateUtils;
import com.heepay.rpc.billing.model.SettleMerchantDetail;
import com.heepay.rpc.billing.model.TpdsSettleMerchantModel;
import com.heepay.rpc.billing.model.querySettleMerchantModel;
import com.heepay.rpc.service.RpcService;

/***
 * 
 * 
 * 描    述：
 *
 * 创 建 者： xuangang
 * 创建时间：  2017年2月13日上午11:40:49
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
@Component
@RpcService(name="TpdsSettleMerchantServiceImpl",processor=com.heepay.rpc.billing.service.TpdsSettleMerchantService.Processor.class)
public class TpdsSettleMerchantServiceImpl implements com.heepay.rpc.billing.service.TpdsSettleMerchantService.Iface{

	/* (non-Javadoc)
	 * @see com.heepay.rpc.billing.service.TpdsSettleMerchantService.Iface#querySettleMerchantRecord(com.heepay.rpc.billing.model.querySettleMerchantModel)
	 */
	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	ClearMerchantRecordMapper  clearMerchantRecordDaoImpl;
	
	@Autowired
	SettleMerchantRecordMapper settleMerchantRecordDaoImpl;
	
	@Override
	public List<TpdsSettleMerchantModel> querySettleMerchantRecord(
			querySettleMerchantModel model) throws TException {
		
		String transType = model.getTransType();       //交易类型
		String merchantId = model.getMerchantId();   //商户编码
		String settleTime = model.getSettleTime();   //结算日期
		String settleStatus = model.getSettleStatus(); //结算状态 
		String settleBath = model.getSettleBath();   //结算单号
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("merchantId", merchantId);
		map.put("transType", transType);
		map.put("settleStatus", settleStatus);
		map.put("settleBath", settleBath);
		
		//如果结算日期为null或为空,则设置默认值（当日）
		if(StringUtil.notBlank(settleTime)){
		  map.put("settleTime", DateUtil.stringToDate(settleTime));
		}else{
			map.put("settleTime", DateUtil.stringToDate(DateUtil.getTodayYYYYMMDD(), "yyyy-MM-dd"));
			//map.put("settleTime", DateUtils.getDate());
		}
		logger.info("存管系统查询结算数据开始，商户号为：{},交易类型为：{},结算日期:{}", merchantId, transType, map.get("settleTime"));
		List<TpdsSettleMerchantModel> tpdsSettleMerchantList = new ArrayList<TpdsSettleMerchantModel>();
		try {
			List<SettleMerchantRecord> list = settleMerchantRecordDaoImpl.querySettleMerchantRecord(map);
			
			logger.info("共查询到" + list.size() + "条已结算数据");
			if(list != null && list.size()>0){
				
				for(SettleMerchantRecord settleMerchantRecord : list){
					
					TpdsSettleMerchantModel tpdsSettleMerchantModel = new TpdsSettleMerchantModel();
					
					tpdsSettleMerchantModel.setMerchantId(settleMerchantRecord.getMerchantId()==null?"":settleMerchantRecord.getMerchantId().toString()); 
					tpdsSettleMerchantModel.setMerchantType(settleMerchantRecord.getMerchantType());
					tpdsSettleMerchantModel.setTransType(settleMerchantRecord.getTransType());
					tpdsSettleMerchantModel.setCurrency(settleMerchantRecord.getCurrency());
					tpdsSettleMerchantModel.setPayNum(settleMerchantRecord.getPayNum());
					tpdsSettleMerchantModel.setTotalAmount(settleMerchantRecord.getTotalAmount().toString());
					tpdsSettleMerchantModel.setSettleTime(DateUtil.dateToString(settleMerchantRecord.getSettleTime()));
					tpdsSettleMerchantModel.setSettleBath(settleMerchantRecord.getSettleBath());
					tpdsSettleMerchantModel.setTotalFee(settleMerchantRecord.getTotalFee().toString());
					
					
					String tpdsSettleBatch = settleMerchantRecord.getSettleBath();
					
					if(StringUtil.notBlank(tpdsSettleBatch)){
						Map<String, Object> clearMap = new HashMap<String, Object>();
						clearMap.put("settleBath", tpdsSettleBatch);
						List<ClearMerchantRecord> clearList = clearMerchantRecordDaoImpl.queryClearMerchantRecord(clearMap);
						logger.info("结算单号" + tpdsSettleBatch + "共对应" + clearList.size() + "条明细数据");
						List<SettleMerchantDetail> detailList = new ArrayList<SettleMerchantDetail>();
						
						for(ClearMerchantRecord clearMerchantRecord : clearList){
							
							SettleMerchantDetail settleMerchantDetail = new SettleMerchantDetail();
							
							settleMerchantDetail.setMerchantId(clearMerchantRecord.getMerchantId()==null?"":clearMerchantRecord.getMerchantId().toString());
							settleMerchantDetail.setMerchantType(clearMerchantRecord.getMerchantType());
							settleMerchantDetail.setProductCode(clearMerchantRecord.getProductCode());
							settleMerchantDetail.setCurrency(clearMerchantRecord.getCurrency());
							settleMerchantDetail.setTransNo(clearMerchantRecord.getTransNo());
							settleMerchantDetail.setTransNoOld(clearMerchantRecord.getTransNoOld());
							settleMerchantDetail.setRequestAmount(clearMerchantRecord.getRequestAmount().toString());
							settleMerchantDetail.setSuccessTime(DateUtil.dateToString(clearMerchantRecord.getSuccessTime()));
							settleMerchantDetail.setSettleTime(DateUtil.dateToString(clearMerchantRecord.getSettleTimePlan()));
							settleMerchantDetail.setSettleNo(clearMerchantRecord.getSettleNo());
							settleMerchantDetail.setSettleBath(clearMerchantRecord.getSettleBath());
							settleMerchantDetail.setFee(clearMerchantRecord.getFee().toString());
							settleMerchantDetail.setFeeWay(clearMerchantRecord.getFeeWay());
							settleMerchantDetail.setFeeSettleCyc(clearMerchantRecord.getFeeSettleCyc());
							settleMerchantDetail.setCheckBath(clearMerchantRecord.getCheckBath());
							settleMerchantDetail.setTransType(clearMerchantRecord.getTransType());
							
							settleMerchantDetail.setMerchantOrderNo(clearMerchantRecord.getMerchantOrderNo());
							
							detailList.add(settleMerchantDetail);
						}
						tpdsSettleMerchantModel.setDetail(detailList);
					}	
					tpdsSettleMerchantList.add(tpdsSettleMerchantModel);
				}
			}
			return tpdsSettleMerchantList;
		} catch (Exception e) {
			logger.info("存管系统结算单数据查询出错，商户号为：{},交易类型为：{},结算日期:{}", merchantId, transType, map.get("settleTime"), e);
		}
		return tpdsSettleMerchantList;
	}
	/**
	 * 
	 * @方法说明：
	 * @author xuangang
	 * @param paraMap
	 * @return
	 * @时间：2017年2月13日下午2:48:09
	 */
	private List<SettleMerchantRecord> querySettleMerchantRecord(Map<String, Object> paraMap){
		
//		settleMerchantRecordDaoImpl
		return null;
	}

}
