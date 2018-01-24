package samples.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class SpringSecuritySampleApplication extends WebSecurityConfigurerAdapter {

    @GetMapping(path = {"/", "/index"})
    public String index() {
        return "index";
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("sample")
                .password("sample")
                .roles("USER");
        //不调用父类方法，配置文件的用户会被忽略掉。
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .and()
                .logout()
                .and()
                //上面配置忽略认证，下面配置需要认证的路径
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringSecuritySampleApplication.class, args);
    }
}
