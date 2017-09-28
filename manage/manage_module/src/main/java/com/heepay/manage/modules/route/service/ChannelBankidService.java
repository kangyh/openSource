/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.route.service;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.common.util.StringUtil;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.route.dao.ChannelBankidDao;
import com.heepay.manage.modules.route.entity.ChannelBankid;
import com.heepay.manage.modules.route.entity.IntegrationChannel;
import com.heepay.manage.modules.route.entity.PayChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * 描    述：通道bankid关联Service
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
public class ChannelBankidService extends CrudService<ChannelBankidDao, ChannelBankid> {

	@Autowired
	private IntegrationChannelService integrationChannelService;
	@Autowired
	private PayChannelService payChannelService;

	public ChannelBankid get(String id) {
		return super.get(id);
	}
	
	public List<ChannelBankid> findList(ChannelBankid channelBankid) {
		return super.findList(channelBankid);
	}
	
	public Page<ChannelBankid> findPage(Page<ChannelBankid> page, ChannelBankid channelBankid) {
		return super.findPage(page, channelBankid);
	}
	
	@Transactional(readOnly = false)
	public void save(ChannelBankid channelBankid) {
		super.save(channelBankid,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(ChannelBankid channelBankid) {
		super.delete(channelBankid);
	}

	/**
	 * @discription 添加.net聚合通道生成通道明细
	 * @param id
	 * @param channelId
	 * @return
	 */
	public ChannelBankid combineDetail(String id, String channelId){
		IntegrationChannel integrationChannel = integrationChannelService.get(id);
		PayChannel payChannel = payChannelService.get(channelId);
		ChannelBankid channelBankid = new ChannelBankid();
		channelBankid.setChannelCode(payChannel.getChannelCode());
		channelBankid.setBankId(integrationChannel.getBankId());
		channelBankid.setBankProvider(integrationChannel.getBankProvider());
		channelBankid.setProviderCode(integrationChannel.getProviderCode());
		channelBankid.setChannelKey(integrationChannel.getChannelKey());
		channelBankid.setChannelName(integrationChannel.getChannelName());
		channelBankid.setPayType(integrationChannel.getPayType());
		channelBankid.setSceneKey(integrationChannel.getSceneKey());
		return channelBankid;
	}

	/**
	 * 批量添加银通通道的.net通道
	 * @param checkedstr
	 * @param channelId
	 */
	@Transactional(readOnly = false)
	public void saveBatchBankId(String checkedstr,String channelId){
		String[] split = checkedstr.split(",");
		for(String s :split) {
			ChannelBankid channelBankid = combineDetail(s, channelId);
			dao.insert(channelBankid);
		}
	}

	/**
	 * 根据channelCode获取bankId信息
	 * @param channelCode
	 * @return
	 */
	public String queryBankId(String channelCode) {
		if (StringUtil.notBlank(channelCode)) {
			List<ChannelBankid> channelBankids = dao.queryByChannelCode(channelCode);
			if (null != channelBankids && !channelBankids.isEmpty()) {
				JsonMapperUtil jsonMapperUtil = JsonMapperUtil.nonEmptyMapper();
				List<IntegrationChannel> integrationChannels = new ArrayList<>();
				for (ChannelBankid channelBankid : channelBankids) {
					IntegrationChannel integrationChannel = new IntegrationChannel();
					if (StringUtil.notBlank(channelBankid.getBankId())){
						integrationChannel.setBankId(channelBankid.getBankId());
						integrationChannel.setSubMerchantNo(channelBankid.getSubMerchantNo());
					}
					integrationChannels.add(integrationChannel);
				}
				return jsonMapperUtil.toJson(integrationChannels);
			}
		}
		return "";
	}

}