package samples.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class SpringBootSampleHelloWorldApplication extends WebSecurityConfigurerAdapter {

    @GetMapping(path = "/")
    public String index() {
        return "index";
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .defaultSuccessUrl("/")
                .and()
                .logout()
                .and()
                //上面配置不要认证的，下面配置需要认证的
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSampleHelloWorldApplication.class, args);
    }
}
