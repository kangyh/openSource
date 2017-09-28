package com.heepay.boss.service.rpc;

import com.heepay.common.util.StringUtils;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * 描 述：BOSS报表服务
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/6/19 16:27
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring.xml")
public class BossReportDateInfoServiceImplTest extends TestCase {

    @Autowired
    private BossReportDateInfoServiceImpl bossReportDateInfoServiceImpl;

    //构造方法
    public BossReportDateInfoServiceImplTest(String name) {
        super(name);
    }

    //测试套件
    public static void main(String[] args) {
        junit.textui.TestRunner.run(new TestSuite(BossReportDateInfoServiceImplTest.class));
    }

    @Test
    public void getbossReportDateInfoList(String params) throws Exception {
        try {
            String ret = bossReportDateInfoServiceImpl.getbossReportDateInfoList(params);
            if(StringUtils.isNotBlank(ret)){
                Assert.assertEquals("BOSS报表list服务","成功");
            }else{
                Assert.assertEquals("BOSS报表list服务","失败");
            }
        } catch (Exception e) {
            fail("Not yet implemented");
        }
    }

    @Test
    public void getbossReportDateInfoDetail(String params) throws Exception {
        try {
            String ret = bossReportDateInfoServiceImpl.getbossReportDateInfoDetail(params);
            if(StringUtils.isNotBlank(ret)){
                Assert.assertEquals("BOSS报表记录详情服务","成功");
            }else{
                Assert.assertEquals("BOSS报表记录详情服务","失败");
            }
        } catch (Exception e) {
            fail("Not yet implemented");
        }
    }

    @Test
    public void getbossReportDateInfoTotal(String params) throws Exception {
        try {
            String ret = bossReportDateInfoServiceImpl.getbossReportDateInfoTotal(params);
            if(StringUtils.isNotBlank(ret)){
                Assert.assertEquals("BOSS报表数据统计服务","成功");
            }else{
                Assert.assertEquals("BOSS报表数据统计服务","失败");
            }
        } catch (Exception e) {
            fail("Not yet implemented");
        }
    }

}