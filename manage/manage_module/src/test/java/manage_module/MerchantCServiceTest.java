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
import com.heepay.manage.modules.merchant.service.MerchantCService;
import com.heepay.manage.modules.merchant.vo.Merchant;

/**
 * 商户Service测试类
 * 
 * @author lgk
 */

@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class MerchantCServiceTest {

  private static Logger log = LogManager.getLogger();

  @Autowired
  private MerchantCService merchantCService;

  // 通过id得到对象
  @Test
  public void testGet() {
    String id = "100003";
    Merchant merchant = merchantCService.get(id);
    //Assert.assertNotNull(merchant);
    String merchantJson = JsonMapper.toJsonString(merchant);
    log.info(merchantJson);

  }

  // 查出list
  @Test
  public void testFindList() {
    Merchant merchant = new Merchant();
    List<Merchant> findList = merchantCService.findList(merchant);
    //Assert.assertEquals(findList.size(), 5);
    log.info(findList.size());
  }

  // 保存
  @Test
  public void testSave() {
    Merchant merchant = new Merchant();
    merchant.setUserId(100333);
    merchant.setCompanyName("wwwww");
    //merchantCService.save(merchant, false);
    //Assert.assertNotNull(merchant);
    log.info(merchant);

  }

  // 设置状态
  @Test
  public void testStatus() {
    String id = "100003";
    Merchant merchant = merchantCService.get(id);
    merchant.setStatus("SUCCES");// FAIL
    merchantCService.status(merchant);
    //Assert.assertEquals(merchant.getStatus(), "SUCCES");
    log.info(merchant.getStatus());

  }

  // 风控审核状态
  @Test
  public void testlegalAuditStatus() {
    String id = "100003";
    Merchant merchant = merchantCService.get(id);
    merchant.setLegalAuditStatus("SUCCES");
    merchantCService.legalAuditStatus(merchant);
    log.info(merchant.getLegalAuditStatus());

  }

  // 法务审核状态
  @Test
  public void testrcAuditStatus() {
    String id = "100003";
    Merchant merchant = merchantCService.get(id);
    merchant.setRcAuditStatus("SUCCES");
    merchantCService.rcAuditStatus(merchant);
    log.info(merchant.getRcAuditStatus());

  }

}
