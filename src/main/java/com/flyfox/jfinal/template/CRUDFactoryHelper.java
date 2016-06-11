package com.flyfox.jfinal.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.flyfox.util.Config;
import com.flyfox.util.StrUtils;

/**
 * 对CRUD 实现XML生成和解析
 * 
 * 2014年10月11日 下午2:32:14 flyfox 330627517@qq.com
 */
public class CRUDFactoryHelper {

	private final static URL classPathUrl = Config.class.getResource("/");
	private final static String classPath = new File(classPathUrl.getFile()).getPath();
	private static String configPath = "\\conf\\model";
	private static String parsePath = classPath + configPath;
	private static String createPath = "data";

	CRUDFactoryHelper() {
	}

	static {
		// 读取配置文件，没有就用默认值
		String modelConfigPath = Config.getStr("modelPath");
		if (StrUtils.isNotEmpty(modelConfigPath)) {
			parsePath = classPath + modelConfigPath;
		}
		String dateConfigPath = Config.getStr("dataPath");
		if (StrUtils.isNotEmpty(dateConfigPath)) {
			createPath = dateConfigPath;
		}
	}

	/**
	 * 解析XML数据，转换成CRUD
	 * 
	 * 2014年10月11日 上午9:42:50 flyfox 330627517@qq.com
	 */
	static void parse() {
		List<String> list = findFiles(parsePath);
		for (String fileName : list) {
			parseFile(parsePath + "\\" + fileName);
		}

	}

	/**
	 * 根据CRUD生成对应的XML
	 * 
	 * 2014年10月11日 上午9:43:05 flyfox 330627517@qq.com
	 */
	static void create() {
		for (String key : CRUDFactory.instance().crudMap.keySet()) {
			creatXml(key, CRUDFactory.instance().crudMap.get(key));
		}
	}

	/**
	 * 解析CURD对象
	 * 
	 * 2014年10月11日 上午9:43:33 flyfox 330627517@qq.com
	 * 
	 * @param fileName
	 * @return
	 */
	private static boolean parseFile(String fileName) {

		boolean returnValaue = false;
		String clsName;
		SAXReader reader = new SAXReader();

		try {
			CRUD crudObj = new CRUD();
			Document docement = reader.read(new File(fileName));
			docement.getRootElement();
			Element crud = docement.getRootElement().element("crud");

			@SuppressWarnings("unchecked")
			List<Element> models = docement.getRootElement().element("models").elements("model");

			clsName = crud.elementText("cls");
			crudObj.setName(crud.elementText("name"));
			crudObj.setPrimaryKey(crud.elementText("primaryKey"));
			crudObj.setUrlKey(crud.elementText("urlKey"));

			for (Element modelObj : models) {
				ModelAttr attr = new ModelAttr();
				attr.setKey(modelObj.elementText("key"));
				attr.setName(modelObj.elementText("name"));
				String formType = modelObj.elementText("formType");
				if (StrUtils.isNotEmpty(formType)) {
					attr.setFormType(FormType.valueOf(formType.toUpperCase()));
				}
				String formTypeData = modelObj.elementText("formTypeData");
				if (StrUtils.isNotEmpty(formTypeData)) {
					attr.setFormTypeData(formTypeData);
				}
				String formTypeVaild = modelObj.elementText("formTypeVaild");
				if (StrUtils.isNotEmpty(formTypeVaild)) {
					attr.setFormTypeVaild(formTypeVaild);
				}
				String inputType = modelObj.elementText("inputType");
				if (StrUtils.isNotEmpty(inputType)) {
					attr.setInputType(InputType.valueOf(inputType.toUpperCase()));
				}
				String operate = modelObj.elementText("operate");
				if (StrUtils.isNotEmpty(operate)) {
					attr.setOperate(Byte.parseByte(operate, 2));
				}
				crudObj.setAttr(attr);

			}

			CRUDFactory.instance().add(clsName, crudObj);
			return returnValaue = true;

		} catch (DocumentException e) {
			System.out.println("xml(" + fileName + ")解析异常！");
			e.printStackTrace();
		}

		return returnValaue;
	}

	/**
	 * 查找当前文件下所有properties文件
	 * 
	 * 2014年10月11日 上午9:43:47 flyfox 330627517@qq.com
	 * 
	 * @param baseDirName
	 * @return
	 */
	private static List<String> findFiles(String baseDirName) {
		List<String> configFiles = new ArrayList<String>();
		// 判断目录是否存在
		File baseDir = new File(baseDirName);
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			System.err.println("search error：" + baseDirName + "is not a dir！");
		} else {
			String[] filelist = baseDir.list();
			for (String fileName : filelist) {
				if (fileName.endsWith("xml")) {
					configFiles.add(fileName);
				}
			}
		}
		return configFiles;
	}

	/**
	 * 根据CRUD创建XML
	 * 
	 * 2014年10月11日 上午9:44:13 flyfox 330627517@qq.com
	 * 
	 * @param clsName
	 * @param crudObj
	 * @return
	 */
	private static boolean creatXml(String clsName, CRUD crudObj) {
		boolean returnValue = false;
		// 定义XML文档
		Document docement = DocumentHelper.createDocument();
		// 设置XML文档的元
		Element rootElement = docement.addElement("nodes");
		rootElement.addComment("创建各个节点信息");
		Element crud = rootElement.addElement("crud");

		Element cls = crud.addElement("cls");
		cls.setText(clsName);
		Element name = crud.addElement("name");
		name.setText(crudObj.getName());
		Element primaryKey = crud.addElement("primaryKey");
		primaryKey.setText(crudObj.getPrimaryKey());
		Element urlKey = crud.addElement("urlKey");
		urlKey.setText(crudObj.getUrlKey());

		Element models = rootElement.addElement("models");
		for (String key : crudObj.getMap().keySet()) {
			ModelAttr modelObj = crudObj.getMap().get(key);
			Element model = models.addElement("model");
			model.addElement("key").addText(modelObj.getKey());
			model.addElement("name").addText(modelObj.getName());
			model.addElement("formType").addText(modelObj.getFormType().toString());
			model.addElement("inputType").addText(modelObj.getInputType().toString());
			model.addElement("formTypeVaild").addText(modelObj.getFormTypeVaild());
			Element formTypeData = model.addElement("formTypeData");
			formTypeData.addText(modelObj.getFormTypeData());
			// formTypeData.addElement("sql").addText("select id,org_name from tb_hr_organizations order by sort ");
			// formTypeData.addElement("key").addText("id");
			// formTypeData.addElement("value").addText("org_name");
			model.addComment("查询，列表，添加，编辑，查看；默认：01111");
			model.addElement("operate").addText(Integer.toString(modelObj.getOperate(), 2));
		}

		// 创建XML文档
		try {
			/** 格式化输,类型IE浏览 */
			OutputFormat format = OutputFormat.createPrettyPrint();
			/** 指定XML编码 */
			format.setEncoding("UTF-8");

			String filePath = createPath + "\\" + crudObj.getUrlKey() + ".xml";
			if (!new File(createPath).exists()) {
				new File(createPath).mkdirs();
			}
			XMLWriter writer = new XMLWriter(new FileWriter(new File(filePath)), format);
			writer.write(docement);
			writer.close();
			return returnValue = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnValue;
	}
}
