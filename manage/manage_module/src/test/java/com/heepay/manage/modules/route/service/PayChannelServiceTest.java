package com.heepay.manage.modules.route.service;

import com.heepay.common.util.JsonMapperUtil;
import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.common.utils.JsonMapper;
import com.heepay.manage.modules.route.dao.PayChannelDao;
import com.heepay.manage.modules.route.entity.ChannelRedisVO;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.route.service.PayChannelService;
import com.heepay.manage.modules.sys.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**          
* 
* 描    述：PayChannelService测试类
*
* 创 建 者： L.M
* 创建时间： 2016年9月29日 下午1:51:00 
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

public class PayChannelServiceTest {
    @Autowired
    private PayChannelService payChannelService;

    @Autowired
    private PayChannelDao payChannelDao;
    
    private static final Logger logger = LogManager.getLogger();

      
    /**     
    * @discription 由通道代码查找通道是否存在
    * @author Administrator       
    * @created 2016年9月29日 下午1:51:05          
    */
    @Test
    public void testSelectByChannelCode(){
        String ChannelCode="319CHGPAYB2CEBCREDITPUBLIC";
        PayChannel payChannel=payChannelService.selectByChannelCode(ChannelCode);
        //Assert.assertEquals(payChannel.getId(), "147");
    }

      
    /**     
    * @discription 当前支付通道的业务类型为本行时，查找由银行，通道类型，卡类型、对公对私标识四者确定的所有通道
    * @author Administrator       
    * @created 2016年9月29日 下午1:51:09          
    */
    @Test
    public void testSelectSortChannelOwnbak(){
        PayChannel payChannel=new PayChannel();
        payChannel.setBankNo("310");
        payChannel.setChannelTypeCode("QUICKP");
        payChannel.setAccountType("PUBLIC");
        payChannel.setCardTypeCode("CREDIT");
        payChannel.setBusinessType("OWNBAK");
        List<PayChannel> payChannels = payChannelDao.selectSortChannelOwnbak(payChannel);
        //Assert.assertEquals(payChannels.size(),3);
        logger.info(payChannels);
    }

      
    /**     
    * @discription 当前支付通道的业务类型为跨行时，查找由通道类型，卡类型、对公对私标识三者确定的所有通道
    * @author Administrator       
    * @created 2016年9月29日 下午1:51:16          
    */
    @Test
    public void testSelectSortChannelSpnbak(){
        PayChannel payChannel=new PayChannel();
        payChannel.setBankNo("310");
        payChannel.setChannelTypeCode("QUICKP");
        payChannel.setAccountType("PUBLIC");
        payChannel.setCardTypeCode("CREDIT");
        payChannel.setBusinessType("SPNBAK");
        List<PayChannel> payChannels = payChannelDao.selectSortChannelSpnbak(payChannel);
        //Assert.assertEquals(payChannels.size(),4);
        logger.info(payChannels);
        logger.info(payChannels);
    }

      
    /**     
    * @discription 修改优先级
    * @author Administrator       
    * @created 2016年9月29日 下午1:51:29          
    */
    @Test
    public void testChangeSort(){
        String ChannelCode="319CHGPAYB2CEBCREDITPUBLIC";
        PayChannel payChannel=payChannelService.selectByChannelCode(ChannelCode);
        String string1=payChannel.getSort();
        if("1".equals(string1)){
            payChannel.setSort(null);
        }else{
            payChannel.setSort("1");
        }
        //只对sort这一个字段进行update操作
        payChannelDao.changeSort(payChannel);
        PayChannel payChannel2=payChannelService.selectByChannelCode(ChannelCode);
        String string2=payChannel2.getSort();
        //Assert.assertNotEquals(string1, string2);

    }

      
    /**     
    * @discription 当前支付通道的业务类型为本行时，查找由银行，通道类型，卡类型、对公对私标识四者确定的所有通道只有一条默认通道
    * @author Administrator       
    * @created 2016年9月29日 下午1:51:36          
    */
    @Test
    public void testSetChannelSort(){
        PayChannel payChannel= new PayChannel();
        payChannel.setBankNo("631");
        payChannel.setChannelPartnerCode("DIRCON");
        payChannel.setCardTypeCode("CREDIT");
        payChannel.setChannelTypeCode("SIGN");
        payChannel.setAccountType("PUBLIC");
        payChannel.setBusinessType("OWNBAK");
        //payChannel.setBusinessType("SPNBAK");
        payChannel.setSettleType("RETIME");
        payChannel.setSettlePeriod("SEASON");//为空
        //payChannel.setSettleType("PERIOD");
        payChannel.setCostType("RATIOD");
        payChannel.setCostCount("1");//为空
        payChannel.setCostRate("2");
        //payChannel.setCostType("COUNT");
        payChannel.setSort("1");
        payChannelService.setChannelSort(payChannel);
        /*Assert.assertNull(payChannel.getSettlePeriod());
        Assert.assertNull(payChannel.getCostCount());
        Assert.assertNull(payChannelService.selectByChannelCode("631PAYNOWSIGNCREDITPUBLICOWNBAK").getSort());
        Assert.assertNull(payChannelService.selectByChannelCode("631PAYEASSIGNCREDITPUBLICOWNBAK").getSort());*/
    }

      
    /**     
    * @discription 实现条件查询支付通道并且根据产品代码区分通道是否已添加
    * @author Administrator       
    * @created 2016年9月29日 下午1:51:42          
    */
    @Test
    public void testFindProductChannelPage(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("productCode", "CP01");
        map.put("bankNo","525");
        /*map.put("channelPartnerCode", "DIRCON");
        map.put("channelTypeCode", "QUICKP");
        map.put("cardTypeCode", "CREDIT");*/
        Page<PayChannel> page  = payChannelService.findProductChannelPage(new Page<PayChannel>(),map);
        //Assert.assertEquals(page.getList().size(),1);
    }

      
    /**     
    * @discription 构建通道代码
    * @author Administrator       
    * @created 2016年9月29日 下午1:51:46          
    */
    @Test
    public void testCombineChannelCode(){
        PayChannel payChannel=new PayChannel();
        payChannel.setBankNo("631");
        payChannel.setChannelPartnerCode("DIRCON");
        payChannel.setCardTypeCode("CREDIT");
        payChannel.setChannelTypeCode("SIGN");
        payChannel.setAccountType("PUBLIC");
        payChannel.setBusinessType("OWNBAK");
        payChannelService.combineChannelCode(payChannel);
        logger.info(payChannel.getChannelCode());

    }

      
    /**     
    * @discription 支付通道的业务类型、账户类型由值置内容
    * @author Administrator       
    * @created 2016年9月29日 下午1:51:50          
    */
    @Test
    public void testChangeValue(){
        PayChannel payChannel=new PayChannel();
        //payChannel.setAccountType("PUBLIC");
        payChannel.setAccountType("PRIVAT");
        //payChannel.setBusinessType("OWNBAK");
        payChannel.setBusinessType("SPNBAK");
        payChannelService.changeValue(payChannel);
        logger.info(payChannel.getAccountType()+payChannel.getBusinessType());
    }

      
    /**     
    * @discription 支付通道状态转换
    * @author Administrator       
    * @created 2016年9月29日 下午1:51:55          
    */
    @Test
    public void testStatus(){
        String ChannelCode="319CHGPAYB2CEBCREDITPUBLIC";
        PayChannel payChannel=payChannelService.selectByChannelCode(ChannelCode);
        String status=payChannel.getStatus();
        if("DISABL".equals(status)){
            payChannel.setStatus("ENABLE");
        }else{
            payChannel.setStatus("DISABL");
        }
        payChannelService.status(payChannel);
        PayChannel payChannel1=payChannelService.selectByChannelCode(ChannelCode);
        //Assert.assertNotEquals(status, payChannel1.getStatus());
    }

      
    /**     
    * @discription 查询
    * @author Administrator       
    * @created 2016年9月29日 下午1:51:59          
    */
    @Test
    public void testSelect(){
        PayChannel payChannel=new PayChannel();
        payChannel.setBankNo("310");
        payChannel.setChannelPartnerCode("EBANK");
        payChannel.setChannelTypeCode("BATCHP");
        payChannel.setCardTypeCode("CREDIT");
        List<PayChannel> payChannels = payChannelService.findList(payChannel);
        //Assert.assertEquals(payChannels.size(), 2);
    }

      
    /**     
    * @discription 保存
    * @author Administrator       
    * @created 2016年9月29日 下午1:52:03          
    */
    @Test
    public void testSave(){
        PayChannel payChannel=new PayChannel();
        //payChannel.setChannelCode("TEST");
        payChannel.setChannelName("TEST");
        User user= new User();
        user.setId("1");
        payChannel.setCreateBy(user);
        payChannel.setUpdateBy(user);
        //payChannelService.save(payChannel, false);
    }

      
    /**     
    * @discription 查询产品通道中状态为启用且在有效期内的通道
    * @author Administrator       
    * @created 2016年9月29日 下午1:52:07          
    */
    @Test
    public void testSelectChannel(){
        List<ChannelRedisVO> channelRedisVOs=payChannelService.selectChannel();
        logger.info(channelRedisVOs);
        logger.info(channelRedisVOs.size());
    }


