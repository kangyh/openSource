package com.heepay.manage.modules.pbc.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcTransInfo;
/**
 * 
 *
 * 描    述：交易信息
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年12月24日 上午8:58:03
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
public interface PbcTransInfoDao  extends CrudDao<PbcTransInfo>{
	
	/**
	 * 
	 * @方法说明：根据id删除交易明细信息
	 * @时间：2016年12月26日 下午3:22:14
	 * @创建人：wangdong
	 */
    int deleteByPrimaryKey(Long pbcId);

    /**
	 * 
	 * @方法说明：插入交易明细信息
	 * @时间：2016年12月26日 下午3:22:14
	 * @创建人：wangdong
	 */
    int insert(PbcTransInfo record);

    /**
	 * 
	 * @方法说明：查询交易明细信息
	 * @时间：2016年12月26日 下午3:22:14
	 * @创建人：wangdong
	 */
    PbcTransInfo selectByPrimaryKey(Long pbcId);

    /**
	 * 
	 * @方法说明：查询交易明细信息
	 * @时间：2016年12月26日 下午3:22:14
	 * @创建人：wangdong
	 */
    List<PbcTransInfo> selectAll();

    /**
	 * 
	 * @方法说明：更新交易明细信息
	 * @时间：2016年12月26日 下午3:22:14
	 * @创建人：wangdong
	 */
    int updateByPrimaryKey(PbcTransInfo record);

    /**
	 * 
	 * @方法说明：根据业务申请编码查询交易明细信息
	 * @时间：2016年12月26日 下午3:22:14
	 * @创建人：wangdong
	 */
	PbcTransInfo getEntityByApplicationId(String applicationId);

	/**
	 * 
	 * @方法说明：根据id查询交易明细信息
	 * @时间：2016年12月26日 下午3:22:14
	 * @创建人：wangdong
	 */
	PbcTransInfo getEntityById(Long pbcId);

	/**
	 * 
	 * @方法说明：插入交易明细信息
	 * @时间：2016年12月26日 下午3:22:14
	 * @创建人：wangdong
	 */
	int saveMap(Map<String, Object> map);
}