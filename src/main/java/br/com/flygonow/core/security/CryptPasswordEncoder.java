package br.com.flygonow.core.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.PasswordEncoder;

public class CryptPasswordEncoder implements PasswordEncoder {
	
	
		
	@Override
	public String encodePassword(
			String rawPass,
			Object salt) throws DataAccessException {

		//byte[] cipherPass = new MD5Base64CipherAlgorithm().cipher(rawPass.getBytes());
		//return new String(cipherPass);
		return CryptUtil.md5base64(rawPass);
	}

	@Override
	public boolean isPasswordValid(
			String encPass,
			String rawPass,
			Object salt) throws DataAccessException {
		//byte[] cipherPass = new MD5Base64CipherAlgorithm().cipher(rawPass.getBytes());
		//return new String (cipherPass).equals(encPass);
		String actual = CryptUtil.md5base64(rawPass);
		return actual.equals(encPass);
	}

	public static void main(String args[]){
		String actual = CryptUtil.md5base64("flygow");
		System.out.println(actual);
		
		String value = "[9,3,4]";
		int number[] = new int[value.length()];
		for (int i = 0; i < value.length(); i++) {
	        number[i] = Integer.parseInt(value, value.charAt(i));
	    }
		
	}
}
