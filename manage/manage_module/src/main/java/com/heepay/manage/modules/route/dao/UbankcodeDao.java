/**
 *  
 */
package com.heepay.manage.modules.route.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.route.entity.Ubankcode;

/**
 * 支行代码DAO接口
 * @author 牛文
 * @version V1.0
 */
@MyBatisDao
public interface UbankcodeDao extends CrudDao<Ubankcode> {
	
	public List<Ubankcode> findList(@Param(value="area_code") String id,@Param(value="bank_no")String bankNo);
	
}