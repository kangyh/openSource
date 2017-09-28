/**
 *  
 */
package com.heepay.manage.modules.route.service;

import com.heepay.common.util.StringUtil;
import com.heepay.common.util.StringUtils;
import com.heepay.enums.AccountType;
import com.heepay.enums.BankcardType;
import com.heepay.enums.BusinessType;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.modules.route.dao.PayChannelDao;
import com.heepay.manage.modules.route.entity.ChannelRedisVO;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.sys.entity.User;
import com.heepay.manage.modules.sys.utils.DictUtils;
import com.heepay.manage.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 名称：支付通道信息操作类
 * 创建者：刘萌
 * 创建日期：2016-8-15
 * 创建描述：支付通道信息的查看、添加、修改、查询功能
 *
 * 审核者：于亮
 * 审核时间：2016年9月1日
 * 审核描述：父类有的方法没有改动可以直接调用；代码缩进部分不标准；只做一次操作不用开启事务；"null"改为null；注意驼峰命名payChannelold
 * 
 * 修改者：马振
 * 修改时间：2016年9月2日
 * 修改描述：修改代码缩进，删除不必要事务；"null"改为null；修改对象名payChannelold为驼峰命名payChannelOld
 * 
 * 修改者：刘萌
 * 修改时间：2016年9月2日
 * 修改描述：父类有的方法没有改动可以直接调用，修改代码缩进
 * 
 */

@Service
@Transactional(readOnly = true)
public class PayChannelService extends CrudService<PayChannelDao, PayChannel> {
  
    @Autowired
    private PayChannelDao payChannelDao;


	/**
	 * 保存通道
	 * @param payChannel
	 */
	@Transactional(rollbackFor = Exception.class)
	public void savePayChannel(PayChannel payChannel) throws Exception {
		try {
			if(StringUtils.isBlank(payChannel.getMonlimitAmount())){
				payChannel.setMonlimitAmount(null);
			}
			super.save(payChannel,false);
			//生成通道代码
			String channelCode = payChannel.getBankNo() + payChannel.getChannelPartnerCode() + "_" + payChannel.getId();
			String channelName;
			//加入商户号生成唯一的支付通道名字
			if (StringUtil.notBlank(payChannel.getMerchantNumber())){
				channelName = payChannel.getBankName() + payChannel.getChannelPartnerName() + payChannel.getChannelTypeName() + payChannel.getCardTypeName() + AccountType.labelOf(payChannel.getAccountType()) + BusinessType.labelOf(payChannel.getBusinessType()) + payChannel.getMerchantNumber();
			}else {
				channelName = payChannel.getBankName() + payChannel.getChannelPartnerName() + payChannel.getChannelTypeName() + payChannel.getCardTypeName() + AccountType.labelOf(payChannel.getAccountType()) + BusinessType.labelOf(payChannel.getBusinessType());
			}

			payChannel.setChannelName(channelName);
			payChannel.setChannelCode(channelCode);
			payChannelDao.updateChannelCode(payChannel);
		} catch (Exception e) {
			throw new Exception("添加支付通道失败",e);
		}
	}
  	  
  	/**     
  	* @discription 根据通道代码查询通道信息（这个方法通道缓存接口也用一下，修改或删除请告知 M.Z）
  	* @author L.M
  	* @created 2016年9月7日 下午8:08:03     
  	* @param ChannelCode
  	* @return     
  	*/
  	public PayChannel selectByChannelCode(String ChannelCode){
  	    PayChannel payChannel = payChannelDao.selectByChannelCode(ChannelCode);
        return payChannel;   
    }
	/**
	 * @discription 根据通道的六个元素银行代码、通道类型、对公对私账户、通道合作方、银行卡类型、付款类型查询通道信息
	 * @author L.M
	 * @created 2016年12月7日 下午5:16:52
	 * @param payChannel
	 * @return
	 */
	public PayChannel selectByPayChannel(PayChannel payChannel){
  	    PayChannel payChannelnew = payChannelDao.selectByPayChannel(payChannel);
        return payChannelnew;
    }
  	
