package com.heepay.manage.modules.risk.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.bossreport.entity.ReportQueryConditions;
import com.heepay.manage.modules.bossreport.service.ReportQueryConditionsService;
import com.heepay.manage.modules.bossreport.service.ReportQueryConditionsServiceTest;
import com.heepay.manage.modules.risk.entity.RiskIncomeQuota;
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
 * 描 述：出入金限额配置
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/6/19 15:44
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
public class RiskIncomeQuotaServiceTest extends TestCase {

    @Autowired
    private RiskIncomeQuotaService riskIncomeQuotaService;

    //构造方法
    public RiskIncomeQuotaServiceTest(String name) {
        super(name);
    }

    //测试套件
    public static void main(String[] args) {
        junit.textui.TestRunner.run(new TestSuite(RiskIncomeQuotaServiceTest.class));
    }

    @Test
    public void get() throws Exception {
        try {
            RiskIncomeQuota riskIncomeQuota = riskIncomeQuotaService.get("1");
            if(null != riskIncomeQuota){
                Assert.assertEquals("根据主键查询出入金限额配置", "成功");
            }else{
                Assert.assertEquals("根据主键查询出入金限额配置", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findList(RiskIncomeQuota riskIncomeQuota) throws Exception {
        try {
            List<RiskIncomeQuota> list = riskIncomeQuotaService.findList(riskIncomeQuota);
            if(null != list && list.size() > 0){
                Assert.assertEquals("查询出入金限额配置", "成功");
            }else{
                Assert.assertEquals("查询出入金限额配置", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findPage(Page<RiskIncomeQuota> page, RiskIncomeQuota riskIncomeQuota) throws Exception {
        try {
            Page<RiskIncomeQuota> pages = riskIncomeQuotaService.findPage(page,riskIncomeQuota);
            if(null != pages && pages.getCount() > 0){
                Assert.assertEquals("根据主键查询出入金限额配置", "成功");
            }else{
                Assert.assertEquals("根据主键查询出入金限额配置", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void save(RiskIncomeQuota riskIncomeQuota) throws Exception {
        try {
            riskIncomeQuotaService.save(riskIncomeQuota);
            Assert.assertEquals("插入出入金限额配置", "成功");
        } catch (Exception e) {
            Assert.assertEquals("插入出入金限额配置", "失败");
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findRiskIncomeQuotaPage(Page<RiskIncomeQuota> page, RiskIncomeQuota riskIncomeQuota, Model model) throws Exception {
        try {
            model = riskIncomeQuotaService.findRiskIncomeQuotaPage(page,riskIncomeQuota,model);
            if(null != model){
                Assert.assertEquals("查询出入金限额配置", "成功");
            }else{
                Assert.assertEquals("查询出入金限额配置", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findExistInfo(RiskIncomeQuota riskIncomeQuota) throws Exception {
        try {
            int count = riskIncomeQuotaService.findExistInfo(riskIncomeQuota);
            if(count > 0){
                Assert.assertEquals("查询出入金限额配置", "成功");
            }else{
                Assert.assertEquals("查询出入金限额配置", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

}