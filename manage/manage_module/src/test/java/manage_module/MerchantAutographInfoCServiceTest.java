package manage_module;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.manage.modules.merchant.service.MerchantAutographInfoCService;
import com.heepay.manage.modules.merchant.vo.MerchantAutographInfo;

/**
 * 技术签约测试类
 * 
 * @author lgk
 */

@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class MerchantAutographInfoCServiceTest {

  private static Logger logger = LogManager.getLogger();

  @Autowired
  private MerchantAutographInfoCService merchantAutographInfoCService;

  // 保存
  @Test
  public void testSave() {
    MerchantAutographInfo merchantAutographInfo = new MerchantAutographInfo();
    merchantAutographInfo.setMerchantId("9");
    merchantAutographInfo.setProductName("测试");
    //merchantAutographInfoCService.save(merchantAutographInfo, false);
    //Assert.assertNotNull(merchantAutographInfo);
    logger.info(merchantAutographInfo);

  }

  // 设置状态
  @Test
  public void testStatus() {
    String id = "7";
    MerchantAutographInfo merchantAutographInfo = merchantAutographInfoCService.get(id);
    merchantAutographInfo.setStatus("DISABL");// ENABLE
    merchantAutographInfoCService.status(merchantAutographInfo);
    //Assert.assertEquals(merchantAutographInfo.getStatus(), "DISABL");
    logger.info(merchantAutographInfo.getStatus());

  }

}
