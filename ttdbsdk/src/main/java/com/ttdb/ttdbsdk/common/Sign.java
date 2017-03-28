package com.ttdb.ttdbsdk.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sign {
	/**
	 * MD5加密
	 */
	public static String getMD5(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();
		StringBuilder md5StrBuff = new StringBuilder();

		for (byte aByteArray : byteArray) {
			if (Integer.toHexString(0xFF & aByteArray).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & aByteArray));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & aByteArray));
		}
		// 16位加密，从第9位到25位
		return md5StrBuff.toString().toLowerCase();
	}

}
