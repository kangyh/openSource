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
import com.heepay.manage.modules.merchant.service.MerchantFuctionsCService;
import com.heepay.manage.modules.merchant.vo.MerchantFuctions;

/**
 * 商户权限测试类
 * 
 * @author lgk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class MerchantFuctionsCServiceTest {

  private static Logger log = LogManager.getLogger();

  @Autowired
  private MerchantFuctionsCService merchantFuctionsCService;

  // 通过id得到对象
  @Test
  public void testGet() {
    String id = "100";
    MerchantFuctions merchantFuctions = merchantFuctionsCService.get(id);
    //Assert.assertNotNull(merchantFuctions);
    String merchantJson = JsonMapper.toJsonString(merchantFuctions);
    log.info(merchantJson);

  }

  // 查出list
  @Test
  public void testFindList() {
    MerchantFuctions merchantFuctions = new MerchantFuctions();
    List<MerchantFuctions> findList = merchantFuctionsCService.findList(merchantFuctions);
    //Assert.assertEquals(findList.size(), 17);
    log.info(findList.size());
  }

  // 保存
  @Test
  public void testSave() {
    MerchantFuctions merchantFuctions = new MerchantFuctions();
    merchantFuctions.setFunctionName("kk");
    //merchantFuctionsCService.save(merchantFuctions, false);
    //Assert.assertNotNull(merchantFuctions);
    log.info(merchantFuctions);

  }

}
