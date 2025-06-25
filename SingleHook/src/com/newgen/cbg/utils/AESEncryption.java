package com.newgen.cbg.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESEncryption
{
	private static final String ALGO = "AES";
	private static final byte[] keyValue = { 84, 104, 101, 66, 101, 115, 116, 83, 101, 99, 114, 101, 116, 75, 101, 121 };

	public static String encrypt(String paramString)
			throws Exception
	{
		Key localKey = generateKey();
		Cipher localCipher = Cipher.getInstance("AES");
		localCipher.init(1, localKey);
		byte[] arrayOfByte = localCipher.doFinal(paramString.getBytes());
		String str = new BASE64Encoder().encode(arrayOfByte);
		return str;
	}

	public static String decrypt(String paramString) throws Exception {
		Key localKey = generateKey();
		Cipher localCipher = Cipher.getInstance("AES");
		localCipher.init(2, localKey);
		byte[] arrayOfByte1 = new BASE64Decoder().decodeBuffer(paramString);
		byte[] arrayOfByte2 = localCipher.doFinal(arrayOfByte1);
		String str = new String(arrayOfByte2);
		return str; 
	}

	public static String encrypt(String input, String key) {
		byte[] crypted = null;
		try {

			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, skey);
			crypted = cipher.doFinal(input.getBytes());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
		// System.out.println("Original value: " + crypted.toString());  
		return new String(encoder.encodeToString(crypted));
	}

	public static String decrypt(String input, String key) {
		byte[] output = null;
		try {
			java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
			SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, skey);
			output = cipher.doFinal(decoder.decode(input));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return new String(output);
	}

	private static Key generateKey() throws Exception {
		SecretKeySpec localSecretKeySpec = new SecretKeySpec(keyValue, "AES");
		return localSecretKeySpec;
	}
	
	public static void main(String[] args){
		String ss ="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";
		try {
			decrypt(ss);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		decrypt(ss, "78214125442A472D4B6150645367566B");
	}
}