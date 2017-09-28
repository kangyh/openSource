package com.heepay.manage.modules.payment.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.payment.entity.BindCardAuth;

/**
 *
 * 描    述：钱包绑卡dao
 *
 * 创 建 者： @author liudh
 * 创建时间： 2017/6/8 14:40
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
public interface BindCardDao extends CrudDao<BindCardAuth> {

}