    /**
    * 实现条件查询支付通道并且根据产品代码区分通道是否已添加
    * @param page 分页对象
    * @param
    * @return 支付通道
    */
    public Page<PayChannel> findProductChannelPage(Page<PayChannel> page, Map<String,Object> params) {
        params.put("page", page);
        page.setList(dao.selectProductChannel(params));
        return page;
    }

    /**
    * 实现条件查询支付通道并且根据商户代码区分通道是否已添加
    * @param page 分页对象
    * @param
    * @return 支付通道
    */
    public Page<PayChannel> findMerchantChannelPage(Page<PayChannel> page, Map<String,Object> params) {
        params.put("page", page);
        page.setList(dao.selectMerchantChannel(params));
        return page;
    }

  	  
  	/**     
  	* @discription 查询状态正常且在有效期内的通道方法
  	* @author M.Z
  	* @created 2016年9月7日 下午8:08:28     
  	* @return     
  	*/
  	public List<ChannelRedisVO> selectChannel(){
		List<PayChannel> payChannels = payChannelDao.selectChannel();
		List<ChannelRedisVO> channelRedisVOs = new ArrayList<ChannelRedisVO>();
		for(PayChannel payChannel:payChannels){
			ChannelRedisVO channelRedisVO = getChannelRedisVO(payChannel);
			channelRedisVOs.add(channelRedisVO);
		}
		return channelRedisVOs;
  	}

	/**
	 * @discription 根据条件查询状态正常且在有效期内的通道方法
	 * @author M.Z
	 * @created 2016年10月20日10:29:59
	 * @return
	 */
	public ChannelRedisVO selectChannel(String bankNo,String channelTypeCode,String cardTypeCode,String accountType,String businessType,String merchantId){
		PayChannel payChannel = payChannelDao.selectChannel(bankNo, channelTypeCode, cardTypeCode, accountType, businessType, merchantId);
		return getChannelRedisVO(payChannel);
	}

	/**
	 * @discription 通道VO赋值所需字段（商家通道）
	 * @author M.Z
	 * @created 2016年10月20日10:35:40
	 * @return ChannelRedisVO
	 */
	private ChannelRedisVO getChannelRedisVO(PayChannel payChannel) {
		ChannelRedisVO channelRedisVO = null;
		if (null != payChannel){
			channelRedisVO = new ChannelRedisVO();
			channelRedisVO.setBankNo(payChannel.getBankNo());
			channelRedisVO.setCardTypeCode(payChannel.getCardTypeCode());
			channelRedisVO.setChannelCode(payChannel.getChannelCode());
			channelRedisVO.setChannelPartnerCode(payChannel.getChannelPartnerCode());
			channelRedisVO.setChannelTypeCode(payChannel.getChannelTypeCode());
			channelRedisVO.setCostCount(payChannel.getCostCount());
			channelRedisVO.setCostRate(payChannel.getCostRate());
			channelRedisVO.setCostType(payChannel.getCostType());
			channelRedisVO.setMerchantId(payChannel.getMerchantId());
			channelRedisVO.setSort(payChannel.getSort());
			channelRedisVO.setAccountType(payChannel.getAccountType());
			channelRedisVO.setBusinessType(payChannel.getBusinessType());
		}
		return channelRedisVO;
	}


