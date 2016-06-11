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

package com.flyfox.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * 标签
 * 
 * @author badqiu
 *
 */
public class BlockTag extends TagSupport{

	private static final long serialVersionUID = -8246166191638588615L;
	
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return EVAL_BODY_INCLUDE or EVAL_BODY_BUFFERED or SKIP_BODY
	 */
	@Override
	public int doStartTag() throws JspException {
		return getOverriedContent() == null ? EVAL_BODY_INCLUDE : SKIP_BODY;
	}

	/**
	 * @return EVAL_PAGE or SKIP_PAGE
	 */
	@Override
	public int doEndTag() throws JspException {
		String overriedContent = getOverriedContent();
		if(overriedContent == null) {
			return EVAL_PAGE;
		}
		
		try {
			pageContext.getOut().write(overriedContent);
		} catch (IOException e) {
			throw new JspException("write overridedContent occer IOException,block name:"+name,e);
		}
		return EVAL_PAGE;
	}
	
	private String getOverriedContent() {
		String varName = Utils.getOverrideVariableName(name);
		return (String)pageContext.getRequest().getAttribute(varName);
	}
}
