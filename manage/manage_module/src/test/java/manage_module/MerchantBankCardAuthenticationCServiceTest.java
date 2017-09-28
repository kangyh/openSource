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
import com.heepay.manage.modules.merchant.service.MerchantBankCardAuthenticationCService;
import com.heepay.manage.modules.merchant.vo.MerchantBankCardAuthentication;

/**
 * 商户打款认证测试类
 * 
 * @author lgk
 *
 */

@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class MerchantBankCardAuthenticationCServiceTest {

  private static Logger log = LogManager.getLogger();

  @Autowired
  private MerchantBankCardAuthenticationCService merchantBankCardAuthenticationCService;

  // 通过id得到对象
  @Test
  public void testGet() {
    String id = "35";
    MerchantBankCardAuthentication merchantBankCardAuthentication = merchantBankCardAuthenticationCService.get(id);
   //Assert.assertNotNull(merchantBankCardAuthentication);
    String merchantJson = JsonMapper.toJsonString(merchantBankCardAuthentication);
    log.info(merchantJson);

  }

  // 查询list
  @Test
  public void testFindList() {
    MerchantBankCardAuthentication merchantBankCardAuthentication = new MerchantBankCardAuthentication();
    List<MerchantBankCardAuthentication> findList = merchantBankCardAuthenticationCService.findList(merchantBankCardAuthentication);
    //Assert.assertEquals(findList.size(), 4);
    log.info(findList.size());

  }

  // 保存
  @Test
  public void testSave() {
    MerchantBankCardAuthentication merchantBankCardAuthentication = new MerchantBankCardAuthentication();
    merchantBankCardAuthentication.setMerchantId("100222");
    //merchantBankCardAuthenticationCService.save(merchantBankCardAuthentication, false);
    //Assert.assertNotNull(merchantBankCardAuthentication);
    log.info(merchantBankCardAuthentication);
  }

  // 设置状态
  @Test
  public void testStatus() {

    String id = "31";
    MerchantBankCardAuthentication merchantBankCardAuthentication = merchantBankCardAuthenticationCService.get(id);
    merchantBankCardAuthentication.setPayStatus("FAIL");// REMIT
    merchantBankCardAuthenticationCService.status(merchantBankCardAuthentication);
    //Assert.assertEquals(merchantBankCardAuthentication.getPayStatus(), "FAIL");
    log.info(merchantBankCardAuthentication.getPayStatus());

  }

}
