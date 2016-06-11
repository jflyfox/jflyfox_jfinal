package com.flyfox.jfinal.component.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.StrKit;

public class CurrentPathHandler extends Handler {
	private String currentPathName;

	public CurrentPathHandler() {
		currentPathName = "CURRENT_PATH";
	}

	public CurrentPathHandler(String currentPathName) {
		if (StrKit.isBlank(currentPathName)) {
			throw new IllegalArgumentException("contextPathName can not be blank.");
		} else {
			this.currentPathName = currentPathName;
			return;
		}
	}

	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean isHandled[]) {
		String currentPath = request.getScheme() + "://" + request.getServerName() //
				+ ":" + request.getServerPort();
		currentPath += request.getRequestURI(); // 参数
		String currentPathParam = currentPath;
		if (request.getQueryString() != null) // 判断请求参数是否为空
			currentPathParam += "?" + request.getQueryString(); // 参数
		// 无参数
		request.setAttribute(currentPathName, currentPath);
		// 有参数
		request.setAttribute(currentPathName + "_PARAM", currentPathParam);
		nextHandler.handle(target, request, response, isHandled);
	}

}
