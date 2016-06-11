/**
 * Copyright 2015-2025 FLY的狐狸(email:jflyfox@sina.com qq:369191470).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */


package com.jflyfox.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * 标签
 * 
 * @author badqiu
 */
public class OverrideTag extends BodyTagSupport{
	private static final long serialVersionUID = -8379959647039117369L;
	
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int doStartTag() throws JspException {
		return isOverrided() ? SKIP_BODY : EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		if(isOverrided()) {
			return EVAL_PAGE;
		}
		BodyContent b = getBodyContent();
//		System.out.println("Override.content:"+b.getString());
		String varName = Utils.getOverrideVariableName(name);

		pageContext.getRequest().setAttribute(varName, b.getString());
		return EVAL_PAGE;
	}

	private boolean isOverrided() {
		String varName = Utils.getOverrideVariableName(name);
		return pageContext.getRequest().getAttribute(varName) != null;
	}
	
}
