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
import com.heepay.manage.modules.merchant.service.MerchantOperationLogCService;
import com.heepay.manage.modules.merchant.vo.MerchantOperationLog;

/**
 * 商户测试类
 * 
 * @author lgk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class MerchantOperationLogCServiceTest {

  private static Logger logger = LogManager.getLogger();

  @Autowired
  private MerchantOperationLogCService merchantOperationLogCService;

  // 保存
  @Test
  public void testSave() {
    MerchantOperationLog merchantOperationLog = new MerchantOperationLog();
    merchantOperationLog.setMerchantId("100444");
    merchantOperationLog.setEmployeeId("100088");
    //merchantOperationLogCService.save(merchantOperationLog, false);
    //Assert.assertNotNull(merchantOperationLog);
    logger.info(merchantOperationLog);

  }

  // 通过id取得对象
  @Test
  public void testGet() {
    String id = "193";
    MerchantOperationLog merchantOperationLog = merchantOperationLogCService.get(id);
    //Assert.assertNotNull(merchantOperationLog);
    String merchantJson = JsonMapper.toJsonString(merchantOperationLog);
    logger.info(merchantJson);

  }

  // 查出list
  @Test
  public void testFindList() {
    MerchantOperationLog merchantOperationLog = new MerchantOperationLog();
    List<MerchantOperationLog> findList = merchantOperationLogCService.findList(merchantOperationLog);
    // Assert.assertEquals(findList.size(), 18);
    logger.info(findList.size());
  }

}
