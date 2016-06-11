package com.flyfox.jfinal.component.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.flyfox.jfinal.component.util.Attr;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**
 * 设置用户session公共属性
 * 
 * 2014年8月9日 下午11:18:02
 * flyfox 330627517@qq.com
 */
public class SessionAttrInterceptor implements Interceptor {

	public void intercept(ActionInvocation ai) {

		Controller controller = ai.getController();

		HttpServletRequest request = controller.getRequest();
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(Attr.SESSION_IS_MOILE);
		if (obj == null) {
			boolean isMoile = HttpRequestDeviceUtils.isMobileDevice(request);
			session.setAttribute(Attr.SESSION_IS_MOILE, isMoile);
		}

		ai.invoke();
	}
}
