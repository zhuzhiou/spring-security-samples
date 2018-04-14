package samples.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestAttributes;

import java.util.Map;

@SpringBootApplication
public class DigestAuthSample {

    public static void main(String[] args) {
        SpringApplication.run(DigestAuthSample.class, args);
    }

    /**
     * 自定义错误信息内容
     */
    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
                Map<String, Object> map = super.getErrorAttributes(requestAttributes, includeStackTrace);
                map.remove("timestamp");
                map.remove("status");
                map.remove("path");
                map.remove("error");
                map.remove("exception");
                map.put("success", false);
                return map;
            }
        };
    }
}
