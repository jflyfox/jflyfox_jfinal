package conf.auto;

import com.flyfox.jfinal.template.CRUD;
import com.flyfox.jfinal.template.TemplateFunctions;

public class BeeltFunctions extends TemplateFunctions {

	private static final String BASE_PATH = "/conf/auto/template/";

	// //////////////////////////数据字典///////////////////////////////////////////
	// //////////////////////增删改查///////////////////////
	/**
	 * 添加
	 * 
	 * 2014年10月8日 下午5:54:26 flyfox 330627517@qq.com
	 * 
	 * @param crud
	 * @return
	 */
	public static String add(CRUD crud) {
		return getStr(BASE_PATH + "temAdd.html", "crud", crud);
	}

	/**
	 * 编辑
	 * 
	 * 2014年10月8日 下午5:54:31 flyfox 330627517@qq.com
	 * 
	 * @param crud
	 * @return
	 */
	public static String edit(CRUD crud) {
		return getStr(BASE_PATH + "temEdit.html", "crud", crud);
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
		return getStr(BASE_PATH + "temListHead.html", "crud", crud);
	}

	/**
	 * 列表内容
	 * 
	 * 2014年10月8日 下午5:54:47 flyfox 330627517@qq.com
	 * 
	 * @param crud
	 * @return
	 */
	public static String listC(CRUD crud) {
		return getStr(BASE_PATH + "temListContent.html", "crud", crud);
	}

	/**
	 * 查看内容
	 * 
	 * 2014年10月8日 下午5:54:54 flyfox 330627517@qq.com
	 * 
	 * @param crud
	 * @return
	 */
	public static String view(CRUD crud) {
		return getStr(BASE_PATH + "temView.html", "crud", crud);
	}

	/**
	 * 查询内容
	 * 
	 * 2014年10月8日 下午5:55:02 flyfox 330627517@qq.com
	 * 
	 * @param crud
	 * @return
	 */
	public static String search(CRUD crud) {
		return getStr(BASE_PATH + "temSearch.html", "crud", crud);
	}

}
