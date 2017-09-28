package com.heepay.manage.modules.route.service;

import com.heepay.manage.modules.route.entity.LineBankNumber;
import com.heepay.manage.modules.route.service.LineBankNumberService;
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
* 描    述：LineBankNumberService测试类
*
* 创 建 者： L.M
* 创建时间： 2016年9月29日 下午1:50:36 
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

public class LineBankNumberServiceTest {
    @Autowired
    private LineBankNumberService lineBankNumberService;

    private static final Logger logger = LogManager.getLogger();

      
    /**     
    * @discription 根据联行号查询对象是否存在
    * @author Administrator       
    * @created 2016年9月29日 下午1:50:41          
    */
    @Test
    public void testSelectByLineNumber(){
        String associateLineNumber="103169086307";
        LineBankNumber lineBankNumber=lineBankNumberService.selectByLineNumber(associateLineNumber);
        //Assert.assertEquals(lineBankNumber.getAssociateLineNumber(),associateLineNumber);
        logger.info(lineBankNumber);
    }

      
    /**     
    * @discription 保存
    * @author Administrator       
    * @created 2016年9月29日 下午1:50:45          
    */
    @Test
    public void testSave(){
        LineBankNumber lineBankNumber=new LineBankNumber();
        /*lineBankNumber.setBankNo("001");
        lineBankNumber.setBankName("中国人民银行");
        lineBankNumber.setProvinceCode("110000");
        lineBankNumber.setProvinceName("北京市");
        lineBankNumber.setCityCode("1000");
        lineBankNumber.setCityName("北京市");
        lineBankNumber.setOpenBankName("人民银行营业管理部营业室");
        lineBankNumber.setOpenBankCode("11002");
        lineBankNumber.setAssociateLineNumber("001100011002");*/
        lineBankNumber.setBankName("TEST");
        lineBankNumber.setAssociateLineNumber("1111");
        User user = new User();
        user.setId("1");
        lineBankNumber.setCreateBy(user);
        lineBankNumber.setUpdateBy(user);
        //lineBankNumberService.save(lineBankNumber,false);
    }

      
    /**     
    * @discription 删除
    * @author Administrator       
    * @created 2016年9月29日 下午1:50:49          
    */
    @Test
    public void testDelete(){
        String associateLineNumber="1111";
        LineBankNumber lineBankNumber=lineBankNumberService.selectByLineNumber(associateLineNumber);
        //lineBankNumberService.delete(lineBankNumber);
    }

      
    /**     
    * @discription 查询
    * @author Administrator       
    * @created 2016年9月29日 下午1:50:53          
    */
    @Test
    public void testSelect(){
        LineBankNumber lineBankNumber=new LineBankNumber();
        lineBankNumber.setBankNo("306");
        lineBankNumber.setProvinceCode("110000");
        lineBankNumber.setCityCode("1000");
        List<LineBankNumber> lineBankNumbers=lineBankNumberService.findList(lineBankNumber);
        //Assert.assertEquals(lineBankNumbers.size(),18);
        Assert.assertEquals(lineBankNumbers.size(),47);
    }

}
