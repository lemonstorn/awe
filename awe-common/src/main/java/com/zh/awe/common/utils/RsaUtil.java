package com.zh.awe.common.utils;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaUtil {

	private static final Logger logger = LoggerFactory.getLogger(RsaUtil.class);
	/** 算法名称 */
	private static final String ALGORITHOM = "RSA";
	public static final String RSA_PUBLIC_FILENAME = "/rsa.pub";
	public static final String RSA_PRIVATE_FILENAME = "/rsa.pri";
	private static String RSA_PATH = null;
	private static String RSA_SECRET = null;
	/** 默认的安全服务提供者 */
	private static final Provider DEFAULT_PROVIDER = new BouncyCastleProvider();

	public synchronized static void initRsaConfig(String rsaPath, String secret) {
		if (StringUtils.isBlank(rsaPath) || StringUtils.isBlank(secret)) {
			throw new RuntimeException("Config is null");
		}
		File dir = new File(rsaPath);
		if (!dir.exists()) {
			try {
				dir.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		RSA_PATH = rsaPath;
		RSA_SECRET = secret;
	}

	/**
	 * 从文件中读取公钥
	 *
	 * @param filename
	 *            公钥保存路径，相对于classpath
	 * @return 公钥对象
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String filename) throws Exception {
		byte[] bytes = readFile(filename);
		return getPublicKey(bytes);
	}

	public static PublicKey getPublicKey(String publicKeyFilename, String privateKeyFilename, String secret)
			throws Exception {
		File dest = new File(publicKeyFilename);
		if (!dest.exists()) {
			RsaUtil.generateKey(publicKeyFilename, privateKeyFilename, secret);
		}
		byte[] bytes = readFile(publicKeyFilename);
		return getPublicKey(bytes);
	}

	public static PublicKey getPublicKey(String rsaPath, String secret) throws Exception {
		String publicKeyFilename = rsaPath + RSA_PUBLIC_FILENAME;
		String privateKeyFilename = rsaPath + RSA_PRIVATE_FILENAME;
		File dest = new File(publicKeyFilename);
		if (!dest.exists()) {
			RsaUtil.generateKey(publicKeyFilename, privateKeyFilename, secret);
		}
		byte[] bytes = readFile(publicKeyFilename);
		return getPublicKey(bytes);
	}

	/**
	 *                      * 从文件中读取密钥                      *                   
	 *   * @param filename 私钥保存路径，相对于classpath                      * @return
	 * 私钥对象                      * @throws Exception                     
	 */
	public static PrivateKey getPrivateKey(String filename) throws Exception {
		byte[] bytes = readFile(filename);
		return getPrivateKey(bytes);
	}

	public static PrivateKey getPrivateKey(String publicKeyFilename, String privateKeyFilename, String secret)
			throws Exception {
		File dest = new File(privateKeyFilename);
		if (!dest.exists()) {
			RsaUtil.generateKey(publicKeyFilename, privateKeyFilename, secret);
		}
		byte[] bytes = readFile(privateKeyFilename);
		return getPrivateKey(bytes);
	}

	public static PrivateKey getPrivateKey(String rsaPath, String secret) throws Exception {
		String publicKeyFilename = rsaPath + RSA_PUBLIC_FILENAME;
		String privateKeyFilename = rsaPath + RSA_PRIVATE_FILENAME;
		File dest = new File(privateKeyFilename);
		if (!dest.exists()) {
			RsaUtil.generateKey(publicKeyFilename, privateKeyFilename, secret);
		}
		byte[] bytes = readFile(privateKeyFilename);
		return getPrivateKey(bytes);
	}

	/**
	 *                      * 获取公钥                      *                     
	 * * @param bytes 公钥的字节形式                      * @return                   
	 *   * @throws Exception                     
	 */
	public static PublicKey getPublicKey(byte[] bytes) throws Exception {
		X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		return factory.generatePublic(spec);
	}

	/**
	 *                      * 获取密钥                      *                     
	 * * @param bytes 私钥的字节形式                      * @return                   
	 *   * @throws Exception                     
	 */
	public static PrivateKey getPrivateKey(byte[] bytes) throws Exception {
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		return factory.generatePrivate(spec);
	}

	public static void generateKey(String publicKeyFilename, String privateKeyFilename, String secret)
			throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		SecureRandom secureRandom = new SecureRandom(secret.getBytes());
		keyPairGenerator.initialize(1024, secureRandom);
		KeyPair keyPair = keyPairGenerator.genKeyPair();
		// 获取公钥并写出
		byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
		writeFile(publicKeyFilename, publicKeyBytes);
		// 获取私钥并写出
		byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
		writeFile(privateKeyFilename, privateKeyBytes);
	}

	private static byte[] readFile(String fileName) throws Exception {
		return Files.readAllBytes(new File(fileName).toPath());
	}

	private static void writeFile(String destPath, byte[] bytes) throws IOException {
		File dest = new File(destPath);
		if (!dest.exists()) {
			dest.createNewFile();
		}
		Files.write(dest.toPath(), bytes);
	}

	public static byte[] encrypt(PublicKey publicKey, byte[] data) throws Exception {
		Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
		ci.init(Cipher.ENCRYPT_MODE, publicKey);
		return ci.doFinal(data);
	}

	public static byte[] decrypt(PrivateKey privateKey, byte[] data) throws Exception {
		Cipher ci = Cipher.getInstance(ALGORITHOM, DEFAULT_PROVIDER);
		ci.init(Cipher.DECRYPT_MODE, privateKey);
		return ci.doFinal(data);
	}

	public static String encryptString(PublicKey publicKey, String plaintext) {
		if (publicKey == null || plaintext == null) {
			return null;
		}
		byte[] data = plaintext.getBytes();
		try {
			byte[] en_data = encrypt(publicKey, data);
			return new String(Hex.encodeHex(en_data));
		} catch (Exception ex) {
			logger.error(ex.getCause().getMessage());
		}
		return null;
	}

	public static String encryptString(String rsaPath, String secret, String plaintext) throws Exception {
		PublicKey publicKey = getPublicKey(rsaPath, secret);
		return encryptString(publicKey, plaintext);
	}

	public static String encryptString(String plaintext) throws Exception {
		String rsaPath = RSA_PATH;
		if (StringUtils.isBlank(rsaPath)) {
			throw new RuntimeException("rsaPath is null");
		}
		return encryptString(rsaPath, RSA_SECRET, plaintext);
	}

	public static String decryptString(PrivateKey privateKey, String encrypttext) {
		if (privateKey == null || StringUtils.isBlank(encrypttext)) {
			return null;
		}
		try {
			byte[] en_data = Hex.decodeHex(encrypttext.toCharArray());
			byte[] data = decrypt(privateKey, en_data);
			return new String(data);
		} catch (Exception ex) {
			logger.error(String.format("\"%s\" Decryption failed. Cause: %s", encrypttext, ex.getCause().getMessage()));
		}
		return null;
	}

	public static String decryptString(String rsaPath, String secret, String encrypttext) throws Exception {
		PrivateKey privateKey = getPrivateKey(rsaPath, secret);
		return decryptString(privateKey, encrypttext);
	}

	public static String decryptString(String encrypttext) throws Exception {
		String rsaPath = RSA_PATH;
		if (StringUtils.isBlank(rsaPath)) {
			throw new RuntimeException("rsaPath is null");
		}
		return decryptString(rsaPath, RSA_SECRET, encrypttext);
	}

	public static void main(String[] args) throws Exception {
		String rsaPath = "E:/app/";
		String secret = "awe666";
		RsaUtil.initRsaConfig(rsaPath, secret);
		String s1 = RsaUtil.encryptString("test666");
		System.out.println("加密后:" + s1);
		String s2 = RsaUtil.decryptString(s1);
		System.out.println("解密后:" + s2);
	}
}
