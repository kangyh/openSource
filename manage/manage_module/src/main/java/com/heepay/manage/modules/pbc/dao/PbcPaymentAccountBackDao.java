package com.heepay.manage.modules.pbc.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcPaymentAccountBack;
/**
 * 
 *
 * 描    述：关联全支付账号反馈
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年12月24日 上午8:56:50
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
@MyBatisDao
public interface PbcPaymentAccountBackDao  extends CrudDao<PbcPaymentAccountBack>{
	
	/**
	 * 
	 * @方法说明：删除关联全支付账号信息
	 * @时间：2016年12月26日 下午3:18:16
	 * @创建人：wangdong
	 */
    int deleteByPrimaryKey(Long paymentAccountId);

    /**
	 * 
	 * @方法说明：插入关联全支付账号信息
	 * @时间：2016年12月26日 下午3:18:16
	 * @创建人：wangdong
	 */
    int insert(PbcPaymentAccountBack record);

    /**
	 * 
	 * @方法说明：查询关联全支付账号信息
	 * @时间：2016年12月26日 下午3:18:16
	 * @创建人：wangdong
	 */
    PbcPaymentAccountBack selectByPrimaryKey(Long paymentAccountId);

    /**
	 * 
	 * @方法说明：查询关联全支付账号信息
	 * @时间：2016年12月26日 下午3:18:16
	 * @创建人：wangdong
	 */
    List<PbcPaymentAccountBack> selectAll();

    /**
	 * 
	 * @方法说明：更新关联全支付账号信息
	 * @时间：2016年12月26日 下午3:18:16
	 * @创建人：wangdong
	 */
    int updateByPrimaryKey(PbcPaymentAccountBack record);

    /**
	 * 
	 * @方法说明：获取关联全支付账号信息
	 * @时间：2016年12月26日 下午3:18:16
	 * @创建人：wangdong
	 */
	PbcPaymentAccountBack getEntityById(Long pbcId);

	/**
	 * 
	 * @方法说明：根据业务申请编码获取关联全支付账号信息
	 * @时间：2016年12月26日 下午3:18:16
	 * @创建人：wangdong
	 */
	PbcPaymentAccountBack getEntityByApplicationId(String applicationId);

	/**
	 * 
	 * @方法说明：插入关联全支付账号信息
	 * @时间：2016年12月26日 下午3:18:16
	 * @创建人：wangdong
	 */
	int saveMap(Map<String, Object> map);
}