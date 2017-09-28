package com.heepay.rpc.billing.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.billing.dao.ClearChannelRecordMapper;
import com.heepay.billing.dao.ClearMerchantRecordMapper;
import com.heepay.billing.dao.ClearingChannelRecordHisMapper;
import com.heepay.billing.dao.ClearingMerchantRecordHisMapper;
import com.heepay.billing.dao.SettleBillRecordHisMapper;
import com.heepay.billing.dao.SettleBillRecordMapper;
import com.heepay.billing.dao.SettleChannelRecordHisMapper;
import com.heepay.billing.dao.SettleChannelRecordMapper;
import com.heepay.billing.dao.SettleDifferChannelHisMapper;
import com.heepay.billing.dao.SettleDifferChannelMapper;
import com.heepay.billing.dao.SettleDifferMerchantHisMapper;
import com.heepay.billing.dao.SettleDifferMerchantMapper;
import com.heepay.billing.dao.SettleDifferRecordHisMapper;
import com.heepay.billing.dao.SettleDifferRecordMapper;
import com.heepay.billing.dao.SettleMerchantRecordHisMapper;
import com.heepay.billing.dao.SettleMerchantRecordMapper;
import com.heepay.billing.entity.ClearChannelRecord;
import com.heepay.billing.entity.ClearChannelRecordExample;
import com.heepay.billing.entity.ClearMerchantRecord;
import com.heepay.billing.entity.ClearMerchantRecordExample;
import com.heepay.billing.entity.SettleBillRecord;
import com.heepay.billing.entity.SettleBillRecordExample;
import com.heepay.billing.entity.SettleChannelRecord;
import com.heepay.billing.entity.SettleChannelRecordExample;
import com.heepay.billing.entity.SettleDifferChannel;
import com.heepay.billing.entity.SettleDifferChannelExample;
import com.heepay.billing.entity.SettleDifferMerchant;
import com.heepay.billing.entity.SettleDifferMerchantExample;
import com.heepay.billing.entity.SettleDifferRecord;
import com.heepay.billing.entity.SettleDifferRecordExample;
import com.heepay.billing.entity.SettleMerchantRecord;
import com.heepay.billing.entity.SettleMerchantRecordExample;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.date.DateUtils;
import com.heepay.rpc.billing.service.IBillingDataBackUpService;
/**
 * 
 *
 * 描    述：清结算数据库备份
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年3月23日 上午10:35:31
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
public class BillingDataBackUpServiceImpl implements IBillingDataBackUpService {
	
	private static final Logger logger = LogManager.getLogger();

	@Autowired
	private ClearChannelRecordMapper clearChannelRecordDaoImpl;//通道侧清算记录
	
	@Autowired
	private ClearingChannelRecordHisMapper clearingChannelRecordHisDaoImpl;//通道侧清算记录历史表
	
	@Autowired
	private ClearMerchantRecordMapper clearMerchantRecordDaoImpl;//商户侧清算记录
	
	@Autowired
	private ClearingMerchantRecordHisMapper clearingMerchantRecordHisDaoImpl;//商户侧清算记录历史表
	
	@Autowired
	private SettleBillRecordMapper settleBillRecordDaoImpl;//账单明细表
	
	@Autowired
	private SettleBillRecordHisMapper settleBillRecordHisDaoImpl;//账单明细历史表
	
	@Autowired
	private SettleChannelRecordMapper settleChannelRecordDaoImpl;//通道结算记录
	
	@Autowired
	private SettleChannelRecordHisMapper settleChannelRecordHisDaoImpl;//通道结算记录历史表
	
	@Autowired
	private SettleDifferChannelMapper settleDifferChannelDaoImpl;//通道差错账汇总
	
	@Autowired
	private SettleDifferChannelHisMapper settleDifferChannelHisDaoImpl;//通道差错账汇总历史表
	
	@Autowired
	private SettleDifferMerchantMapper settleDifferMerchantDaoImpl;//商户差错账汇总
	
	@Autowired
	private SettleDifferMerchantHisMapper settleDifferMerchantHisDaoImpl;//商户差错账汇总历史表
	
	@Autowired
	private SettleDifferRecordMapper settleDifferRecordDaoImpl;//差异记录
	
	@Autowired
	private SettleDifferRecordHisMapper settleDifferRecordHisDaoImpl;//差异记录历史表
	
	@Autowired
	private SettleMerchantRecordMapper settleMerchantRecordDaoImpl;//商户结算记录
	
	@Autowired
	private SettleMerchantRecordHisMapper settleMerchantRecordHisDaoImpl;//商户结算记录历史表
	
	/*
	 * 数据备份
	 * (non-Javadoc)
	 * @see com.heepay.rpc.billing.service.BillingDataBackUpService.Iface#billingDataBackUp()
	 */
	@Override
	public void billingDataBackUp(){
		try {
			//通道侧清算记录历史表入库
			clearingChannelRecordHis();
			//商户侧清算记录历史表入库
			clearingMerchantRecordHis();
			//账单明细历史表入库
			settleBillRecordHis();
			//通道结算记录历史表入库
			settleChannelRecordHis();
			//通道差错账汇总历史表入库
			settleDifferChannelHis();
			//商户差错账汇总历史表入库
			settleDifferMerchantHis();
			//差异记录历史表入库
			settleDifferRecordHis();
			//商户结算记录历史表入库
			settleMerchantRecordHis();
		} catch (Exception e) {
			logger.error("数据清结算数据备份异常--------->{}", e);
			throw new RuntimeException();
		}
	}

	/**
	 * 
	 * @方法说明：通道侧清算记录历史表入库
	 * @时间：2017年3月10日 下午5:25:07
	 * @创建人：wangdong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void clearingChannelRecordHis() {
		ClearChannelRecordExample example = new ClearChannelRecordExample();
		com.heepay.billing.entity.ClearChannelRecordExample.Criteria criteria = example.createCriteria();
		criteria.andSettleTimeLessThan(DateUtils.getMonthFirstDate());
		List<ClearChannelRecord> list  = clearChannelRecordDaoImpl.selectByExample(example);
		int insertNum = 0;
		if(null != list && list.size() > 0){
			logger.info("【通道侧清算记录条数:"+list.size()+"】--------->{}", list.size());
			for(ClearChannelRecord record : list){
				logger.info("数据清结算数据备份【通道侧清算记录历史表入库开始】--------->{}", record);
				String json = JsonMapperUtil.nonDefaultMapper().toJson(record);
				Map<String, Object> map = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
				if(map.containsKey("payTime")){
					map.put("payTime", DateUtils.format(Long.valueOf(map.get("payTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("channelTime")){
					map.put("channelTime", DateUtils.format(Long.valueOf(map.get("channelTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("checkTime")){
					map.put("checkTime", DateUtils.format(Long.valueOf(map.get("payTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("settleTime")){
					map.put("settleTime", DateUtils.format(Long.valueOf(map.get("settleTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("settleTimePlan")){
					map.put("settleTimePlan", DateUtils.format(Long.valueOf(map.get("settleTimePlan")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("costTime")){
					map.put("costTime", DateUtils.format(Long.valueOf(map.get("costTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("finishTime")){
					map.put("finishTime", DateUtils.format(Long.valueOf(map.get("finishTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				map.put("dateZip", new Date());
				insertNum += clearingChannelRecordHisDaoImpl.insertMap(map);
			}
			logger.info("数据清结算数据备份【通道侧清算记录历史表入库结束】--------->{}", insertNum);
			
			if(insertNum>0){
				//删除对应的时间段数据
				int deleteNum = clearChannelRecordDaoImpl.deleteByExample(example);
				logger.info("数据清结算数据备份【通道侧清算记录删除对应的时间段数据】--------->{}", deleteNum);
			}
		}else{
			logger.info("数据清结算数据备份【通道侧清算记录历史表无符合条件的数据】--------->{}", insertNum);
		}
	}
	
	/**
	 * 
	 * @方法说明：商户侧清算记录历史表入库
	 * @时间：2017年3月10日 下午5:25:07
	 * @创建人：wangdong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void clearingMerchantRecordHis() {
		ClearMerchantRecordExample clearMerchantRecordExample = new ClearMerchantRecordExample();
		com.heepay.billing.entity.ClearMerchantRecordExample.Criteria criteria = clearMerchantRecordExample.createCriteria();
		criteria.andSettleTimeLessThan(DateUtils.getMonthFirstDate());
		List<ClearMerchantRecord> list  = clearMerchantRecordDaoImpl.selectByExample(clearMerchantRecordExample);
		int insertNum = 0;
		if(null != list && list.size() > 0){
			logger.info("【商户侧清算记录条数:"+list.size()+"】--------->{}", list.size());
			for(ClearMerchantRecord record : list){
				logger.info("数据清结算数据备份【商户侧清算记录历史表入库开始】--------->{}", record);
				String json = JsonMapperUtil.nonDefaultMapper().toJson(record);
				Map<String, Object> map = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
				if(map.containsKey("successTime")){
					map.put("successTime", DateUtils.format(Long.valueOf(map.get("successTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("settleTime")){
					map.put("settleTime", DateUtils.format(Long.valueOf(map.get("settleTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("settleTimePlan")){
					map.put("settleTimePlan", DateUtils.format(Long.valueOf(map.get("settleTimePlan")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("feeTime")){
					map.put("feeTime", DateUtils.format(Long.valueOf(map.get("feeTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("finishTime")){
					map.put("finishTime", DateUtils.format(Long.valueOf(map.get("finishTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("busiTime")){
					map.put("busiTime", DateUtils.format(Long.valueOf(map.get("busiTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				map.put("dateZip", new Date());
				insertNum += clearingMerchantRecordHisDaoImpl.insertMap(map);
			}
			logger.info("数据清结算数据备份【商户侧清算记录历史表入库结束】--------->{}", insertNum);
			
			if(insertNum>0){
				//删除对应的时间段数据
				int deleteNum = clearMerchantRecordDaoImpl.deleteByExample(clearMerchantRecordExample);
				logger.info("数据清结算数据备份【商户侧清算记录删除对应的时间段数据】--------->{}", deleteNum);
			}
		}else{
			logger.info("数据清结算数据备份【商户侧清算记录历史表无符合条件的数据】--------->{}", insertNum);
		}
	}
	
	/**
	 * 
	 * @方法说明：账单明细记录历史表
	 * @时间：2017年3月10日 下午5:52:08
	 * @创建人：wangdong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void settleBillRecordHis() {
		SettleBillRecordExample billRecordExample = new SettleBillRecordExample();
		com.heepay.billing.entity.SettleBillRecordExample.Criteria criteria = billRecordExample.createCriteria();
		criteria.andSaveTimeLessThan(DateUtils.getMonthFirstDate());
		List<SettleBillRecord> list  = settleBillRecordDaoImpl.selectByExample(billRecordExample);
		int insertNum = 0;
		if(null != list && list.size() > 0){
			logger.info("【账单明细记录条数:"+list.size()+"】--------->{}", list.size());
			for(SettleBillRecord record : list){
				logger.info("数据清结算数据备份【账单明细记录历史表入库开始】--------->{}", record);
				String json = JsonMapperUtil.nonDefaultMapper().toJson(record);
				Map<String, Object> map = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
				if(map.containsKey("saveTime")){
					map.put("saveTime", DateUtils.format(Long.valueOf(map.get("saveTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				map.put("dateZip", new Date());
				insertNum += settleBillRecordHisDaoImpl.insertMap(map);
			}
			logger.info("数据清结算数据备份【账单明细记录历史表入库结束】--------->{}", insertNum);
			
			if(insertNum>0){
				//删除对应的时间段数据
				int deleteNum = settleBillRecordDaoImpl.deleteByExample(billRecordExample);
				logger.info("数据清结算数据备份【账单明细记录删除对应的时间段数据】--------->{}", deleteNum);
			}
		}else{
			logger.info("数据清结算数据备份【账单明细记录历史表无符合条件的数据】--------->{}", insertNum);
		}
	}
	
	/**
	 * 
	 * @方法说明：通道结算记录历史表
	 * @时间：2017年3月10日 下午5:25:07
	 * @创建人：wangdong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void settleChannelRecordHis() {
		SettleChannelRecordExample settleChannelRecordExample = new SettleChannelRecordExample();
		com.heepay.billing.entity.SettleChannelRecordExample.Criteria criteria = settleChannelRecordExample.createCriteria();
		criteria.andSettleTimeLessThan(DateUtils.getMonthFirstDate());
		List<SettleChannelRecord> list  = settleChannelRecordDaoImpl.selectByExample(settleChannelRecordExample);
		int insertNum = 0; 
		if(null != list && list.size() > 0){
			logger.info("【通道结算记录条数:"+list.size()+"】--------->{}", list.size());
			for(SettleChannelRecord record : list){
				logger.info("数据清结算数据备份【通道结算记录历史表入库开始】--------->{}", record);
				String json = JsonMapperUtil.nonDefaultMapper().toJson(record);
				Map<String, Object> map = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
				if(map.containsKey("checkTime")){
					map.put("checkTime", DateUtils.format(Long.valueOf(map.get("checkTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("settleTime")){
					map.put("settleTime", DateUtils.format(Long.valueOf(map.get("settleTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("costTime")){
					map.put("costTime", DateUtils.format(Long.valueOf(map.get("costTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				map.put("dateZip", new Date());
				insertNum += settleChannelRecordHisDaoImpl.insertMap(map);
			}
			logger.info("数据清结算数据备份【通道结算记录历史表入库结束】--------->{}", insertNum);
			
			if(insertNum>0){
				//删除对应的时间段数据
				int deleteNum = settleChannelRecordDaoImpl.deleteByExample(settleChannelRecordExample);
				logger.info("数据清结算数据备份【通道结算记录删除对应的时间段数据】--------->{}", deleteNum);
			}
		}else{
			logger.info("数据清结算数据备份【通道结算记录历史表无符合条件的数据】--------->{}", insertNum);
		}
	}
	
	/**
	 * 
	 * @方法说明：通道差错账汇总历史表
	 * @时间：2017年3月10日 下午5:25:07
	 * @创建人：wangdong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void settleDifferChannelHis() {
		SettleDifferChannelExample differChannelExample = new SettleDifferChannelExample();
		com.heepay.billing.entity.SettleDifferChannelExample.Criteria criteria = differChannelExample.createCriteria();
		criteria.andDealTimeLessThan(DateUtils.getMonthFirstDate());
		List<SettleDifferChannel> list  = settleDifferChannelDaoImpl.selectByExample(differChannelExample);
		int insertNum = 0; 
		if(null != list && list.size() > 0){
			logger.info("【通道差错账汇总记录条数:"+list.size()+"】--------->{}", list.size());
			for(SettleDifferChannel record : list){
				logger.info("数据清结算数据备份【通道差错账汇总记录历史表入库开始】--------->{}", record);
				String json = JsonMapperUtil.nonDefaultMapper().toJson(record);
				Map<String, Object> map = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
				if(map.containsKey("errorDate")){
					map.put("errorDate", DateUtils.format(Long.valueOf(map.get("errorDate")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("dealTime")){
					map.put("dealTime", DateUtils.format(Long.valueOf(map.get("dealTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				map.put("dateZip", new Date());
				insertNum += settleDifferChannelHisDaoImpl.insertMap(map);
			}
			logger.info("数据清结算数据备份【通道差错账汇总记录历史表入库结束】--------->{}", insertNum);
			
			if(insertNum>0){
				//删除对应的时间段数据
				int deleteNum = settleDifferChannelDaoImpl.deleteByExample(differChannelExample);
				logger.info("数据清结算数据备份【通道差错账汇总记录删除对应的时间段数据】--------->{}", deleteNum);
			}
		}else{
			logger.info("数据清结算数据备份【通道差错账汇总记录历史表无符合条件的数据】--------->{}", insertNum);
		}
	}
	
	/**
	 * 
	 * @方法说明：商户差错账汇总历史表
	 * @时间：2017年3月10日 下午5:25:07
	 * @创建人：wangdong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void settleDifferMerchantHis() {
		SettleDifferMerchantExample merchantExample = new SettleDifferMerchantExample();
		com.heepay.billing.entity.SettleDifferMerchantExample.Criteria criteria = merchantExample.createCriteria();
		criteria.andDealTimeLessThan(DateUtils.getMonthFirstDate());
		List<SettleDifferMerchant> list  = settleDifferMerchantDaoImpl.selectByExample(merchantExample);
		int insertNum = 0; 
		if(null != list && list.size() > 0){
			logger.info("【商户差错账汇总记录条数:"+list.size()+"】--------->{}", list.size());
			for(SettleDifferMerchant record : list){
				logger.info("数据清结算数据备份【商户差错账汇总记录历史表入库开始】--------->{}", record);
				String json = JsonMapperUtil.nonDefaultMapper().toJson(record);
				Map<String, Object> map = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
				if(map.containsKey("errorDate")){
					map.put("errorDate", DateUtils.format(Long.valueOf(map.get("errorDate")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("dealTime")){
					map.put("dealTime", DateUtils.format(Long.valueOf(map.get("dealTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				map.put("dateZip", new Date());
				insertNum += settleDifferMerchantHisDaoImpl.insertMap(map);
			}
			logger.info("数据清结算数据备份【商户差错账汇总记录历史表入库结束】--------->{}", insertNum);
			
			if(insertNum>0){
				//删除对应的时间段数据
				int deleteNum = settleDifferMerchantDaoImpl.deleteByExample(merchantExample);
				logger.info("数据清结算数据备份【商户差错账汇总记录删除对应的时间段数据】--------->{}", deleteNum);
			}
		}else{
			logger.info("数据清结算数据备份【商户差错账汇总记录历史表无符合条件的数据】--------->{}", insertNum);
		}
	}
	
	/**
	 * 
	 * @方法说明：差异记录历史表
	 * @时间：2017年3月10日 下午5:25:07
	 * @创建人：wangdong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void settleDifferRecordHis() {
		SettleDifferRecordExample recordExample = new SettleDifferRecordExample();
		com.heepay.billing.entity.SettleDifferRecordExample.Criteria criteria = recordExample.createCriteria();
		criteria.andPayTimeLessThan(DateUtils.getMonthFirstDate());
		List<SettleDifferRecord> list  = settleDifferRecordDaoImpl.selectByExample(recordExample);
		int insertNum = 0; 
		if(null != list && list.size() > 0){
			logger.info("【差异记录条数:"+list.size()+"】--------->{}", list.size());
			for(SettleDifferRecord record : list){
				logger.info("数据清结算数据备份【差异记录历史表入库开始】--------->{}", record);
				String json = JsonMapperUtil.nonDefaultMapper().toJson(record);
				Map<String, Object> map = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
				if(map.containsKey("errorDate")){
					map.put("errorDate", DateUtils.format(Long.valueOf(map.get("errorDate")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("operationDate")){
					map.put("operationDate", DateUtils.format(Long.valueOf(map.get("operationDate")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("successTime")){
					map.put("successTime", DateUtils.format(Long.valueOf(map.get("successTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("payTime")){
					map.put("payTime", DateUtils.format(Long.valueOf(map.get("payTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("busiTime")){
					map.put("busiTime", DateUtils.format(Long.valueOf(map.get("busiTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				map.put("dateZip", new Date());
				insertNum += settleDifferRecordHisDaoImpl.insertMap(map);
			}
			logger.info("数据清结算数据备份【差异记录历史表入库结束】--------->{}", insertNum);
			
			if(insertNum>0){
				//删除对应的时间段数据
				int deleteNum = settleDifferRecordDaoImpl.deleteByExample(recordExample);
				logger.info("数据清结算数据备份【差异记录删除对应的时间段数据】--------->{}", deleteNum);
			}
		}else{
			logger.info("数据清结算数据备份【差异记录历史表无符合条件的数据】--------->{}", insertNum);
		}
	}
	
	/**
	 * 
	 * @方法说明：商户结算记录历史表
	 * @时间：2017年3月10日 下午5:25:07
	 * @创建人：wangdong
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public void settleMerchantRecordHis() {
		SettleMerchantRecordExample merchantRecordExample = new SettleMerchantRecordExample();
		com.heepay.billing.entity.SettleMerchantRecordExample.Criteria criteria = merchantRecordExample.createCriteria();
		criteria.andSettleTimeLessThan(DateUtils.getMonthFirstDate());
		List<SettleMerchantRecord> list  = settleMerchantRecordDaoImpl.selectByExample(merchantRecordExample);
		int insertNum = 0; 
		if(null != list && list.size() > 0){
			logger.info("【商户结算记录条数:"+list.size()+"】--------->{}", list.size());
			for(SettleMerchantRecord record : list){
				logger.info("数据清结算数据备份【商户结算记录历史表入库开始】--------->{}", record);
				String json = JsonMapperUtil.nonDefaultMapper().toJson(record);
				Map<String, Object> map = JsonMapperUtil.nonEmptyMapper().fromJson(json, Map.class);
				if(map.containsKey("checkTime")){
					map.put("checkTime", DateUtils.format(Long.valueOf(map.get("checkTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("settleTime")){
					map.put("settleTime", DateUtils.format(Long.valueOf(map.get("settleTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				if(map.containsKey("feeTime")){
					map.put("feeTime", DateUtils.format(Long.valueOf(map.get("feeTime")+""),"yyyy-MM-dd HH:mm:ss"));
				}
				map.put("dateZip", new Date());
				insertNum += settleMerchantRecordHisDaoImpl.insertMap(map);
			}
			logger.info("数据清结算数据备份【商户结算记录历史表入库结束】--------->{}", insertNum);
			
			if(insertNum>0){
				//删除对应的时间段数据
				int deleteNum = settleMerchantRecordDaoImpl.deleteByExample(merchantRecordExample);
				logger.info("数据清结算数据备份【商户结算记录删除对应的时间段数据】--------->{}", deleteNum);
			}
		}else{
			logger.info("数据清结算数据备份【商户结算记录历史表无符合条件的数据】--------->{}", insertNum);
		}
	}
	
}
