/**
 *  
 */
package com.heepay.prom.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.heepay.common.util.StringUtils;
import com.heepay.prom.common.persistence.CrudDao;
import com.heepay.prom.common.persistence.DataEntity;
import com.heepay.prom.common.persistence.Page;

/**
 * Service基类
 *  
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.get(id);
	}
	
	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity);
	}
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findUserWithdrawPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findUserWithdrawPage(entity));
		return page;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findList(entity));
		return page;
	}
	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	public void update(T entity,boolean flag) {
			entity.preUpdate();
			dao.update(entity);
	}
	/**
	 * 保存数据(自增id)
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity,boolean flag) {
		if (entity.getIsNewRecord()){
			entity.setUserOrDate();
			if(StringUtils.isBlank(entity.getId())){
				entity.setId(null);
			}
			dao.insert(entity);	
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}
	
	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}

	/**
	 * 获取所有表数据
	 * @return
	 */
	public List<T> findAllList(){
		return dao.findAllList();
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPageByStatus(Page<T> page, T entity) {
		entity.setPage(page);
		page.setList(dao.findPageByStatus(entity));
		return page;
	}
	
	/**
	 * 根据查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findListByStatus(T entity) {
		return dao.findListByStatus(entity);
	}


}
