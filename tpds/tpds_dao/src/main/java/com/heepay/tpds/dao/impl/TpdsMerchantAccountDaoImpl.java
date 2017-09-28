package com.heepay.tpds.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsBankWithdrawMapper;
import com.heepay.tpds.dao.TpdsMerchantAccountMapper;
import com.heepay.tpds.entity.TpdsBankWithdraw;
import com.heepay.tpds.entity.TpdsMerchantAccount;

/**
 * 
 * 
 * 描 述：描述
 *
 * 创 建 者：dongzhengjiang
 * 创建时间： 2017年2月16日 下午1:36:26
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
public class TpdsMerchantAccountDaoImpl extends BaseNewDaoImpl<TpdsMerchantAccount> implements TpdsMerchantAccountMapper {

	// 默认构造方法设置命名空间
		public TpdsMerchantAccountDaoImpl() {
			super.setNs("com.heepay.tpds.dao.TpdsMerchantAccountMapper");
		}

		@Override
		public TpdsMerchantAccount selectByMerchantId(String MerchantId) {
			return this.getSqlSession().selectOne(this.getNs()+".selectByMerchantId",MerchantId);
		}

		@Override
		public TpdsMerchantAccount selectBySysNoAndBankNo(TpdsMerchantAccount entity) {
			// TODO Auto-generated method stub
			return this.getSqlSession().selectOne(this.getNs()+".selectBySysNoAndBankNo",entity);
		}

		@Override
		public int insert(TpdsMerchantAccount entity) {
			return this.getSqlSession().insert(this.getNs()+".insert",entity);
		}

		@Override
		public int update(TpdsMerchantAccount entity) {
			return this.getSqlSession().update(this.getNs()+".update",entity);
		}

		@Override
		public List<TpdsMerchantAccount> getList() {
			// TODO Auto-generated method stub			
				return this.getSqlSession().selectList(this.getNs()+".getList");
		}

		/*
		 * 获取联行号
		 * (non-Javadoc)
		 * @see com.heepay.tpds.dao.TpdsMerchantAccountMapper#selectMerchantIdBySystemNo(java.lang.String)
		 */
		@Override
		public TpdsMerchantAccount selectBankCardBranchIdBySystemNo(String SystemNo) {
			return this.getSqlSession().selectOne(this.getNs()+".selectBankCardBranchIdBySystemNo",SystemNo);
		}

		/**
		 * 
		 * @方法说明： 根据状态获取商户列表
		 * @author chenyanming
		 * @param status
		 * @return
		 * @时间：2017年2月18日下午7:03:22
		 */
		@Override
		public List<TpdsMerchantAccount> selectAccountRecordByStatus(String status) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", status);
			return super.getSqlSession().selectList(this.getNs()+".selectAccountRecordByStatus",map);
		}

		@Override
		public TpdsMerchantAccount selectAccountRecordByStatusSystemNo(String status, String SystemNo) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", status);
			map.put("systemNo", SystemNo);
			return super.getSqlSession().selectOne(this.getNs()+".selectAccountRecordByStatusSystemNo",map);
		}
		
}

	

 