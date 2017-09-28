package com.heepay.manage.modules.pbc.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcMeasureInfo;
/**
 * 
 *
 * 描    述：措施信息
 *
 * 创 建 者：   wangdong
 * 创建时间：2016年12月24日 上午8:56:32
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
public interface PbcMeasureInfoDao  extends CrudDao<PbcMeasureInfo>{
	
	/**
	 * 
	 * @方法说明：根据主键删除措施信息
	 * @时间：2016年12月26日 下午3:16:20
	 * @创建人：wangdong
	 */
    int deleteByPrimaryKey(Long pbcId);

    /**
	 * 
	 * @方法说明：插入措施信息
	 * @时间：2016年12月26日 下午3:16:20
	 * @创建人：wangdong
	 */
    int insert(PbcMeasureInfo record);

    /**
	 * 
	 * @方法说明：根据主键查询措施信息
	 * @时间：2016年12月26日 下午3:16:20
	 * @创建人：wangdong
	 */
    PbcMeasureInfo selectByPrimaryKey(Long pbcId);

    /**
	 * 
	 * @方法说明：查询措施信息
	 * @时间：2016年12月26日 下午3:16:20
	 * @创建人：wangdong
	 */
    List<PbcMeasureInfo> selectAll();

    /**
	 * 
	 * @方法说明：根据主键更新措施信息
	 * @时间：2016年12月26日 下午3:16:20
	 * @创建人：wangdong
	 */
    int updateByPrimaryKey(PbcMeasureInfo record);

    /**
	 * 
	 * @方法说明：根据id查询措施信息
	 * @时间：2016年12月26日 下午3:16:20
	 * @创建人：wangdong
	 */
	PbcMeasureInfo getEntityById(Long pbcId);

	/**
	 * 
	 * @方法说明：根据业务申请编码查询措施信息
	 * @时间：2016年12月26日 下午3:16:20
	 * @创建人：wangdong
	 */
	PbcMeasureInfo getEntityByApplicationId(String applicationId);

	/**
	 * 
	 * @方法说明：插入措施信息
	 * @时间：2016年12月26日 下午3:16:20
	 * @创建人：wangdong
	 */
	int saveMap(Map<String, Object> map);
}