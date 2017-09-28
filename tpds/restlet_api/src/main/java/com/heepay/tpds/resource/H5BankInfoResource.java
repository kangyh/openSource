package com.heepay.tpds.resource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class H5BankInfoResource extends ServerResource{

	private static final Logger log = LogManager.getLogger();
	
	@Get
	public Representation huiYuan4Get(Representation entity) throws IOException{
		
		String path="https://ibsbjstar.ccb.com.cn/CCBIS/B2CMainPlat_07_EPAY?CLIENTIP=&BRANCHID=110000000&REGINFO=&CURCODE=01&PUB=ef776623e6be3fe68b8c0049020111&PROINFO=&ORDERID=1001581&REFERER=&TYPE=1&REMARK1=&REMARK2=&TXCODE=520100&CCB_IBSVersion=V6&GATEWAY=&MERCHANTID=105110073992921&POSID=443741701&PAYMENT=2.00&MAC=95061a29582b31ef4edd556956d1e5cc&QRCODE=1&CHANNEL=0";
		URI uriBase = null;
		try {
			uriBase = new URI(path);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reference targetRef = new Reference(uriBase);
		getResponse().redirectPermanent(targetRef);
		
		return new StringRepresentation("html跳转...");
	}
	
	@Post
	public Representation huiYuan4Post(Representation entity) throws IOException{
		//获取原始请求的方法
		Reference reference2 = this.getReference();
		
		String path="https://c.heepay.com/online/onlineBankList.do";
		URI uriBase = null;
		try {
			uriBase = new URI(path);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reference targetRef = new Reference(uriBase);
		targetRef.setProtocol(Protocol.HTTPS);
		targetRef.addQueryParameter("merchantId", "100005");
		getResponse().redirectPermanent(targetRef);
		
		return new StringRepresentation("html跳转...");
	}
}
