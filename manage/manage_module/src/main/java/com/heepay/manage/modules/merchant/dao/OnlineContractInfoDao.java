/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.OnlineContractInfo;

import java.util.List;

/**
 *
 * 描    述：线上签约DAO接口
 *
 * 创 建 者： @author ly
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
public interface OnlineContractInfoDao extends CrudDao<OnlineContractInfo> {

    public String queryProductIsOpenByProductCode(String userId, String productCode);

    public List<OnlineContractInfo> queryUnfinishedProductInfoByUserId(String userId);

    public List<OnlineContractInfo> queryAllProductInfoByUserId(String userId);

    OnlineContractInfo queryProductByUserIdAndProductCode(String userId, String productCode);

    OnlineContractInfo selectOnlineContractInfo(String userId);

    int status(OnlineContractInfo onlineContractInfo);

    void legalAudit(OnlineContractInfo onlineContractInfo);

    void rcAudit(OnlineContractInfo onlineContractInfo);

    String queryMaxBatchNoByUserId(String userId);

    List<OnlineContractInfo> queryProductsBybatchNoAndUserId(String batchNo, String userId);

    Integer queryProductsBatchByUserId(String userId);

    void updateSignFailProduct(OnlineContractInfo onlineContractInfo);

    List<OnlineContractInfo> findProductList(OnlineContractInfo onlineContractInfo);
}