package com.heepay.manage.modules.pbc.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.pbc.entity.PbcAccountDetailSubject;

/***
 * 
* 
* 描    述：账户主体详情
*
* 创 建 者：wangl
* 创建时间：  Dec 16, 20165:19:12 PM
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
public interface PbcAccountDetailSubjectDao  extends CrudDao<PbcAccountDetailSubject>{

	/**
	 * 插入
	 */
    int save(PbcAccountDetailSubject record);

    /**
     * 
     * @方法说明： 插入默认使用这个
     * @时间：Dec 16, 2016
     * @创建人：wangl
     */
    int saveEntity(PbcAccountDetailSubject record);

    /**
     * 
     * @方法说明：更新
     * @时间：Dec 16, 2016
     * @创建人：wangl
     */
    int update(PbcAccountDetailSubject record);

    /**
     * 
     * @方法说明：更新默认使用这个
     * @时间：Dec 16, 2016
     * @创建人：wangl
     */
    int updateEntity(PbcAccountDetailSubject record);

    /**
     * 
     * @方法说明：根据id查询整个对象
     * @时间：Dec 16, 2016
     * @创建人：wangl
     */
	PbcAccountDetailSubject getEntityById(int differId);

	/**
	 * 
	 * @方法说明：根据反馈id获取信息
	 * @时间：2016年12月17日 下午2:16:17
	 * @创建人：wangdong
	 */
	PbcAccountDetailSubject getFeeBackId(Long feedBackId);

	@SuppressWarnings("rawtypes")
	int insertValue(List<Map> readExcel2003);

	/**
	 * 
	 * @方法说明：数据入库
	 * @时间：Dec 20, 2016
	 * @创建人：wangl
	 */
	@SuppressWarnings("rawtypes")
	void saveMap(Map map);

	/**
	 * 
	 * @方法说明：根据用户查询出关联的银行卡信息
	 * @时间：Dec 22, 2016
	 * @创建人：wangl
	 */
	List<PbcAccountDetailSubject> getListByMerchantNumber(String merchantNumber);

	/**
	 * @方法说明：删除子数据
	 * @时间：Jan 12, 20174:42:00 PM
	 * @创建人：wangl
	 */
	int deleteData(String merchantNumber);

	
}