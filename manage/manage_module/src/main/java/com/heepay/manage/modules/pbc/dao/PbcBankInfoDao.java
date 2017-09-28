package com.heepay.manage.modules.pbc.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcBankInfo;
/**
 * 
 *
 * 描    述：银行信息
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年12月24日 上午8:56:07
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
public interface PbcBankInfoDao  extends CrudDao<PbcBankInfo>{
	
	/**
	 * 
	 * @方法说明：根据主键删除银行卡信息
	 * @时间：2016年12月26日 下午3:14:03
	 * @创建人：wangdong
	 */
    int deleteByPrimaryKey(Long pbcId);

    /**
	 * 
	 * @方法说明：插入银行卡信息
	 * @时间：2016年12月26日 下午3:14:03
	 * @创建人：wangdong
	 */
    int insert(PbcBankInfo record);

    /**
	 * 
	 * @方法说明：根据id查询银行卡信息
	 * @时间：2016年12月26日 下午3:14:03
	 * @创建人：wangdong
	 */
    PbcBankInfo selectByPrimaryKey(Long pbcId);

    /**
	 * 
	 * @方法说明：查询银行卡信息
	 * @时间：2016年12月26日 下午3:14:03
	 * @创建人：wangdong
	 */
    List<PbcBankInfo> selectAll();

    /**
	 * 
	 * @方法说明：更新银行卡信息
	 * @时间：2016年12月26日 下午3:14:03
	 * @创建人：wangdong
	 */
    int updateByPrimaryKey(PbcBankInfo record);

    /**
	 * 
	 * @方法说明：根据id查询银行卡信息
	 * @时间：2016年12月26日 下午3:14:03
	 * @创建人：wangdong
	 */
	PbcBankInfo getEntityById(Long pbcId);

	/**
	 * 
	 * @方法说明：根据业务申请编码获取实体
	 * @时间：2016年12月26日 下午3:14:03
	 * @创建人：wangdong
	 */
	PbcBankInfo getEntityByApplicationId(String applicationId);

	/**
	 * 
	 * @方法说明：插入银行卡信息
	 * @时间：2016年12月26日 下午3:14:03
	 * @创建人：wangdong
	 */
	int saveMap(Map<String, Object> map);
}