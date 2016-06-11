package com.flyfox.jfinal.component.interceptor;

import com.flyfox.util.Config;
import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;

/**
 * 设置用户session公共属性
 * 
 * 2014年8月9日 下午11:18:02 flyfox 330627517@qq.com
 */
public class ExceptionInterceptor implements Interceptor {

	private final static Logger log = Logger.getLogger(ExceptionInterceptor.class);

	public void intercept(ActionInvocation ai) {

		try {
			ai.invoke();
		} catch (Exception e) {
			log.error("异常：", e);
			Controller controller = ai.getController();
			controller.setAttr("error", e.toString());
			controller.render(Config.getStr("PAGES.500"));
		}

	}
}
