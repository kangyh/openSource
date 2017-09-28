package com.heepay.utils.fileutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 文件加解密说明： 需要同时获取公钥和私钥。 公钥加密要用私钥解密 私钥加密就用公钥解密
 */

public class FileRSA {

	private static FileRSA instance = null;
	// 日志打印
	private static final Logger log = LogManager.getLogger();

	private FileRSA() {

	}

	public static FileRSA getInstance() {
		if (instance == null)
			instance = new FileRSA();
		return instance;
	}

	/**
	 * RSA公钥加密文件
	 * 
	 * @param 加密前路径，加密后路径，文件名，公钥(已测试)
	 * @return
	 */

	public static boolean fileEncrypt(String SOURCEFILE_PATH, String SEND_PATH, String sourceFileName,
			String BASE64PUBLICKEY) throws Exception {
		if (isFileExist(SOURCEFILE_PATH + sourceFileName)) {
			try {
				int executorNum = 0;// 计数
				// P2PstRSAServer.encryptFileByPublicKeyPath(SOURCEFILE_PATH,SEND_PATH,sourceFileName,BASE64PUBLICKEY);
				boolean isfile = isFileExist(SEND_PATH + sourceFileName);
				while (!isfile) {// true flase
					log.info("第" + executorNum + "次加密开始");
					// SEND_PATH = getFieldPath(PARAM,payOfPlatform);
					P2PstRSAServer.encryptFileByPublicKeyPath(SOURCEFILE_PATH, SEND_PATH, sourceFileName,
							BASE64PUBLICKEY);
					isfile = isFileExist(SEND_PATH + sourceFileName);// true
																		// false
					if (executorNum == 5) {
						isfile = true;
						log.info("第" + executorNum + "次加密失败");
					}
				}
				// if(isFileExist(SEND_PATH+sourceFileName)){
				// deleteFile(SOURCEFILE_PATH+sourceFileName); //删除源文件
				//
				// }
			} catch (Exception e) {
				log.info("加密处理异常:" + SOURCEFILE_PATH + sourceFileName + "异常信息：" + e);
			}
		} else {
			log.info("找不到源文件:" + SOURCEFILE_PATH + sourceFileName);
		}
		return true;
	}

	/**
	 * 文件解密 私钥解密方法
	 * 
	 * @param 解密前路径，解密后路径，文件名，秘钥(已测试)
	 * @return
	 */
	public static boolean fileDecrypt(String SOURCEFILE_PATH, String SEND_PATH1, String sourceFileName,
			String BASE64PUBLICKEY) throws Exception {
		if (isFileExist(SOURCEFILE_PATH + sourceFileName)) {
			try {
				int executorNum = 0;
				// P2PstRSAServer.decryptFileByPublicKeyPath(SOURCEFILE_PATH,
				// SEND_PATH1, sourceFileName, BASE64PUBLICKEY);
				boolean isfile = isFileExist(SEND_PATH1 + sourceFileName);
				while (!isfile) {
					log.info("第" + executorNum + "次解密");
					// SEND_PATH1 = TimerRSA.getPath(PARAM,p2p2OfPlatform);
					P2PstRSAServer.decryptFileByPrivateKeyPath(SOURCEFILE_PATH, SEND_PATH1, sourceFileName,
							BASE64PUBLICKEY);
					isfile = isFileExist(SEND_PATH1 + sourceFileName);
					if (executorNum == 5) {
						isfile = true;
						log.info("第" + executorNum + "次解密失败");
					}
				}

				// if(isFileExist(SEND_PATH1+sourceFileName)){
				// deleteFile(SOURCEFILE_PATH+sourceFileName); //删除源文件
				// log.info("删除源文件成功:"+SOURCEFILE_PATH+sourceFileName);
				// }
			} catch (Exception e) {
				log.info("解密处理异常:" + SOURCEFILE_PATH + sourceFileName + "异常" + e);
			}
		} else {
			log.info("找不到源文件:" + SOURCEFILE_PATH + sourceFileName);
		}
		return true;
	}

	/**
	 * RSA私钥加密文件
	 * 
	 * @param 加密前路径，加密后路径，文件名，私钥
	 * @return
	 */

	public static boolean fileEncrypt2(String SOURCEFILE_PATH, String SEND_PATH, String sourceFileName,
			String BASE64PUBLICKEY) throws Exception {
		if (isFileExist(SOURCEFILE_PATH + sourceFileName)) {
			try {
				int executorNum = 0;// 计数
				// P2PstRSAServer.encryptFileByPublicKeyPath(SOURCEFILE_PATH,SEND_PATH,sourceFileName,BASE64PUBLICKEY);
				boolean isfile = isFileExist(SEND_PATH + sourceFileName);
				while (!isfile) {// true flase
					log.info("第" + executorNum + "次加密开始");
					// SEND_PATH = getFieldPath(PARAM,payOfPlatform);
					P2PstRSAServer.encryptFileByPrivateKeyPath(SOURCEFILE_PATH, SEND_PATH, sourceFileName,
							BASE64PUBLICKEY);
					isfile = isFileExist(SEND_PATH + sourceFileName);// true
																		// false
					if (executorNum == 5) {
						isfile = true;
						log.info("第" + executorNum + "次加密失败");
					}
				}
				// if(isFileExist(SEND_PATH+sourceFileName)){
				// deleteFile(SOURCEFILE_PATH+sourceFileName); //删除源文件
				//
				// }
			} catch (Exception e) {
				log.info("加密处理异常:" + SOURCEFILE_PATH + sourceFileName + "异常信息：" + e);
			}
		} else {
			log.info("找不到源文件:" + SOURCEFILE_PATH + sourceFileName);
		}
		return true;
	}

