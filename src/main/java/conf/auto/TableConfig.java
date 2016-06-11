package conf.auto;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.flyfox.jfinal.template.CRUD;
import com.flyfox.jfinal.template.FormType;
import com.flyfox.jfinal.template.InputType;
import com.flyfox.jfinal.template.ModelAttr;

public class TableConfig {

	public final static Map<String, CRUD> crudMap = new HashMap<String, CRUD>();

	static {
		init();
	}

	public static void init() {
		// 联系人管理
		CRUD contact = new CRUD();
		contact.setPrimaryKey("id");
		contact.setUrlKey("contact");
		contact.setName("联系人");
		contact.setAttr(new ModelAttr().setKey("name").setName("姓名").addSearch()
				.setFormTypeVaild("required='required'"));
		contact.setAttr(new ModelAttr().setKey("phone").setName("手机号"));
		contact.setAttr(new ModelAttr().setKey("email").setName("Email").setInputType(InputType.EMAIL));
		contact.setAttr(new ModelAttr().setKey("addr").setName("地址").removeList());
		contact.setAttr(new ModelAttr().setKey("birthday").setName("生日").removeList().setInputType(InputType.DATE));
		contact.setAttr(new ModelAttr().setKey("remark").setName("说明").setFormType(FormType.TEXTAREA));
		add(contact);

		// 金钱管理
		JSONObject select = new JSONObject();
		select.put("sql", "select id,name from tb_project");
		select.put("key", "id");
		select.put("value", "name");

		CRUD money = new CRUD();
		money.setPrimaryKey("id");
		money.setUrlKey("money");
		money.setName("金钱");
		money.setAttr(new ModelAttr().setKey("project_id").setName("项目名称") //
				.setFormType(FormType.SELECT).setFormTypeData("project_name"));
		money.setAttr(new ModelAttr().setKey("name").setName("描述"));
		money.setAttr(new ModelAttr().setKey("amount").setName("金额(RMB)"));
		money.setAttr(new ModelAttr().setKey("type").setName("类型") //
				.setFormType(FormType.DICT).setFormTypeData("moneyType"));
		money.setAttr(new ModelAttr().setKey("pay_time").setName("时间") //
				.setInputType(InputType.DATE));
		money.setAttr(new ModelAttr().setKey("remark").setName("备注").setFormType(FormType.TEXTAREA));
		add(money);

		// 项目管理
		CRUD project = new CRUD();
		project.setPrimaryKey("id");
		project.setUrlKey("project");
		project.setName("项目");
		project.setAttr(new ModelAttr().setKey("name").setName("项目名称").addSearch()
				.setFormTypeVaild("required='required'"));
		project.setAttr(new ModelAttr().setKey("remark").setName("说明").setFormType(FormType.TEXTAREA));
		add(project);

		// 用户管理
		CRUD user = new CRUD();
		user.setPrimaryKey("userid");
		user.setUrlKey("user");
		user.setName("用户");
		user.setAttr(new ModelAttr().setKey("username").setName("登陆名").addSearch()
				.setFormTypeVaild("required='required'"));
		user.setAttr(new ModelAttr().setKey("realname").setName("真实姓名").addSearch());
		add(user);
	}

	protected static void add(CRUD contact) {
		crudMap.put(contact.getUrlKey(), contact);
	}

}
