package com.flyfox.jfinal.component.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flyfox.jfinal.component.oauth.util.OathConfig;
import com.flyfox.jfinal.component.oauth.util.TokenUtil;
import com.flyfox.util.StrUtils;

/**
 * OauthBaidu
 * email: 596392912@qq.com
 * site:  http://www.dreamlu.net
 * @author L.cm
 * @date Jun 26, 2013 11:25:58 PM
 */
public class OauthBaidu extends Oauth {

    private static final Logger LOGGER = Logger.getLogger(OauthBaidu.class);

    private static final String AUTH_URL = "https://openapi.baidu.com/oauth/2.0/authorize";
    private static final String TOKEN_URL = "https://openapi.baidu.com/oauth/2.0/token";
    private static final String USER_INFO_URL = "https://openapi.baidu.com/rest/2.0/passport/users/getInfo";

    private static OauthBaidu oauthBaidu = new OauthBaidu();

    /**
     * 用于链式操作
     * @return
     */
    public static OauthBaidu me() {
        return oauthBaidu;
    }

    public OauthBaidu() {
        setClientId(OathConfig.getValue("openid_baidu"));
        setClientSecret(OathConfig.getValue("openkey_baidu"));
        setRedirectUri(OathConfig.getValue("redirect_baidu"));
    }

    /**
     * @throws UnsupportedEncodingException 
     * 获取授权url
     * DOC：http://developer.baidu.com/wiki/index.php?title=docs/oauth
     * @param @return    设定文件
     * @return String    返回类型
     * @throws
     */ 
    public String getAuthorizeUrl(String state) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("response_type", "code");
        params.put("client_id", getClientId());
        params.put("redirect_uri", getRedirectUri());
        if (StrUtils.isNotEmpty(state)) {
            params.put("state", state); //OAuth2.0标准协议建议，利用state参数来防止CSRF攻击。可存储于session或其他cache中
        }
        return super.getAuthorizeUrl(AUTH_URL, params);
    }
    
    /**
     * 获取token
     * @param @param code
     * @param @return    设定文件
     * @return String    返回类型
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */
    public String getTokenByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{
        Map<String, String> params = new HashMap<String, String>();
        params.put("code", code);
        params.put("client_id", getClientId());
        params.put("client_secret", getClientSecret());
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", getRedirectUri());
        String token = TokenUtil.getAccessToken(super.doPost(TOKEN_URL, params));
        LOGGER.debug(token);
        return token;
    }
    
    /**
     * 获取UserInfo
     * @return    设定文件
     * @return String    返回类型
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */
    public String getUserInfo(String accessToken) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException{
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", accessToken);
        return super.doPost(USER_INFO_URL, params);
    }
    
    /**
     * 根据code一步获取用户信息
     * @param @param args    设定文件
     * @return void    返回类型
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     */
    public JSONObject getUserInfoByCode(String code) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
        String accessToken = getTokenByCode(code);
        if (StrUtils.isEmpty(accessToken)) {
            return null;
        }
        String userInfo = getUserInfo(accessToken);
        JSONObject dataMap = JSON.parseObject(userInfo);
        dataMap.put("access_token", accessToken);
        LOGGER.debug(dataMap);
        return dataMap;
    }
}
