package com.heepay.manage.modules.risk.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.bossreport.entity.ReportQueryConditions;
import com.heepay.manage.modules.bossreport.service.ReportQueryConditionsService;
import com.heepay.manage.modules.risk.entity.SettleIncomeInfo;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 描 述：
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/6/19 15:45
 * 创建描述：
 * <p>
 * 修 改 者：
 * 修改时间：
 * 修改描述：
 * <p>
 * 审 核 者：
 * 审核时间：
 * 审核描述：
 */
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class SettleIncomeInfoServiceTest extends TestCase {

    @Autowired
    private SettleIncomeInfoService settleIncomeInfoService;

    //构造方法
    public SettleIncomeInfoServiceTest(String name) {
        super(name);
    }

    //测试套件
    public static void main(String[] args) {
        junit.textui.TestRunner.run(new TestSuite(SettleIncomeInfoServiceTest.class));
    }

    @Test
    public void get() throws Exception {
        try {
            SettleIncomeInfo settleIncomeInfo = settleIncomeInfoService.get("1");
            if(null != settleIncomeInfo){
                Assert.assertEquals("根据主键查询出入金配置记录", "成功");
            }else{
                Assert.assertEquals("根据主键查询出入金配置记录", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findList(SettleIncomeInfo settleIncomeInfo) throws Exception {
        try {
            List<SettleIncomeInfo> list = settleIncomeInfoService.findList(settleIncomeInfo);
            if(null != list && list.size() > 0){
                Assert.assertEquals("查询出入金配置记录", "成功");
            }else{
                Assert.assertEquals("查询出入金配置记录", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void save(SettleIncomeInfo settleIncomeInfo) throws Exception {
        try {
            settleIncomeInfoService.save(settleIncomeInfo);
            Assert.assertEquals("插入出入金配置记录", "成功");
        } catch (Exception e) {
            Assert.assertEquals("插入出入金配置记录", "失败");
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findSettleIncomeInfoPage(Page<SettleIncomeInfo> page, SettleIncomeInfo settleIncomeInfo, Model model) throws Exception {
        try {
            model = settleIncomeInfoService.findSettleIncomeInfoPage(page,settleIncomeInfo,model);
            if(null != model){
                Assert.assertEquals("查询出入金配置记录", "成功");
            }else{
                Assert.assertEquals("查询出入金配置记录", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findExistInfo(SettleIncomeInfo settleIncomeInfo) throws Exception {
        try {
            int count = settleIncomeInfoService.findExistInfo(settleIncomeInfo);
            if(count > 0){
                Assert.assertEquals("查询出入金配置记录", "成功");
            }else{
                Assert.assertEquals("查询出入金配置记录", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

}