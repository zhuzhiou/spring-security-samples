# 表单认证示例

## ExceptionHandlingConfigurer

`o.s.s.config.annotation.web.configurers.HttpBasicConfigurer.registerDefaultEntryPoint`、`o.s.s.config.annotation.web.configurers.FormLoginConfigurer.registerDefaultAuthenticationEntryPoint`这2个方法，都有调用 `o.s.s.config.annotation.web.configurers。ExceptionHandlingConfigurer.defaultAuthenticationEntryPointFor`。只要配置了这2者中的任意一个，默认的`AuthenticationEntryPoint`不会是`Http403ForbiddenEntryPoint`。
```
public ExceptionHandlingConfigurer<H> defaultAuthenticationEntryPointFor(
        AuthenticationEntryPoint entryPoint, RequestMatcher preferredMatcher) {
    this.defaultEntryPointMappings.put(preferredMatcher, entryPoint);
    return this;
}

private AuthenticationEntryPoint createDefaultEntryPoint(H http) {
    if (defaultEntryPointMappings.isEmpty()) {
        return new Http403ForbiddenEntryPoint();
    }
    if (defaultEntryPointMappings.size() == 1) {
        return defaultEntryPointMappings.values().iterator().next();
    }
    DelegatingAuthenticationEntryPoint entryPoint = new DelegatingAuthenticationEntryPoint(
            defaultEntryPointMappings);
    entryPoint.setDefaultEntryPoint(defaultEntryPointMappings.values().iterator()
            .next());
    return entryPoint;
}
```

## LogoutFilter

