package risk_es_engine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.heepay.risk_es_engine.ESearchSysLogService;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年3月17日 上午10:51:05
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
public class TestSysLogServer {
	ESearchSysLogService essearch = null;
	

	@Before
	public void initESClient() {
		essearch = new ESearchSysLogService();
		essearch.initESClient();
	}
	@After
	public void closeESClient() {
		essearch.closeESClient();
	}
	/**
	 * 测试日志结果
	 */
	@Test
	public void getSysLogInfo()
	{
		essearch.getSysErrorLogByTimeSpan("");
	}
}
