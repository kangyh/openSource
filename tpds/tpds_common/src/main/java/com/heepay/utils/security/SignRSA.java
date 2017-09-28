/***
 * 
* 
* 描    述：
*
* 创 建 者： xuangang
* 创建时间：  2017年4月14日下午5:27:00
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
package com.heepay.utils.security;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 示例内容包括：生成公私钥对、将公私钥写入文件、从文件读取公私钥、签名、对签名串做BASE64编码和解码、验签
 * 可参考https://docs.oracle.com/javase/tutorial/security/apisign/index.html
 */
public class SignRSA {
	public static final int PRIVATE = 0;
	public static final int PUBLIC = 1;

	private static final Logger log = LogManager.getLogger();

	/**
	 * @param filenamePath
	 * @param type
	 * @return
	 * @throws FileNotFoundException
	 * @方法说明：读取密钥
	 * @author xuangang
	 * @时间：2017年4月17日下午4:06:25
	 */
	private static Key getKeyFromFile(String filenamePath, int type) throws FileNotFoundException {
		FileInputStream fis = null;
//		ClassLoader classLoader = SignRSA.class.getClassLoader();
//		URL url = classLoader.getResource(filename);
		fis = new FileInputStream(filenamePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int b;
		try {
			while ((b = fis.read()) != -1) {
				baos.write(b);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] keydata = baos.toByteArray();

		Key key = null;
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			switch (type) {
				case PRIVATE:
					PKCS8EncodedKeySpec encodedPrivateKey = new PKCS8EncodedKeySpec(keydata);
					key = kf.generatePrivate(encodedPrivateKey);
					return key;
				case PUBLIC:
					X509EncodedKeySpec encodedPublicKey = new X509EncodedKeySpec(keydata);
					key = kf.generatePublic(encodedPublicKey);
					return key;
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			log.error("读取密钥文件异常！", e);
		}

		return key;
	}



	/**
	 * 加签
	 * value 需加签字段，
	 * privateKeyName 秘钥文件名
	 * 返回：加签之后的串
	 */
	public static String addSign(String vale,String privateKeyName){
		String keypath = privateKeyName;
		//签名示例
		log.info("开始签名...");
		//从文件中读取私钥
		String signatureInBase64 = "";
		try {
			PrivateKey privateKeyLoadedFromFile = (PrivateKey)getKeyFromFile(keypath,PRIVATE);
			//初始化签名算法
			Signature sign = Signature.getInstance("SHA256withRSA");
			//建议SHA256withRSA
//        sign.initSign(privateKey);//指定签名所用私钥
			sign.initSign(privateKeyLoadedFromFile);
			//指定使用从文件中读取的私钥
			byte[] data = vale.getBytes();//待签名明文数据
			log.info("待签名的明文串: "+new String(data));
			//更新用于签名的数据
			sign.update(data);

			//签名
			byte[] signature = sign.sign();
			//将签名signature转为BASE64编码，用于HTTP传输
			Base64 base64 = new Base64();
			signatureInBase64 = base64.encodeToString(signature);
			log.info("Base64格式编码的签名串: " + signatureInBase64);
			log.info("签名结束...");
		}catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return signatureInBase64;
	}

	/**
	 * 验签
	 * value 待验签明文数据，
	 * signature 收到的签名（报文中的签名）
	 * publickeyName 公钥文件名
	 *
	 */
	public static boolean varifySign(String publickeyName,String value,String signature){
		String keypath = publickeyName;
		boolean flag = false;
		//验签示例
		//从文件中读取公钥
		log.info("验签开始...公钥名称:----->{}",publickeyName);
		try {
			PublicKey publicKeyLoadedFromFile = (PublicKey)getKeyFromFile(keypath, PUBLIC);
			//将签名signature转为BASE64编码，用于HTTP传输
			Base64 base64 = new Base64();
			//从Base64还原得到签名
			byte[] signatureFromBase64 = base64.decodeBase64(signature);
			//初始化验签算法
			Signature verifySign = Signature.getInstance("SHA256withRSA");
//        verifySign.initVerify(publicKey);
			//指定验签所用公钥
			verifySign.initVerify(publicKeyLoadedFromFile);
			byte[] data = value.getBytes();//待签名明文数据
			//data为待验签的数据(明文)
			verifySign.update(data);
			log.info("待验签的明文串："+new String(data)+" 签名（BASE64格式）："+signature);
			//验签
			flag = verifySign.verify(signatureFromBase64);
			log.info("验签是否通过:"+flag);//true为验签成功
			log.info("验签结束!...");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 获取treeMap对象迭代遍历后value的字符串
	 *
	 * @param map
	 * @return
	 */
	public static String getTreeMap(TreeMap map) {

		// 定义返回字符串
		String rtnVal = "";

		// 这是用TreeMap的keySet()方法，生成的对象是由key对象组成的Set
		// 再利用TreeMap的get(key)方法，得到对应的value值

		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			// it.next()得到的是key，tm.get(key)得到obj
			rtnVal = rtnVal + map.get(it.next()) + "|";
		}
		// 去除最后一位的|线分隔符
		rtnVal = rtnVal.substring(0, rtnVal.length() - 1);

		return rtnVal;
	}

}