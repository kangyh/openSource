package com.heepay.utils.fileutil;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class RSAUtil {
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";
	/**
	 * 获取公钥的key
	 */
	private static final String PUBLIC_KEY = "RSAPublicKey";

	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 116;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	final public static int RAW = 1;
	final public static int PKCS1 = 2;

	/**
	 * 产生RSA公私钥对
	 */
	public static Map<String, Object> genRSAKeyPair() {
		KeyPairGenerator rsaKeyGen = null;
		KeyPair rsaKeyPair = null;
		Map<String, Object> keyMap = null;
		try {
			System.out.println("Generating a pair of RSA key*****");
			rsaKeyGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			SecureRandom random = new SecureRandom();
			random.nextBytes(new byte[1]);
			rsaKeyGen.initialize(2048, new SecureRandom());
			rsaKeyPair = rsaKeyGen.genKeyPair();
			RSAPublicKey publicKey = (RSAPublicKey) rsaKeyPair.getPublic();
			RSAPrivateKey privateKey = (RSAPrivateKey) rsaKeyPair.getPrivate();
			System.out.println("1024-bit RSA key Generated");
			keyMap = new HashMap<String, Object>(2);
			keyMap.put(PUBLIC_KEY, publicKey);
			keyMap.put(PRIVATE_KEY, privateKey);

		} catch (Exception e) {
			System.out.println("Exception in keypair genration.Reason :" + e);
		}

		return keyMap;
	}

	/**
	 * 使用私钥加密
	 * 
	 * @throws Exception
	 */
	public static byte[] rsaPriEncrypt(byte[] encodedBytes, String privateKey, int type) throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		RSAPrivateCrtKey prvKey = (RSAPrivateCrtKey) privateK;
		BigInteger mod = prvKey.getModulus();
		BigInteger pubExp = prvKey.getPublicExponent();
		BigInteger privExp = prvKey.getPrivateExponent();
		BigInteger pExp = prvKey.getPrimeExponentP();
		BigInteger qExp = prvKey.getPrimeExponentQ();
		BigInteger p = prvKey.getPrimeP();
		BigInteger q = prvKey.getPrimeQ();
		BigInteger crtCoef = prvKey.getCrtCoefficient();
		RSAKeyParameters privParameters = new RSAPrivateCrtKeyParameters(mod, pubExp, privExp, p, q, pExp, qExp,
				crtCoef);
		System.out.println("mod:\r\n" + mod.toString(16));
		System.out.println("pubExp:\r\n" + pubExp.toString(16));
		AsymmetricBlockCipher eng = new RSAEngine();
		if (type == PKCS1)
			eng = new PKCS1Encoding(eng);
		eng.init(true, privParameters);
		int inputLen = encodedBytes.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		byte[] encryptedData = null;
		try {
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = eng.processBlock(encodedBytes, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = eng.processBlock(encodedBytes, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			encryptedData = out.toByteArray();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return encryptedData;
	}

	/**
	 * 使用公钥解密
	 * 
	 * @throws Exception
	 */
	public static byte[] rsapubDecrypt(byte[] clearBytes, String publicKey, int type) throws Exception {
		byte[] keyBytes = Base64Utils.decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		RSAPublicKey pubKey = (RSAPublicKey) publicK;
		BigInteger mod = pubKey.getModulus();
		BigInteger pubExp = pubKey.getPublicExponent();
		RSAKeyParameters pubParameters = new RSAKeyParameters(false, mod, pubExp);
		System.out.println("mod:\r\n" + mod.toString(16));
		System.out.println("pubExp:\r\n" + pubExp.toString(16));
		AsymmetricBlockCipher eng = new RSAEngine();
		if (type == PKCS1)
			eng = new PKCS1Encoding(eng);
		eng.init(false, pubParameters);
		int inputLen = clearBytes.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] decryptedData = null;
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		try {
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = eng.processBlock(clearBytes, offSet, MAX_DECRYPT_BLOCK);
				} else {
					cache = eng.processBlock(clearBytes, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			decryptedData = out.toByteArray();
			out.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return decryptedData;
	}

	/**
	 * 使用公钥加密
	 */
	/**
	 * 使用公钥加密
	 */
	public static byte[] rsaPubEncrypt(byte[] encodedBytes, String puclicKey, int type) throws Exception {
		byte[] keyBytes = Base64Utils.decode(puclicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		RSAPublicKey pubKey = (RSAPublicKey) publicK;
		BigInteger mod = pubKey.getModulus();
		BigInteger pubExp = pubKey.getPublicExponent();
		RSAKeyParameters pubParameters = new RSAKeyParameters(false, mod, pubExp);
		System.out.println("mod:\r\n" + mod.toString(16));
		System.out.println("pubExp:\r\n" + pubExp.toString(16));
		AsymmetricBlockCipher eng = new RSAEngine();
		if (type == PKCS1)
			eng = new PKCS1Encoding(eng);
		eng.init(true, pubParameters);
		int inputLen = encodedBytes.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		byte[] encryptedData = null;
		try {
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = eng.processBlock(encodedBytes, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = eng.processBlock(encodedBytes, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			encryptedData = out.toByteArray();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return encryptedData;
	}

	/**
	 * 私钥解密
	 */

	public static byte[] rsaPriDecrypt(byte[] encodedBytes, String privateKey, int type) throws Exception {
		byte[] keyBytes = Base64Utils.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		RSAPrivateCrtKey prvKey = (RSAPrivateCrtKey) privateK;
		BigInteger mod = prvKey.getModulus();
		BigInteger pubExp = prvKey.getPublicExponent();
		BigInteger privExp = prvKey.getPrivateExponent();
		BigInteger pExp = prvKey.getPrimeExponentP();
		BigInteger qExp = prvKey.getPrimeExponentQ();
		BigInteger p = prvKey.getPrimeP();
		BigInteger q = prvKey.getPrimeQ();
		BigInteger crtCoef = prvKey.getCrtCoefficient();
		RSAKeyParameters privParameters = new RSAPrivateCrtKeyParameters(mod, pubExp, privExp, p, q, pExp, qExp,
				crtCoef);
		System.out.println("mod:\r\n" + mod.toString(16));
		System.out.println("pubExp:\r\n" + pubExp.toString(16));
		AsymmetricBlockCipher eng = new RSAEngine();
		if (type == PKCS1)
			eng = new PKCS1Encoding(eng);
		eng.init(false, privParameters);

		int inputLen = encodedBytes.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		byte[] encryptedData = null;
		System.out.println("ooooooooooooooooo" + inputLen);
		try {
			// 对数据分段解密
			while (inputLen - offSet > 0) {

				if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
					cache = eng.processBlock(encodedBytes, offSet, MAX_DECRYPT_BLOCK);
					System.out.println("%%%%%%%%%%%%%%%%%%" + (inputLen - offSet) + "$$$$$$$$" + cache);
				} else {
					cache = eng.processBlock(encodedBytes, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_DECRYPT_BLOCK;
			}
			encryptedData = out.toByteArray();
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return encryptedData;
	}

	/**
	 * <p>
	 * 获取私钥
	 * </p>
	 *
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return Base64Utils.encode(key.getEncoded());
	}

	/**
	 * <p>
	 * 获取公钥
	 * </p>
	 *
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		return Base64Utils.encode(key.getEncoded());
	}
}
