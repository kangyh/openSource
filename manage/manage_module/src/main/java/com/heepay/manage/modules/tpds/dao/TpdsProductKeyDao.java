package com.heepay.manage.modules.tpds.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.tpds.entity.TpdsProductKey;

@MyBatisDao
public interface TpdsProductKeyDao extends CrudDao<TpdsProductKey>{
	int updateEntity(TpdsProductKey record);

	int saveEntity(TpdsProductKey record);

	TpdsProductKey getEntityById(Integer id);
}