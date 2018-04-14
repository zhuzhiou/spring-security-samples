# 摘要认证示例

Basic Access Authentication scheme是在HTTP1.0提出的认证方法，它是一种基于challenge/response的认证模式，针对特定的realm需要提供用户名和密码认证后才可访问，其中密码使用明文传输。

Basic模式认证过程如下：

①浏览器发送http报文请求一个受保护的资源。

②服务端的web容器将http响应报文的响应码设为401，响应头部加入WWW-Authenticate: Basic realm=”xxx”。

③浏览器会弹出默认对话框让用户输入用户名和密码，并用Base64进行编码，实际是用户名+冒号+密码进行Base64编码，即Base64(username:password)，然后浏览器就会在HTTP报文头部加入这个编码，形如：Authorization: Basic YWRtaW46YWRtaW4=。

④服务端web容器获取HTTP报文头部相关认证信息，匹配此用户名与密码是否正确，是否有相应资源的权限，如果认证成功则返回相关资源，否则再执行②，重新进行认证。

⑤以后每次访问都要带上认证头部。

Basic认证的优点是基本上所有流行的网页浏览器都支持，一般被用在受信赖的或安全性要求不高的系统中（如路由器配置页面的认证，tomcat管理界面认证）

缺点是，用户名密码基本等于是明文传输带来很大风险，并且没有注销认证信息的手段，只能依靠关闭浏览器退出认证。

HTTP基本认证由过滤器链中的BasicAuthenticationFilter处理，对应的类名字是org.springframework.security.web.authentication.www.BasicAuthenticationFilter。

核心代码如下：

```
String header = request.getHeader("Authorization");

if (header == null || !header.startsWith("Basic ")) {
    chain.doFilter(request, response);
    return;
}

try {
    String[] tokens = extractAndDecodeHeader(header, request);
    assert tokens.length == 2;
    String username = tokens[0];
    if (authenticationIsRequired(username)) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, tokens[1]);
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
        Authentication authResult = this.authenticationManager.authenticate(authRequest);

        SecurityContextHolder.getContext().setAuthentication(authResult);
        this.rememberMeServices.loginSuccess(request, response, authResult);
        onSuccessfulAuthentication(request, response, authResult);
    }

} catch (AuthenticationException failed) {
    SecurityContextHolder.clearContext();
    this.rememberMeServices.loginFail(request, response);
    onUnsuccessfulAuthentication(request, response, failed);
    if (this.ignoreFailure) {
        chain.doFilter(request, response);
    } else {
        this.authenticationEntryPoint.commence(request, response, failed);
    }
    return;
}
```

认证异常，由`BasicAuthenticationEntryPoint`处理，核心代码如下：

```
response.addHeader("WWW-Authenticate", "Basic realm=\"" + realmName + "\"");
response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
```

> 如果请求没有`Authorization`头，不进入`BasicAuthenticationFilter`过滤器，但最后会由`ExceptionTranslationFilter`统一处理认证异常的情况，`ExceptionTranslationFilter`同样是使用`AuthenticationEntryPoint`处理认证异常的。在配置`httpBasic`时会创建`BasicAuthenticationEntryPoint`对象并将此`AuthenticationEntryPoint`设置到ExceptionTranslationFilter，详细源码查看`HttpBasicConfigurer`的`registerDefaultEntryPoint`方法。

## csrf

跨站请求伪造，由于目标站无token/referer限制，导致攻击者可以用户的身份完成操作达到各种目的。RESTful Web服务供第3方使用，只要考虑认证就行了，因此项目中取消配置CSRF攻击防御。

## stateless

主要是在RESTful API，无状态的web调用的stateless authentication。

这个配置的意思是：Spring Security对登录成功的用户不会创建Session了，你的application也不会允许新建session，而且Spring Security会跳过所有的 filter chain：HttpSessionSecurityContextRepository, SessionManagementFilter, RequestCacheFilter. 

也就是说每个请求都是无状态的独立的，需要被再次认证re-authentication。开销显然是增大了，因为每次请求都必须在服务器端重新认证并建立用户角色和权限的上下文。

大家知道，Spring Security在认证的过程中，Spring Security会运行一个过滤器（SecurityContextPersistenceFilter）来存储请求的Security Context，这个上下文的存储是一个策略模式，但默认的是保存在HTTP Session中的HttpSessionSecurityContextRepository。现在我们设置了 create-session=”stateless”，就会保存在NullSecurityContextRepository，里面没有任何session在上下文中保持。既然没有为何还要调用这个空的filter？因为需要调用这个filter来保证每次请求完了SecurityContextHolder被清空了，下一次请求必须re-authentication。

## 主流的浏览器都支持`http basic`认证

![IE浏览器](https://raw.githubusercontent.com/zhuzhiou/oauth2-samples/develop/http-basic-sample/screenshot/Internet%20Explorer.png)

![Edge浏览器](https://raw.githubusercontent.com/zhuzhiou/oauth2-samples/develop/http-basic-sample/screenshot/Microsoft%20Edge.png)

![谷歌浏览器](https://raw.githubusercontent.com/zhuzhiou/oauth2-samples/develop/http-basic-sample/screenshot/Google%20Chrome.png)

![火狐浏览器](https://raw.githubusercontent.com/zhuzhiou/oauth2-samples/develop/http-basic-sample/screenshot/Mozilla%20Firefox.png)