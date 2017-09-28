package com.heepay.boss.dao.impl;

import com.heepay.boss.dao.ReportQueryConditionsMapper;
import com.heepay.boss.entity.ReportQueryConditions;
import com.heepay.boss.entity.ReportQueryConditionsExample;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 描 述：BOSS报表条件设置
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/6/1 14:01
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
@Repository
public class ReportQueryConditionsDaoImpl extends BaseDaoImpl<ReportQueryConditions,ReportQueryConditionsExample> implements ReportQueryConditionsMapper {

    // 默认构造方法设置命名空间
    public ReportQueryConditionsDaoImpl() {
        super.setNs("com.heepay.boss.dao.ReportQueryConditionsMapper");
    }

    @Override
    public List<ReportQueryConditions> selectAll() {
        return this.getSqlSession().selectList(this.getNs()+".selectAll");
    }
}
