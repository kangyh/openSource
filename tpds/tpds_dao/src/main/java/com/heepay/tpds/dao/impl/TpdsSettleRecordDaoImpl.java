package com.heepay.tpds.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsSettleRecordMapper;
import com.heepay.tpds.entity.TpdsSettleRecord;
import com.heepay.tpds.entity.TpdsSettleRecordExample;

/**
 * 
 * 
 * 描    述：结算数据实现类
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年2月18日下午3:01:22 
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
public class TpdsSettleRecordDaoImpl extends BaseDaoImpl<TpdsSettleRecord, TpdsSettleRecordExample> implements TpdsSettleRecordMapper {

	// 默认构造方法设置命名空间
	public TpdsSettleRecordDaoImpl() {
			super.setNs("com.heepay.tpds.dao.TpdsSettleRecordMapper");
	}
	
	@Override
	public int insert(TpdsSettleRecord record) {
		return super.getSqlSession().insert(this.getNs() + ".insert", record);
	}

	/**
     * 
     * @方法说明：根据结算单号查询
     * @author chenyanming
     * @param settleBath
     * @return
     * @时间：2017年3月15日下午2:41:53
     */
	@Override
	public TpdsSettleRecord queryBySettleBath(String settleBath) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("settleBath", settleBath);
		return super.getSqlSession().selectOne(this.getNs() + ".queryBySettleBath", map);
	}

	

}
