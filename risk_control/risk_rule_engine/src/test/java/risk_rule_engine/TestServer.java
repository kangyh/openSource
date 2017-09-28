package risk_rule_engine;

import static org.junit.Assert.*;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * 
 * 
 * 描    述：风控系统
 *
 * 创 建 者：王英雷  E-mail:wangyl@9186.com
 * 创建时间： 2017年1月16日 下午3:00:07
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
public class TestServer {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	@Test
	public void droolsTest()
	{
		 try {
	            // load up the knowledge base
		        KieServices ks = KieServices.Factory.get();
	    	    KieContainer kContainer = ks.getKieClasspathContainer();
	        	KieSession kSession = kContainer.newKieSession("ksession-rules");
	            
	            /*MessageInfo messageInfo = new MessageInfo();
	            messageInfo.setMessage("Hello World");
	            messageInfo.setStatus(Message.HELLO);
	            kSession.insert(messageInfo);
	            
	            MessageInfo messageInfo1 = new MessageInfo();
	            messageInfo1.setMessage("Hello World");
	            messageInfo1.setStatus(Message.HELLO);
	            kSession.insert(messageInfo1);
	            
	         // go !
	            Message message = new Message();
	            message.setMessage("Hello World");
	            message.setStatus(Message.HELLO);
	            kSession.insert(message);*/
	            
	            kSession.fireAllRules();
	        } catch (Throwable t) {
	            t.printStackTrace();
	        }
	}
}



