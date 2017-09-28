package com.heepay.tpds.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.heepay.tpds.dao.TpdsCustomerMapper;
import com.heepay.tpds.dao.TpdsObjectDetailMapper;
import com.heepay.tpds.dao.TpdsObjectMakeMapper;
import com.heepay.tpds.entity.TpdsCustomer;
import com.heepay.tpds.entity.TpdsCustomerExample;
import com.heepay.tpds.entity.TpdsObjectDetail;
import com.heepay.tpds.entity.TpdsObjectDetailExample;
import com.heepay.tpds.entity.TpdsObjectMake;
import com.heepay.tpds.entity.TpdsObjectMakeExample;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangyinglei
* 创建时间：  2017年2月14日下午6:47:32
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
public class TpdsObjectDetailDaoImpl extends BaseDaoImpl<TpdsObjectDetail,TpdsObjectDetailExample> implements TpdsObjectDetailMapper{
	
	// 默认构造方法设置命名空间
	public TpdsObjectDetailDaoImpl() {
		super.setNs("com.heepay.tpds.dao.TpdsObjectDetailMapper");
	}

	
}
