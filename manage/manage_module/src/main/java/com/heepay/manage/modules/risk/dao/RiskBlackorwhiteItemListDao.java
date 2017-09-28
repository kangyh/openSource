package com.heepay.manage.modules.risk.dao;

import java.util.List;
import java.util.Map;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.risk.entity.RiskBlackorwhiteItemList;
import com.heepay.manage.modules.risk.entity.RiskBlackorwhiteList;
@MyBatisDao
public interface RiskBlackorwhiteItemListDao  extends CrudDao<RiskBlackorwhiteItemList>{
    /**
	 * @方法说明：导出Excel,获取全部数据
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    public RiskBlackorwhiteItemList getRiskBlackorwhiteItemById(Integer itemId) ;
    /**
	 * @方法说明：导出Excel,获取全部数据
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    public List<Map<String, Object>> findListExcel(RiskBlackorwhiteItemList record);
    /**
	 * @方法说明：查询列表
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    public List<RiskBlackorwhiteItemList> findBwItemPageList(RiskBlackorwhiteItemList entity);
    /**
	 * @方法说明：导出Excel,获取选中数据
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    public  List<Map<String, Object>> findCheckedListExcel(RiskBlackorwhiteItemList record);
    /**
	 * @方法说明：插入黑白名单信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    public int insertRiskBlackorwhiteItem(RiskBlackorwhiteItemList record);
    
    /**
	 * @方法说明：删除黑白名单信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    public int deleteRiskBlackorwhiteItem(Integer blackItemId);
    
    public int  insertMapItems(Map map) ;
    
    /**
	 * @方法说明：更新黑白名单信息
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    public int updateRiskBlackorwhiteItem(RiskBlackorwhiteItemList record);
    /**
	 * @方法说明：根据元素值和类别获取数量
	 * @时间：2017年4月20日
	 * @创建人：李震
	 */
    public int getCountByItemValue(RiskBlackorwhiteItemList record);
    /**
   	 * @方法说明：根据元素值和类别获取主键
   	 * @时间：2017年4月20日
   	 * @创建人：李震
   	 */
    public String getItemIdByValueAndBlackId(RiskBlackorwhiteItemList record);
}