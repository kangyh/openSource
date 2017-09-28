package com.heepay.manage.modules.route.service;

import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;
import com.heepay.manage.modules.route.entity.ProductDetail;
import com.heepay.manage.modules.route.service.ProductDetailService;
import com.heepay.manage.modules.sys.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**          
* 
* 描    述：ProductDetailService测试类
*
* 创 建 者： L.M
* 创建时间： 2016年9月29日 下午1:52:29 
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
    
@RunWith(SpringJUnit4ClassRunner.class) //用于配置spring中测试的环境
@ContextConfiguration(locations = {"classpath*:/spring-context.xml"}) //用于指定配置文件所在的位置

public class ProductDetailServiceTest {

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ProductCService productCService;

    private static final Logger logger = LogManager.getLogger();

      
    /**     
    * @discription 绑定产品、支付通道构建产品通道
    * @author Administrator       
    * @created 2016年9月29日 下午1:52:34          
    */
    @Test
    public void testCombineDetail (){
        String id="155";
        String productId="11";
        ProductDetail productDetail=productDetailService.combineDetail(id,productId);
        logger.info(productDetail);
    }


    /**     
    * @discription 查询产品
    * @author Administrator       
    * @created 2016年9月29日 下午1:52:38          
    */
    @Test
    public void testFindListChannel (){
        Product product=new Product();
        //product.setName("快");
        product.setName("快捷支付—API");
        List<Product> products=productCService.findListChannel(product);
        logger.info(products.size());
        //Assert.assertEquals(products.size(), 4);
        //Assert.assertEquals(products.size(),1);
        logger.info(products);
    }

      
    /**     
    * @discription 保存
    * @author Administrator       
    * @created 2016年9月29日 下午1:52:41          
    */
    @Test
    public void testSave (){
        ProductDetail productDetail=new ProductDetail();
        productDetail.setProductName("TEST");
        User user= new User();
        user.setId("1");
        productDetail.setCreateBy(user);
        productDetail.setUpdateBy(user);
        //productDetailService.save(productDetail,false);
    }

}
