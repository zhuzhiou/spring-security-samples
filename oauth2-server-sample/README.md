# oauth2-server-sample

安全认证笔记

## 一、WebSecurityConfigurer

在spring boot中使用spring security，只要加入@EnableWebSecurity注解就行了，使用WebSecurityConfigurerAdapter定制相关配置。

在spring boot中加入@EnableAuthorizationServer、@EnableResourceServer后，分别会导入ResourceServerConfiguration与AuthorizationServerSecurityConfiguration，而这2个类都是WebSecurityConfigurerAdapter的子类，在配置有相同逻辑功能的时候，就有先后处理的顺序。spring security对排序的源码在`org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration`，关键代码是`Collections.sort(webSecurityConfigurers, AnnotationAwareOrderComparator.INSTANCE);`

排序使用了spring的Order规则，具体如下：

优先检测是否实现了Ordered接口
然后检测是否有@Order注解

WebSecurityConfigurerAdapter使用了注解@Order(100)
AuthorizationServerSecurityConfiguration使用了注解@Order(0)
ResourceServerConfiguration则实现了Ordered接口，默认值为3

> 在spring boot中使用@EnableResourceServer，会根据ResourceServerProperties的filterOrder属性设置order值，这个属性的默认值 `SecurityProperties.ACCESS_OVERRIDE_ORDER - 1`; 因此需要设置security.oauth2.resource.filter-order为3，与预设的一致。 设置order值的源码在`org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerConfiguration`，关键代码是`config.setOrder(this.properties.getFilterOrder());`

## 二、clientAuthenticationScheme

clientAuthenticationScheme默认值为http_basic，可以继承`AuthorizationServerConfigurerAdapter`重载`public void configure(AuthorizationServerSecurityConfigurer security)`，编码`security.allowFormAuthenticationForClients();`设置为form。

> 如果clientAuthenticationScheme为http_basic，则客户端在获取令牌时，client_id与client_secret是通过头部authorization传递，而form则通过formData形式传递数据，如果客户端与服务端设置不一致，服务端会返回Bad client credentials等错误。

![获取访问令牌](https://raw.githubusercontent.com/zhuzhiou/oauth2-samples/master/oauth2server-sample/screenshot/Get_New_Access_Token.png)

![调用私有接口](https://raw.githubusercontent.com/zhuzhiou/oauth2-samples/master/oauth2server-sample/screenshot/New_OAuth2_Request.png)

## autoApprove

使用Authorization Code授权码模式，认证流程中有一环节是需要用户参与的，用户可以选择允许或拒绝，如果申请的权限范围较安全，服务端可以实现`org.springframework.security.oauth2.provider.ClientDetails`接口的`isAutoApprove(String scope)`方法，系统自动进行授权。