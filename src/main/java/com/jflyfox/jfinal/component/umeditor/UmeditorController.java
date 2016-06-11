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

package com.jflyfox.jfinal.component.umeditor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.jflyfox.jfinal.component.ueditor.UeditorController;
import com.jflyfox.jfinal.component.ueditor.Uploader;
import com.jflyfox.util.Config;

/**
 * Ueditor 封装
 * 
 * @author flyfox 2014-1-20
 */
public class UmeditorController extends UeditorController {

	/**
	 * 图片上传
	 * 
	 */
	public void imageup() {
		String[] fileType = { ".gif", ".png", ".jpg", ".jpeg", ".bmp" };

		List<String> savePath = Arrays.asList(Config.getStr("savePath").split(","));

		// 获取存储目录结构
		if (getPara("fetch") != null) {
			// 构造json数据
			Iterator<String> iterator = savePath.iterator();
			String dirs = "[";
			while (iterator.hasNext()) {
				dirs += "'" + iterator.next() + "'";
				if (iterator.hasNext()) {
					dirs += ",";
				}

			}
			dirs += "]";
			renderJavascript("updateSavePath( " + dirs + " );");
			return;
		}

		Uploader up = new Uploader(getRequest());
		// 获取前端提交的path路径
		String dir = getPara("dir");
		// 普通请求中拿不到参数， 则从上传表单中拿
		if (dir == null) {
			dir = up.getParameter("dir");
		}

		if (dir == null || "".equals(dir)) {
			// 赋予默认值
			dir = savePath.get(0);
			// 安全验证
		} else if (!savePath.contains(dir)) {
			renderText("{'state':'\\u975e\\u6cd5\\u4e0a\\u4f20\\u76ee\\u5f55'}");
			return;

		}

		try {
			up.setSavePath(dir);
			up.setAllowFiles(fileType);
			up.setMaxSize(500 * 1024); // 单位KB
			up.upload();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "{\"name\":\"" + up.getFileName() + "\", \"originalName\": \"" + up.getOriginalName()
				+ "\", \"size\": \"" + up.getSize() + "\", \"state\": \"" + up.getState() + "\", \"type\": \""
				+ up.getType() + "\", \"url\": \"" + up.getUrl() + "\"}";
		result = result.replaceAll( "\\\\", "\\\\" );
		renderHtml(result);
	}
}