    //查找成本类型为按比例最优通道方法
    @Test
    public void testSelectRateList(){
        List<ChannelRedisVO> ChannelRedisVOs=payChannelService.selectRateList();
        logger.info(ChannelRedisVOs.size());
    }

    //查找成本类型为按笔数最优通道方法
    @Test
    public void testSelectCountList(){
        List<ChannelRedisVO> ChannelRedisVOs=payChannelService.selectCountList();
        logger.info(ChannelRedisVOs.size());
    }

    //查找跨行通道 (按比例)
    @Test
    public void testSelectSpnChannel(){
        List<ChannelRedisVO> ChannelRedisVOs=payChannelService.selectSpnChannel();
        logger.info(ChannelRedisVOs.size());
    }

    //查找跨行通道 (按笔数)
    @Test
    public void testSelectSpnChannelCount(){
        List<ChannelRedisVO> ChannelRedisVOs=payChannelService.selectSpnChannelCount();
        logger.info(ChannelRedisVOs.size());
    }

    //查找每种通道类型对应的银行及卡类型
    @Test
    public void testSelectChannelType(){
        List<PayChannel> payChannels=payChannelService.selectChannelType();
        logger.info(payChannels.size());
    }
    
    @Test
    public void test(){
        PayChannel payChannel = payChannelService.selectByChannelCode("308BILPAYQUICKPSAVINGCOMMONOWNBAK");
        payChannel.setOrderSettlePeriod("1");
        System.out.println(JsonMapper.toJsonString(payChannel));
    }
    
    @Test
    public void testFindListByBankNoAndChannelTypeCode(){
        PayChannel payChannelFind = new PayChannel();
        payChannelFind.setBankNo("105");
        payChannelFind.setChannelTypeCode("QUICKP");
        List<PayChannel> payChannels = payChannelService.findListByBankNoAndChannelTypeCode(payChannelFind);
        System.out.println(new JsonMapperUtil().toJson(payChannels));
    }
}
