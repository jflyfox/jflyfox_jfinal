package com.flyfox.jfinal.component.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.flyfox.jfinal.component.oauth.util.OathConfig;
import com.flyfox.util.StrUtils;

/**
 * Oauth 人人网
 * email: 596392912@qq.com
 * site:  http://www.dreamlu.net
 * @author L.cm
 * @date Jun 26, 2013 11:25:58 PM
 */
public class OauthRenren extends Oauth {

    private static final Logger LOGGER = Logger.getLogger(OauthRenren.class);
    
    private static final String AUTH_URL = "https://graph.renren.com/oauth/authorize";
    private static final String TOKEN_URL = "https://graph.renren.com/oauth/token";

    private static OauthRenren oauthRenren = new OauthRenren();

    /**
     * 用于链式操作
     * @return
     */
    public static OauthRenren me() {
        return oauthRenren;
    }
    
    public OauthRenren() {
        setClientId(OathConfig.getValue("openid_renren"));
        setClientSecret(OathConfig.getValue("openkey_renren"));
        setRedirectUri(OathConfig.getValue("redirect_renren"));
    }
    
    /**
     * 获取授权url
     * DOC： http://wiki.dev.renren.com/wiki/Authentication
     * @param display    page，iframe，popup，touch，mobile
     * @return String    返回类型
     * @throws UnsupportedEncodingException 
     */ 
    public String getAuthorizeUrl(String state, String display) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("response_type", "code");
        params.put("client_id", getClientId());
        params.put("redirect_uri", getRedirectUri());
        if (StrUtils.isNotEmpty(display)) {
            params.put("display", display);
        }
        if (StrUtils.isNotEmpty(state)) {
            params.put("state", state); //OAuth2.0标准协议建议，利用state参数来防止CSRF攻击。可存储于session或其他cache中
        }
        return super.getAuthorizeUrl(AUTH_URL, params);
    }

    /**
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     * 获取token
     * @param @param code
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */
    private String getTokenByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{
        Map<String, String> params = new HashMap<String, String>();
        params.put("code", code);
        params.put("client_id", getClientId());
        params.put("client_secret", getClientSecret());
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", getRedirectUri());
        String token = super.doPost(TOKEN_URL, params);
        LOGGER.debug(token);
        return token;
    }
    
    /**
     * 根据code一步获取用户信息
     * @param @param args    设定文件
     * @return void    返回类型
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */
    public JSONObject getUserInfoByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{
        String tokenInfo = getTokenByCode(code);
        if (StrUtils.isEmpty(tokenInfo)) {
            return null;
        }
        JSONObject json = JSONObject.parseObject(tokenInfo);
        String access_token = json.getString("access_token");
        if (StrUtils.isEmpty(access_token)) {
            return null;
        }
        JSONObject userJson = json.getJSONObject("user");
        userJson.put("access_token", access_token);
        LOGGER.debug(userJson);
        return userJson;
    }
}
