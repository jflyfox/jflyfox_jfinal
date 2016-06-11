jflyfox_jfinal
------------------------

jflyfox_jfinal是对Jfinal和beetl进行封装。

1. 包含controller，model，form，service基础类封装。
2. 对分页进行了后台和前台的实现。
3. 加入了自动扫描model和controller以及注解支持。
4. 实现了ueditor后台Controller代码。
5. 加入了页面增删改查代码自动生成功能，可通过beetl模板进行配置。
6. 实现了SessionAttrInterceptor、页面和手机设备判断拦截器以及BasePathHandler。

# 更新说明

>## 2016-01-16
> 1.将版本升级为4.0，目录重构，jfinal升级为2.1，日志使用jfinal Log

>## 2016-01-14
> 1.将版本升级为3.2，加入handlerUtils，可以处理请求没有前缀的model

>## 2014-11-29
> 1.将版本升级为3.0，jfinal升级为2.0版本。

>## 2014-06-04
> 1.将版本升级为2.1，全面删除模板功能，易于初学者学习。

>## 2014-04-24
> 1.将版本升级为2.0，删除自动生成。

>## 2014-04-24
> 1.将版本升级为1.9，仍兼容以前版本。
> 2.最后一个保留增删改查，自动生成的版本。后续将废弃，独立门户。

>## 2014-01-08
> 1. 本框架有CRUD模块，希望可以将增、删、改、查的前台代码进行统一管理，不再需要重复编写。
> 2. 由于个性化需求的存在，现在CRUD模块加入了自动生成代码功能，实现类AutoCreate。
> 3. AutoCreate通过template.properties进行配置模板路径和输出路径。
> 4. AutoCreate的实现通过beetl实现，对原有默认便签进行重新配置，以适应各种版本引擎的生成。
> 5. AutoCreate使用开始：#，结束：#（#  #）进行代码块标示，开始：@{，结束：}（@{ }）进行标签标示。
> 6. 生成路径默认在项目下的autopath目录下，以urlKey作为下一级目录，生成add.html,edit.html,view.html和list.html。
> 7. 示例实现是生成beetl模板，使用在本人OSC@GIT的jmoney项目。

鸣谢
------------------------

 1. [JFinal](http://www.oschina.net/p/jfinal)
 2. [beetl](http://ibeetl.com/community/)
 3. [oschina](http://www.oschina.net/)

开源赞助
------------------------

* 支付宝支付

![jflyfox](http://ww1.sinaimg.cn/mw690/3fc7e281jw1eqec436tzwj2074074mxr.jpg "Open source support(alipay)")

* 微信支付

![jflyfox](http://ww1.sinaimg.cn/mw690/3fc7e281jw1es3jr0k25xj20a50a5q3v.jpg "Open source support(weixin)")
