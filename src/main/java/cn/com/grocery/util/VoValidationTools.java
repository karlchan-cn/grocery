/**
 * 
 */
package cn.com.grocery.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author karl
 *
 */
public final class VoValidationTools {

	static Pattern emailPattern = Pattern.compile(
			"[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+");

	public static boolean isValidEmail(String email) {
		Matcher m = emailPattern.matcher(email);
		return !m.matches();
	}
}
