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

import com.heepay.manage.modules.merchant.service.MerchantEmployeeCService;

import com.heepay.manage.modules.merchant.vo.MerchantEmployee;

/**
 * 商户员工测试类
 * 
 * @author lgk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class MerchantEmployeeCServiceTest {

  private static Logger log = LogManager.getLogger();

  @Autowired
  private MerchantEmployeeCService merchantEmployeeCService;

  // 保存
  @Test
  public void testSave() {
    MerchantEmployee merchantEmployee = new MerchantEmployee();
    merchantEmployee.setUserId("100256");
    merchantEmployee.setMerchantId("100334");
    //merchantEmployeeCService.save(merchantEmployee, false);
    //Assert.assertNotNull(merchantEmployee);
    log.info(merchantEmployee);

  }

  // @Test
  // public void testGet(){
  // String id ="6";
  // MerchantEmployee merchantEmployee = merchantEmployeeCService.get(id);
  // Assert.assertNotNull(merchantEmployee);
  // String merchantJson = JsonMapper.toJsonString(merchantEmployee);
  // log.info(merchantJson);
  //
  // }

  // 查出list
  @Test
  public void testFindList() {
    MerchantEmployee merchantEmployee = new MerchantEmployee();
    List<MerchantEmployee> findList = merchantEmployeeCService.findList(merchantEmployee);
    //Assert.assertEquals(findList.size(), 1);
    log.info(findList.size());
  }

}
