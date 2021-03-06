/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.prom.modules.prom.dao;

import com.heepay.prom.common.persistence.CrudDao;
import com.heepay.prom.common.persistence.annotation.MyBatisDao;
import com.heepay.prom.modules.prom.entity.PromTeacherManage;

/**
 *
 * 描    述：上下级关系管理DAO接口
 *
 * 创 建 者： @author wangdong
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
public interface PromTeacherManageDao extends CrudDao<PromTeacherManage> {

    Integer findExit(PromTeacherManage promTeacherManage);

    PromTeacherManage findEntityByCode(PromTeacherManage promTeacherManage);

    Integer findSuperExit(PromTeacherManage superProm);

    Integer findCountByCode(PromTeacherManage promTeacherManage);
}