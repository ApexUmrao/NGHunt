package com.newgen.dscop.utils;


import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.newgen.AESEncryption;
import com.newgen.dscop.stub.InqCustomerUAEPassInfoStub.RequestedDocuments_type0;
import com.newgen.dscop.utils.EncDec;

public class EncDec
{
  private static final byte[] SALT = { -57, -75, -103, 
    -12, 75, 124, -127, 119 };
  private static final int ITERATION_COUNT = 4096;
  private static final int KEY_LENGTH = 256;
  private static final int IV_LENGTH = 16;
  private Cipher eCipher;
  private Cipher dCipher;
  private byte[] encrypt;
  private byte[] iv;

  public EncDec(String passPhrase)
  {
    try
    {
      SecretKeyFactory secretKeyFactory = 
        SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), SALT, 4096, 256);
      SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
      SecretKey secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(),"AES");
      this.eCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      this.eCipher.init(1, secretKey);
      this.dCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      this.iv = ((IvParameterSpec)this.eCipher.getParameters().getParameterSpec(IvParameterSpec.class)).getIV();
      this.dCipher.init(2, secretKey, new IvParameterSpec(this.iv));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public EncDec(String passPhrase, String encryptedString) {
    try {
      SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), SALT, 4096, 256);
      SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
      SecretKey secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");

      this.encrypt = Base64.decodeBase64(encryptedString);

      this.eCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      this.eCipher.init(1, secretKey);

      byte[] iv = extractIV();
      this.dCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      this.dCipher.init(2, secretKey, 
        new IvParameterSpec(iv));
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public String encrypt(String encrypt) {
    String encStr = null;
    try
    {
      byte[] bytes = encrypt.getBytes("UTF8");
      byte[] encrypted = encrypt(bytes);
      byte[] cipherText = new byte[encrypted.length + this.iv.length];
      System.arraycopy(this.iv, 0, cipherText, 0, this.iv.length);
      System.arraycopy(encrypted, 0, cipherText, this.iv.length, 
        encrypted.length);
      encStr = new String(Base64.encodeBase64(cipherText));
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return encStr;
  }

  public byte[] encrypt(byte[] plain) throws Exception {
    return this.eCipher.doFinal(plain);
  }

  private byte[] extractIV() {
    byte[] iv = new byte[16];
    System.arraycopy(this.encrypt, 0, iv, 0, iv.length);
    return iv;
  }

  public String decrypt() {
    String decStr = null;
    try
    {
      byte[] bytes = extractCipherText();

      byte[] decrypted = decrypt(bytes);
      decStr = new String(decrypted, "UTF8");
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return decStr;
  }

  private byte[] extractCipherText() {
    byte[] ciphertext = new byte[this.encrypt.length - 16];
    System.arraycopy(this.encrypt, 16, ciphertext, 0, ciphertext.length);
    return ciphertext;
  }

  public byte[] decrypt(byte[] encrypt) throws Exception {
    return this.dCipher.doFinal(encrypt);
  }

  public String generateNewKey() {
    String newKey = null;
    try
    {
      KeyGenerator kgen = KeyGenerator.getInstance("AES");
      kgen.init(256);

      SecretKey skey = kgen.generateKey();
      byte[] raw = skey.getEncoded();

      newKey = new String(Base64.encodeBase64(raw));
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return newKey;
  }

  public String generateNewSalt() {
    String newSalt = null;
    try
    {
      byte[] salt = new byte[8];

      SecureRandom rnd = new SecureRandom();
      rnd.nextBytes(salt);

      newSalt = Hex.encodeHexString(salt);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return newSalt;
  }

  public String getEncryptedString(String str) {
    String key = "YT7uZPwbYGYJPKC8NJGcH0qnLmMoAEyfxtBkHSJs74U=";
    EncDec aesEncrypt = new EncDec(key);
    String encryptedStr = aesEncrypt.encrypt(str);
    return encryptedStr;
  }
  
  public static void main(String[] args) {
	    String key="";
		try {
			RequestedDocuments_type0[] docArray = new RequestedDocuments_type0[]{};
			RequestedDocuments_type0 docType = new RequestedDocuments_type0();
			docType.setDocumentType("");
			docType.setRequired("");
			docArray[0] = docType;
			key = AESEncryption.decrypt("ju+yJRdtYp3mZ5PbcsLdvoBxWZrttqeITx0NC+YwtZ+3aiNPAMcmrnQZTyM7w1oW");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    EncDec aesEncrypt = new EncDec(key,"/cSDC/w8jnY97/eLfQlfpvoIrCwm4I2+fMTyOGcbzQQXkTtyusaJ5vL1ItNiLqzpXe7yhRNnvf6qxmAB/b/CQW9wVfomPqTOE8gLm15k5KrbQYIdG4lVKkKN05Qb6FHd5YLC5jFiINbd8EWWDnFuikS1gpiu6b/UnnP/6dtNSp2IVBit8WQrbu0fVETu1F52rSdR7/UX5GnwZmo3/ktXynTVWDHRexjYHtjcXtWrAd1DEKrr4SJqXOg3UmW4+iLdL6k8Rri6MRNS8bZ1G1RoWEhD1st9BwCWauCHLqusWtTVNoS//hrAFZY+yqMB2pf43sjIgpmjwz7R18zuWUOp1P1CV/o+PX1vxUKuhzMyXaoUX1XR3ulHx9aI6FXQ0Zft");
	    String decryptedStr = aesEncrypt.decrypt();
	    System.out.println(decryptedStr);
	    String encryptedStr = aesEncrypt.encrypt("");
	    System.out.println(encryptedStr);
	    
	  }
  
}