package com.heepay.boss.dao;

import java.util.List;

/**
 * *
 * 
* 
* 描    述：
*
* 创 建 者： wangjie
* 创建时间：  2017年6月1日下午2:14:57
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
public interface BaseDao<T, E> {
//	public List<T> find(Map pMap);
//	public void insert(T entity);
//	public void update(T entity);
//	public void delete(Integer[] ids);
	
	public int deleteByPrimaryKey(Long id);
	
	public int deleteByPrimaryKey(String id);

	public int insert(T record);

	public int insertSelective(T record);

	public T selectByPrimaryKey(Long id);
	
	public T selectByPrimaryKey(String id);

	public int updateByPrimaryKeySelective(T record);

	public int updateByPrimaryKey(T record);
	
	public int countByExample(E example);

	public int deleteByExample(E example);

	public List<T> selectByExample(E example);

	public int updateByExampleSelective(T record, E example);

	public int updateByExample(T record, E example);
	
	public List<T> getPage(E example, int index, int size);

}
