/**
 * Copyright © since 2008. All Rights Reserved
 * 汇元银通（北京）在线支付技术有限公司   www.heepay.com
 */
package manage_module;

import com.fasterxml.jackson.databind.JavaType;
import com.heepay.codec.Md5;
import com.heepay.common.util.JsonMapperUtil;
import com.heepay.manage.common.utils.HttpClientUtil;
import com.heepay.manage.modules.route.entity.IntegrationChannel;
import com.heepay.manage.modules.route.entity.PayChannel;
import com.heepay.manage.modules.route.service.ChannelBankidService;
import com.heepay.manage.modules.route.service.IntegrationChannelService;
import com.heepay.redis.JedisClusterUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 描    述：
 *
 * 创 建 者： M.Z
 * 创建时间： 2017/4/21 14:12 
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
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境
@ContextConfiguration(locations = { "classpath*:/spring-context.xml" }) // 用于指定配置文件所在的位置
public class BankIdSyncTest {

	@Autowired
	private IntegrationChannelService integrationChannelService;
	@Autowired
	private ChannelBankidService channelBankidService;

	@Test
	public void testSync(){
		HttpClientUtil httpClientUtil = HttpClientUtil.getInstance();
		String ss = Md5.encode("MIID6AYKKoEcz1UGAQQCA6CCA9gwgg99");
		String xx = httpClientUtil.sendHttpGet("http://211.103.157.45/PayHeepay/API/Inner/GetChannel.aspx?sign=" + ss);
		System.out.println("=============================="+xx);
		JavaType javaType = JsonMapperUtil.nonEmptyMapper().createCollectionType(ArrayList.class,IntegrationChannel.class);
		List<IntegrationChannel> integrationChannels = JsonMapperUtil.nonEmptyMapper().fromJson(xx,javaType);
		for (IntegrationChannel integrationChannel : integrationChannels){
			if (null!=integrationChannel.getBankId()&&integrationChannel.getBankId().equals("WeiXinProCITIC113")){
				System.out.println("===================================" + integrationChannel.getChannelKey());
			}
		}

	}

	@Test
	public void testJsonPase(){
		List<PayChannel> payChannels = new ArrayList<>();
		PayChannel payChannel1 = new PayChannel();
		payChannel1.setChannelCode("1");
		payChannel1.setAccountType("2");
		payChannel1.setBankName("3");
		PayChannel payChannel2 = new PayChannel();
		payChannel2.setChannelCode("4");
		payChannel2.setAccountType("5");
		payChannel2.setBankName("6");
		payChannels.add(payChannel1);
		payChannels.add(payChannel2);

		String json = JsonMapperUtil.nonEmptyMapper().toJson(payChannels);
		System.out.println("==================="+json);


		JavaType javaType = JsonMapperUtil.nonEmptyMapper().createCollectionType(ArrayList.class,PayChannel.class);
		List<PayChannel> payChanneln = JsonMapperUtil.nonEmptyMapper().fromJson(json,javaType);
		int i = 0;
		for (PayChannel payChannel : payChanneln){
			System.out.println("=============" + i + payChannel );
			i++;
		}
	}

	@Test
	public void testDD(){
		JedisCluster jedisCluster = JedisClusterUtil.getJedisCluster();
		String ss = jedisCluster.get("WeiXin_WeiXinCITIC_All");
		System.out.println("=============================:"+ss);
	}

	@Test
	public void testQueryBankId(){
		String xx = channelBankidService.queryBankId("708UNOPAY_10493");
		System.out.println("=======================: " + xx);
	}

}
