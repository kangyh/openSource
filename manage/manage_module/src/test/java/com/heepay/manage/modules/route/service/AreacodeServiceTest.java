package com.heepay.manage.modules.route.service;

import com.heepay.manage.modules.route.entity.Areacode;
import com.heepay.manage.modules.route.entity.HatProvince;
import com.heepay.manage.modules.route.entity.Ubankcode;
import com.heepay.manage.modules.route.service.AreacodeService;
import com.heepay.manage.modules.route.service.BankInfoService;
import com.heepay.manage.modules.route.service.HatProvinceService;
import com.heepay.manage.modules.route.service.UbankcodeService;
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
* 描    述：AreacodeService/HatProvinceService/UbankcodeService测试类
*
* 创 建 者： Administrator  
* 创建时间： 2016年9月29日 下午1:47:46 
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

public class AreacodeServiceTest {
    @Autowired
    private AreacodeService areacodeService;

    @Autowired
    private HatProvinceService hatProvinceService;

    @Autowired
    private UbankcodeService ubankcodeService;

    private static final Logger logger = LogManager.getLogger();

      
    /** 查找省对应的市县-表areacode
    * @discription
    * @author Administrator       
    * @created 2016年9月29日 下午1:47:56          
    */
    @Test
    public void testFindAreaList(){
        String id="340000";
        List<Areacode> areacodes = areacodeService.findList(id);
        //Assert.assertEquals(areacodes.size(),75);
    }

      
    /**     
    * @discription 查找省份-表hat_province
    * @author Administrator       
    * @created 2016年9月29日 下午1:48:10          
    */
    @Test
    public void testFindProvinceList(){
        List<HatProvince> hatProvinces = hatProvinceService.findList();
        //Assert.assertEquals(hatProvinces.size(),34);
    }

      
    /**     
    * @discription 由银行、市县（石家庄市)查找开户银行-表ubankcod
    * @author Administrator       
    * @created 2016年9月29日 下午1:48:14          
    */
    @Test
    public void testFindUbankList(){
        String id="1210";
        String bankNo="310";
        List<Ubankcode> ubankcodes = ubankcodeService.findList(id, bankNo);
        //Assert.assertEquals(ubankcodes.size(),6);
    }


}
