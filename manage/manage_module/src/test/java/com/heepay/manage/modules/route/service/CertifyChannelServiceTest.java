package com.heepay.manage.modules.route.service;

import com.heepay.manage.modules.route.dao.CertifyChannelDao;
import com.heepay.manage.modules.route.entity.CertifyChannel;
import com.heepay.manage.modules.route.service.CertifyChannelService;
import com.heepay.manage.modules.sys.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**          
* 
* 描    述：CertifyChannelService测试类

* 创 建 者： L.M
* 创建时间： 2016年9月29日 下午1:50:15 
* 创建描述：
* 
* 修 改 者：  
* 修改时间： 
* 修改描述： 
* 
* 审 核 者：
* 审核时间：
* 审核描述：
*
*/      
    
@RunWith(SpringJUnit4ClassRunner.class) //用于配置spring中测试的环境
@ContextConfiguration(locations = {"classpath*:/spring-context.xml"}) //用于指定配置文件所在的位置

public class CertifyChannelServiceTest {
    @Autowired
    private CertifyChannelService certifyChannelService;
    
    @Autowired
    private CertifyChannelDao certifyChannelDao;

    private static final Logger logger = LogManager.getLogger();

      
    /**     
    * @discription 查找表route_cetify_channel中通道信息是否存在
    * @author Administrator       
    * @created 2016年9月29日 下午1:50:20          
    */
    @Test
    public void  testSelectByPartnerCode(){
        //String channelPartnerCode="TOIPO";
        String channelPartnerCode="LAKALA";
        String productCode = "CP25";
        String channelTypeCode = "E";
        CertifyChannel certifyChannelNew=certifyChannelService.selectByPartnerCode(channelPartnerCode,productCode,channelTypeCode);
        //Assert.assertNotNull(certifyChannelNew);
        logger.info(certifyChannelNew);

    }

      
    /**     
    * @discription 构建实名认证通道信息
    * @author Administrator       
    * @created 2016年9月29日 下午1:50:24          
    */
    @Test
    public void testCreateCertifyDetail(){
        CertifyChannel certifyChannel=new CertifyChannel();
        String ProductId="31";
        certifyChannel.setChannelPartnerCode("POLICE");
        certifyChannel.setCost("11");
        //certifyChannel.setSettleType("RETIME");
        certifyChannel.setSettleType("PERIOD");
        certifyChannel.setSettlePeriod("T+1");
        certifyChannel.setSort("1");
        CertifyChannel certifyChannel1=certifyChannelService.combineCertifyDetail(ProductId, certifyChannel);
        logger.info(certifyChannel1);
    }
      
    /**     
    * @discription 保存
    * @author Administrator       
    * @created 2016年9月29日 下午1:50:29          
    */
    @Test
    public void testSave(){
        CertifyChannel certifyChannel=new CertifyChannel();
        String ProductId="33";
        certifyChannel.setChannelPartnerCode("TOIPO");
        certifyChannel.setCost("12");
        //certifyChannel.setSettleType("RETIME");
        certifyChannel.setSettleType("PERIOD");
        certifyChannel.setSettlePeriod("T+1");
        certifyChannel.setSort("1");
        User user=new User();
        user.setId("1");
        certifyChannel.setCreateBy(user);
        certifyChannel.setUpdateBy(user);
        CertifyChannel certifyChannel1=certifyChannelService.combineCertifyDetail(ProductId, certifyChannel);
        //certifyChannelService.save(certifyChannel1,false);
        logger.info(certifyChannel1);
    }
    
    @Test
    public void testGetOrderBySort(){
      System.out.println(certifyChannelDao.getOrderBySort("CP15").get(0).getChannelCode());
    }

}
