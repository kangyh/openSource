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
import com.heepay.manage.modules.merchant.service.MerchantIndustryBaseCService;
import com.heepay.manage.modules.merchant.vo.MerchantIndustryBase;

/**
 * mcc基础数据测试类
 * 
 * @author lgk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class MerchantIndustryBaseCServiceTest {

  private static final Logger logger = LogManager.getLogger();

  @Autowired
  private MerchantIndustryBaseCService merchantIndustryBaseCService;

  // 通过id得到对象
  @Test
  public void testGet() {
    String id = "3";
    MerchantIndustryBase merchantIndustryBase = merchantIndustryBaseCService.get(id);
    //Assert.assertNotNull(merchantIndustryBase);
    String merchantJson = JsonMapper.toJsonString(merchantIndustryBase);
    logger.info(merchantJson);

  }

  // 查出list
  @Test
  public void testFindList() {
    MerchantIndustryBase merchantIndustryBase = new MerchantIndustryBase();
    List<MerchantIndustryBase> findList = merchantIndustryBaseCService.findList(merchantIndustryBase);
    //Assert.assertEquals(findList.size(), 25);
    logger.info(findList.size());
  }

  // 保存
  @Test
  public void testSave() {
    MerchantIndustryBase merchantIndustryBase = new MerchantIndustryBase();
    merchantIndustryBase.setIndustryId("2");
    //merchantIndustryBaseCService.save(merchantIndustryBase, false);
    //Assert.assertNotNull(merchantIndustryBase);
    logger.info(merchantIndustryBase);

  }

  // 查出mcc基础数据list
  @Test
  public void testGetIndustry() {
    List<MerchantIndustryBase> industry = merchantIndustryBaseCService.getIndustry();
    //Assert.assertNotNull(industry);
    logger.info(industry);

  }

  // 查出行业子类
  @Test
  public void testIndustryChild() {
    String id = "1";
    List<MerchantIndustryBase> industry = merchantIndustryBaseCService.industryChild(id);
    //Assert.assertEquals(industry.size(), 12);
    logger.info(industry.size());

  }

  // 得到list mcc
  @Test
  public void testIndustryMcc() {
    String id = "2";
    List<MerchantIndustryBase> industryMcc = merchantIndustryBaseCService.industryMcc(id);
    //Assert.assertEquals(industryMcc.size(), 2);
    logger.info(industryMcc.size());

  }

  // 得到mcc码
  @Test
  public void testGetMcc() {

    String id = "7295";
    MerchantIndustryBase mcc = merchantIndustryBaseCService.getMcc(id);
    //Assert.assertNotNull(mcc);
    String merchantJson = JsonMapper.toJsonString(mcc);
    logger.info(merchantJson);

  }

}
