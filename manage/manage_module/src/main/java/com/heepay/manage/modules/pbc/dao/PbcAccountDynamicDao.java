package com.heepay.manage.modules.pbc.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcAccountDynamic;

/***
 * 
* 
* 描    述：账户动态
*
* 创 建 者：wangl
* 创建时间：  Dec 16, 20165:19:26 PM
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
public interface PbcAccountDynamicDao  extends CrudDao<PbcAccountDynamic>{


	/**
	 * 插入
	 */
    int save(PbcAccountDynamic record);

    /**
	 * 插入默认使用这个
	 */
    int saveEntity(PbcAccountDynamic record);

    /**
     * 更新
     */
    int update(PbcAccountDynamic record);

    /**
 	 * 更新默认使用这个
 	 */
    int updateEntity(PbcAccountDynamic record);

    /**
     * 
     * @方法说明：根据id查询对象
     * @时间：Dec 16, 2016
     * @创建人：wangl
     */
	PbcAccountDynamic getEntityById(int differId);

	/**
	 * 
	 * @方法说明：根据反馈id获取信息
	 * @时间：2016年12月17日 下午2:18:37
	 * @创建人：wangdong
	 */
	PbcAccountDynamic getFeeBackId(Long feedBackId);

	/**
	 * 
	 * @方法说明：去重获取账户信息
	 * @时间：2016年12月19日 下午7:48:26
	 * @创建人：wangdong
	 */
	List<PbcAccountDynamic> findDistinctList(PbcAccountDynamic pbcAccountDynamic);

	/**
	 * 
	 * @方法说明：获取该商户的账户交易信息
	 * @时间：2016年12月19日 下午8:05:45
	 * @创建人：wangdong
	 */
	List<PbcAccountDynamic> findTransList(PbcAccountDynamic pbcAccountDynamic);

	/**
	 * 
	 * @方法说明：保存账户动态信息
	 * @时间：2016年12月26日 下午3:11:31
	 * @创建人：wangdong
	 */
	@SuppressWarnings("rawtypes")
	void saveMap(Map map);

	/**
	 * 
	 * @方法说明：根据用户查询出关联的银行卡信息
	 * @时间：Dec 22, 2016
	 * @创建人：wangl
	 */
	List<PbcAccountDynamic> getListByMerchantNumber(String merchantNumber);

	/**
	 * 
	 * @方法说明：查询出批量的子对象
	 * @时间：Dec 26, 2016
	 * @创建人：wangl
	 */
	List<PbcAccountDynamic> getListByfeatureCode(String applicationCode);

	/**
	 * @方法说明：账户交易明细表pbc_account_dynamic
	 * @时间：Jan 12, 20175:19:27 PM
	 * @创建人：wangl
	 */
	int deleteData(String accountNumber);

	/**
	 * @方法说明：删除信息
	 * @时间：Jan 12, 20175:43:26 PM
	 * @创建人：wangl
	 */
	int deleteDataTwo(String applicationId);
}