	/**
	 * @discription 通道VO赋值所需字段
	 * @author M.Z
	 * @created 2016年9月7日 下午8:10:43
	 * @param payChannels
	 * @return
	 */
	private List<ChannelRedisVO> setAttribute(List<PayChannel> payChannels){
		List<ChannelRedisVO> channelRedisVOs = new ArrayList<ChannelRedisVO>();
		if (null!=payChannels && !payChannels.isEmpty()){
			for(PayChannel payChannel:payChannels){
				ChannelRedisVO channelRedisVO = new ChannelRedisVO();
				channelRedisVO.setBankNo(payChannel.getBankNo());
				channelRedisVO.setCardTypeCode(payChannel.getCardTypeCode());
				channelRedisVO.setChannelCode(payChannel.getChannelCode());
				channelRedisVO.setChannelPartnerCode(payChannel.getChannelPartnerCode());
				channelRedisVO.setChannelTypeCode(payChannel.getChannelTypeCode());
				channelRedisVO.setCostCount(payChannel.getCostCount());
				channelRedisVO.setCostRate(payChannel.getCostRate());
				channelRedisVO.setCostType(payChannel.getCostType());
				channelRedisVO.setSort(payChannel.getSort());
				channelRedisVO.setAccountType(payChannel.getAccountType());
				channelRedisVO.setBusinessType(payChannel.getBusinessType());
				channelRedisVOs.add(channelRedisVO);
			}
		}
		return channelRedisVOs;
	}


  	/**     
  	* @discription 查找成本类型为按比例最优通道方法（本行）
  	* @author M.Z
  	* @created 2016年9月7日 下午8:08:42     
  	* @return     
  	*/
  	public List<ChannelRedisVO> selectRateList(){
		List<PayChannel> payChannels = payChannelDao.selectRateList();
		return setAttribute(payChannels);
  	}

	/**
	 * @discription 按条件查找成本类型为按比例最优通道方法（本行）
	 * @author M.Z
	 * @created 2016年10月20日13:55:31
	 * @return
	 */

