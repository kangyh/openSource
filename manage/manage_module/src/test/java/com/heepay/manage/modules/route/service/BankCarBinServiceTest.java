package com.heepay.manage.modules.route.service;

import com.heepay.common.util.BankCarBinUtil;
import com.heepay.manage.modules.route.entity.BankCardBin;
import com.heepay.manage.modules.route.service.BankCardBinService;
import com.heepay.manage.modules.sys.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**          
* 
* 描    述：BankCarBinServiceTest
*
* 创 建 者： L.GK
* 创建时间： 2016年9月29日 下午1:48:23 
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

public class BankCarBinServiceTest {

    @Autowired
    private BankCardBinService bankCardBinService;

    private static final Logger logger = LogManager.getLogger();


    /**     
    * @discription 根据银行卡号返回对应的银行类型和名称
    * @author Administrator       
    * @created 2016年9月29日 下午1:48:29          
    */
    @Test
    public void testBankCarbin() {

        logger.info(BankCarBinUtil.getNameOfBank("8430100000000000"));
        logger.info(BankCarBinUtil.getNameOfBank("6217310000000000"));
        logger.info(BankCarBinUtil.getNameOfBank("5491031000000000"));
        logger.info(BankCarBinUtil.getNameOfBank("6221505100000000"));
    }

      
    /**     
    * @discription 保存
    * @author Administrator       
    * @created 2016年9月29日 下午1:48:33          
    */
    @Test
    public void testSave(){
        BankCardBin bankCardBin=new BankCardBin();
        /*bankCardBin.setBankcardName("中国农业银行");
        bankCardBin.setBankcardNote("620501");
        bankCardBin.setBankcardNoteLength("6");
        bankCardBin.setBankcardLength("19");
        bankCardBin.setCardType("SAVING");
        bankCardBin.setStatus("ENABLE");*/
        bankCardBin.setBankcardName("TEST");
        bankCardBin.setBankcardNote("1111");
        User user=new User();
        user.setId("1");
        bankCardBin.setCreateBy(user);
        bankCardBin.setUpdateBy(user);
        //bankCardBinService.save(bankCardBin,false);
    }

      
    /**     
    * @discription 删除
    * @author Administrator       
    * @created 2016年9月29日 下午1:48:37          
    */
    @Test
    public void testDelete(){
        BankCardBin bankCardBin=new BankCardBin();
        bankCardBin.setBankcardNote("1111");
        List<BankCardBin> bankCardBins =bankCardBinService.findList(bankCardBin);
        logger.info(bankCardBins);
        //bankCardBinService.delete(bankCardBins.get(0));
    }

      
    /**     
    * @discription 查询
    * @author Administrator       
    * @created 2016年9月29日 下午1:48:41          
    */
    @Test
    public void testSelect(){
        BankCardBin bankCardBin=new BankCardBin();
        bankCardBin.setBankcardNote("427020");
        /*bankCardBin.setCardName("牡丹运通卡金卡");
        bankCardBin.setCardType(null);*/
        List <BankCardBin> bankCardBins = bankCardBinService.findList(bankCardBin);
        //Assert.assertNotNull(bankCardBins);
        //Assert.assertEquals(bankCardBins.size(),1);
    }

}
