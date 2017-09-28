/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.route.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.EnumView;
import com.heepay.manage.modules.route.dao.IntegrationChannelDao;
import com.heepay.manage.modules.route.entity.IntegrationChannel;
import com.heepay.manage.modules.sys.dao.DictDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;



/**
 *
 * 描    述：聚合通道Service
 *
 * 创 建 者： @author 马振
 * 创建时间：
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
@Service
@Transactional(readOnly = true)
public class IntegrationChannelService extends CrudService<IntegrationChannelDao, IntegrationChannel> {

	@Autowired
	private IntegrationChannelDao integrationChannelDao;
	@Autowired
	private DictDao dictDao;

	public IntegrationChannel get(String id) {
		return super.get(id);
	}
	
	public List<IntegrationChannel> findList(IntegrationChannel integrationChannel) {
		return super.findList(integrationChannel);
	}
	
	public Page<IntegrationChannel> findPage(Page<IntegrationChannel> page, IntegrationChannel integrationChannel) {
		return super.findPage(page, integrationChannel);
	}
	
	@Transactional(readOnly = false)
	public void save(IntegrationChannel integrationChannel) {
		super.save(integrationChannel,false);
	}

	@Transactional(readOnly = false)
	public void saveList(List<IntegrationChannel> integrationChannels) {
		for (IntegrationChannel integrationChannel:integrationChannels){
			integrationChannelDao.insertOnDuplicateKey(integrationChannel);
		/*	integrationChannelDao.insert(integrationChannel);*/
		}
	}


	@Transactional(readOnly = false)
	public void delete(IntegrationChannel integrationChannel) {
		super.delete(integrationChannel);
	}


	/**
	 * 实现条件查询.net聚合通道并且根据通道区分bankId是否已添加
	 * @param page 分页对象
	 * @param
	 * @return 支付通道
	 */
	public Page<IntegrationChannel> findIntegraChannelPage(Page<IntegrationChannel> page, Map<String,Object> params) {
		params.put("page", page);
		List<IntegrationChannel> integrationChannels = dao.selectIntegraChannel(params);
		//.NET聚合通道字段转换
		page.setList(EnumView.integraChannel(integrationChannels));
		return page;
	}
}