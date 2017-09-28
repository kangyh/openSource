/**
 *  
 */
package com.heepay.manage.modules.route.dao;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.route.entity.ProductDetail;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品明细DAO接口
 * @author lm
 * @version V1.0
 */
@MyBatisDao
public interface ProductDetailDao extends CrudDao<ProductDetail> {


    /**
    * @discription 根据产品code获取产品通道明细
    * @author ly
    * @created 2016年12月7日 下午5:51:35
    * @param productCode
    * @return
    */
    public List<ProductDetail> selectByProductCode(@Param(value = "productCode")String productCode);

    /**
     * 根据产品code获取有效产品通道明细
     * @param productCode
     * @return
     */
    public List<ProductDetail> selectValidByProductCode(@Param(value = "productCode")String productCode);

    /**
    * @discription 根据产品code获取产品通道支持的银行
    * @author ly
    * @created 2016年12月7日 下午5:52:12
    * @param productDetail
    * @return
    */
    public List<ProductDetail> getProductDetailBank(ProductDetail productDetail);


    /**
    * @discription 根据产品code获取产品通道支持的银行卡类型
    * @author ly
    * @created 2016年12月7日 下午5:52:32
    * @param productCode
    * @return
    */
    public List<String> getProductDetailBankCardType(@Param(value = "productCode")String productCode);


    /**
    * @discription 根据产品code获取产品通道支持的银行No
    * @author ly
    * @created 2016年12月9日 下午2:39:08
    * @param productCode
    * @return
    */
    public List<ProductDetail> getProductDetailBankNo(@Param(value = "productCode")String productCode);

    /**
     * 根据产品code获取产品通道支持的银行及卡类型
     * @param productCode
     * @return
     */
    public List<ProductDetail> selectBankInfoByProductCode(@Param(value = "productCode")String productCode);

    /**
     * @discription 根据产品code,成本类型获取产品通道支持的成本最大值
     * @author ly
     * @created 2017年2月14日16:03:38
     * @param productCode
     * @return
     */
    BigDecimal getMaxCost(@Param(value = "productCode")String productCode,@Param(value = "chargeType")
            String chargeType,@Param(value = "cardTypeCode")String cardTypeCode);
}