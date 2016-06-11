package com.flyfox.jfinal.component.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.StrKit;

public class BasePathHandler extends Handler {
	private String basePathName;

	public BasePathHandler() {
		basePathName = "BASE_PATH";
	}

	public BasePathHandler(String contextPathName) {
		if (StrKit.isBlank(contextPathName)) {
			throw new IllegalArgumentException("contextPathName can not be blank.");
		} else {
			this.basePathName = contextPathName;
			return;
		}
	}

	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean isHandled[]) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() //
				+ ":" + request.getServerPort() + path + "/";
		request.setAttribute(basePathName, basePath);
		nextHandler.handle(target, request, response, isHandled);
	}

}
