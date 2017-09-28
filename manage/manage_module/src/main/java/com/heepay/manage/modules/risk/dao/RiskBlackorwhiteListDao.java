package com.heepay.manage.modules.risk.dao;


import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.risk.entity.RiskBlackorwhiteList;

import java.util.List;
import java.util.Map;
@MyBatisDao
public interface RiskBlackorwhiteListDao extends CrudDao<RiskBlackorwhiteList>{
    /**
	 * @方法说明：导出Excel,获取全部数据
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    public RiskBlackorwhiteList getRiskBlackorwhiteListById(Integer blackId) ;
    /**
	 * @方法说明：导出Excel,获取全部数据
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    List<Map<String, Object>> findListExcel(RiskBlackorwhiteList record);
    /**
	 * @方法说明：导出Excel,获取选中数据
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    List<Map<String, Object>> findCheckedListExcel(RiskBlackorwhiteList record);
    /**
	 * @方法说明：插入黑白名单信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    int insertRiskBlackorwhiteList(RiskBlackorwhiteList record);
    
    /**
	 * @方法说明：删除黑白名单信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    int deleteRiskBlackorwhiteList(Integer blackId);
    /**
	 * @方法说明：批量删除黑白名单信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    int deleteManyRiskBlackorwhiteList(RiskBlackorwhiteList record);
    
    /**
	 * @方法说明：更新黑白名单信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    int updateRiskBlackorwhiteList(RiskBlackorwhiteList record);
    
    public int deleteRiskBlackorwhiteCascadeItem(Integer blackId);
    
    public int checkIfRepeatCateAndProd(RiskBlackorwhiteList record);
    
    public int checkIfRepeatName(String name);
    
}