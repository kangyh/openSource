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
import com.heepay.manage.modules.merchant.service.MerchantUnionpayAreaBaseCService;
import com.heepay.manage.modules.merchant.vo.MerchantUnionpayAreaBase;

/**
 * 银联地区编码基础数据测试类
 * 
 * @author lgk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class MerchantUnionpayAreaBaseCServiceTest {

  private static Logger logger = LogManager.getLogger();

  @Autowired
  private MerchantUnionpayAreaBaseCService merchantUnionpayAreaBaseCService;

  // 通过id得到对象
  @Test
  public void testGet() {
    String id = "3";
    MerchantUnionpayAreaBase merchantUnionpayAreaBase = merchantUnionpayAreaBaseCService.get(id);
    //Assert.assertNotNull(merchantUnionpayAreaBase);
    String merchantJson = JsonMapper.toJsonString(merchantUnionpayAreaBase);
    logger.info(merchantJson);

  }

  // 查出list
  @Test
  public void testFindList() {
    MerchantUnionpayAreaBase merchantUnionpayAreaBase = new MerchantUnionpayAreaBase();
    List<MerchantUnionpayAreaBase> findList = merchantUnionpayAreaBaseCService.findList(merchantUnionpayAreaBase);
    //Assert.assertEquals(findList.size(), 329);
    logger.info(findList.size());
  }

  // 保存
  @Test
  public void testSave() {
    MerchantUnionpayAreaBase merchantUnionpayAreaBase = new MerchantUnionpayAreaBase();
    merchantUnionpayAreaBase.setProvinceId("99");
    merchantUnionpayAreaBase.setCityId("999");
    //merchantUnionpayAreaBaseCService.save(merchantUnionpayAreaBase, false);
    //Assert.assertNotNull(merchantUnionpayAreaBase);
    logger.info(merchantUnionpayAreaBase);

  }

  // 查出省
  @Test
  public void testUnionpayP() {
    List<MerchantUnionpayAreaBase> unionpayP = merchantUnionpayAreaBaseCService.UnionpayP();
    //Assert.assertEquals(unionpayP.size(), 31);
    logger.info(unionpayP.size());

  }

  // 查出市
  @Test
  public void testUnionpayCity() {
    String id = "3";
    List<MerchantUnionpayAreaBase> unionpayCity = merchantUnionpayAreaBaseCService.UnionpayCity(id);
    //Assert.assertEquals(unionpayCity.size(), 12);
    logger.info(unionpayCity.size());

  }

}
