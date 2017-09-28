/**
 * 
 */
package com.heepay.rpc.risk.service.impl;
/**
 * @author Administrator
 *
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heepay.common.util.StringUtil;
import com.heepay.enums.FeeDeductType;
import com.heepay.enums.TransType;
import com.heepay.risk.dao.RiskIpbaseMapper;
import com.heepay.risk.dao.RiskTransInfoMapper;

import com.heepay.risk.entity.RiskIpbase;
import com.heepay.risk.entity.RiskRuleDetail;
import com.heepay.risk.entity.TransInfoObj;

import com.heepay.risk.service.IRuleService;
import com.heepay.risk.service.IpQueryService;
import com.heepay.rpc.client.GateWayDataServiceClient;
import com.heepay.rpc.risk.model.AsyncMsgVO;
import com.heepay.rpc.risk.model.TransOrderRiskModel;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Assert;


/**
 * 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring.xml")
public class TestImportIp {
	
	public static boolean enableFileWatch = false;

    private static int offset;
    private static int[] index = new int[256];
    private static ByteBuffer dataBuffer;
    private static ByteBuffer indexBuffer;
    private static Long lastModifyTime = 0L;
    private static File ipFile ;
    private static ReentrantLock lock = new ReentrantLock();
	@Autowired
	RiskIpbaseMapper riskIpbaseMapper;
	@Autowired
	IpQueryService ipQueryService;
	@Test
	public void test2() throws Exception 
	{
//		TestImportIp.load("C:\\Users\\Administrator\\Desktop\\17monipdb\\17monipdb.dat");
//		TestImportIp.findstr(riskIpbaseMapper);
//		riskIpbaseMapper.insert(record);
//		System.out.println( ipQueryService.checkIfChineseIp("101.254.183.14") );
		System.out.println( ipQueryService.getIpInfo("1.1.63.255") );
	}
	
	

	    public static String randomIp() {
	        Random r = new Random();
	        StringBuffer str = new StringBuffer();
	        str.append(r.nextInt(1000000) % 255);
	        str.append(".");
	        str.append(r.nextInt(1000000) % 255);
	        str.append(".");
	        str.append(r.nextInt(1000000) % 255);
	        str.append(".");
	        str.append(0);

	        return str.toString();
	    }
	    

	    public static void load(String filename) {
	        ipFile = new File(filename);
	        load();
	        if (enableFileWatch) {
	            watch();
	        }
	    }

	    public static void load(String filename, boolean strict) throws Exception {
	        ipFile = new File(filename);
	        if (strict) {
	            int contentLength = Long.valueOf(ipFile.length()).intValue();
	            if (contentLength < 512 * 1024) {
	                throw new Exception("ip data file error.");
	            }
	        }
	        load();
	        if (enableFileWatch) {
	            watch();
	        }
	    }

	    public static String[] find(String ip) {
	        int ip_prefix_value = new Integer(ip.substring(0, ip.indexOf(".")));
	        long ip2long_value  = ip2long(ip);
	        int start = index[ip_prefix_value];
	        int max_comp_len = offset - 1028;
	        long index_offset = -1;
	        int index_length = -1;
	        byte b = 0;
	        for (start = start * 8 + 1024; start < max_comp_len; start += 8) {
	            if (int2long(indexBuffer.getInt(start)) >= ip2long_value) {
	                index_offset = bytesToLong(b, indexBuffer.get(start + 6), indexBuffer.get(start + 5), indexBuffer.get(start + 4));
	                index_length = 0xFF & indexBuffer.get(start + 7);
	                break;
	            }
	        }

	        byte[] areaBytes;

	        lock.lock();
	        try {
	            dataBuffer.position(offset + (int) index_offset - 1024);
	            areaBytes = new byte[index_length];
	            dataBuffer.get(areaBytes, 0, index_length);
	        } finally {
	            lock.unlock();
	        }

	        return new String(areaBytes, Charset.forName("UTF-8")).split("\t", -1);
	    }

	    
	    public static String[] findstr(RiskIpbaseMapper riskIpbaseMapper) throws Exception {
	    	int start  = index[0];
	        int max_comp_len = offset - 1028;
	        long index_offset = -1;
	        int index_length = -1;
	        byte b = 0;
	        int i=0;
	        byte[] areaBytes;
	        List<RiskIpbase> baseList = new ArrayList<RiskIpbase>();
	        baseList = TestImportIp.getProvinceId();
	        
	        
	        List<RiskIpbase> insertList = new ArrayList<RiskIpbase>();
	        for (start = start * 8 + 1024; start < max_comp_len  ; start += 8,i++) {
	                index_offset = bytesToLong(b, indexBuffer.get(start + 6), indexBuffer.get(start + 5), indexBuffer.get(start + 4));
	                index_length = 0xFF & indexBuffer.get(start+7);
	                dataBuffer.position(offset + (int) index_offset - 1024);
	                areaBytes = new byte[index_length];
	                dataBuffer.get(areaBytes, 0, index_length);
	                String resIp = null;
	                resIp =longToIp((int2long(indexBuffer.getInt(start)))) ;
	                String str = null;
	                str = Arrays.toString(new String(areaBytes, Charset.forName("UTF-8")).split("\t", -1) );
//	                String result = null; 
//	                result =resIp +"——"+str ;
	                String[] array = str.replace(" ", "").replace("　", "").replace("[", "").replace("]", "").split(",");
	                
//	                result =resIp +"——"+array.length+"%"+array[0]+"%"+array[1];//+"%"+array[2]+"%"+array[3]+"%" ;
//	                
//	                
//	                System.out.println("结果："+result + "，验证："+Arrays.toString( find(resIp) ) );
//	                System.out.println( );
	                RiskIpbase ipbase = null ;
	               ipbase = new RiskIpbase();
	                ipbase.setIp(resIp.trim());
	                ipbase.setCountry(array[0]);
	                if(array.length>1){
	                	ipbase.setProvince(array[1]);
	                	for( int j=0;j<baseList.size();j++){
	                		if(  baseList.get(j).getProvince().indexOf(array[1]) >-1  ){
	                			ipbase.setProvinceId( baseList.get(j).getProvinceId() );
	                			break;
	                		}
	                    }
	                }
	                if(array.length>2 && !"".equals(array[2])){
	                	ipbase.setCity(array[2]);
	                }
	                if(array.length>3 && !"".equals(array[3])){
	                	ipbase.setRegion(array[3]);
	                }
	                ipbase.setRemark(Arrays.toString(array).replace(" ", ""));
	                
//						riskIpbaseMapper.insert(ipbase);
						insertList.add(ipbase);
	                if(i%10000==0) {
	                	System.out.println("listsize:"+insertList.size()+",已插入满"+i+"条数据"  );
	                	riskIpbaseMapper.insertBatch(insertList);
	                	insertList = null;
	                	insertList = new ArrayList<RiskIpbase>();
	                	
	                }
	                
	        }
	    	System.out.println(i);
	        return null;
	    }
	    
	    public static String longToIp(long ip) {  
	    	StringBuilder result = new StringBuilder(15);  
	    	  
	    	for (int i = 0; i < 4; i++) {  
	    	  
	    	    result.insert(0,Long.toString(ip & 0xff));  
	    	  
	    	    if (i < 3) {  
	    	        result.insert(0,'.');  
	    	    }  
	    	  
	    	    ip = ip >> 8;  
	    	}  
	    	return result.toString();  
	    	 }
	    private static void watch() {
	        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
	            @Override
	            public void run() {
	                long time = ipFile.lastModified();
	                if (time > lastModifyTime) {
	                    lastModifyTime = time;
	                    load();
	                }
	            }
	        }, 1000L, 5000L, TimeUnit.MILLISECONDS);
	    }

	    private static void load() {
	        lastModifyTime = ipFile.lastModified();
	        FileInputStream fin = null;
	        lock.lock();
	        try {
	            dataBuffer = ByteBuffer.allocate(Long.valueOf(ipFile.length()).intValue());
	            fin = new FileInputStream(ipFile);
	            int readBytesLength;
	            byte[] chunk = new byte[4096];
	            while (fin.available() > 0) {
	                readBytesLength = fin.read(chunk);
	                dataBuffer.put(chunk, 0, readBytesLength);
	            }
	            dataBuffer.position(0);
	            int indexLength = dataBuffer.getInt();
	            byte[] indexBytes = new byte[indexLength];
	            dataBuffer.get(indexBytes, 0, indexLength - 4);
	            indexBuffer = ByteBuffer.wrap(indexBytes);
	            indexBuffer.order(ByteOrder.LITTLE_ENDIAN);
	            offset = indexLength;

	            int loop = 0;
	            while (loop++ < 256) {
	                index[loop - 1] = indexBuffer.getInt();
	            }
	            indexBuffer.order(ByteOrder.BIG_ENDIAN);
	        } catch (IOException ioe) {
	            ioe.printStackTrace();
	        } finally {
	            try {
	                if (fin != null) {
	                    fin.close();
	                }
	            } catch (IOException e){
	                e.printStackTrace();
	            }
	            lock.unlock();
	        }
	    }

	    private static long bytesToLong(byte a, byte b, byte c, byte d) {
	        return int2long((((a & 0xff) << 24) | ((b & 0xff) << 16) | ((c & 0xff) << 8) | (d & 0xff)));
	    }

	    private static int str2Ip(String ip)  {
	        String[] ss = ip.split("\\.");
	        int a, b, c, d;
	        a = Integer.parseInt(ss[0]);
	        b = Integer.parseInt(ss[1]);
	        c = Integer.parseInt(ss[2]);
	        d = Integer.parseInt(ss[3]);
	        return (a << 24) | (b << 16) | (c << 8) | d;
	    }

	    private static long ip2long(String ip)  {
	        return int2long(str2Ip(ip));
	    }

	    private static long int2long(int i) {
	        long l = i & 0x7fffffffL;
	        if (i < 0) {
	            l |= 0x080000000L;
	        }
	        return l;
	    }
	    public static List<RiskIpbase> getProvinceId() {
	    	List<RiskIpbase> baseList = new ArrayList<RiskIpbase>();
	    	RiskIpbase base = null;
	    	base = new RiskIpbase();
	    	base.setProvince("北京市");
	    	base.setProvinceId("110000");
	    	baseList.add(base);
	    	
	    	base = new RiskIpbase();
	    	base.setProvince("天津市");
	    	base.setProvinceId("120000");
	    	baseList.add(base);
	    	
	    	base = new RiskIpbase();
	    	base.setProvince("河北省");
	    	base.setProvinceId("130000");
	    	baseList.add(base);
	    	
	    	base = new RiskIpbase();
	    	base.setProvince("山西省");
	    	base.setProvinceId("140000");
	    	baseList.add(base);
	    	base = new RiskIpbase();
	    	base.setProvince("内蒙古自治区");
	    	base.setProvinceId("150000");
	    	baseList.add(base);
	    	base = new RiskIpbase();
	    	base.setProvince("辽宁省");
	    	base.setProvinceId("210000");
	    	baseList.add(base);
	    	base = new RiskIpbase();
	    	base.setProvince("吉林省");
	    	base.setProvinceId("220000");
	    	baseList.add(base);
	    	base = new RiskIpbase();
	    	base.setProvince("黑龙江省");
	    	base.setProvinceId("230000");
	    	baseList.add(base);
	    	base = new RiskIpbase();
	    	base.setProvince("上海市");
	    	base.setProvinceId("310000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("江苏省");
	    	base.setProvinceId("320000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("浙江省");
	    	base.setProvinceId("330000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("安徽省");
	    	base.setProvinceId("340000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("福建省");
	    	base.setProvinceId("350000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("江西省");
	    	base.setProvinceId("360000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("山东省");
	    	base.setProvinceId("370000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("河南省");
	    	base.setProvinceId("410000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("湖北省");
	    	base.setProvinceId("420000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("湖南省");
	    	base.setProvinceId("430000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("广东省");
	    	base.setProvinceId("440000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("广西壮族自治区");
	    	base.setProvinceId("450000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("海南省");
	    	base.setProvinceId("460000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("重庆市");
	    	base.setProvinceId("500000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("四川省");
	    	base.setProvinceId("510000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("贵州省");
	    	base.setProvinceId("520000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("云南省");
	    	base.setProvinceId("530000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("西藏自治区");
	    	base.setProvinceId("540000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("陕西省");
	    	base.setProvinceId("610000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("甘肃省");
	    	base.setProvinceId("620000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("青海省");
	    	base.setProvinceId("630000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("宁夏回族自治区");
	    	base.setProvinceId("640000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("新疆维吾尔自治区");
	    	base.setProvinceId("650000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("台湾省");
	    	base.setProvinceId("710000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("香港特别行政区");
	    	base.setProvinceId("810000");
	    	baseList.add(base);base = new RiskIpbase();
	    	base.setProvince("澳门特别行政区");
	    	base.setProvinceId("820000");
	    	baseList.add(base);
	    	
	    	return baseList;
	    }
	

}
