package com.flyfox.jfinal.base;

import java.util.List;

/**
 * baseForm 表单参数
 * 
 * @author flyfox
 * 2014-2-11
 */
public class BaseForm {

	private Paginator paginator;
	private boolean showCondition;

	/**
	 * 设置Where条件
	 * 
	 * @author flyfox
	 * 2013-12-2
	 */
	public void setWhere() {
	}

	/**
	 * 设置Where条件
	 * 
	 * @param where
	 * @author flyfox
	 * 2013-12-2
	 */
	public void setWhere(List<Object> where) {
	}
	
	public boolean isShowCondition() {
		return showCondition;
	}

	public void setShowCondition(boolean showCondition) {
		this.showCondition = showCondition;
	}

	public Paginator getPaginator() {
		return paginator;
	}

	public void setPaginator(Paginator paginator) {
		this.paginator = paginator;
	}
}
