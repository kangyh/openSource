package com.heepay.billing.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.billing.dao.SettleChannelLogMapper;
import com.heepay.billing.entity.SettleChannelLog;
import com.heepay.billing.entity.SettleChannelLogExample;
import com.heepay.enums.billing.BillingCheckStatus;



@Repository
public class SettleChannelLogDaoImpl extends BaseDaoImpl<SettleChannelLog, SettleChannelLogExample> implements SettleChannelLogMapper {

	// 默认构造方法设置命名空间
	public SettleChannelLogDaoImpl() {
		super.setNs("com.heepay.billing.dao.SettleChannelLogMapper");
	}
	
	/*
	 * 根据对账状态查询对账日志表中对账批次号
	 * (non-Javadoc)
	 * @see com.heepay.billing.dao.SettleChannelLogMapper#findSettleChannelLogCheckBathByCheckStatus(java.util.Map)
	 */
	@Override
	public List<SettleChannelLog> findSettleChannelLogCheckBathByCheckStatus(Map<String,Object> map) {
		return super.getSqlSession().selectList(this.getNs()+".findSettleChannelLogCheckBathByCheckStatus",map);
	}

	/*
	 * 更新对账日志表（settle_channel_log）中的同一对账批次号的记录：记录数(平账)、总金额(平账)、对账状态、最后操作时间
	 * (non-Javadoc)
	 * @see com.heepay.billing.dao.SettleChannelLogMapper#updateSettleChannelLog(java.util.Map)
	 */
	@Override
	public void updateSettleChannelLog(SettleChannelLog settleChannelLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null != settleChannelLog){
			if(null != settleChannelLog.getRecordNum()){//记录数
				map.put("recordNum", settleChannelLog.getRecordNum());
			}
			if(null != settleChannelLog.getTotalAmount()){//总金额
				map.put("totalAmount", settleChannelLog.getTotalAmount());
			}
			if(null != settleChannelLog.getCheckStatus()){//对账状态
				map.put("checkStatus", settleChannelLog.getCheckStatus());
			}
			if(null != settleChannelLog.getOperEndTime()){//最后操作时间
				map.put("operEndTime", settleChannelLog.getOperEndTime());
			}
			if(null != settleChannelLog.getCheckBathNo()){//对账批次号
				map.put("checkBathNo", settleChannelLog.getCheckBathNo());
			}
			if(null != settleChannelLog.getInRecordNum()){//入款总笔数
				map.put("inRecordNum", settleChannelLog.getInRecordNum());
			}
			if(null != settleChannelLog.getInTotalAmount()){//入款总金额
				map.put("inTotalAmount", settleChannelLog.getInTotalAmount());
			}
			if(null != settleChannelLog.getOutRecordNum()){//出款总笔数
				map.put("outRecordNum", settleChannelLog.getOutRecordNum());
			}
			if(null != settleChannelLog.getOutTotalAmount()){//出款总金额
				map.put("outTotalAmount", settleChannelLog.getOutTotalAmount());
			}
			if(null != settleChannelLog.getErrorRecordNum()){//差错总笔数
				map.put("errorRecordNum", settleChannelLog.getErrorRecordNum());
			}
			if(null != settleChannelLog.getErrorTotalAmount()){//差错总金额
				map.put("errorTotalAmount", settleChannelLog.getErrorTotalAmount());
			}
		}
		super.getSqlSession().update(this.getNs()+".updateSettleChannelLog",map);
	}
	
	//把下载好的对账单记录插入日记记录表
	@Override
	public int insert(SettleChannelLog record) {
		
		//modified by xuangagn 2017-01-12  将不为null值的字段插入对账日志表
		return super.insertSelective(record);
	}
	
	
	//根据checkBath查询log
	@Override
	public SettleChannelLog selectByCheckBathNo(String checkBathNo){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkBathNo", checkBathNo);
		
		SettleChannelLog settleChannelLog = super.getSqlSession().selectOne(this.getNs()+".selectByCheckBathNo", map);
		
		return settleChannelLog;
	}

	@Override
	public boolean fingSettleChannelLog(String file) {
         boolean flag=false;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("file", file);
		List<SettleChannelLog> list =super.getSqlSession().selectList(this.getNs()+".fingSettleChannelLog", map);
		if( null != list && list.size()>0){
			flag=true;
		}
		return flag;
	}
	
	//查对账最大日期
	@Override
	public String maxPayTime() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String maxPayTime = super.getSqlSession().selectOne(this.getNs()+".maxPayTime", map);
		
		return maxPayTime;
		
		
	}

	/**
	 * 
	 * @方法说明：查询出上传对账文件的新纪录
	 * @时间：2016年10月13日
	 * @创建人：wangl
	 */
	@Override
	public List<SettleChannelLog> selectListEntity(SettleChannelLog record) {
		
		return super.getSqlSession().selectList(this.getNs()+".selectListEntity", record);
	}

	/**
	 * 
	 * @方法说明：  查询对账日志，判断是否有符合条件的对账文件
	 * @author chenyanming
	 * @param channelCode
	 * @param channelType
	 * @param checkBathno
	 * @param value
	 * @return
	 * @时间：2016年10月13日下午6:08:01
	 */
	@Override
	public SettleChannelLog querySettleChannelLog(String channelCode, String channelType, String checkBathno,
			String checkStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("channelCode", channelCode);
		map.put("channelType", channelType);
		map.put("checkBathNo", checkBathno);
		map.put("checkStatus", checkStatus);
		return super.getSqlSession().selectOne(this.getNs()+".querySettleChannelLog", map);
	}
	
	/**
	 * 
	 * @方法说明：通过对账状态，查询对账日志
	 * @author xuangang
	 * @param map
	 * @return
	 * @时间：2016年11月23日上午11:44:40
	 */
	@Override
	public List<SettleChannelLog> querySettleChannelLogByCheckStatus(Map<String, Object> map){
		
		return super.getSqlSession().selectList(this.getNs()+".querySettleChannelLogByCheckStatus", map);
	}
	
	 @Override
		public List<SettleChannelLog> selectByCodeAndType(String channelCode, String channelType, String data) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channelCode", channelCode);
			map.put("channelType", channelType);
			map.put("operBeginTime", data);
			return super.getSqlSession().selectList(this.getNs()+".selectByCodeAndType", map);
		}

		@Override
		public List<SettleChannelLog> selectByCheckStatus(String checkStatus, String operBeginTime) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("checkStatus", BillingCheckStatus.CHECKSTATUSWS.getValue());
			map.put("operBeginTime", operBeginTime);
			return super.getSqlSession().selectList(this.getNs()+".selectByCheckStatus", map);
		}

		@Override
		public List<SettleChannelLog> selectByOperBeginTime(String operBeginTime) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("operBeginTime", operBeginTime);
			return super.getSqlSession().selectList(this.getNs()+".selectByOperBeginTime", map);
		}
}
