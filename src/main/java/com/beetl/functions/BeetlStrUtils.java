package com.beetl.functions;

import java.text.MessageFormat;
import java.util.Date;

import com.flyfox.util.DateUtils;
import com.flyfox.util.StrUtils;

/**
 * 字符串处理
 * 
 * @author flyfox 2012.08.08
 * @email 330627517@qq.com
 */
public class BeetlStrUtils  extends StrUtils{

	/**
	 * startWith
	 * 
	 * 2014年8月29日 下午1:40:31 flyfox 330627517@qq.com
	 * 
	 * @param str
	 * @param key
	 * @return
	 */
	public boolean startWith(String str, String key) {
		if (isEmpty(str) || isEmpty(key))
			return false;
		return str.startsWith(key);
	}

	/**
	 * endWith
	 * 
	 * 2014年8月29日 下午1:40:31 flyfox 330627517@qq.com
	 * 
	 * @param str
	 * @param key
	 * @return
	 */
	public boolean endWith(String str, String key) {
		if (isEmpty(str) || isEmpty(key))
			return false;
		return str.endsWith(key);
	}

	/**
	 * length
	 * 
	 * 2014年8月29日 下午1:40:31 flyfox 330627517@qq.com
	 * 
	 * @param str
	 * @param key
	 * @return
	 */
	public int length(String str) {
		if (isEmpty(str))
			return 0;
		return str.length();
	}

	public String subStringTo(String str, int start, int end) {
		if (isEmpty(str))
			return str;
		return str.substring(start, end);
	}

	public String subString(String str, int start) {
		if (isEmpty(str))
			return str;
		return str.substring(start);
	}

	public String[] split(String str, String key) {
		if (isEmpty(str) || isEmpty(key))
			return new String[] { str };
		return str.split(key);
	}

	public boolean contain(String str, String key) {
		if (isEmpty(str) || isEmpty(key))
			return false;
		return str.indexOf(key) != -1;
	}

	public String format(String str, Object[] args) {
		String result = MessageFormat.format(str, args);
		return result;
	}

	public String formatDate(Date o, String args) {
		return DateUtils.format(o, args);
	}

}
