package com.flyfox.jfinal.config;

import static com.flyfox.util.Config.getStr;

import com.flyfox.jfinal.component.annotation.AutoBindModels;
import com.flyfox.jfinal.component.annotation.AutoBindRoutes;
import com.flyfox.jfinal.component.handler.BasePathHandler;
import com.flyfox.jfinal.component.handler.CurrentPathHandler;
import com.flyfox.jfinal.component.interceptor.ExceptionInterceptor;
import com.flyfox.jfinal.component.interceptor.SessionAttrInterceptor;
import com.flyfox.util.Config;
import com.flyfox.util.StrUtils;
import com.flyfox.util.cache.Cache;
import com.flyfox.util.cache.CacheManager;
import com.flyfox.util.cache.ICacheManager;
import com.flyfox.util.cache.impl.MemorySerializeCache;
import com.flyfox.util.serializable.FSTSerializer;
import com.flyfox.util.serializable.SerializerManage;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.interceptor.SessionInViewInterceptor;
import com.jfinal.kit.PathKit;
import com.jfinal.log.Log4jLoggerFactory;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.OracleDialect;
import com.jfinal.plugin.activerecord.dialect.PostgreSqlDialect;
import com.jfinal.plugin.activerecord.dialect.Sqlite3Dialect;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

/**
 * API引导式配置
 */
public class JflyfoxConfig extends JFinalConfig {

	private static final String CONFIG_WEB_ROOT = "{webroot}";

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		me.setDevMode(isDevMode());
		me.setViewType(ViewType.JSP); // 设置视图类型为Jsp，否则默认为FreeMarker
		me.setBaseViewPath("/pages");
		me.setError401View(Config.getStr("PAGES.401"));
		me.setError403View(Config.getStr("PAGES.403"));
		me.setLoggerFactory(new Log4jLoggerFactory());
		me.setError404View(Config.getStr("PAGES.404"));
		me.setError500View(Config.getStr("PAGES.500"));
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		// 自动绑定
		// 1.如果没用加入注解，必须以Controller结尾,自动截取前半部分为key
		// 2.加入ControllerBind的 获取 key
		me.add(new AutoBindRoutes());
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = null;

		String db_type = getStr("db_type") + ".";

		String webRoot = PathKit.getWebRootPath();
		String DBPath = webRoot + "\\WEB-INF\\";
		DBPath = StrUtils.replace(DBPath, "\\", "/");
		String jdbcUrl = getStr(db_type + "jdbcUrl");
		if (db_type.startsWith("sqlite")) {
			jdbcUrl = StrUtils.replaceOnce(jdbcUrl, CONFIG_WEB_ROOT, DBPath);
		}

		c3p0Plugin = new C3p0Plugin( //
				jdbcUrl, getStr(db_type + "user"), //
				getStr(db_type + "password").trim(), getStr(db_type + "driverClass"));

		me.add(c3p0Plugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		me.add(arp);
		if (isDevMode()) {
			arp.setShowSql(true);
		}

		// 数据库类型
		if (db_type.startsWith("postgre")) {
			arp.setDialect(new PostgreSqlDialect());
		} else if (db_type.startsWith("sqlite")) {
			arp.setDialect(new Sqlite3Dialect());
		} else if (db_type.startsWith("oracle")) {
			arp.setDialect(new OracleDialect());
			arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		}

		new AutoBindModels(arp);
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		// 异常拦截器，跳转到500页面
		me.add(new ExceptionInterceptor());
		// session model转换
		me.add(new SessionInViewInterceptor());
		// 设置session属性
		me.add(new SessionAttrInterceptor());
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		// 全路径获取
		me.add(new BasePathHandler(Config.getStr("PATH.BASE_PATH")));
		// 根目录获取
		me.add(new ContextPathHandler(Config.getStr("PATH.CONTEXT_PATH")));
		// 当前获取
		me.add(new CurrentPathHandler(Config.getStr("PATH.CURRENT_PATH")));
	}

	private boolean isDevMode() {
		return Config.getToBoolean("CONSTANTS.DEV_MODE");
	}

	@Override
	public void afterJFinalStart() {
		super.afterJFinalStart();

		// 初始化Cache为fst序列化
		SerializerManage.add("fst", new FSTSerializer());

		CacheManager.setCache(new ICacheManager() {

			public Cache getCache() {
				return new MemorySerializeCache(SerializerManage.get("fst"));
			}
		});
	}

	@Override
	public void beforeJFinalStop() {
		super.beforeJFinalStop();
	}

	public static void main(String[] args) {
		String jdbcUrl = "jdbc:sqlite://{webroot}jflyfox_blog.db";
		jdbcUrl = StrUtils.replaceOnce(jdbcUrl, CONFIG_WEB_ROOT,
				"D:\\Project\\workspace\\jflyfox_blog\\src\\main\\webapp\\WEB-INF\\");
		System.out.println(jdbcUrl);
	}

}
