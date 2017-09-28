/**
 *  
 */
package com.heepay.manage.modules.route.service;

import com.heepay.manage.common.service.CrudService;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.route.dao.ProductDetailDao;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.route.entity.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**          
* 
* 描    述：产品明细Service
*
* 创 建 者： L.M
* 创建时间： 2016年9月9日 下午3:22:39 
* 创建描述：
* 
* 修 改 者：  刘萌
* 修改时间： 2016年9月9日
* 修改描述：父类有的方法没有改动可以直接调用；代码缩进标准 
* 
* 审核者：于亮
* 审核时间：2016年9月1日
* 审核描述：父类有的方法没有改动可以直接调用；代码缩进部分不标准；只做一次操作不用开启事务；
*
*/      
    
@Service
@Transactional(readOnly = true)
public class ProductDetailService extends CrudService<ProductDetailDao, ProductDetail> {

    @Autowired
    private ProductCService productCService;
    
    @Autowired
    private PayChannelService payChannelService;

  	
    /**     
    * @discription 添加支付通道生成产品通道明细
    * @author L.M
    * @created 2016年9月9日 下午3:18:34
    * @param id
    * @param productId
    * @return     
    */
    public ProductDetail combineDetail(String id,String productId){
        PayChannel payChannela = payChannelService.get(id);
        Product product = productCService.get(productId);
        ProductDetail productDetail = new ProductDetail();
        productDetail.setProductName(product.getName()) ;       
        productDetail.setProductCode(product.getCode());
        productDetail.setChannelName(payChannela.getChannelName()) ;       
        productDetail.setChannelCode(payChannela.getChannelCode()) ;
        productDetail.setBankName(payChannela.getBankName()) ;       
        productDetail.setBankNo(payChannela.getBankNo()) ;
        productDetail.setChannelPartnerName(payChannela.getChannelPartnerName()) ;       
        productDetail.setChannelPartnerCode(payChannela.getChannelPartnerCode()) ;
        productDetail.setChannelTypeName(payChannela.getChannelTypeName()) ;       
        productDetail.setChannelTypeCode(payChannela.getChannelTypeCode()) ;
        productDetail.setCardTypeName(payChannela.getCardTypeName()) ;       
        productDetail.setCardTypeCode(payChannela.getCardTypeCode()) ;
        //支付通道的状态
        productDetail.setStatus(payChannela.getStatus()) ;
        return productDetail;   
    }

    /**
     * 批量添加产品的支付通道
     *  @author L.M
     * @param checkedstr
     * @param productId
     */
    @Transactional(readOnly = false)
    public void saveBatchChannel(String checkedstr,String productId){
        String[] split = checkedstr.split(",");
        for(String s :split) {
            ProductDetail productDetail = combineDetail(s, productId);
            super.save(productDetail, false);
        }
    }

    /**
     * 批量删除产品的支付通道
     *  @author L.M
     * @param checkedstr
     */
    @Transactional(readOnly = false)
    public void delBatchChannel(String checkedstr){
        String[] split = checkedstr.split(",");
        for(String s :split) {
            ProductDetail productDetail = get(s);
            super.delete(productDetail);
        }
    }

}