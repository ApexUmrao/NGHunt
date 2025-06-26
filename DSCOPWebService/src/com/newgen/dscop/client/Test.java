package com.newgen.dscop.client;

import com.newgen.AESEncryption;
import com.newgen.dscop.utils.EncDec;

public class Test {

	public static void main(String[] args) {
		String key = null;
		try {
			key = AESEncryption.decrypt("eq/XeNHnqe0oqIozsHk1UA==");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//"577B9FE9510FC0EF0FC8BFC8523751CAF374E04B66D74773F505A46F3C9413D4";
		EncDec aesEncrypt = new EncDec(key,("JO8bhn4kKKO1yRZVdFY2otW8z4zSr3iYsfhNjSevsBKvWCwpx42fOgzHJ99yhYFh"));
		String decryptedStr = aesEncrypt.decrypt();
		System.out.println(decryptedStr);

	}

}
