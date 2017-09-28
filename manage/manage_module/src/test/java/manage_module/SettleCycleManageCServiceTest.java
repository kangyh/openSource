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
import com.heepay.manage.modules.merchant.service.SettleCycleManageCService;
import com.heepay.manage.modules.merchant.vo.SettleCycleManage;
import com.heepay.manage.modules.sys.entity.User;

/**
 * 结算周期管理测试类
 * 
 * @author lgk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class SettleCycleManageCServiceTest {

  private static Logger logger = LogManager.getLogger();

  @Autowired
  private SettleCycleManageCService settleCycleManageCService;

  // 通过id得到对象
  @Test
  public void testGet() {
    String id = "15";
    SettleCycleManage settleCycleManage = settleCycleManageCService.get(id);
    //Assert.assertNotNull(settleCycleManage);
    String merchantJson = JsonMapper.toJsonString(settleCycleManage);
    logger.info(merchantJson);

  }

  // 查出list
  @Test
  public void testFindList() {
    SettleCycleManage settleCycleManage = new SettleCycleManage();
    List<SettleCycleManage> findList = settleCycleManageCService.findList(settleCycleManage);
    //Assert.assertEquals(findList.size(), 4);
    logger.info(findList.size());
  }

  // 保存
  @Test
  public void testSave() {
    SettleCycleManage settleCycleManage = new SettleCycleManage();
    User user = new User();
    settleCycleManage.setCreateBy(user);
    settleCycleManage.setUpdateBy(user);
    settleCycleManage.setMerchantId("100005");
    //settleCycleManageCService.save(settleCycleManage, false);
    //Assert.assertNotNull(settleCycleManage);
    logger.info(settleCycleManage);

  }

  // 状态
  @Test
  public void testStatus() {
    String id = "7";
    SettleCycleManage settleCycleManage = settleCycleManageCService.get(id);
    settleCycleManage.setStatus("DISABL");// ENABLE
    settleCycleManageCService.status(settleCycleManage);
    //Assert.assertEquals(settleCycleManage.getStatus(), "DISABL");
    logger.info(settleCycleManage.getStatus());

  }

}
