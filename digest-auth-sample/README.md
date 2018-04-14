# 摘要认证示例

Spring Security提供了一个 DigestAuthenticationFilter，它可以处理HTTP头部中的摘要认证证书。 摘要认证在尝试着解决许多基本认证的缺陷，特别是保证不会通过纯文本发送证书。 许多用户支持摘要式认证，包括FireFox和IE。 HTTP摘要式认证的执行标准定义在RFC 2617，它是对RFC 2069这个早期摘要式认证标准的更新。 Spring Security DigestAuthenticationFilter会保证"auth"的安全质量（qop），它订明在RFC 2617中，并与RFC 2069提供了兼容。 如果你需要使用没有加密的HTTP（比如没有TLS/HTTP），还希望认证达到最大的安全性的时候，摘要式认证便具有很高吸引力。

本示例没用使用默认的过滤器链，因此需要显式的配置`ExceptionTranslationFilter`、`RequestCacheAwareFilter`

## ExceptionTranslationFilter

`o.s.s.web.access.ExceptionTranslationFilter`默认使用`o.s.s.web.authentication.Http403ForbiddenEntryPoint`处理需要登陆的情况，除了`o.s.s.web.authentication.Http403ForbiddenEntryPoint`，还有`o.s.s.web.authentication.LoginUrlAuthenticationEntryPoint`、`o.s.s..oauth2.provider.error.OAuth2AuthenticationEntryPoint`、`o.s.s.web.authentication.www.BasicAuthenticationEntryPoint`，本例使用`o.s.s.web.authentication.www.DigestAuthenticationEntryPoint`。

## RequestCacheAwareFilter

在处理认证异常的时候，都会将当前的请求保存在会话中，登陆成功后，该过滤器重新恢复因为登录被打断的请求。无状态的`RESTFul`应用应设置为`o.s.s.web.savedrequest.NullRequestCache`。