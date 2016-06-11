package com.flyfox.jfinal.component.util;

import com.flyfox.util.Config;

public class Attr {

	/**
	 * session user
	 */
	public static final String SESSION_NAME = Config.getStr("ATTR.SESSION_NAME");

	/**
	 * 是否是移动设备
	 */
	public static final String SESSION_IS_MOILE = Config.getStr("ATTR.SESSION_IS_MOILE");

	/**
	 * model前缀
	 */
	public static final String MODEL_NAME = Config.getStr("ATTR.MODEL_NAME");

	/**
	 * 手机用户路径前缀
	 */
	public static final String PATH_MOBILE = Config.getStr("ATTR.PATH_MOBILE");

	/**
	 * PC用户路径前缀
	 */
	public static final String PATH_PC = Config.getStr("ATTR.PATH_PC");
}
