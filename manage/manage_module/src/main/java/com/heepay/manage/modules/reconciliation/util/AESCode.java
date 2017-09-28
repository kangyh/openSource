package com.heepay.manage.modules.reconciliation.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * 
 * 
 * 描 述：
 *
 * 创 建 者：chenyanming
 * 创建时间： 2017年7月25日下午1:49:53
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
public class AESCode {

	private static String encodeRules = "c2b297204fb76b312a99a5db42606e75";

	private static final Logger logger = LogManager.getLogger();

	public static void main(String[] args) throws Exception {
		String data = AESEncode("jenkins");
		System.out.println(data);
		String data1 = AESDncode(data);
		System.out.println(data1);
		
	}
	
	/*
	   * 加密
	   * 1.构造密钥生成器
	   * 2.根据ecnodeRules规则初始化密钥生成器
	   * 3.产生密钥
	   * 4.创建和初始化密码器
	   * 5.内容加密
	   * 6.返回字符串
	   */
	    public static String AESEncode(String content){
	        try {
	            //1.构造密钥生成器，指定为AES算法,不区分大小写
	            KeyGenerator keygen=KeyGenerator.getInstance("AES");
	            //2.根据ecnodeRules规则初始化密钥生成器
	            //生成一个128位的随机源,根据传入的字节数组
	            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
	            secureRandom.setSeed(encodeRules.getBytes());
	            keygen.init(128, secureRandom);
	              //3.产生原始对称密钥
	            SecretKey original_key=keygen.generateKey();
	              //4.获得原始对称密钥的字节数组
	            byte [] raw=original_key.getEncoded();
	            //5.根据字节数组生成AES密钥
	            SecretKey key=new SecretKeySpec(raw, "AES");
	              //6.根据指定算法AES自成密码器
	            Cipher cipher=Cipher.getInstance("AES");
	              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
	            cipher.init(Cipher.ENCRYPT_MODE, key);
	            //8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
	            byte [] byte_encode=content.getBytes("utf-8");
	            //9.根据密码器的初始化方式--加密：将数据加密
	            byte [] byte_AES=cipher.doFinal(byte_encode);
	          //10.将加密后的数据转换为字符串
	            //这里用Base64Encoder中会找不到包
	            //解决办法：
	            //在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
	            String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
	          //11.将字符串返回
	            return AES_encode;
	        } catch (Exception e) {
	        	logger.info("加密失败", e);
	        }
	        
	        //如果有错就返加nulll
	        return null;         
	    }
	    /*
	     * 解密
	     * 解密过程：
	     * 1.同加密1-4步
	     * 2.将加密后的字符串反纺成byte[]数组
	     * 3.将加密内容解密
	     */
	    public static String AESDncode(String content){
	        try {
	            //1.构造密钥生成器，指定为AES算法,不区分大小写
	            KeyGenerator keygen=KeyGenerator.getInstance("AES");
	            //2.根据ecnodeRules规则初始化密钥生成器
	            //生成一个128位的随机源,根据传入的字节数组
	            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");  
	            secureRandom.setSeed(encodeRules.getBytes());
	            keygen.init(128, secureRandom);
	              //3.产生原始对称密钥
	            SecretKey original_key=keygen.generateKey();
	              //4.获得原始对称密钥的字节数组
	            byte [] raw=original_key.getEncoded();
	            //5.根据字节数组生成AES密钥
	            SecretKey key=new SecretKeySpec(raw, "AES");
	              //6.根据指定算法AES自成密码器
	            Cipher cipher=Cipher.getInstance("AES");
	              //7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
	            cipher.init(Cipher.DECRYPT_MODE, key);
	            //8.将加密并编码后的内容解码成字节数组
	            byte [] byte_content= new BASE64Decoder().decodeBuffer(content);
	            /*
	             * 解密
	             */
	            byte [] byte_decode=cipher.doFinal(byte_content);
	            String AES_decode=new String(byte_decode,"utf-8");
	            return AES_decode;
	        } catch (Exception e) {
	        	logger.info("解密失败", e);
	        } 
	        //如果有错就返加nulll
	        return null;         
	    }
	    
	    /** 
	     * 随机生成秘钥 
	     */  
	    public static void getKey(){    
	        try {    
	            KeyGenerator kg = KeyGenerator.getInstance("AES");    
	            kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256    
	            SecretKey sk = kg.generateKey();    
	            byte[] b = sk.getEncoded();    
	            String s = byteToHexString(b);    
	            System.out.println(s);    
	            System.out.println("十六进制密钥长度为"+s.length());    
	            System.out.println("二进制密钥的长度为"+s.length()*4);    
	        } catch (Exception e) {    
	            e.printStackTrace();    
	            System.out.println("没有此算法。");    
	        }    
	    }
	    /** 
	     * byte数组转化为16进制字符串 
	     * @param bytes 
	     * @return 
	     */  
	    public static String byteToHexString(byte[] bytes){       
	        StringBuffer sb = new StringBuffer();       
	        for (int i = 0; i < bytes.length; i++) {       
	             String strHex=Integer.toHexString(bytes[i]);   
	             if(strHex.length() > 3){       
	                    sb.append(strHex.substring(6));       
	             } else {    
	                  if(strHex.length() < 2){    
	                     sb.append("0" + strHex);    
	                  } else {    
	                     sb.append(strHex);       
	                  }       
	             }    
	        }    
	       return  sb.toString();       
	    }    
}
