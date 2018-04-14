package samples.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 全部可以访问
     */
    @GetMapping("/sample1")
    @ResponseBody
    public String sample1() {
        return "sample1";
    }

    /**
     * 管理员角色可以访问
     */
    @GetMapping("/sample2")
    @ResponseBody
    public String sample2() {
        return "sample2";
    }

    /**
     * 登陆用户可以访问
     */
    @GetMapping("/sample3")
    @ResponseBody
    public String sample3() {
        return "sample3";
    }

    /***
     * 用户角色可以访问
     */
    @GetMapping("/sample4")
    @ResponseBody
    public String sample4() {
        return "sample4";
    }
}
