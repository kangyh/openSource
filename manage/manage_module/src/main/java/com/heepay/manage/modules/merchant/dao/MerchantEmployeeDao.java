/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantEmployee;

import java.util.List;
import java.util.Map;

/**
 * merchantDAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface MerchantEmployeeDao extends CrudDao<MerchantEmployee> {
    /**
     * 通过邮箱查找员工
     * @param email  邮箱
     * @return       员工信息
     */
    MerchantEmployee queryEmployeeByEmail(String email);

    /**
     * 根据员工id修改员工信息
     * @param merchantEmployee        员工信息
     */
    void updateByEmployeeId(MerchantEmployee merchantEmployee);

    /**
     * 通过电话号查找员工
     * @param mobile    电话号
     * @return          员工信息
     */
    MerchantEmployee queryEmployeeByMobile(String mobile);

    /**
     * 根据员工id查找bossId
     * @param employeeId        员工id
     * @return                  bossId
     */
    MerchantEmployee queryBossIdByEmployeeId(Integer employeeId);

    /**
     * 通过商户的id查询商户有多少员工
     * @param merchantId     商户id
     * @return              员工数
     */
    Integer selectCount(Integer merchantId);

    /**
     * 分页查询员工list
     * @param params        参数
     * @return              员工list
     */
    List<MerchantEmployee> pagingQuery(Map<String, Object> params);

    /**
     * 更新员工信息
     * @param merchantEmployee      merchantEmployee
     */
    void updateEmployeeMsg(MerchantEmployee merchantEmployee);

      
    /**     
    * @discription 根据商户id查询员工
    * @author ly     
    * @created 2016年11月30日 下午8:18:00     
    * @param id
    * @return     
    */
    List<MerchantEmployee> getEmployeesByMerchantId(Integer merchantId);
}