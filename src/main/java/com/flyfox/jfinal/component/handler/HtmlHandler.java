package com.flyfox.jfinal.component.handler;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 路径伪静态化
 * 
 * 2015年5月5日 下午5:07:20 flyfox 330627517@qq.com
 */
public class HtmlHandler extends Handler {
	@Override
	public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			boolean[] booleans) {
		if (s.lastIndexOf(".html") != -1) {
			s = s.substring(0, s.indexOf(".html"));
		} else if (s.lastIndexOf(".htm") != -1) {
			s = s.substring(0, s.indexOf(".htm"));
		}
		nextHandler.handle(s, httpServletRequest, httpServletResponse, booleans);
	}
}
