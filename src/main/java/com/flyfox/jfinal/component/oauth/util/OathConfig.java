package com.flyfox.jfinal.component.oauth.util;

import com.flyfox.util.Config;

/**
 * Oauth 授权
 * @author L.cm
 * email: 596392912@qq.com
 * site:  http://www.dreamlu.net
 * @date Jun 24, 2013 9:58:25 PM
 */
public class OathConfig {

    public static String getValue(String key) {
        return Config.getStr(key);
    }
}
