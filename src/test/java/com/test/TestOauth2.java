package com.test;


import com.flyfox.jfinal.component.oauth.OauthBaidu;
import com.flyfox.jfinal.component.oauth.OauthDouban;
import com.flyfox.jfinal.component.oauth.OauthOsc;
import com.flyfox.jfinal.component.oauth.OauthQQ;
import com.flyfox.jfinal.component.oauth.OauthRenren;
import com.flyfox.jfinal.component.oauth.OauthSina;
import com.flyfox.jfinal.component.oauth.util.Display;
import com.flyfox.jfinal.component.oauth.util.TokenUtil;

/**
 * Oauth 测试
 * @author L.cm
 * email: 596392912@qq.com
 * site:  http://www.dreamlu.net
 * @date Jun 24, 2013 9:58:25 PM
 */
public class TestOauth2 {

	/**
	 * 注意，测试时先·更改hosts·（具体可以google）
	 * 
	 * TokenUtil.randomState() OAuth2.0标准协议建议，利用state参数来防止CSRF攻击。可存储于session或其他cache中
	 * 
	 * 回调时比较 state 参数是否一致
	 */
	public static void main(String[] args) throws Exception {
		// qq test --------------------------------- //
		OauthQQ qq = OauthQQ.me();
		System.out.println(qq.getAuthorizeUrl(TokenUtil.randomState()));
//		System.out.println(qq.getUserInfoByCode("2D8402D86A3AC8EB3F18B06AF89E24E9"));
		// {is_yellow_year_vip=0, ret=0, figureurl_qq_1=http://q.qlogo.cn/qqapp/100413274/0559BF5ED23328377FBF6305874A41E4/40, nickname=DreamLu, figureurl_qq_2=http://q.qlogo.cn/qqapp/100413274/0559BF5ED23328377FBF6305874A41E4/100, yellow_vip_level=0, is_lost=0, msg=, figureurl_1=http://qzapp.qlogo.cn/qzapp/100413274/0559BF5ED23328377FBF6305874A41E4/50, vip=0, level=0, figureurl_2=http://qzapp.qlogo.cn/qzapp/100413274/0559BF5ED23328377FBF6305874A41E4/100, gender=男, is_yellow_vip=0, openid=0559BF5ED23328377FBF6305874A41E4, figureurl=http://qzapp.qlogo.cn/qzapp/100413274/0559BF5ED23328377FBF6305874A41E4/30, access_token=DE1775C57F7057DECC532F078D6A181D}
		// --------------------------------- //
		
		// sina test --------------------------------- //
		OauthSina sina = OauthSina.me();
		System.out.println(sina.getAuthorizeUrl(TokenUtil.randomState()));
//		System.out.println(sina.getUserInfoByCode("fac83d13da78e6deb4a8dbf3bfd3aa4a"));
		// {"access_token":"2.00DJtlyCz13oZEb054cc0f38UoN73B","allow_all_act_msg":false,"allow_all_comment":true,"avatar_hd":"http://tp2.sinaimg.cn/2730259221/180/39997798609/1","avatar_large":"http://tp2.sinaimg.cn/2730259221/180/39997798609/1","bi_followers_count":15,"block_app":0,"block_word":0,"city":"8","class":1,"created_at":"Tue May 15 12:04:58 +0800 2012","description":"活着就是折腾！生命不息，折腾不止！","domain":"lcm596392912","favourites_count":4,"follow_me":false,"followers_count":41,"following":false,"friends_count":99,"gender":"m","geo_enabled":true,"id":2730259221,"idstr":"2730259221","lang":"zh-cn","location":"北京 海淀区","mbrank":0,"mbtype":0,"name":"dreamlu_net","online_status":0,"profile_image_url":"http://tp2.sinaimg.cn/2730259221/50/39997798609/1","profile_url":"lcm596392912","province":"11","ptype":0,"remark":"","screen_name":"dreamlu_net","star":0,"status":{"attitudes_count":0,"comments_count":0,"created_at":"Fri Apr 04 07:01:19 +0800 2014","favorited":false,"id":3695528342923957,"idstr":"3695528342923957","in_reply_to_screen_name":"","in_reply_to_status_id":"","in_reply_to_user_id":"","mid":"3695528342923957","mlevel":0,"pic_urls":[],"reposts_count":0,"source":"<a href=\"http://app.weibo.com/t/feed/9ksdit\" rel=\"nofollow\">iPhone客户端</a>","text":"要不要这么叼 //@Python发烧友:图上就是这个数字吧？//@孤独的登山人:一年不见，node 性能咋这样了？ 记得以前单进程 100% cpu ， 8K+ qps 没问题的啊 @Python发烧友","truncated":false,"visible":{"list_id":0,"type":0}},"statuses_count":96,"url":"http://blog.sina.com.cn/u/2730259221","verified":false,"verified_reason":"","verified_type":-1,"weihao":""}
		// --------------------------------- //
		
		// baidu test --------------------------------- //
		OauthBaidu baidu = OauthBaidu.me();
		System.out.println(baidu.getAuthorizeUrl(TokenUtil.randomState()));
//		System.out.println(baidu.getUserInfoByCode("912ccdefe794ca3c98c0b07d19ace9de"));
		// {birthday=1989-11-27, constellation=11, sex=1, trade=5, userid=906798575, job=0, blood=0, education=7, marriage=1, portrait=eb3671713539363339323931323008, username=qq596392912, figure=7, userdetail=javaer, access_token=21.6f4e2de01c8f9b658bc5cacd21612dad.2592000.1399538921.906798575-606191}
		// --------------------------------- //
		
		// osc test --------------------------------- //
		// 1.构造请求url
        OauthOsc osc = OauthOsc.me();
        System.out.println(osc.getAuthorizeUrl(TokenUtil.randomState()));
//        System.out.println(osc.getUserInfoByCode("L2VMV3"));
        // {id=813039, email=596392912@qq.com, location=北京 海淀, name=孤独的3, gender=male, avatar=http://static.oschina.net/uploads/user/406/813039_50.jpg?t=1348824049000, access_token=a55f6c8d-1828-4fda-8142-eb4ffaa11664, url=http://my.oschina.net/qq596392912}

        // 2.根据code换取accessToken L0kOlo
//        String accessToken = osc.getTokenByCode("9XP3eY"); // 获取accessToken
		// 3.根据accessToken获取用户信息
//        System.out.println(osc.getUserInfo(accessToken));
//      System.out.println(new OauthOsc().tweetPub(accessToken, "测试动弹，啦啦啦...").toString());
		// --------------------------------- //
		
		// renren test --------------------------------- //
		OauthRenren o = OauthRenren.me();
		System.out.println(o.getAuthorizeUrl(TokenUtil.randomState(), Display.TOUCH.getType()));
//		System.out.println(o.getUserInfoByCode("bIXTvtjlXX0DyML7Ud2t2tfoGL8SP3yw").toString());
		// {id=402524326, name=卢春梦, avatar=[{"type":"avatar","url":"http://hdn.xnimg.cn/photos/hdn121/20110827/1320/h_head_IlGt_39d5000393232f76.jpg"},{"type":"tiny","url":"http://hdn.xnimg.cn/photos/hdn221/20110827/1325/tiny_2xdl_224366c019118.jpg"},{"type":"main","url":"http://hdn.xnimg.cn/photos/hdn121/20110827/1320/h_main_oI5W_39d5000393232f76.jpg"},{"type":"large","url":"http://hdn.xnimg.cn/photos/hdn121/20110827/1320/h_large_tgbz_39d5000393232f76.jpg"}], access_token=266088|6.3b212f7d5dfd72d9c32c781360af266e.2592000.1399539600-402524326}
		// --------------------------------- //
		
		// douban test --------------------------------- //
		OauthDouban db = OauthDouban.me();
		System.out.println(db.getAuthorizeUrl(TokenUtil.randomState()));
//		System.out.println(db.getUserInfoByCode("4ad0733f8c8601d9"));
		// --------------------------------- //
	}
}
