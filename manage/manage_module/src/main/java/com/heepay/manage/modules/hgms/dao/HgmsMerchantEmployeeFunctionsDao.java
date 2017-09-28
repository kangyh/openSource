/**
 *
 */
package com.heepay.manage.modules.hgms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.hgms.entity.HgmsMerchantEmployeeFunctions;

import java.util.List;

/**
 * 描    述：商户员工功能权限DAO接口
 * <p>
 * 创 建 者：guozx@9186.com
 * 创建时间：2017-07-31 15:13:36
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
public interface HgmsMerchantEmployeeFunctionsDao extends CrudDao<HgmsMerchantEmployeeFunctions> {

    /**
     * 根据员工的id删除权限信息
     *
     * @param id 员工id
     */
    void deleteByEmployeeId(Long id);

    /**
     * 根据员工的id查询员工对应拥有的权限数字
     *
     * @param id 员工id
     */
    List<String> queryPermissionByEmployeeId(Long id);
}