/**
 *  
 */
package com.heepay.prom.modules.sys.dao;

import java.util.List;

import com.heepay.prom.common.persistence.CrudDao;
import com.heepay.prom.common.persistence.annotation.MyBatisDao;
import com.heepay.prom.modules.sys.entity.Dict;
import org.apache.ibatis.annotations.Param;

/**
 * 字典DAO接口
 *  
 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);

	public Dict getValueByLabel(@Param("type") String type, @Param("label")String label);

	Dict getLabelByValue(@Param("type") String type, @Param("value")String value);
	
}
