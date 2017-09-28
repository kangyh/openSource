/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.cbms.entity.CbmsOrderSum;

import java.util.List;

/**
 *
 * 描    述：导入文件查询DAO接口
 *
 * 创 建 者： @author 牛俊鹏
 * 创建时间：
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
public interface CbmsOrderSumDao extends CrudDao<CbmsOrderSum> {
    /**
     *分页查询
     * @param cbmsOrderSum
     * @return
     */
    List<CbmsOrderSum> findPageimport(CbmsOrderSum cbmsOrderSum);
    /**
     *根据ImportBatchNumber查询ordersum 表中数据
     * @param ImportBatchNumber
     * @return
     */
    CbmsOrderSum ordersumselect( String ImportBatchNumber);
}