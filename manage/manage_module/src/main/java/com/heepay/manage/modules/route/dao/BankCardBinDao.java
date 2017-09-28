/**
 *  
 */
package com.heepay.manage.modules.route.dao;

import java.util.List;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.route.entity.BankCardBin;

/**
 * 银行卡bin管理DAO接口
 * @author lgk
 * @version V1.0
 */
@MyBatisDao
public interface BankCardBinDao extends CrudDao<BankCardBin> {
    public List<BankCardBin> selectList();

    public void status(BankCardBin bankCardBin);

    public BankCardBin selectBankCardNote(BankCardBin bankCardBin);

    public BankCardBin selectBnakCardBin(BankCardBin bankCardBin);

}