	/**
	 * 文件解密 公钥解密方法
	 * 
	 * @param 解密前路径，解密后路径，文件名，公钥
	 * @return
	 */
	public static boolean fileDecrypt2(String SOURCEFILE_PATH, String SEND_PATH1, String sourceFileName,
			String BASE64PUBLICKEY) throws Exception {
		if (isFileExist(SOURCEFILE_PATH + sourceFileName)) {
			try {
				int executorNum = 0;
				// P2PstRSAServer.decryptFileByPublicKeyPath(SOURCEFILE_PATH,
				// SEND_PATH1, sourceFileName, BASE64PUBLICKEY);
				boolean isfile = isFileExist(SEND_PATH1 + sourceFileName);
				while (!isfile) {
					log.info("第" + executorNum + "次解密");
					// SEND_PATH1 = TimerRSA.getPath(PARAM,p2p2OfPlatform);
					P2PstRSAServer.decryptFileByPublicKeyPath(SOURCEFILE_PATH, SEND_PATH1, sourceFileName,
							BASE64PUBLICKEY);
					isfile = isFileExist(SEND_PATH1 + sourceFileName);
					if (executorNum == 5) {
						isfile = true;
						log.info("第" + executorNum + "次解密失败");
					}
				}

				// if(isFileExist(SEND_PATH1+sourceFileName)){
				// deleteFile(SOURCEFILE_PATH+sourceFileName); //删除源文件
				// log.info("删除源文件成功:"+SOURCEFILE_PATH+sourceFileName);
				// }
			} catch (Exception e) {
				log.info("解密处理异常:" + SOURCEFILE_PATH + sourceFileName + "异常" + e);
			}
		} else {
			log.info("找不到源文件:" + SOURCEFILE_PATH + sourceFileName);
		}
		return true;
	}

	/**
	 * 判断文件是否存在，存在返回true，否则返回false
	 */
	public static boolean isFileExist(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			log.info(filePath + "文件不存在");
			return false;
		} else {
			log.info(filePath + "文件存在");
			return true;
		}
	}

	/**
	 * 删除原文件
	 */
	public static boolean deleteFile(String delPath) throws FileNotFoundException, IOException {
		try {
			if (isFileExist(delPath)) {// 判断删除文件是否存在
				File file = new File(delPath);
				file.delete();
				log.info("删除文件成功:" + delPath);
			} else {
				log.info("删除文件不存在:" + delPath);
				return false;
			}
		} catch (Exception e) {
			log.info("deleteFile() Exception:" + e.getMessage());
		}
		return true;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Map<String, Object> map = RSA.genKeyPair();
		// String b = RSA.getPrivateKey(map);
		// String a = RSA.getPublicKey(map);
		// log.info("公钥："+a);
		// log.info("私钥："+b);
		boolean j = FileRSA.fileEncrypt("D:/home/tpds/", "D:/home/tpds1/", "_OUTPAYTRADECHECK_20170419174417.txt",
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNk30toZnj1OJuHVT/w4h5gn6OHJfNnU708JEYj9Vn09X2LRK9kavKIDUKujGuivwVj2GEOhgHac+1aH4oxVtnLFi9dizFFVn7a3iGpM3wV/AABqqMQN9q+yjsG71Ax23QvutDgCZS5eIjpYvqAnyesQ9xDhsYYxVmCS78Ax/dAQIDAQAB");// 解密
		log.info("公钥加密：" + j);
		boolean s = FileRSA.fileDecrypt("D:/home/tpds1/", "D:/home/tpds2/", "_OUTPAYTRADECHECK_20170419174417.txt",
				"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAI2TfS2hmePU4m4dVP/DiHmCfo4cl82dTvTwkRiP1WfT1fYtEr2Rq8ogNQq6Ma6K/BWPYYQ6GAdpz7VofijFW2csWL12LMUVWftreIakzfBX8AAGqoxA32r7KOwbvUDHbdC+60OAJlLl4iOli+oCfJ6xD3EOGxhjFWYJLvwDH90BAgMBAAECgYBmF1QuQpkxVMNPenFf2gWg8bwHCJFjISvdr1hCKP5P188zrT8VWmVR5TW6nZfT8L9lYLaGXkoTlnbEryxChEarIlE0Juc6QQczQTu6x9fEUmjJ/PM/YdNPaxVIhoD1p/Eig7a8uNxg92BgGiaTah7HK66D/C+KCM1jvhAvCCqwkQJBAOC9fHvObcEiJ9tB9xmgVxGgr5SYIpUDYGavjhVJDh902MdCN/OtURXdJM9UXGxEcgcoC3xXaFyRlVoTk0iT39UCQQChRLXAMv0FgHX7qJfP8CGxwSvOhDXeWkg+lwAKyk4SZ/LjG2ZvxemdyUVaFXB+dEYnRS6uVKIj0uIwbS7XWEp9AkBLOgIeKwX//N/ce6Mrt8kNRH0hPx9sQ7oUBTWJGHAaxXD+mcLC1UPsDp8Qq0tYjjyg+p2FnHh5a93TTfQh+iWVAkBrnMH7YwrcJQCV0cacBw005EYrJKYdnfkfavuoN58pQRwe2CXe6B4gIDKbDjtm5of5pHyl7+vSI+Kzlefv4waxAkAb4X4pqFc0DGG3LpVguCakp2O285JWhYIHFme4Z+h/sNoAYe6BT9JSOhJf73aD3IzC6CfSebq/xK9FG/yGm5ta");
		log.info("私钥解密：" + s);

	}
}