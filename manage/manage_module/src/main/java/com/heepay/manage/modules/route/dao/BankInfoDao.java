/**
 *
 */
package com.heepay.manage.modules.route.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.route.entity.BankInfo;
import com.heepay.manage.modules.route.entity.PayChannel;

/**
 * 银行信息管理DAO接口
 *
 * @author ljh
 * @version V1.0
 */
@MyBatisDao
public interface BankInfoDao extends CrudDao<BankInfo> {

    /**
     * @return银行list
     */
    public List<BankInfo> selectList();

    /**
     * 根据银行代码查找对象
     */

    public List<BankInfo> selectByBankN(BankInfo bankInfo);

    public void status(BankInfo bankInfo);

    /**
     * @方法说明：根据 银行代码查询银行名称
     * @时间：Nov 9, 2016
     * @创建人：wangl
     */
    public BankInfo getBankNameByBankNo(String bankNo);

    /**
     * 根据 银行名称查询银行代码
     *  ly 2017年3月10日16:35:36
     */
    BankInfo getBankByBankName(String bankName);

    /**
     * @方法说明：查询多个名称
     * @时间： 2017-09-12 14:22
     * @创建人：wangl
     */
    List<BankInfo> getBankNameForList(List<String> list);
}