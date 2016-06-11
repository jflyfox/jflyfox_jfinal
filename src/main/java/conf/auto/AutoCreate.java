package conf.auto;

import java.io.File;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;

import com.flyfox.jfinal.template.CRUD;
import com.flyfox.jfinal.template.TemplateUtils;
import com.flyfox.util.Config;
import com.flyfox.util.FileUtils;

public class AutoCreate {

	protected static final String AUTOPATH_STRING = Config.getStr("template.path");
	protected static final String AUTOPATH_OUTPUT = System.getProperty("user.dir") + "/"
			+ Config.getStr("template.output.path");
	protected static GroupTemplate groupTemplate = BeeltFunctions.groupTemplate;
	public static Map<String, CRUD> crudMap;
	public static String suffix = "html";

	public static void main(String[] args) throws Exception {
		crudMap = TableConfig.crudMap;
		createCode();
	}

	public static void createCode() throws Exception {
		if (crudMap == null) {
			System.err.println("###请参考conf.auto.TableConfig配置数据信息。");
			return;
		}
		System.out.println("###生成模板开始...");
		init();
		String path, html;
		for (CRUD crud : crudMap.values()) {
			path = AUTOPATH_OUTPUT + "/" + crud.getUrlKey();
			if (!new File(path).exists()) {
				new File(path).mkdirs();
			}

			html = TemplateUtils.getStr(AUTOPATH_STRING + Config.getStr("template.add") //
			, "crud", crud);
			FileUtils.write(path + "/" + "add." + suffix, html.getBytes("UTF-8"));

			html = TemplateUtils.getStr(AUTOPATH_STRING + Config.getStr("template.edit") //
			, "crud", crud);
			FileUtils.write(path + "/" + "edit." + suffix, html.getBytes("UTF-8"));

			html = TemplateUtils.getStr(AUTOPATH_STRING + Config.getStr("template.list") //
			, "crud", crud);
			FileUtils.write(path + "/" + "list." + suffix, html.getBytes("UTF-8"));

			html = TemplateUtils.getStr(AUTOPATH_STRING + Config.getStr("template.view") //
			, "crud", crud);
			FileUtils.write(path + "/" + "view." + suffix, html.getBytes("UTF-8"));
		}
		System.out.println("###生成模板完成。");
	}

	public static void init() {
		groupTemplate.registerFunctionPackage("flyfox", BeeltFunctions.class);
		Configuration cfg = groupTemplate.getConf();
		cfg.setStatementStart("#");
		cfg.setStatementEnd("#");
		cfg.setPlaceholderStart("@{");
		cfg.setPlaceholderEnd("}");
	}
}
