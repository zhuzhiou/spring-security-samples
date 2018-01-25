# 最简配置

步骤简单，适合学习但不适用于生产。

## 1. 依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
</dependencies>
```
## 2.默认用户

```yaml
security:
    basic:
        enabled: false
    user:
        name: admin
        password: admin
        role: ADMIN
```

## 代码

```java
@SpringBootApplication
@Controller
public class SpringBootSampleApplication extends WebSecurityConfigurerAdapter {

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
        SpringApplication.run(SpringBootSampleApplication.class, args);
    }
}
```

运行以上代码即可打开登陆界面，输入用户名字密码（admin/admin）登陆成功，跳转到欢迎页。