/**
 *  
 */
package com.heepay.manage.modules.merchant.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.heepay.manage.common.persistence.CrudDao;
import com.heepay.manage.common.persistence.annotation.MyBatisDao;
import com.heepay.manage.modules.merchant.vo.Product;

/**
 * 产品DAO接口
 * @author ly
 * @version V1.0
 */
@MyBatisDao
public interface ProductDao extends CrudDao<Product> {
      
    /**     
    * @discription 获取全部产品
    * @author ly     
    * @created 2016年12月3日 下午4:03:53     
    * @return     
    */
    List<Product> selectList();

      
    /**     
    * @discription 修改产品状态
    * @author ly     
    * @created 2016年12月3日 下午4:03:50     
    * @param product     
    */
    void status(Product product);

      
    /**     
    * @discription 根据code查询产品
    * @author ly     
    * @created 2016年12月3日 下午4:03:48     
    * @param code
    * @return     
    */
    Product selectByCode(String code);
    
      
    /**     
    * @discription 通道查询产品
    * @author ly     
    * @created 2016年12月3日 下午4:03:43     
    * @param product
    * @return     
    */
    List<Product> findListChannel(Product product);
    
      
    /**     
    * @discription 获取产品数量
    * @author ly     
    * @created 2016年12月3日 下午4:03:45     
    * @return     
    */
    Integer count();

      
    /**     
    * @discription 根据业务类型获取产品
    * @author ly     
    * @created 2016年12月3日 下午4:03:40     
    * @param businessType
    * @return     
    */
    List<Product> getProductList(@Param("businessType") String businessType);

      
    /**     
    * @discription 根据交易类型获取产品
    * @author ly     
    * @created 2016年12月3日 下午4:03:38     
    * @param trxType
    * @return     
    */
    List<Product> selectByType(@Param("trxType")String trxType);

      
    /**     
    * @discription 根据产品code获取启用的产品
    * @author ly     
    * @created 2016年12月3日 下午4:03:35     
    * @param productCode
    * @return     
    */
    Product selectByCodeEnable(String productCode);


      
    /**     
    * @discription 根据产品名字获取产品
    * @author ly     
    * @created 2016年12月14日 下午6:09:17     
    * @param product
    * @return     
    */
    Product existByName(Product product);

    /**
     * @discription 获取综合通道list
     * @author ly
     * @created 2017年2月28日16:16:52
     * @param product
     * @return
     */
    List<Product> findCertifyList(Product product);
}