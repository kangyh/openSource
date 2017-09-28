package manage_module;

import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.heepay.codec.CodecException;
import com.heepay.manage.common.utils.JsonMapper;
import com.heepay.manage.modules.merchant.service.MerchantUserCService;
import com.heepay.manage.modules.merchant.vo.MerchantUser;
import com.heepay.redis.JedisClusterUtil;

/**
 * 商户员工功能测试类
 * 
 * @author lgk
 *
 */
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class MerchantUserCServiceTest {

  private static Logger logger = LogManager.getLogger();

  @Autowired
  private MerchantUserCService merchantUserCService;

  // 通过id得到对象
  @Test
  public void testGet() {
    String id = "100003";
    MerchantUser merchantUser = merchantUserCService.get(id);
    //Assert.assertNotNull(merchantUser);
    String merchantJson = JsonMapper.toJsonString(merchantUser);
    logger.info(merchantJson);
  }

  // 查出list
  @Test
  public void testFindList() {
    MerchantUser merchantUser = new MerchantUser();
    List<MerchantUser> findList = merchantUserCService.findList(merchantUser);
    //Assert.assertEquals(findList.size(), 7);
    logger.info(findList.size());
  }

  // 保存
  @Test
  public void testSave() {
    MerchantUser merchantUser = new MerchantUser();
    merchantUser.setLoginName("ttt@9186.com");
    //merchantUserCService.save(merchantUser, false);
    //Assert.assertNotNull(merchantUser);
    logger.info(merchantUser);

  }

  @Test
  public void testSetInfoAuthStatus() {
    String id = "100003";
    MerchantUser merchantUser = merchantUserCService.get(id);
    merchantUserCService.setInfoAuthStatus(merchantUser);
    //Assert.assertNotNull(merchantUser);
    logger.info(merchantUser.getInfoAuthStatus());
  }

  // 设置状态
  @Test
  public void testStatus() {
    String id = "100003";
    MerchantUser merchantUser = merchantUserCService.get(id);
    merchantUser.setStatus("NORMAL");
    merchantUserCService.status(merchantUser);
    //Assert.assertEquals(merchantUser.getStatus(), "NORMAL");
    logger.info(merchantUser.getStatus());

  }

  // 发送邮件
  @Test
  public void testSendMail() throws UnsupportedEncodingException, CodecException {
    String emailAddress = "shixp@9186.com";
    String loginName = "shixp@9186.com";
    String resetWhat = "重置登录密码";
    String sendMail = merchantUserCService.sendMail(emailAddress, loginName, resetWhat);
    //Assert.assertNotNull(sendMail);
    logger.info(sendMail);
  }

  // 验证操作员密码是否正确
  // @Test
  // public boolean testCheckPassword(){
  // User user = UserUtils.getUser();
  // String password ="123456";
  // String encode = Sha.encode(password);
  // boolean checkPassword = merchantUserCService.checkPassword(password);
  // if(SystemService.validatePassword(password, user.getPassword())){
  // return true;
  // }else {
  // return false;
  // }
  // }

  // 邮箱内容
  @Test
  public void testGetEmailContent() {
    String emailAddress = "kkk@9186.com";
    String url = "162.168.4.115";
    String emailContent = merchantUserCService.getEmailContent(emailAddress, url);
    //Assert.assertNotNull(emailContent);
    logger.info(emailContent);

  }

  // 邮箱验证码存入缓存
  @Test
  public void testSetAttibuteForEmailCode() {
    String emailTest = "kkk@9186.com";
    String value = "741963";
    merchantUserCService.setAttibuteForEmailCode(emailTest, value);
    JedisClusterUtil.setJedisValue(setter -> setter.set(emailTest, value));
    String vaule = (String) JedisClusterUtil.getJedisValue(getter -> getter.get(emailTest));
    //Assert.assertNotNull(vaule);
    logger.info(vaule);

  }

  // 加密
  @Test
  public void testEncode() throws CodecException {
    String src = "123456";
    String key = "456a511s";
    String encode = merchantUserCService.encode(src, key);
    //Assert.assertNotNull(encode);
    logger.info(encode);

  }

  // 解绑手机
  @Test
  public void testUnbundling() {
    String id = "100003";
    MerchantUser merchantUser = merchantUserCService.get(id);
    String unbundling = merchantUserCService.unbundling(merchantUser);
    //Assert.assertNotNull(unbundling);
    logger.info(unbundling);
  }

}
