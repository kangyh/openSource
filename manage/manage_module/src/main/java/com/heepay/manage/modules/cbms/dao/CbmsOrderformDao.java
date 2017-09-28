/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.cbms.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.cbms.entity.CannotImOrderQuery;
import com.heepay.manage.modules.cbms.entity.CbmsOrderform;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 描    述：同申报批次号详情查看和新增DAO接口
 *
 * 创 建 者： @author 郭正新
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
public interface CbmsOrderformDao extends CrudDao<CbmsOrderform> {

    /**
     *根据商户sentCustomsNumber获取CbmsOrderform列表（查询同一报送海关批次号的订单详情）
     * @param sentCustomsNumber
     * @return
     */
    List<CbmsOrderform> select(String sentCustomsNumber);

    /**
     *根据商户importBatchNumber获取CbmsOrderform列表（查询同一导入批次号的订单详情）
     * @param importBatchNumber
     * @return
     */
    List<CbmsOrderform> selectimport(String importBatchNumber);
    List<CbmsOrderform> selectIdList(List<CbmsOrderform> list);
    /**
     *查询重复订单
     * @param cannotImOrderQuery
     * @return
     */
    List<String> queryRepeatOrder(CannotImOrderQuery cannotImOrderQuery);

    /**
     *根据商户merchantNo获取CbmsOrderform
     * @param merchantNo
     * @return
     */
    CbmsOrderform findOrderform(String merchantNo);
    void  updateList(List<CbmsOrderform> list);
    int   notAuditedNum(String importBatchNumber);
    //审核拒绝操作批量更新订单记录
    void   updateAuditFailList(List<CbmsOrderform> list);
    //查询是否有未申报的订单(1,2)
    List<CbmsOrderform> vailedOrderFormApi(String importBatchNumber);
    /**
     * API根据汇总记录查询相关需要异步通知的订单状态
     * @param importBatchNumber
     */
    List<CbmsOrderform> selectOrderFormApi(String importBatchNumber);
}