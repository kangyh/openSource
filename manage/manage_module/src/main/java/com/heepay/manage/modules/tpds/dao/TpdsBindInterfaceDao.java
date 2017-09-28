package com.heepay.manage.modules.tpds.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.tpds.entity.TpdsBankCer;
import com.heepay.manage.modules.tpds.entity.TpdsBindInterface;
import java.util.List;
import org.apache.ibatis.annotations.Param;
/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年2月17日下午5:27:10
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
public interface TpdsBindInterfaceDao extends CrudDao<TpdsBindInterface>{

	int updateEntity(TpdsBindInterface record);

	int saveEntity(TpdsBindInterface record);

	TpdsBindInterface getEntityById(Integer id);

	TpdsBindInterface selectByMerchantNo(String no);
   
}