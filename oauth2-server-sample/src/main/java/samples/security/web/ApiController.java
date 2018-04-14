package samples.security.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class ApiController {

    /**
     * 全部可以访问
     */
    @GetMapping("/sample1")
    public String sample1() {
        return "sample1";
    }

    /**
     * 管理员角色可以访问
     */
    @GetMapping("/sample2")
    public String sample2() {
        return "sample2";
    }

    /**
     * 登陆用户可以访问
     */
    @GetMapping("/sample3")
    public String sample3() {
        return "sample3";
    }

    /***
     * 用户角色可以访问
     */
    @GetMapping("/sample4")
    public String sample4() {
        return "sample4";
    }
}
