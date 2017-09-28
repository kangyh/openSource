package com.heepay.billing.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.heepay.billing.dao.BaseDao;


public abstract class BaseDaoImpl<T, E> extends SqlSessionDaoSupport implements BaseDao<T, E> {
	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
		super.setSqlSessionFactory(sqlSessionFactory);
	}
	
	private String ns;
	
	public String getNs() {
		return ns;
	}
	public void setNs(String ns) {
		this.ns = ns;
	}
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return super.getSqlSession().delete(this.getNs()+".deleteByPrimaryKey", id);
	}
	@Override
	public int deleteByPrimaryKey(String id) {
		return super.getSqlSession().delete(this.getNs()+".deleteByPrimaryKey", id);
	}
	@Override
	public int insert(T record) {
		return super.getSqlSession().insert(this.getNs()+".insert", record);
	}
	@Override
	public int insertSelective(T record) {
		return super.getSqlSession().insert(this.getNs()+".insertSelective", record);
	}
	@Override
	public T selectByPrimaryKey(Long id) {
		return super.getSqlSession().selectOne(this.getNs()+".selectByPrimaryKey", id);
	}
	@Override
	public T selectByPrimaryKey(String id) {
		return super.getSqlSession().selectOne(this.getNs()+".selectByPrimaryKey", id);
	}
	@Override
	public int updateByPrimaryKeySelective(T record) {
		return super.getSqlSession().update(this.getNs()+".updateByPrimaryKeySelective", record);
	}
	@Override
	public int updateByPrimaryKey(T record) {
		return super.getSqlSession().update(this.getNs()+".updateByPrimaryKey", record);
	}
	@Override
	public int countByExample(E example){
		return super.getSqlSession().selectOne(this.getNs()+".countByExample", example);
	}
	@Override
	public int deleteByExample(E example){
		return super.getSqlSession().delete(this.getNs()+".deleteByExample", example);
	}
	@Override
	public List<T> selectByExample(E example){
		return super.getSqlSession().selectList(this.getNs()+".selectByExample", example);
	}
	@Override
	public int updateByExampleSelective(T record, E example){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("record", record);
		map.put("example", example);
		return super.getSqlSession().update(this.getNs()+".updateByExampleSelective", map);
	}
	@Override
	public int updateByExample(T record, E example){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("record", record);
		map.put("example", example);
		return super.getSqlSession().update(this.getNs()+".updateByExample", map);
	}
	@Override
	public List<T> getPage(E example, int index, int size){
		RowBounds rb = new RowBounds(index, size);
		return super.getSqlSession().selectList(this.getNs()+".selectByExample", example, rb);
	}
}
