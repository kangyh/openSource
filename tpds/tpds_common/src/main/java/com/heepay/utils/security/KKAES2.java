package com.heepay.utils.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 
 *
 * 描    述：AES加解密
 *
 * 创 建 者：   wangdong
 * 创建时间：2017年2月9日 下午5:24:59
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
public class KKAES2 {

	private static KKAES2 instance = null;

	private KKAES2() {
	}

	public static KKAES2 getInstance() {
		if (instance == null)
			instance = new KKAES2();
		return instance;
	}

	/**
	 * 加密String明文输入,经过BASE64编码String密文输出
	 * @param text,keystr,ivstr
	 * @return
	 */

	@SuppressWarnings("static-access")
	public static String encrypt(String text, String keystr, String iv) throws Exception {
		String ivStr = iv;
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] keyBytes = new byte[16];
		byte[] ivBytes = new byte[16];

		byte[] b = keystr.getBytes("UTF-8");
		byte[] v = ivStr.getBytes("UTF-8");

		int len = b.length;
		int len2 = v.length;

		if (len > keyBytes.length)
			len = keyBytes.length;
		if (len2 > ivBytes.length)
			len2 = ivBytes.length;

		System.arraycopy(b, 0, keyBytes, 0, len);
		System.arraycopy(v, 0, ivBytes, 0, len2);

		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		BASE64 decoder = new BASE64();
		byte[] results = cipher.doFinal(text.getBytes("UTF8"));
		return new String(decoder.encode(results));
	}

	/**
	 * 解密 以BASE64形式String密文输入,String明文输出
	 * @param text,keystr,ivstr
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static String decrypt(String text, String keystr, String iv) throws Exception {
		String ivStr = iv;
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] keyBytes = new byte[16];
		byte[] ivBytes = new byte[16];
		byte[] b = keystr.getBytes("UTF-8");
		byte[] v = ivStr.getBytes("UTF-8");
		int len = b.length;
		int len2 = v.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		if (len2 > ivBytes.length)
			len2 = ivBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);
		System.arraycopy(v, 0, ivBytes, 0, len2);
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		BASE64 decoder = new BASE64();
		byte[] results = cipher.doFinal(decoder.decode(text));
		return new String(results, "UTF-8");
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String text = "lksjdflkjsd;lfjksd;lkjsfdsfsdfsdfjjskjfldksjflksdjlfkjsdlkfjsldkjflsdjf";
		String key = "1234567812345678";
		String iv = "0000000000000000";
		String strenc = KKAES2.encrypt(text, key, iv);// 加密
		System.out.println("加密结果:" + strenc);
		String strDes = KKAES2.decrypt(strenc, key, iv);// 解密
		System.out.println("解密结果:" + strDes);

	}
}