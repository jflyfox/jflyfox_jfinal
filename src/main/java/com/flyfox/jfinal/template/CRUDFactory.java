package com.flyfox.jfinal.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flyfox.util.StrUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class CRUDFactory {

	private static final CRUDFactory factory = new CRUDFactory();
	private final Map<String, CRUD> crudMap = new HashMap<String, CRUD>();

	private CRUDFactory() {
	}

	public static CRUDFactory instance() {
		return factory;
	}

	public CRUDFactory add(Class<?> cls, CRUD crud) {
		crudMap.put(cls.getName(), crud);
		return this;
	}

	public CRUD get(Class<?> cls) {
		CRUD crud = crudMap.get(cls.getName());
		// 拼接数据
		Map<String, String> select = crud.getSelectMap();
		String selectData = null;
		for (Map.Entry<String, String> entry : select.entrySet()) {
			ModelAttr modelAttr = crud.getMap().get(entry.getKey());
			if (modelAttr.getFormType() == FormType.SELECT) {
				JSONObject json = JSON.parseObject(modelAttr.getFormTypeData());
				String sql = json.getString("sql");
				// 设置设置key，value的Json数据
				if (StrUtils.isEmpty(sql)) {
					selectData = modelAttr.getFormTypeData();
				} else {
					String key = json.getString("key");
					String value = json.getString("value");
					selectData = getJsonData(sql, key, value);
				}
			} else if (modelAttr.getFormType() == FormType.DICT) {
				String sql = "select detail_id as key,detail_name as value from sys_dict_detail ";
				sql += " where dict_type = '" + modelAttr.getFormTypeData() + "'";
				String key = "key";
				String value = "value";
				selectData = getJsonData(sql, key, value);
			}

			select.put(entry.getKey(), selectData);

		}

		return crud;
	}

	private String getJsonData(String sql, String key, String value) {
		if (StrUtils.isEmpty(sql)) {
			return "";
		}

		List<Record> list = Db.find(sql);
		JSONObject dataJson = new JSONObject();
		for (Record record : list) {
			Object keyTmp = record.get(key);
			Object valueTmp = record.get(value);
			dataJson.put(String.valueOf(keyTmp), String.valueOf(valueTmp));
		}
		return dataJson.toJSONString();
	}

	public CRUDFactory clear() {
		return this;
	}

}
