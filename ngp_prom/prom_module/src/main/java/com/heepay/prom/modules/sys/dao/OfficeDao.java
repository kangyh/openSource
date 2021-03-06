/**
 *  
 */
package com.heepay.prom.modules.sys.dao;

import com.heepay.prom.common.persistence.TreeDao;
import com.heepay.prom.common.persistence.annotation.MyBatisDao;
import com.heepay.prom.modules.sys.entity.Office;

/**
 * 机构DAO接口
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
      
    /**     
    * @discription 根据机构名称获取机构
    * @author ly     
    * @created 2016年12月1日 下午5:42:33     
    * @param office
    * @return     
    */
    Office getOfficeByName(Office office);
}
