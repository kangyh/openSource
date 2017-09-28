package com.heepay.billing.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.heepay.billing.entity.ClearProfitRecord;
import com.heepay.billing.entity.ClearProfitRecordExample;

public interface ClearProfitRecordMapper {
    int countByExample(ClearProfitRecordExample example);

    int deleteByExample(ClearProfitRecordExample example);

    int deleteByPrimaryKey(Long profitId);

    int insert(ClearProfitRecord record);

    int insertSelective(ClearProfitRecord record);

    List<ClearProfitRecord> selectByExample(ClearProfitRecordExample example);

    ClearProfitRecord selectByPrimaryKey(Long profitId);

    int updateByExampleSelective(@Param("record") ClearProfitRecord record, @Param("example") ClearProfitRecordExample example);

    int updateByExample(@Param("record") ClearProfitRecord record, @Param("example") ClearProfitRecordExample example);

    int updateByPrimaryKeySelective(ClearProfitRecord record);

    int updateByPrimaryKey(ClearProfitRecord record);

    /**
     * 
     * @方法说明：分润明细入库
     * @时间：2016年11月3日 下午2:41:06
     * @创建人：wangdong
     */
	int insertClearProfitRecord(ClearProfitRecord clearProfitRecord);
	
	/**
	 * 通过订单号，查询分润明细
	 * @param map
	 * @return
	 */
	List<ClearProfitRecord> queryByTransNo(Map map);
	
	/**
	 * 清算明细结算后，更新结果
	 * @param map
	 */
	void updateAfterSettle(Map map);

	/**
	 * 
	 * @方法说明：判断是否重复发送的数据
	 * @时间：Nov 7, 2016
	 * @创建人：wangl
	 */
	int getBooleanExist(int shareDetailId);
}