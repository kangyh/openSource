package com.heepay.manage.modules.bossreport.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.bossreport.entity.ReportQueryConditions;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;
import com.heepay.manage.modules.settle.service.ClearingChannelRecordService;
import com.heepay.manage.modules.settle.service.ClearingChannelRecordServiceTest;
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
 * 描 述：BOSS报表条件配置
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/6/19 15:30
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
public class ReportQueryConditionsServiceTest extends TestCase {

    @Autowired
    private ReportQueryConditionsService reportQueryConditionsService;

    //构造方法
    public ReportQueryConditionsServiceTest(String name) {
        super(name);
    }

    //测试套件
    public static void main(String[] args) {
        junit.textui.TestRunner.run(new TestSuite(ReportQueryConditionsServiceTest.class));
    }

    @Test
    public void get() throws Exception {
        try {
            ReportQueryConditions reportQueryConditions = reportQueryConditionsService.get("1");
            if(null != reportQueryConditions){
                Assert.assertEquals("根据主键查询BOSS报表条件配置记录", "成功");
            }else{
                Assert.assertEquals("根据主键查询BOSS报表条件配置记录", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findList(ReportQueryConditions reportQueryConditions) throws Exception {
        try{
            List<ReportQueryConditions> list = reportQueryConditionsService.findList(reportQueryConditions);
            if(null != list && list.size() > 0){
                Assert.assertEquals("根据查询BOSS报表条件配置记录List", "成功");
            }else{
                Assert.assertEquals("根据查询BOSS报表条件配置记录List", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findPage(Page<ReportQueryConditions> page, ReportQueryConditions reportQueryConditions) throws Exception {
        try{
            Page<ReportQueryConditions> pages = reportQueryConditionsService.findPage(page,reportQueryConditions);
            if(null != pages && pages.getCount() > 0){
                Assert.assertEquals("根据查询BOSS报表条件配置记录List", "成功");
            }else{
                Assert.assertEquals("根据查询BOSS报表条件配置记录List", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void save(ReportQueryConditions reportQueryConditions) throws Exception {
        try{
            reportQueryConditionsService.save(reportQueryConditions);
            Assert.assertEquals("根据插入BOSS报表条件配置记录", "成功");
    } catch (Exception e) {
            Assert.assertEquals("根据插入BOSS报表条件配置记录List", "失败");
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findReportQueryConditionsPage(Model model, Page<ReportQueryConditions> page, ReportQueryConditions reportQueryConditions) throws Exception {
        try{
            model = reportQueryConditionsService.findReportQueryConditionsPage(page,reportQueryConditions,model);
            if(model != null){
                Assert.assertEquals("根据查询BOSS报表条件配置记录", "成功");
            }else{
                Assert.assertEquals("根据查询BOSS报表条件配置记录List", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findExistInfo(ReportQueryConditions reportQueryConditions) throws Exception {
        try{
            int count = reportQueryConditionsService.findExistInfo(reportQueryConditions);
            if(count > 0){
                Assert.assertEquals("根据主键查询BOSS报表条件配置记录", "成功");
            }else{
                Assert.assertEquals("根据主键查询BOSS报表条件配置记录List", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

}