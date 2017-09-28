package com.heepay.billing.dao;

import java.util.List;

import com.heepay.billing.entity.SettleChannelRecord;

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
