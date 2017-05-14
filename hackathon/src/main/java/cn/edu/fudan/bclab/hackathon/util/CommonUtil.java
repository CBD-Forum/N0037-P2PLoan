package cn.edu.fudan.bclab.hackathon.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CommonUtil {

	public static String calSHA256(String message) {

		try {
			MessageDigest md;

			md = MessageDigest.getInstance("SHA-256");

			md.update(message.getBytes());

			byte byteData[] = md.digest();

			// convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		if (obj instanceof String) {
			String s = (String) obj;
			if ("".equals(s.trim()))
				return true;
		}
		return false;
	}

}
