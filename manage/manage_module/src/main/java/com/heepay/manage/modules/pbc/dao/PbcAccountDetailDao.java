package com.heepay.manage.modules.pbc.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcAccountDetail;
/**
 * 
 *
 * 描    述：账户主体详情
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年12月24日 上午8:54:46
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
public interface PbcAccountDetailDao  extends CrudDao<PbcAccountDetail>{
	
	/**
	 * 
	 * @方法说明：根据主键删除信息
	 * @时间：2016年12月26日 下午3:08:51
	 * @创建人：wangdong
	 */
    int deleteByPrimaryKey(Long pbcId);

    /**
	 * 
	 * @方法说明：保存账户明细信息
	 * @时间：2016年12月26日 下午3:08:51
	 * @创建人：wangdong
	 */
    int insert(PbcAccountDetail record);

    /**
	 * 
	 * @方法说明：根据主键查询账户信息
	 * @时间：2016年12月26日 下午3:08:51
	 * @创建人：wangdong
	 */
    PbcAccountDetail selectByPrimaryKey(Long pbcId);

    /**
	 * 
	 * @方法说明：查询账户信息
	 * @时间：2016年12月26日 下午3:08:51
	 * @创建人：wangdong
	 */
    List<PbcAccountDetail> selectAll();

    /**
	 * 
	 * @方法说明：根据主键更新账户信息
	 * @时间：2016年12月26日 下午3:08:51
	 * @创建人：wangdong
	 */
    int updateByPrimaryKey(PbcAccountDetail record);

    /**
	 * 
	 * @方法说明：根据id查询实体信息
	 * @时间：2016年12月26日 下午3:08:51
	 * @创建人：wangdong
	 */
	PbcAccountDetail getEntityById(Long pbcId);

	/**
	 * 
	 * @方法说明：根据业务申请编码获取实体信息
	 * @时间：2016年12月26日 下午3:08:51
	 * @创建人：wangdong
	 */
	PbcAccountDetail getEntityByApplicationId(String applicationId);

	/**
	 * 
	 * @方法说明：保存账户实体信息
	 * @时间：2016年12月26日 下午3:08:51
	 * @创建人：wangdong
	 */
	int saveMap(Map<String, Object> map);
}