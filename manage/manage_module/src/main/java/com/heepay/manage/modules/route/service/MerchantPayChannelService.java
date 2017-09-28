/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.route.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;
import com.heepay.manage.modules.route.dao.MerchantPayChannelDao;
import com.heepay.manage.modules.route.entity.MerchantPayChannel;
import com.heepay.manage.modules.route.entity.PayChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * 描    述：商户通道配置Service
 *
 * 创 建 者： @author 牛文
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
public class MerchantPayChannelService extends CrudService<MerchantPayChannelDao, MerchantPayChannel> {


	@Autowired
	private MerchantCService merchantCService;

	@Autowired
	private PayChannelService payChannelService;


	/**
	 * @discription 添加支付通道生成产品通道明细
	 * @author N.W
	 * @created 2017年4月25日 下午3:18:34
	 * @param id
	 * @param userId
	 * @return
	 */
	public MerchantPayChannel combineDetail(String id, String userId){
		PayChannel payChannela = payChannelService.get(id);
        Merchant merchant = merchantCService.get(userId);
        MerchantPayChannel merchantPayChannel = new MerchantPayChannel();
        merchantPayChannel.setMerchantId(String.valueOf(merchant.getUserId()));
        merchantPayChannel.setChannelCode(payChannela.getChannelCode());
		return merchantPayChannel;
	}
    /**
     * 批量添加产品的支付通道
     *  @author N.W
     * @param checkedstr
     * @param MerchantId
     */
    @Transactional(readOnly = false)
    public void saveBatchChannel(String checkedstr,String MerchantId,String productCode,String productName){
        String[] split = checkedstr.split(",");
        for(String s :split) {
            MerchantPayChannel merchantPayChannel = combineDetail(s, MerchantId);
			merchantPayChannel.setProductCode(productCode);
			merchantPayChannel.setProductName(productName);
            super.save(merchantPayChannel, false);
        }
    }

	public MerchantPayChannel get(String id) {
		return super.get(id);
	}
	
	public List<MerchantPayChannel> findList(MerchantPayChannel merchantPayChannel) {
		return super.findList(merchantPayChannel);
	}

	public Page<MerchantPayChannel> findPage(Page<MerchantPayChannel> page, MerchantPayChannel merchantPayChannel) {
		return super.findPage(page, merchantPayChannel);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantPayChannel merchantPayChannel) {
		super.save(merchantPayChannel,false);
	}
	
	@Transactional(readOnly = false)
	public void delete(MerchantPayChannel merchantPayChannel) {
		super.delete(merchantPayChannel);
	}


	
}