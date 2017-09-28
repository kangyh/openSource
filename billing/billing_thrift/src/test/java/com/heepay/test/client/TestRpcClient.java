package com.heepay.test.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;



public class TestRpcClient {
	
	public static void main(String[] args) { 
        try { 
        	System.out.println("================================222");
            // 设置调用的服务地址为本地，端口为 7911 
        	//Transport transport = new  Transport("192.168.4.107", 8019); 
            //transport.open(); 
            // 设置传输协议为 TBinaryProtocol 
           // TProtocol protocol = new TBinaryProtocol(transport); 
    		//TProtocol protocol = new TCompactProtocol(transport);
    		//TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "UserServiceImpl");
            
    		
    		TTransport transport = new TFramedTransport(new TSocket("192.168.4.101", 8019));

    		TProtocol protocol = new TCompactProtocol(transport);
    		TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, "BankcardServiceImpl");
            transport.open();		
    		
            //BankcardService.Client client = new BankcardService.Client(mp); 

//            Bankcard card=client.queryByCardId(1);
//            System.out.println("==pay.name="+card.getBank_name());
            
//            boolean result = client.deleteByCardId(2);
//            System.out.println("delete result:" + result );


            //快速插入模拟数据
//            for(int i=5; i<50; i++){
//            Bankcard bankcard = new Bankcard();
//            bankcard.setCard_id(i);
//            bankcard.setCard_no("2222");
//            bankcard.setBank_id(2222);
//            bankcard.setBank_name("CCB");
//            bankcard.setBank_info("建设银行");
//            bankcard.setOwner_name("liud");
//            bankcard.setOwner_cerno("140521199202046017");
//            bankcard.setOwner_moblie("15351234853");
//            bankcard.setUser_id(2);
//            boolean result = client.insertBankCard(bankcard);
//            System.out.println(result);
//            }
            
            
        //   Bankcard list = client.queryByCardId(1l);
        //    System.out.println("getAllBankCard查询的记录数为"+list);
            
            transport.close(); 
        } catch (TTransportException e) { 
            e.printStackTrace(); 
        } catch (TException e) { 
            e.printStackTrace(); 
        } 
    } 
}
