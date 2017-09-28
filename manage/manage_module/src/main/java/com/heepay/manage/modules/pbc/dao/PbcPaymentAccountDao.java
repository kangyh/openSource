package com.heepay.manage.modules.pbc.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcPaymentAccount;

/***
 * 
* 
* 描    述：关联全支付账号
*
* 创 建 者：wangl
* 创建时间：  Dec 16, 20165:20:13 PM
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
public interface PbcPaymentAccountDao  extends CrudDao<PbcPaymentAccount>{


	/**
	 * 插入
	 */
    int save(PbcPaymentAccount record);

    /**
	 * 插入默认使用这个
	 */
    int saveEntity(PbcPaymentAccount record);

    /**
     * 更新
     */
    int update(PbcPaymentAccount record);

    /**
	 * 更新默认使用这个
	 */
    int updateEntity(PbcPaymentAccount record);

    /**
     * 
     * @方法说明：根据id查询对象
     * @时间：Dec 16, 2016
     * @创建人：wangl
     */
	PbcPaymentAccount getEntityById(int differId);

	/**
	 * 
	 * @方法说明：根据反馈id获取信息
	 * @时间：2016年12月17日 下午2:24:20
	 * @创建人：wangdong
	 */
	PbcPaymentAccount getFeeBackId(Long feedBackId);

	/**
	 * 
	 * @方法说明：插入关联全支付账号信息
	 * @时间：2016年12月26日 下午3:19:58
	 * @创建人：wangdong
	 */
	@SuppressWarnings("rawtypes")
	int insertValue(List<Map> readExcel2003);

	
	/**
	 * 
	 * @方法说明：根据featureCode查询出所有数据发送给网关
	 * @时间：Dec 22, 2016
	 * @创建人：wangl
	 */
	List<PbcPaymentAccount> getListByfeatureCode(String applicationCode);

	/**
	 * 
	 * @方法说明：账户入库
	 * @时间：Dec 22, 2016
	 * @创建人：wangl
	 */
	@SuppressWarnings("rawtypes")
	int saveEntityToMap(Map map);

	/**
	 * @方法说明：更加id查询对象
	 * @时间：Jan 12, 20174:31:01 PM
	 * @创建人：wangl
	 */
	PbcPaymentAccount getEntyById(long feedBackId);

	/**
	 * @方法说明：
	 * @时间：Jan 12, 20174:44:49 PM
	 * @创建人：wangl
	 */
	int deleteData(String applicationCode);
}