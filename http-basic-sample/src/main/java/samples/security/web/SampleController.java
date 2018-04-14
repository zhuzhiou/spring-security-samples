package samples.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhuzhiou
 */
@Controller
public class SampleController {

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
    @RequestMapping(path = "/sample2", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String sample2() {
        return "sample2";
    }

    /**
     * 登陆用户可以访问
     */
    @RequestMapping(path = "/sample3", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String sample3() {
        return "sample3";
    }

    /***
     * 用户角色可以访问
     */
    @RequestMapping(path = "/sample4", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String sample4() {
        return "sample4";
    }
}
