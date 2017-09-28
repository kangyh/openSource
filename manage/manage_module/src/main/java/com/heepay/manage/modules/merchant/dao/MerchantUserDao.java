/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.MerchantUser;

import java.util.List;

/**
 * userDAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface MerchantUserDao extends CrudDao<MerchantUser> {
    /**
     * 查询该邮箱是否存在
     * @param email     邮箱
     * @return          对象或者null
     */
    MerchantUser queryEmailExist(String email);

    /**
     * 保存用户信息
     * @param merchantUser  用户对象
     * @return  用户的id
     */
    int insert(MerchantUser merchantUser);

    void status(MerchantUser merchantUser);
    
    void unbundling(MerchantUser merchantUser);

    void resetLoginPassword(MerchantUser merchantUser);

    void resetPayPassword(MerchantUser merchantUser);

    List<MerchantUser> queryEmployeeByEmail(String email);

    List<MerchantUser> queryEmployeeByMobile(String mobile);

    void updateEmployee(MerchantUser merchantUser);

    List<MerchantUser> selectCompanyForEmployee();

    /**
     * 通过当前用户id查询类型
     */
    String judgmentIdentity(Integer currentUserId);
    
    /**
     * 查出手机号
     */
    MerchantUser queryPhoneNo(String phone);

    /**
     * @discription
     * @author 郭正新
     * @created 2017年1月10日 下午20:40:58
     * @param merchantUser
     */
    void updateMerchantPASS(MerchantUser merchantUser);
}