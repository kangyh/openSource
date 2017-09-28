package com.heepay.manage.modules.tpds.service;

import com.heepay.manage.common.persistence.Page;
import com.heepay.manage.modules.settle.entity.ClearingChannelRecord;
import com.heepay.manage.modules.settle.service.ClearingChannelRecordService;
import com.heepay.manage.modules.tpds.entity.TpdsCustomCharge;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * 描 述：
 * <p>
 * 创 建 者：wangdong
 * 创建时间：2017/6/19 16:03
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
public class TpdsCustomChargeServiceTest extends TestCase {

    @Autowired
    private TpdsCustomChargeService tpdsCustomChargeService;

    //构造方法
    public TpdsCustomChargeServiceTest(String name) {
        super(name);
    }

    //测试套件
    public static void main(String[] args) {
        junit.textui.TestRunner.run(new TestSuite(TpdsCustomChargeServiceTest.class));
    }

    @Test
    public void get() throws Exception {
        try {
            TpdsCustomCharge tpdsCustomCharge = tpdsCustomChargeService.get("1");
            if(null != tpdsCustomCharge){
                Assert.assertEquals("根据主键查询存管充值记录", "成功");
            }else{
                Assert.assertEquals("根据主键查询存管充值记录", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findList(TpdsCustomCharge tpdsCustomCharge) throws Exception {
        try {
            List<TpdsCustomCharge> list = tpdsCustomChargeService.findList(tpdsCustomCharge);
            if(null != list && list.size() > 0){
                Assert.assertEquals("查询存管充值记录", "成功");
            }else{
                Assert.assertEquals("查询存管充值记录", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void findPage(Page<TpdsCustomCharge> page, TpdsCustomCharge tpdsCustomCharge) throws Exception {
        try {
            Page<TpdsCustomCharge> pages = tpdsCustomChargeService.findPage(page,tpdsCustomCharge);
            if(null != pages && pages.getCount() > 0){
                Assert.assertEquals("查询存管充值记录", "成功");
            }else{
                Assert.assertEquals("查询存管充值记录", "失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

    @Test
    public void save(TpdsCustomCharge tpdsCustomCharge) throws Exception {
        try {
            tpdsCustomChargeService.save(tpdsCustomCharge);
            Assert.assertEquals("插入存管充值记录", "成功");
        } catch (Exception e) {
            Assert.assertEquals("插入存管充值记录", "失败");
            e.printStackTrace();
            fail("Not yet implemented");
        }
    }

}