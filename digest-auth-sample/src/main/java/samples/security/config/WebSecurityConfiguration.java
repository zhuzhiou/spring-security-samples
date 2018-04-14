package samples.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.annotation.Jsr250Voter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;

import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * 禁用默认的过滤器，注：这里仅是示范，建议尽量使用默认的配置，因为boot做了很多
     * 初始化工作，boot定义的各种SecurityConfigurer也足够定制各种需求。但boot并未为
     * DigestAuthenticationEntryPoint提供Configurer，其他自定义的认证体系，也可以参
     * 考本文件的配置。
     */
    public WebSecurityConfiguration() {
        super(true);
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public DigestAuthenticationEntryPoint authenticationEntryPoint() {
        DigestAuthenticationEntryPoint authenticationEntryPoint = new DigestAuthenticationEntryPoint();
        authenticationEntryPoint.setRealmName("practice");
        authenticationEntryPoint.setKey("secret");
        authenticationEntryPoint.setNonceValiditySeconds(300);
        return authenticationEntryPoint;
    }

    @Bean
    public Filter digestAuthenticationFilter() {
        DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
        filter.setAuthenticationEntryPoint(authenticationEntryPoint());
        filter.setUserDetailsService(userDetailsService);
        filter.setCreateAuthenticatedToken(false);// 不带权限代码
        return filter;
    }

    @Autowired
    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .addFilter(digestAuthenticationFilter())
            .exceptionHandling()
                // 默认的authenticationEntryPoint是Http403ForbiddenEntryPoint
                .authenticationEntryPoint(authenticationEntryPoint())
        .and()
            // 不创建会话
            .requestCache()
                .requestCache(new NullRequestCache())
        .and()
            .authorizeRequests()
                .anyRequest()
                .authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O filterSecurityInterceptor) {
                        filterSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource);
                        AccessDecisionManager accessDecisionManager = new AffirmativeBased(Arrays.asList(new AuthenticatedVoter(), new RoleVoter(), new Jsr250Voter()));
                        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager);
                        return filterSecurityInterceptor;
                    }
                });
        // @formatter:on
    }
}
