package com.heepay.manage.modules.pbc.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcAccountTransDetail;

/***
 * 
* 
* 描    述：账户交易明细
*
* 创 建 者：wangl
* 创建时间：  Dec 16, 20165:19:50 PM
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
public interface PbcAccountTransDetailDao  extends CrudDao<PbcAccountTransDetail>{


	/**
	 * 插入
	 */
    int save(PbcAccountTransDetail record);

    /**
	 * 插入默认使用这个
	 */
    int saveEntity(PbcAccountTransDetail record);

    /**
     * 更新
     */
    int update(PbcAccountTransDetail record);

    /**
   	 * 更新默认使用这个
   	 */
    int updateEntity(PbcAccountTransDetail record);

    /**
     * 
     * @方法说明：根据id查询对象
     * @时间：Dec 16, 2016
     * @创建人：wangl
     */
	PbcAccountTransDetail getEntityById(int differId);

	/**
	 * 
	 * @方法说明：根据反馈id查询账户交易明细信息
	 * @时间：2016年12月17日 下午1:24:09
	 * @创建人：wangdong
	 */
	PbcAccountTransDetail getFeeBackId(Long feeBackId);

	/**
	 * 
	 * @方法说明：去重获取账户信息
	 * @时间：2016年12月19日 下午7:41:31
	 * @创建人：wangdong
	 */
	List<PbcAccountTransDetail> findDistinctList(PbcAccountTransDetail pbcAccountTransDetail);

	/**
	 * 
	 * @方法说明：获取该商户的账户交易信息
	 * @时间：2016年12月19日 下午7:56:02
	 * @创建人：wangdong
	 */
	List<PbcAccountTransDetail> findTransList(PbcAccountTransDetail pbcAccountTransDetail);

	/**
	 * 
	 * @方法说明：上传文件入库
	 * @时间：Dec 21, 2016
	 * @创建人：wangl
	 */
	@SuppressWarnings("rawtypes")
	int insertValue(List<Map> readExcel2003);

	/**
	 * 
	 * @方法说明：数据上传入库
	 * @时间：Dec 23, 2016
	 * @创建人：wangl
	 */
	@SuppressWarnings("rawtypes")
	int saveEntityToMap(Map map);

	/**
	 * 
	 * @方法说明：根据用户查询出关联的银行卡信息
	 * @时间：Dec 22, 2016
	 * @创建人：wangl
	 */
	List<PbcAccountTransDetail> getListByfeatureCode(String applicationCode);
}