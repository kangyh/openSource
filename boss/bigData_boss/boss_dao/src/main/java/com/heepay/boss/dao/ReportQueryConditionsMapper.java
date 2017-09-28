package com.heepay.boss.dao;

import com.heepay.boss.entity.ReportQueryConditions;
import com.heepay.boss.entity.ReportQueryConditionsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 描 述：BOSS报表条件设置
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/6/1 13:59
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
public interface ReportQueryConditionsMapper{

    ReportQueryConditions selectByPrimaryKey(Long reportId);

    List<ReportQueryConditions> selectAll();

    int updateByPrimaryKey(ReportQueryConditions record);

    int countByExample(ReportQueryConditionsExample example);

    int deleteByExample(ReportQueryConditionsExample example);

    int deleteByPrimaryKey(Long reportId);

    int insert(ReportQueryConditions record);

    int insertSelective(ReportQueryConditions record);

    List<ReportQueryConditions> selectByExample(ReportQueryConditionsExample example);

    int updateByExampleSelective(@Param("record") ReportQueryConditions record, @Param("example") ReportQueryConditionsExample example);

    int updateByExample(@Param("record") ReportQueryConditions record, @Param("example") ReportQueryConditionsExample example);

    int updateByPrimaryKeySelective(ReportQueryConditions record);

}
