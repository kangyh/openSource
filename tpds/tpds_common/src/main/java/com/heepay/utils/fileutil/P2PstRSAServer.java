package com.heepay.utils.fileutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * <P>
 * 此类实现文件加密/解密服务
 * </P>
 * 
 * @author lipengzhi
 *
 */
public class P2PstRSAServer {
	final public static int TYPE = 2;

	/**
	 * @author lipengzhi
	 * @param path
	 *            文件存放路径
	 * @param FileName
	 *            文件名
	 * @return fileByte 字节流文件数据 将文件转换成字节文件，获得字节流文件数据
	 */
	public static byte[] getFilePathName(String path, String FileName) {
		String filePathName = path + FileName; // 配置源文件绝对路径
		byte[] fileByte = null;
		try {
			fileByte = Base64Utils.encodeFiles(filePathName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileByte;
	}

	public byte[] getFilePathName(String strFileName) {
		byte[] fileByte = null;
		try {
			fileByte = Base64Utils.encodeFiles(strFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileByte;
	}

	/**
	 * @author lipengzhi
	 * @param sourceFilePath
	 * @param sourceFileName
	 * @param base64privateKey
	 * @return secretFile 加密后byte[]数据
	 * @throws Exception
	 *             私钥加密（已测试）
	 */

	public static byte[] encryptByPrivateKeyPath(String sourceFilePath, String sourceFileName, String base64privateKey)
			throws Exception {
		byte[] fileData = P2PstRSAServer.getFilePathName(sourceFilePath, sourceFileName);
		byte[] secretFile = RSAUtil.rsaPriEncrypt(fileData, base64privateKey, TYPE); // 加密
		return secretFile;
	}

	/**
	 * 
	 * @param sourceFilePath
	 * @param secretFilePath
	 * @param sourceFileName
	 * @param base64privateKey
	 * @throws Exception
	 */
	public static void encryptFileByPrivateKeyPath(String sourceFilePath, String secretFilePath, String sourceFileName,
			String base64privateKey) throws Exception {
		// readFile
		System.out.println(sourceFilePath + sourceFileName);
		FileInputStream fis = new FileInputStream(new File(sourceFilePath + sourceFileName));
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "GBK"));
		StringBuilder sb = new StringBuilder();
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			sb.append(temp).append("\n");// 加密换行
			sb.append(temp);
		}
		String plain = sb.toString();
		byte[] fileData = plain.getBytes("GBK");
		byte[] encodedByte = RSAUtil.rsaPriEncrypt(fileData, base64privateKey, TYPE);
		String files = Base64Utils.encode(encodedByte);
		FileOutputStream fos = new FileOutputStream(new File(secretFilePath + sourceFileName));
		fos.write(files.getBytes("GBK"));
		fos.flush();
		fos.close();

	}

	/**
	 * @author lipengzhi
	 * @param path
	 * @param fileName
	 *            文件名
	 * @param secretFile
	 * @return fileName 文件
	 * @throws Exception
	 *             写文件（已测试base64）
	 */
	public static String generateFile(String path, String fileName, byte[] secretFile) throws Exception {
		String secretFileName = path + fileName;
		System.out.println("存放路径" + secretFileName);// 配置密件存放路径
		Base64Utils.byteArrayToFile(secretFile, secretFileName); // 密件写入，secretFileName密件全名（绝对路径）
		System.out.println("文件写入完成:" + secretFileName);
		return fileName;
	}

	/**
	 * 
	 * @param secretFilePath
	 * @param secretFileName
	 * @param base64publicKey
	 * @return
	 * @throws Exception
	 *             公钥解密（已测试）
	 */
	public static byte[] decryptByPublicKeyPath(String secretFilePath, String secretFileName, String base64publicKey)
			throws Exception {
		byte[] fileData = P2PstRSAServer.getFilePathName(secretFilePath, secretFileName);
		byte[] secretFile = RSAUtil.rsapubDecrypt(fileData, base64publicKey, TYPE);
		return secretFile;
	}

	/**
	 * 
	 * @param sourceFilePath
	 * @param secretFilePath
	 * @param sourceFileName
	 * @param base64publicKey
	 * @throws Exception
	 *             公钥解密
	 */

	public static void decryptFileByPublicKeyPath(String sourceFilePath, String secretFilePath, String sourceFileName,
			String base64publicKey) throws Exception {
		// readFile
		//
		FileInputStream fis = new FileInputStream(new File(sourceFilePath + sourceFileName));
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "GBK"));
		StringBuilder sb = new StringBuilder();
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			sb.append(temp);
		}
		String encStr = sb.toString();
		byte[] fileData = Base64Utils.decode(encStr);
		byte[] secretFile = RSAUtil.rsapubDecrypt(fileData, base64publicKey, TYPE);
		String files = new String(secretFile, "GBK");
		System.out.println(files);
		FileOutputStream fos = new FileOutputStream(new File(secretFilePath + sourceFileName));
		fos.write(files.getBytes("GBK"));
		fos.flush();
		fos.close();
		System.out.println("文件：" + secretFilePath + sourceFileName);
	}

	/**
	 * @author lipengzhi
	 * @param sourceFilePath
	 * @param secretFilePath
	 * @param sourceFileName
	 * @param base64publicKey
	 * @throws Exception
	 *             公钥加密
	 */
	public static void encryptByPublicKey(String sourceFilePath, String secretFilePath, String sourceFileName,
			String base64publicKey) throws Exception {
		P2PstRSAServer p2pEnc = new P2PstRSAServer();
		byte[] fileData = p2pEnc.getFilePathName(sourceFilePath, sourceFileName);
		byte[] secretFile = RSA.encryptByPublicKey(fileData, base64publicKey); // 加密
		String secretFileName = secretFilePath + sourceFileName; // 配置密件存放路径
		Base64Utils.byteArrayToFile(secretFile, secretFileName); // 密件写入，secretFileName密件全名（绝对路径）
		System.out.println("*****密件写入完成*****");
		String encryptByPublicKey = Base64Utils.encode(secretFile);
		System.out.println("加密\n" + encryptByPublicKey);
	}

	/**
	 * 
	 * @param sourceFilePath
	 * @param secretFilePath
	 * @param sourceFileName
	 * @param base64publicKey
	 * @return
	 * @throws Exception
	 *             公钥加密(已测试)
	 */
	public static byte[] encryptByPublicKeyPath(String sourceFilePath, String secretFilePath, String sourceFileName,
			String base64publicKey) throws Exception {
		byte[] fileData = P2PstRSAServer.getFilePathName(sourceFilePath, sourceFileName);
		byte[] secretFile = RSAUtil.rsaPubEncrypt(fileData, base64publicKey, TYPE); // 加密
		return secretFile;
	}

	/**
	 * 
	 * @param sourceFilePath
	 * @param secretFilePath
	 * @param sourceFileName
	 * @param base64publicKey
	 * @throws Exception
	 *             测试完成
	 */
	public static void encryptFileByPublicKeyPath(String sourceFilePath, String secretFilePath, String sourceFileName,
			String base64publicKey) throws Exception {
		// readFile
		System.out.println(sourceFilePath + sourceFileName);
		FileInputStream fis = new FileInputStream(new File(sourceFilePath + sourceFileName));
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			sb.append(temp).append("\n");// 加密换行
		}
		String plain = sb.toString();
		byte[] fileData = plain.getBytes("UTF-8");
		byte[] encodedByte = RSAUtil.rsaPubEncrypt(fileData, base64publicKey, TYPE);
		String files = Base64Utils.encode(encodedByte);
		FileOutputStream fos = new FileOutputStream(new File(secretFilePath + sourceFileName));
		fos.write(files.getBytes("UTF-8"));
		fos.flush();
		fos.close();
		System.out.println("文件：" + secretFilePath + sourceFileName);
	}

	/**
	 * @author lipengzhi
	 * @param restoreFilePath
	 * @param secretFilePath
	 * @param secretFileName
	 * @param base64publicKey
	 * @return
	 * @throws Exception
	 *             私钥解密(已测试)
	 */
	public static byte[] decryptByPrivateKey(String secretFilePath, String restoreFilePath, String secretFileName,
			String base64privateKey) throws Exception {
		byte[] fileData = P2PstRSAServer.getFilePathName(secretFilePath, secretFileName);
		byte[] secretFile = RSAUtil.rsaPriDecrypt(fileData, base64privateKey, TYPE);
		return secretFile;
	}

	/**
	 * 
	 * @param sourceFilePath
	 * @param secretFilePath
	 * @param sourceFileName
	 * @param base64privateKey
	 * @throws Exception
	 */
	/**
	 * 私钥解密
	 */
	public static void decryptFileByPrivateKeyPath(String sourceFilePath, String secretFilePath, String sourceFileName,
			String base64privateKey) throws Exception {
		// readFile
		//
		FileInputStream fis = new FileInputStream(new File(sourceFilePath + sourceFileName));
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String temp = null;
		while ((temp = reader.readLine()) != null) {
			sb.append(temp);
		}
		String encStr = sb.toString();
		byte[] fileData = Base64Utils.decode(encStr);
		byte[] secretFile = RSAUtil.rsaPriDecrypt(fileData, base64privateKey, TYPE);
		String files = new String(secretFile, "UTF-8");
		System.out.println(files);
		FileOutputStream fos = new FileOutputStream(new File(secretFilePath + sourceFileName));
		fos.write(files.getBytes("UTF-8"));
		fos.flush();
		fos.close();
	}

	/**
	 * 生成密钥对，获取私钥和公钥
	 */
	public static void genKeyPair() throws Exception {
		Map<String, Object> genKeyPair = RSA.genKeyPair();
		String base64privateKey = RSA.getPrivateKey(genKeyPair);
		System.out.println("私钥 \n" + base64privateKey);
		String base64publicKey = RSA.getPublicKey(genKeyPair);
		System.out.println("公钥\n" + base64publicKey);
	}
}
