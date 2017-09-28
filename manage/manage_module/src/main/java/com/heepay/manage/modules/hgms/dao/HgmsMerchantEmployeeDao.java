/**
 *
 */
package com.heepay.manage.modules.hgms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantEmployee;

import java.util.List;
import java.util.Map;

/**
 * 描    述：商户权限员工DAO接口
 * <p>
 * 创 建 者： guozx@9186.com
 * 创建时间： 2017-07-31 15:12:16
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@MyBatisDao
public interface HgmsMerchantEmployeeDao extends CrudDao<HgmsMerchantEmployee> {
    /**
     * 通过邮箱查找员工
     *
     * @param email 邮箱
     * @return 员工信息
     */
    HgmsMerchantEmployee queryEmployeeByEmail(String email);

    /**
     * 根据员工id修改员工信息
     *
     * @param hgmsMerchantEmployee 员工信息
     */
    void updateByEmployeeId(HgmsMerchantEmployee hgmsMerchantEmployee);

    /**
     * 通过电话号查找员工
     *
     * @param mobile 电话号
     * @return 员工信息
     */
    HgmsMerchantEmployee queryEmployeeByMobile(String mobile);

    /**
     * 根据员工id查找bossId
     *
     * @param employeeId 员工id
     * @return bossId
     */
    HgmsMerchantEmployee queryBossIdByEmployeeId(Integer employeeId);

    /**
     * 通过商户的id查询商户有多少员工
     *
     * @param merchantId 商户id
     * @return 员工数
     */
    Integer selectCount(Integer merchantId);

    /**
     * 分页查询员工list
     *
     * @param params 参数
     * @return 员工list
     */
    List<HgmsMerchantEmployee> pagingQuery(Map<String, Object> params);

    /**
     * 更新员工信息
     *
     * @param hgmsMerchantEmployee HgmsMerchantEmployee
     */
    void updateEmployeeMsg(HgmsMerchantEmployee hgmsMerchantEmployee);


    /**
     * @param merchantId
     * @return
     * @discription 根据商户id查询员工
     * @author guozx@9186.com
     * @created 2016年11月30日 下午8:18:00
     */
    List<HgmsMerchantEmployee> getEmployeesByMerchantId(Integer merchantId);
}