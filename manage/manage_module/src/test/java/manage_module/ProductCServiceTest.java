package manage_module;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.heepay.manage.common.utils.JsonMapper;
import com.heepay.manage.modules.merchant.service.ProductCService;
import com.heepay.manage.modules.merchant.vo.Product;

/**
 * 产品管理功能测试类
 * 
 * @author lgk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class ProductCServiceTest {

  private static Logger logger = LogManager.getLogger();

  @Autowired
  private ProductCService productCService;

  // 通过id得到对象
  @Test
  public void testGet() {
    String id = "33";
    Product product = productCService.get(id);
    //Assert.assertNotNull(product);
    String merchantJson = JsonMapper.toJsonString(product);
    logger.info(merchantJson);

  }

  // 查出list
  @Test
  public void testFindList() {
    Product product = new Product();
    List<Product> findList = productCService.findList(product);
    //Assert.assertEquals(findList.size(), 16);
    logger.info(findList.size());
  }

  // 保存
  @Test
  public void testSave() {
    Product product = new Product();
    product.setName("测试");
    product.setAuditStatus("SUCCES");
    product.setRemark("测试");
    product.setStatus("DISABL");
    //productCService.save(product);
    //Assert.assertNotNull(product);
    logger.info(product);

  }

  // 状态
  @Test
  public void testStatus() {
    String id = "33";
    Product product = productCService.get(id);
    product.setStatus("DISABL");// ENABLE
    productCService.status(product);
    //Assert.assertEquals(product.getStatus(), "DISABL");
    logger.info(product.getStatus());

  }

  // @Test
  // private void testSelectByCode(){
  // String code="CP16";
  // Product selectByCode = productCService.selectByCode(code);
  // logger.info(selectByCode.getCode());
  // }
  //

  // 去掉实名认证产品
  @Test
  public void testFindListChannel() {
    Product product = new Product();
    List<Product> findList = productCService.findListChannel(product);
    //Assert.assertEquals(findList.size(), 15);
    logger.info(findList.size());
  }

}
