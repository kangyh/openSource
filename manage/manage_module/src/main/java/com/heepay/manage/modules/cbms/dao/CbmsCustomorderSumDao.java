/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.cbms.entity.CbmsCustomorderSum;
import com.heepay.manage.modules.cbms.entity.CusdeclarationResult;
import com.heepay.manage.modules.cbms.entity.CustdeclarationQuery;

import java.util.List;

/**
 *
 * 描    述：通关申报订单查询DAO接口
 *
 * 创 建 者： @author guozx
 * 创建时间： 2016年12月30日 10:30:25
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 *
 */
@MyBatisDao
public interface CbmsCustomorderSumDao extends CrudDao<CbmsCustomorderSum> {
    CusdeclarationResult getCusdeclarationResult(CustdeclarationQuery query);

    /**
     *根据merchantNo获取CbmsCustomorderSum
     * @param merchantNo
     * @return
     */
    List<CbmsCustomorderSum> selectList(String merchantNo);
}