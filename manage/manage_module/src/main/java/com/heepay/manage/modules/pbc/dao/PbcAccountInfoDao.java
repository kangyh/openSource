package com.heepay.manage.modules.pbc.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcAccountInfo;
/**
 * 
 *
 * 描    述：账户信息
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年12月24日 上午8:55:32
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
public interface PbcAccountInfoDao  extends CrudDao<PbcAccountInfo>{
	
	/**
	 * 
	 * @方法说明：根据主键删除账户信息
	 * @时间：2016年12月26日 下午3:11:58
	 * @创建人：wangdong
	 */
    int deleteByPrimaryKey(Long pbcId);

    /**
	 * 
	 * @方法说明：插入账户信息
	 * @时间：2016年12月26日 下午3:11:58
	 * @创建人：wangdong
	 */
    int insert(PbcAccountInfo record);

    /**
	 * 
	 * @方法说明：根据主键查询账户信息
	 * @时间：2016年12月26日 下午3:11:58
	 * @创建人：wangdong
	 */
    PbcAccountInfo selectByPrimaryKey(Long pbcId);

    /**
	 * 
	 * @方法说明：查询账户信息
	 * @时间：2016年12月26日 下午3:11:58
	 * @创建人：wangdong
	 */
    List<PbcAccountInfo> selectAll();

    /**
	 * 
	 * @方法说明：根据主键更新账户信息
	 * @时间：2016年12月26日 下午3:11:58
	 * @创建人：wangdong
	 */
    int updateByPrimaryKey(PbcAccountInfo record);

    /**
	 * 
	 * @方法说明：根据id查询账户信息
	 * @时间：2016年12月26日 下午3:11:58
	 * @创建人：wangdong
	 */
	PbcAccountInfo getEntityById(Long pbcId);

	/**
	 * 
	 * @方法说明：根据业务申请编码查询账户信息
	 * @时间：2016年12月26日 下午3:11:58
	 * @创建人：wangdong
	 */
	PbcAccountInfo getEntityByApplicationId(String applicationId);

	/**
	 * 
	 * @方法说明：插入账户信息
	 * @时间：2016年12月26日 下午3:11:58
	 * @创建人：wangdong
	 */
	int saveMap(Map<String, Object> map);
}