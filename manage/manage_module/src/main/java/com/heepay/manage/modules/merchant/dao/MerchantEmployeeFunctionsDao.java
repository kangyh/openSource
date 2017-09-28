/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantEmployeeFunctions;

import java.util.List;

/**
 * merchantDAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface MerchantEmployeeFunctionsDao extends CrudDao<MerchantEmployeeFunctions> {

    /**
     * 根据员工的id删除权限信息
     * @param id    员工id
     */
    void deleteByEmployeeId(Long id);

    /**
     * 根据员工的id查询员工对应拥有的权限数字
     * @param id    员工id
     */
    List<String> queryPermissionByEmployeeId(Long id);
}