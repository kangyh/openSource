/**
 *  
 */
package com.heepay.manage.modules.route.service;

import com.heepay.common.util.StringUtils;
import com.heepay.manage.common.enums.CertifyChannelPartner;
import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.common.utils.Constants;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.route.dao.CertifyChannelDao;
import com.heepay.manage.modules.route.entity.CertifyChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**          
* 
* 描    述：实名认证通道管理Service
*
* 创 建 者： lm
* 创建时间： 2016年9月29日 下午1:46:37 
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
public class CertifyChannelService extends CrudService<CertifyChannelDao, CertifyChannel> {
 
    @Autowired
    private CertifyChannelDao certifyChannelDao;

    @Autowired
    private ProductCService productCService;
	  
	/**     
	* @discription 根据通道合作方代码查询表route_certify_channel中的信息
	* @author L.M
	* @created 2016年9月13日 下午6:19:44     
	* @param channelPartnerCode
	* @return     
	*/
	public CertifyChannel selectByPartnerCode(String channelPartnerCode,String productCode,String channelTypeCode){
        CertifyChannel certifyChannel = certifyChannelDao.selectByPartnerCode(channelPartnerCode,productCode,channelTypeCode);
        return certifyChannel;
	}


   /**
   * @discription 添加、修改通道生成实名认证产品通道明细
   * @author L.M
   * @created 2016年9月13日 下午6:20:01
   * @param ProductId
   * @param certifyChannel
   * @return
   */

    @Transactional(readOnly = false)
    public CertifyChannel combineCertifyDetail (String ProductId,CertifyChannel certifyChannel){
        Product product=productCService.get(ProductId);
        certifyChannel.setProductName(product.getName());
        certifyChannel.setProductCode(product.getCode());
        certifyChannel.setChannelPartnerName(CertifyChannelPartner.labelOf(certifyChannel.getChannelPartnerCode())) ;
        certifyChannel.setChannelTypeName(StringUtils.isBlank(certifyChannel.getChannelTypeName())?"实名认证":certifyChannel.getChannelTypeName());
        certifyChannel.setChannelTypeCode(StringUtils.isBlank(certifyChannel.getChannelTypeCode())?"CERTIF":certifyChannel.getChannelTypeCode());
        String partnerCode = Constants.KOALA;
        if("CP15".indexOf(product.getCode()) != -1){
            partnerCode = certifyChannel.getChannelPartnerCode();
        }
        if("CP26".indexOf(product.getCode()) != -1 && !certifyChannel.getChannelPartnerCode().equals(CertifyChannelPartner.LAKALA.getValue())){
            partnerCode = certifyChannel.getChannelPartnerCode();
        }
        if("CP50".indexOf(product.getCode()) != -1){
            partnerCode = Constants.QARED;
        }
        certifyChannel.setChannelName(certifyChannel.getChannelPartnerName()+certifyChannel.getChannelTypeName()) ;
        certifyChannel.setChannelCode(partnerCode+certifyChannel.getChannelTypeCode()) ;
        if("RETIME".equals(certifyChannel.getSettleType())){
            certifyChannel.setSettlePeriod(null);
        }
        if(Constants.DEFAULT_SORT.equals(certifyChannel.getSort())){
            List<CertifyChannel> certifyChannels = certifyChannelDao.findList(certifyChannel);
            if(null!=certifyChannels && !certifyChannels.isEmpty()){
                for(CertifyChannel certifyChannelOld:certifyChannels){
                    certifyChannelOld.setSort(null);
                    //只对sort这一个字段进行update操作
                    certifyChannelDao.changeSort(certifyChannelOld);
                }
            }
        }
        //支付通道的状态
        return certifyChannel;
    }
}