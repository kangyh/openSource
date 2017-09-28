package com.heepay.tpds.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsSettleItemMapper;
import com.heepay.tpds.dao.TpdsSettleRecordMapper;
import com.heepay.tpds.entity.TpdsCutDay;
import com.heepay.tpds.entity.TpdsSettleItem;
import com.heepay.tpds.entity.TpdsSettleItemExample;
import com.heepay.tpds.entity.TpdsSettleRecord;
import com.heepay.tpds.entity.TpdsSettleRecordExample;

/**
 * 
 * 
 * 描    述：
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月18日下午3:30:11 
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
@Repository
public class TpdsSettleItemMapperDaoImpl extends BaseDaoImpl<TpdsSettleItem, TpdsSettleItemExample> implements TpdsSettleItemMapper {

	// 默认构造方法设置命名空间
		public TpdsSettleItemMapperDaoImpl() {
				super.setNs("com.heepay.tpds.dao.TpdsSettleItemMapper");
		}
		
		@Override
		public int insert(TpdsSettleItem record) {
			return super.getSqlSession().insert(this.getNs() + ".insert", record);
		}

		/**
		 * 
		 * @方法说明： 根据日切点查询符合条件的数据
		 * @author chenyanming
		 * @param tpdsSettleItem
		 * @param tpdsCutDay
		 * @return
		 * @时间：2017年2月18日下午4:18:16
		 */
		@Override
		public List<TpdsSettleItem> getTpdsSettleItemBath(TpdsSettleItem tpdsSettleItem) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("merchantNo", tpdsSettleItem.getMerchantNo());
			map.put("transType", tpdsSettleItem.getTransType());
			map.put("successTime", tpdsSettleItem.getSuccessTime());
			map.put("settleStatus", tpdsSettleItem.getSettleStatus());
			return super.getSqlSession().selectList(this.getNs()+".getTpdsSettleItemBath",map);
		}

		/**
		 * 
		 * @方法说明： 将对应的明细记录结算状态修改为已结算
		 * @author chenyanming
		 * @param transNo
		 * @时间：2017年2月20日上午10:22:30
		 */
		@Override
		public void updateByTransNo(String transNo) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("transNo", transNo);
			map.put("settleStatus", "Y");
			map.put("finishTime", new Date());
			super.getSqlSession().update(this.getNs() + ".updateByTransNo", map);
		}

}
