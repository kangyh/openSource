/**
 *  
 */
package com.heepay.prom.modules.sys.web;

import com.heepay.common.util.WebUtil;
import com.heepay.prom.common.web.BaseController;
import com.heepay.prom.modules.sys.entity.User;
import com.heepay.prom.modules.sys.utils.UserUtils;
import com.heepay.redis.JedisClusterUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.thrift.TException;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 
* 
* 描    述：缓存设置菜单controller
*
* 创 建 者： yanxb  
* 创建时间： 2017年2月10日 下午1:44:54 
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
@Controller
@RequestMapping(value = "${adminPath}/sys/redis")
public class RedisController extends BaseController {

	private static Logger log = LogManager.getLogger();
	
	private static JedisCluster jedis = JedisClusterUtil.getJedisCluster();
	
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/sys/redisForm";
	}
	
	/**
	 * 
	* @discription 重新设置缓存
	* @author yanxb       
	* @created 2017年2月13日 下午3:55:30     
	* @return
	 */
	@RequestMapping(value = {"resetRedis", ""})
	public void resetRedis(HttpServletRequest request, HttpServletResponse response) throws TException {
		User user = UserUtils.getUser();
		log.info("用户{}操作缓存设置开始！开始时间={}",user.getName(),DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));

		log.info("用户{}操作缓存设置结束！结束时间={}",user.getName(),DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		String result = "缓存更新完成";
		WebUtil.outputJson("{\"result\":\""+result+"\"}",response);
	}

}
