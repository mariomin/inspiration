package com.huatai.common;

import java.nio.charset.Charset;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AESEncrypt {
	private static final Logger log = LoggerFactory.getLogger(AESEncrypt.class);
	public static String CIPHER_ALGORITHM = "AES"; // optional value AES/DES/DESede
	private static final String SHA1PRNG = "SHA1PRNG";
	private static String secretKey = "si@ac032Ab9B0132";
	private static Charset UTF_8 = Charset.forName("UTF-8");
	
	public static Key getSecretKey(String strKey) throws Exception {
		if (strKey == null) {
			strKey = "";
		}
		KeyGenerator _generator = KeyGenerator.getInstance(CIPHER_ALGORITHM);
		SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG);
		secureRandom.setSeed(strKey.getBytes());
		_generator.init(128, secureRandom);
		return _generator.generateKey();
	}
	
	public static String encrypt(String data) {
		if (null == data) return null;
		try {
			Key secureKey = getKey(secretKey);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, secureKey);
			byte[] bt = cipher.doFinal(data.getBytes(UTF_8));
			bt = Base64.getEncoder().encode(bt);
			return new String(bt, UTF_8);
			
		} catch (Exception e) {
			throw new IllegalArgumentException(data, e);
		}
	}
	
	public static String detrypt(String message) {
		if (null == message) return null;
		try {
			Key secureKey = getKey(secretKey);
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, secureKey);
			byte[] res = message.getBytes(UTF_8);
			res = Base64.getDecoder().decode(res);
			res = cipher.doFinal(res);
			return new String(res, UTF_8);
		} catch (Exception e) {
			throw new IllegalArgumentException(message, e);
		}
	}
	
	private static Key getKey(String strKey) {
		try {
			if (strKey == null) {
				strKey = "";
			}
			KeyGenerator _generator = KeyGenerator.getInstance(CIPHER_ALGORITHM);
			SecureRandom secureRandom = SecureRandom.getInstance(SHA1PRNG);
			secureRandom.setSeed(strKey.getBytes());
			_generator.init(128, secureRandom);
			return _generator.generateKey();
		} catch (Exception e) {
			throw new RuntimeException("Init secretKey fail", e);
		}
	}
	
	public static void main(String[] args) {
		if (args.length != 2) {
			// 该输出是给脚本显示使用，不要使用log
			log.info("invalid command");
			log.info("example:\n   aes_encrypt encode abcdefg \n   aes_encrypt decode ZWvgSggevBUXawok6xjmAg==");
			System.exit(-1);
		}
		
		String mode = args[0];
		String input = args[1];
		
		try {
			if ("encode".equalsIgnoreCase(mode)) {
				log.info(AESEncrypt.encrypt(input));
			} else if ("decode".equalsIgnoreCase(mode)) {
				log.info(AESEncrypt.detrypt(input));
			} else {
				log.info("invalid command");
				log.info("example:\n aes_encrypt encode abcdefg \n aes_encrypt decode ZWvgSggevBUXawok6xjmAg==");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}