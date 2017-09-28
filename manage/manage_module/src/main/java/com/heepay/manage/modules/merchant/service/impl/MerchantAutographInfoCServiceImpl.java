/**
 *   Copyright © since 2008. All Rights Reserved
 *   汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package com.heepay.manage.modules.merchant.service.impl;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.heepay.common.util.StringUtils;
import com.heepay.date.DateUtils;
import com.heepay.manage.common.cache.RedisUtil;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.ListEnum;
import com.heepay.manage.common.utils.RandomUtil;
import com.heepay.manage.modules.merchant.dao.MerchantAutographInfoDao;
import com.heepay.manage.modules.merchant.service.MerchantAutographInfoCService;
import com.heepay.manage.modules.merchant.vo.MerchantAutographInfo;

/**
 *
 * 描    述：技术签约Service
 *
 * 创 建 者：ly
 * 创建时间：2016-08-23
 * 创建描述：
 *
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 *
 * 审 核 者：ljh
 * 审核时间：2016-09-01
 * 审核描述：42行定义变量命名不符合规范;62行注释不用代码直接删除;44、48、52行如果直接使用父类方法，子类中可不用重写，直接调用父类。
 *
 */
@Service
@Transactional(readOnly = true)
public class MerchantAutographInfoCServiceImpl extends CrudService<MerchantAutographInfoDao, MerchantAutographInfo>
		implements MerchantAutographInfoCService {

	@Autowired
	private MerchantAutographInfoDao merchantAutographInfoDao;
	
	@Transactional(readOnly = false)
	public void status(MerchantAutographInfo merchantAutographInfo) {
		merchantAutographInfoDao.status(merchantAutographInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(MerchantAutographInfo merchantAutographInfo, boolean flag) {
	  if(StringUtils.isNotBlank(merchantAutographInfo.getProductName())){
      merchantAutographInfo.setProductName(HtmlUtils.htmlUnescape(merchantAutographInfo.getProductName()));
    }
    //转换复选框
    String allowInterfaceType = ListEnum.listToString(merchantAutographInfo.getAllowInterfaceTypes(), ",");
    merchantAutographInfo.setAllowInterfaceType(allowInterfaceType);
    //生成签约号
    String now = DateFormatUtils.format(new Date(), DateUtils.DB_FORMAT_DATE);
    merchantAutographInfo.setMerchantSignNo(RandomUtil.getNum(now));
    //生成签名key
    merchantAutographInfo.setAutographKey(RandomUtil.getKey(merchantAutographInfo.getMerchantId(),
            merchantAutographInfo.getProductCode(),merchantAutographInfo.getAutographType() ));
	  super.save(merchantAutographInfo, flag);
	  //写费率(商家产品)
//    RedisUtil.getRedisUtil().saveMerchantProductRedis(merchantAutographInfo.getMerchantId(),
//        merchantAutographInfo.getProductCode(), false, false);
	}

  @Override
  public MerchantAutographInfo exist(MerchantAutographInfo merchantAutographInfo) {
    return merchantAutographInfoDao.exist(merchantAutographInfo);
  }
}