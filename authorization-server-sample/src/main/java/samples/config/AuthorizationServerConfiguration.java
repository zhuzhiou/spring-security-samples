package samples.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import samples.security.service.ClientDetailsServiceImpl;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    /**
     * 改变clientAuthenticationScheme的默认值http_basic，设置为form
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //从安全角度考虑，建议使用http_basic。
        security.allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService());
    }

    /**
     * 如果不想使用默认uuid作为令牌值，可以自定义TokenEnhancer对token进行二次加工。
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //endpoints.tokenEnhancer()
    }

    @Bean
    public ClientDetailsService clientDetailsService() {
        ClientDetailsService clientDetailsService = new ClientDetailsServiceImpl();
        return clientDetailsService;
    }
}
