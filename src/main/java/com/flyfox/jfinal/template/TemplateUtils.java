package com.flyfox.jfinal.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flyfox.util.StrUtils;

public class TemplateUtils {

	private static String temPath = "/conf/template/";
	public static final String KEY = "key";
	public static final String VALUE = "value";
	public static GroupTemplate groupTemplate;

	static {
		// 需要注册对应的方法
		try {
			if (groupTemplate != null)
				groupTemplate.close();
			ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
			Configuration cfg = Configuration.defaultConfiguration();
			groupTemplate = new GroupTemplate(resourceLoader, cfg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// //////////////////////增删改查///////////////////////
	/**
	 * 添加
	 * 
	 * 2014年10月8日 下午5:54:26 flyfox 330627517@qq.com
	 * 
	 * @param crud
	 * @param obj
	 * @return
	 */
	public static String add(CRUD crud, Object obj) {
		return getStr(temPath + "temAdd.bee", "crud", crud, "model", obj);
	}

	/**
	 * 编辑
	 * 
	 * 2014年10月8日 下午5:54:31 flyfox 330627517@qq.com
	 * 
	 * @param crud
	 * @param obj
	 * @return
	 */
	public static String edit(CRUD crud, Object obj) {
		return getStr(temPath + "temEdit.bee", "crud", crud, "model", obj);
	}

	/**
	 * 列表头部
	 * 
	 * 2014年10月8日 下午5:54:36 flyfox 330627517@qq.com
	 * 
	 * @param crud
	 * @return
	 */
	public static String listH(CRUD crud) {
		return getStr(temPath + "temListHead.bee", "crud", crud);
	}

	/**
	 * 列表内容
	 * 
	 * 2014年10月8日 下午5:54:47 flyfox 330627517@qq.com
	 * 
	 * @param crud
	 * @param obj
	 * @return
	 */
	public static String listC(CRUD crud, Object obj) {
		return getStr(temPath + "temListContent.bee", "crud", crud, "item", obj);
	}

	/**
	 * 查看内容
	 * 
	 * 2014年10月8日 下午5:54:54 flyfox 330627517@qq.com
	 * 
	 * @param crud
	 * @param obj
	 * @return
	 */
	public static String view(CRUD crud, Object obj) {
		return getStr(temPath + "temView.bee", "crud", crud, "model", obj);
	}

	/**
	 * 查询内容
	 * 
	 * 2014年10月8日 下午5:55:02 flyfox 330627517@qq.com
	 * 
	 * @param crud
	 * @param obj
	 * @return
	 */
	public static String search(CRUD crud, Object obj) {
		return getStr(temPath + "temSearch.bee", "crud", crud, "attr", obj);
	}

	// /////////////////////////form组件//////////////////////////////
	public static String radioJson(String... objs) {
		return selJson(objs);
	}

	public static String radio(String jsonData, String name, String selected_value) {
		JSONArray array = (JSONArray) JSON.parse(jsonData);
		if (array == null) {
			return "";
		}

		List<TemplateUtilsObj> list = getBindList(array, name, selected_value);

		return getStr(temPath + "radio.bee", "obj", list);
	}

	public static String radioValue(String jsonData, String key) {
		return selValue(jsonData, key);
	}

	public static String checkboxJson(String... objs) {
		return selJson(objs);
	}

	public static String checkbox(String jsonData, String name, String selected_value) {
		JSONArray array = (JSONArray) JSON.parse(jsonData);
		if (array == null) {
			return "";
		}

		List<TemplateUtilsObj> list = getBindList(array, name, selected_value);

		return getStr(temPath + "checkbox.bee", "obj", list);
	}

	public static String checkboxValue(String jsonData, String key) {
		return selValue(jsonData, key);
	}

	// //////////////////////////select 方法/////////////////////////////////

	/**
	 * 拼接radio json字符串 由于JSONOject排序问题，只能用数组解决
	 * 
	 * 2014年10月8日 上午10:11:36 flyfox 330627517@qq.com
	 * 
	 * @param objs
	 * @return
	 */
	public static String selJson(String... objs) {
		// 没传或者是单数
		if (objs.length == 0 || objs.length % 2 != 0) {
			return null;
		}

		// fastjson为有序对象
		JSONArray array = new JSONArray();
		String key = "";
		String value = "";
		for (int i = 0; i < objs.length; i++) {
			if (i % 2 == 0) {
				key = objs[i];
			}
			if (i % 2 != 0) {
				value = objs[i];
				// 放入数组的json数据
				JSONObject tmp = new JSONObject();
				tmp.put(KEY, key);
				tmp.put(VALUE, value);
				array.add(tmp);
			}
		}

		// 放入JSON数据，Array有序
		return array.toString();
	}

	/**
	 * 获取select 选择框
	 * 
	 * 2014年10月8日 上午10:37:07 flyfox 330627517@qq.com
	 * 
	 * @param jsonData
	 * @param selected_value
	 * @return
	 */
	public static String sel(String jsonData, String selected_value) {
		JSONArray array = (JSONArray) JSON.parse(jsonData);
		if (array == null) {
			return "";
		}

		List<TemplateUtilsObj> list = getBindList(array, selected_value);

		return getStr(temPath + "select.bee", "obj", list);
	}

	/**
	 * 获取对应的值
	 * 
	 * 2014年10月8日 上午10:37:22 flyfox 330627517@qq.com
	 * 
	 * @param jsonData
	 * @param key
	 * @return
	 */
	public static String selValue(String jsonData, String key) {
		if (StrUtils.isEmpty(jsonData)) {
			return null;
		}
		JSONArray array = (JSONArray) JSON.parse(jsonData);
		if (array == null) {
			return "";
		}
		for (int i = 0; i < array.size(); i++) {
			JSONObject tmp = array.getJSONObject(i);
			if (tmp.getString(KEY).equals(key)) {
				return tmp.getString(VALUE);
			}
		}
		return "";
	}

	/**
	 * 获取模板绑定List
	 * 
	 * 2014年10月8日 下午4:31:18 flyfox 330627517@qq.com
	 * 
	 * @param selected_value
	 * @param array
	 * @return
	 */
	protected static List<TemplateUtilsObj> getBindList(JSONArray array, String selected_value) {
		return getBindList(array, null, selected_value);
	}

	/**
	 * 获取模板绑定List
	 * 
	 * 2014年10月8日 下午4:31:18 flyfox 330627517@qq.com
	 * 
	 * @param selected_value
	 * @param array
	 * @return
	 */
	protected static List<TemplateUtilsObj> getBindList(JSONArray array, String name, String selected_value) {
		List<TemplateUtilsObj> list = new ArrayList<TemplateUtilsObj>();
		TemplateUtilsObj obj;
		for (int i = 0; i < array.size(); i++) {
			JSONObject tmp = array.getJSONObject(i);
			obj = new TemplateUtilsObj();
			String key = tmp.getString(KEY);
			obj.setKey(key);
			if (StrUtils.isNotEmpty(name))
				obj.setName(name);
			obj.setValue(tmp.getString(VALUE));
			obj.setSelected(key.equals(selected_value) ? "true" : "false");
			list.add(obj);
		}
		return list;
	}

	public static String getStr(String template, Object... objs) {
		if (objs.length <= 0 || objs.length % 2 != 0) {
			return null;
		}

		String str = "";

		Template t = groupTemplate.getTemplate(template);
		Object name = "";
		Object value = "";
		for (int i = 0; i < objs.length; i++) {
			if (i % 2 == 0) {
				name = objs[i];
			}
			if (i % 2 != 0) {
				value = objs[i];
				t.binding(String.valueOf(name), value);
			}
		}

		str = t.render();
		return str;
	}

	public static String getTemPath() {
		return temPath;
	}
	/**
	 * 测试
	 * 
	 */
	public static void main(String[] args) {
		String select = selJson("1", "男", "3", "未知", "2", "女");
		System.out.println(select);
		System.out.println(sel(select, "1"));
		System.out.println(selValue(select, "2"));

		String radio = radioJson("2", "女", "3", "未知", "1", "男");
		System.out.println(radio);
		System.out.println(radio(radio, "sex", "1"));
		System.out.println(radioValue(radio, "1"));

		String checkbox = checkboxJson("3", "未知", "1", "男", "2", "女");
		System.out.println(checkbox);
		System.out.println(checkbox(checkbox, "sex", "1"));
		System.out.println(checkboxValue(checkbox, "3"));
	}

}
