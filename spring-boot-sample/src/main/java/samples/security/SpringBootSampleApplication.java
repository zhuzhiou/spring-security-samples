package samples.security;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.DataSource;

@SpringBootApplication
@Controller
public class SpringBootSampleApplication extends WebSecurityConfigurerAdapter {

    @GetMapping(path = "/admin")
    public String index() {
        return "index";
    }

    @Bean
    TimeBasedGenerator timeBasedGenerator() {
        return Generators.timeBasedGenerator(EthernetAddress.fromInterface());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder("secret");
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder)
                .dataSource(dataSource);
                //.withDefaultSchema()hsql脚本
                //.withUser("admin")
                //.password(passwordEncoder.encode("admin"))
                //.roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .defaultSuccessUrl("/admin")
                .and()
                .logout()
                .and()
                //上面配置不要认证的，下面配置需要认证的
                .authorizeRequests()
                .antMatchers("/admin/**")
                .authenticated();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSampleApplication.class, args);
    }
}
