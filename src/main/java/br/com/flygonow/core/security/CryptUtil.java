package br.com.flygonow.core.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 */
public class CryptUtil {

	/**
	 */
	public static final String DEFAULT_ALGORITHM = "MD5";
	
	private CryptUtil(){}
	
	/**
	 * @param input
	 * @return
	 */
	public static byte[] md5(String input) {
		byte[] message = null;
		try {
			MessageDigest md = MessageDigest.getInstance(DEFAULT_ALGORITHM);
			md.update(input.getBytes());
			message = md.digest();
		} catch (NoSuchAlgorithmException e) {
			message = input.getBytes();
		}
		return message;
	}

	/**
	 * @param input
	 * @return
	 */
	public static String md5base64(String input) {
		byte[] message = md5(input);
		//BASE64Encoder encoder = new BASE64Encoder();
		//return encoder.encode(message);
		return new String(Base64Coder.encode(message));
	}

}