	public List<ChannelRedisVO> selectRateList(String bankNo,String channelTypeCode,String cardTypeCode,String accountType){
		List<PayChannel> payChannels = null;
		try {
			payChannels = payChannelDao.selectRateList(bankNo,channelTypeCode,cardTypeCode,accountType);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		return setAttribute(payChannels);
	}

  	
  	  
  	/**     
  	* @discription 查找成本类型为按笔数最优通道方法（本行）
  	* @author M.Z
  	* @created 2016年9月7日 下午8:09:04     
  	* @return     
  	*/
  	public List<ChannelRedisVO> selectCountList(){
		List<PayChannel> payChannels = payChannelDao.selectCountList();
		return setAttribute(payChannels);
  	}

	/**
	 * @discription 按条件查找成本类型为按笔数最优通道方法（本行）
	 * @author M.Z
	 * @created 2016年10月20日13:57:29
	 * @return
	 */
	public List<ChannelRedisVO> selectCountList(String bankNo,String channelTypeCode,String cardTypeCode,String accountType){
		List<PayChannel> payChannels = null;
		try {
			payChannels = payChannelDao.selectCountList(bankNo, channelTypeCode, cardTypeCode, accountType);
		} catch (Exception e) {
			return null;
		}
		return setAttribute(payChannels);
	}


	/**
  	* @discription 查找跨行通道 (按比例)
  	* @author M.Z
  	* @created 2016年9月7日 下午8:09:35     
  	* @return     
  	*/
  	public List<ChannelRedisVO> selectSpnChannel(){
		List<PayChannel> payChannels = payChannelDao.selectSpnChannel();
		return setAttribute(payChannels);
  	}

	/**
	 * @discription 按条件查找跨行通道 (按比例)
	 * @author M.Z
	 * @created 2016年10月20日13:58:25
	 * @return
	 */
	public List<ChannelRedisVO> selectSpnChannel(String channelTypeCode,String cardTypeCode,String accountType){
		List<PayChannel> payChannels = null;
		try {
			payChannels = payChannelDao.selectSpnChannel(channelTypeCode, cardTypeCode, accountType);
		} catch (Exception e) {
			return null;
		}
		return setAttribute(payChannels);
	}

	/**
	 * @discription 查找跨行通道 (按笔数)
	 * @author M.Z
	 * @created 2016年9月21日 下午8:09:35
	 * @return
	 */
	public List<ChannelRedisVO> selectSpnChannelCount(){
		List<PayChannel> payChannels = payChannelDao.selectSpnChannelCount();
		return setAttribute(payChannels);
	}

	/**
	 * @discription 按条件查找跨行通道 (按笔数)
	 * @author M.Z
	 * @created 2016年10月20日13:59:33
	 * @return
	 */
	public List<ChannelRedisVO> selectSpnChannelCount(String channelTypeCode,String cardTypeCode,String accountType){
		List<PayChannel> payChannels = null;
		try {
			payChannels = payChannelDao.selectSpnChannelCount(channelTypeCode, cardTypeCode, accountType);
		} catch (Exception e) {
			return null;
		}
		return setAttribute(payChannels);
	}

	/**
	 * @discription 查找每种通道类型对应的银行及卡类型
	 * @author M.Z
	 * @created 2016年9月7日 下午8:09:16
	 * @return
	 */
	public List<PayChannel> selectChannelType(){
		return payChannelDao.selectChannelType();
	}

	/**
	 * @discription 按条件查找每种通道类型对应的银行及卡类型
	 * @author M.Z
	 * @created 2016年9月7日 下午8:09:16
	 * @return
	 */
	public List<PayChannel> selectChannelType(String channelTypeCode){
		return payChannelDao.selectChannelType(channelTypeCode);
	}

	/**
	 * @discription 生成支付通道代码
	 * @author L.M
	 * @created 2016年9月7日 下午8:09:35
	 * @return
	 */
	public PayChannel combineChannelCode(PayChannel payChannel){
		payChannel.setChannelCode(payChannel.getBankNo()+payChannel.getChannelPartnerCode()+payChannel.getChannelTypeCode()+payChannel.getCardTypeCode()+payChannel.getAccountType()+payChannel.getBusinessType());
		return payChannel;
	}

  	/**     
  	* @discription  
  	* 业务类型为本行时，银行，通道类型，卡类型、对公对私标识四者确定的通道只能有一条优先级是默认
    * 业务类型为跨行时，通道类型，卡类型、对公对私标识三者确定的通道只能有一条优先级是默认
  	* @author L.M
  	* @created 2016年9月7日 下午8:09:53     
  	* @param payChannel
  	* @return     
  	*/
  	@Transactional(readOnly = false)
  	public PayChannel setChannelSort(PayChannel payChannel){
  	    //生成支付通道名称
		if(StringUtils.isBlank(payChannel.getId())){
            //通道默认启用状态
			//payChannel.setStatus(CommonStatus.ENABLE.getValue());
			payChannel.setChannelPartnerName(DictUtils.getDictLabel(payChannel.getChannelPartnerCode(),
					Constants.CHANNEL_PARTNER,""));
			payChannel.setChannelTypeName(DictUtils.getDictLabel(payChannel.getChannelTypeCode(),
					Constants.CHANNEL_TYPE,""));
			if(StringUtils.isNotBlank(payChannel.getCardTypeCode())){
				payChannel.setCardTypeName(BankcardType.labelOf(payChannel.getCardTypeCode()));
				payChannel.setChannelName(payChannel.getBankName() + payChannel.getChannelPartnerName() + payChannel.getChannelTypeName() + payChannel.getCardTypeName() + AccountType.labelOf(payChannel.getAccountType()) + BusinessType.labelOf(payChannel.getBusinessType()));
			}else{
				payChannel.setChannelName(payChannel.getBankName() + payChannel.getChannelPartnerName() + payChannel.getChannelTypeName() + AccountType.labelOf(payChannel.getAccountType()) + BusinessType.labelOf(payChannel.getBusinessType()));
			}
		}
  	   	if("RETIME".equals(payChannel.getSettleType())){
  	        payChannel.setSettlePeriod(null);
  	    }
  	    if("RATIOD".equals(payChannel.getCostType())){
              payChannel.setCostCount(null);
        }else{
              payChannel.setCostRate(null);
        }
		if("EXDEDU".equals(payChannel.getChargeDeductType())){
			payChannel.setChargeReturnTag(null);
		}
  	    if((Constants.DEFAULT_SORT).equals(payChannel.getSort())){
			if("OWNBAK".equals(payChannel.getBusinessType())){
				List<PayChannel> payChannels = payChannelDao.selectSortChannelOwnbak(payChannel);
				if(null!=payChannels && !payChannels.isEmpty()){
					for(PayChannel payChannelOld:payChannels){
						payChannelOld.setSort(null);
						//只对sort这一个字段进行update操作,记录操作者设置通道优先级时被修改的其他通道优先级。
						payChannelDao.changeSort(payChannelOld);
					}
				}
			}else{
				List<PayChannel> payChannels = payChannelDao.selectSortChannelSpnbak(payChannel);
				if(null!=payChannels && !payChannels.isEmpty()){
					for(PayChannel payChannelOld:payChannels){
						payChannelOld.setSort(null);
						//只对sort这一个字段进行update操作
						payChannelDao.changeSort(payChannelOld);
					}
				}
			}
        }
		//生成渠道标识(名字OR代码)
		payChannel.setChannelTag(payChannel.getBankNo()+payChannel.getChannelPartnerCode()+payChannel.getChannelTypeCode()+payChannel.getCardTypeCode()+payChannel.getOrderSettlePeriod());
		return payChannel;
  	 }


    /**
    * @discription 支付通道的业务类型、账户类型由值置内容
    * @author L.M
    * @created 2016年9月7日 下午8:10:28
    * @param payChannel
    * @return
    */
    public PayChannel changeValue(PayChannel payChannel){
		if(StringUtils.isNotBlank(payChannel.getBusinessType())) {
			payChannel.setBusinessType(BusinessType.labelOf(payChannel.getBusinessType()));
		}
		if(StringUtils.isNotBlank(payChannel.getAccountType())) {
			payChannel.setAccountType(AccountType.labelOf(payChannel.getAccountType()));
		}
        return payChannel;
    }
  	
  	  
  	/**     
  	* @discription 支付通道启用、禁用状态转换
  	* @author L.M
  	* @created 2016年9月7日 下午8:10:58     
  	* @param payChannel     
  	*/
  	@Transactional(readOnly = false)
    public void status(PayChannel payChannel) {
		User user = UserUtils.getUser();
		if(StringUtils.isNotBlank(user.getId())){
			payChannel.setUpdateBy(user);
		}
  	    payChannelDao.status(payChannel);
    }

	/**
	 * @discription 查找所有通道信息
	 * @author M.Z
	 * @created 2016年9月14日
	 * @return
	 */
	public List<PayChannel> findAllList(){
		return payChannelDao.findAllList();
	}

      
    /**     
    * @discription 银行代码、通道类型
    * @author ly     
    * @created 2016年12月9日 上午11:41:57     
    * @param payChannelFind
    * @return     
    */
    public List<PayChannel> findListByBankNoAndChannelTypeCode(PayChannel payChannelFind) {
        return payChannelDao.findListByBankNoAndChannelTypeCode(payChannelFind);
    }

	/**
	 * @discription 根据通道合作方、支付类型、银行卡类型、状态对支付通道分组
	 * @author lm
	 * @param page
	 * @param payChannel
	 * @return
	 */
	public Page<PayChannel> findChannelGroupPage(Page<PayChannel> page, PayChannel payChannel) {
		payChannel.setPage(page);
		page.setList(payChannelDao.findChannelGroupPage(payChannel));
		return page;
	}

	/**
	 * 将涉及到的修改支付通道赋给页面展示
	 * @param page
	 * @param payChannel
	 * @return
	 */
	public Page<PayChannel> findInvolvedChannelPage(Page<PayChannel> page, PayChannel payChannel) {
		payChannel.setPage(page);
		List<PayChannel> involvedList = new ArrayList<>();
		String[] groupIdStr = payChannel.getGroupId().split(",");
		for(String s: groupIdStr){
			PayChannel payChannels = get(s);
			involvedList.add(payChannels);
		}
		page.setList(involvedList);
		return page;
	}

	/**
	 * 实际操作的批量修改页面显示值
	 * @param payChannel
	 * @return
	 */
	public PayChannel groupPayChannel(PayChannel payChannel){
		String groupIdStr = payChannel.getGroupId();
		String[] split = groupIdStr.split(",");
		PayChannel payChannelOne = get(split[0]);
		payChannelOne.setGroupId(groupIdStr);
		payChannelOne.setId(null);
		return payChannelOne;
	}

	/**
	 * @discription 通道合作方批量启用、禁用状态转换
	 * @author L.M
	 * @created 2016年9月7日 下午8:10:58
	 * @param payChannel
	 */
	@Transactional(readOnly = false)
	public void batchStatus(PayChannel payChannel) {
		String[] groupIdStr = payChannel.getGroupId().split(",");
		String groupStatus = payChannel.getStatus();
		for(String s: groupIdStr){
			PayChannel payChannelOld = get(s);
			payChannelOld.setStatus(groupStatus);
			User user = UserUtils.getUser();
			if(StringUtils.isNotBlank(user.getId())){
				payChannelOld.setUpdateBy(user);
			}
			payChannelDao.status(payChannelOld);
		}
	}

	/**
	 * 通道合作方批量批量修改
	 * @param payChannel
	 */
	@Transactional(readOnly = false)
	public void batchUpdatePayChannel(PayChannel payChannel) {
		String[] groupIdStr = payChannel.getGroupId().split(",");
		for(String s: groupIdStr){
			PayChannel payChannelNew = get(s);
			//当前页面选择值，赋给待修改通道
			payChannelNew.setChargeDeductType(payChannel.getChargeDeductType());
			payChannelNew.setChargeReturnTag(payChannel.getChargeReturnTag());
			payChannelNew.setOrderSettlePeriod(payChannel.getOrderSettlePeriod());
			payChannelNew.setCostType(payChannel.getCostType());
			payChannelNew.setCostRate(payChannel.getCostRate());
			payChannelNew.setCostCount(payChannel.getCostCount());
			payChannelNew.setSettleType(payChannel.getSettleType());
			payChannelNew.setSettlePeriod(payChannel.getSettlePeriod());
			User user = UserUtils.getUser();
			if(StringUtils.isNotBlank(user.getId())){
				payChannelNew.setUpdateBy(user);
			}
			//对数据变化进行处理
			if("RETIME".equals(payChannelNew.getSettleType())){
				payChannelNew.setSettlePeriod(null);
			}
			if("RATIOD".equals(payChannelNew.getCostType())){
				payChannelNew.setCostCount(null);
			}else{
				payChannelNew.setCostRate(null);
			}
			if("EXDEDU".equals(payChannelNew.getChargeDeductType())){
				payChannelNew.setChargeReturnTag(null);
			}
			//TODO 生成CHANNELTag
			//生成渠道标识(名字OR代码)
			payChannelNew.setChannelTag(payChannelNew.getBankNo()+payChannelNew.getChannelPartnerCode()+payChannelNew.getChannelTypeCode()+payChannelNew.getCardTypeCode()+payChannelNew.getOrderSettlePeriod());
			super.save(payChannelNew,false);
		}
	}

	/**
	 * @方法说明：多条插入去重查询
	 * @时间： 2017-09-13 09:21
	 * @创建人：wangl
	 */
	public int sumNum(PayChannel payChannel) {

		return payChannelDao.sumNum(payChannel);
	}
}