package com.heepay.manage.modules.route.service;

import com.heepay.manage.modules.route.dao.BankInfoDao;
import com.heepay.manage.modules.route.entity.BankInfo;
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
* 描    述：BankInfoService测试类
*
* 创 建 者： L.M
* 创建时间： 2016年9月29日 下午1:48:51 
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

public class BankInfoServiceTest {
    @Autowired
    private BankInfoService bankInfoService;

    @Autowired
    private BankInfoDao bankInfoDao;
    
    private static final Logger logger = LogManager.getLogger();

      
    /**     
    * @discription 添加银行时查询数据库中该信息是否存在
    * @author Administrator       
    * @created 2016年9月29日 下午1:48:56          
    */
    @Test
    public void testSelectByBankN (){
        BankInfo bankInfo= new BankInfo();
        bankInfo.setBankNo("201");
        bankInfo.setBankName("中国人民银行");
        List<BankInfo>bankInfos = bankInfoService.selectByBankN(bankInfo);
        //Assert.assertEquals(bankInfos.size(), 2);
        logger.info(bankInfos);
    }


    /**     
    * @discription 修改银行启用、禁用状态
    * @author Administrator       
    * @created 2016年9月29日 下午1:49:00          
    */
    @Test
    public void testStatus(){
        BankInfo bankInfo=bankInfoService.get("344");
        String string1 = bankInfo.getStatus();
        if ("ENABLE".equals(bankInfo.getStatus())){
            bankInfo.setStatus("DISABL");
        }else {
            bankInfo.setStatus("ENABLE");
        }
        bankInfoService.status(bankInfo);
        String string2 = bankInfo.getStatus();
        //Assert.assertNotEquals(string1, string2);
        logger.info(bankInfo);
    }

      
    /**     
    * @discription 添加
    * @author Administrator       
    * @created 2016年9月29日 下午1:49:04          
    */
    @Test
    public void testSave(){
        BankInfo bankInfo= new BankInfo();
        bankInfo.setBankNo("007");
        bankInfo.setBankName("汇元网");
        bankInfo.setStatus("ENABLE");
        User user= new User();
        user.setId("1");
        bankInfo.setCreateBy(user);
        bankInfo.setUpdateBy(user);
        List<BankInfo> bankInfoInner = bankInfoDao.selectList();
        Integer num =bankInfoInner.size();
        //bankInfoService.save(bankInfo);
        //Assert.assertEquals(bankInfoDao.selectList().size(), num + 1);

    }

      
    /**     
    * @discription 修改
    * @author Administrator       
    * @created 2016年9月29日 下午1:49:09          
    */
    @Test
    public void testUpdate(){
        List<BankInfo> bankInfoInner = bankInfoDao.selectList();
        Integer num1=bankInfoInner.size();
        logger.info(num1);
        BankInfo bankInfo=bankInfoService.get("344");
        bankInfo.setBankCode("PBC");
        User user= new User();
        user.setId("1");
        bankInfo.setUpdateBy(user);
        //bankInfoService.save(bankInfo);
        List<BankInfo> bankInfoInners = bankInfoDao.selectList();
        Integer num2=bankInfoInners.size();
        logger.info(num2);
        //Assert.assertEquals(num1, num2);
    }

      
    /**     
    * @discription 查询
    * @author Administrator       
    * @created 2016年9月29日 下午1:49:13          
    */
    @Test
    public void testSelect(){
        BankInfo bankInfo= new BankInfo();
        //bankInfo.setBankNo("001");
        bankInfo.setBankName("中国");
        List<BankInfo> bankInfos = bankInfoService.findList(bankInfo);
        //Assert.assertEquals(bankInfos.size(),1);
        Assert.assertEquals(bankInfos.size(),10);
        logger.info(bankInfos);
    }

    /**
     * 
     * @方法说明：根据 银行代码查询银行名称
     * @时间：Nov 10, 2016
     * @创建人：wangl
     */
    @Test
    public void testGetBankNameByBankNo(){
    	try {
			BankInfo bankNameByBankNo = bankInfoService.getBankNameByBankNo("105");
			logger.info("查找到的数据---------->",bankNameByBankNo);
			Assert.assertEquals("查找到的数据---------->",bankNameByBankNo);
		} catch (Exception e) {
			logger.info("没有查找到数据-------->",e);
			Assert.assertEquals("没有查找到数据-------->",e);
		}
    }
}
