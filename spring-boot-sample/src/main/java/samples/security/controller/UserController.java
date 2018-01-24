package samples.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/user")
@Controller
public class UserController {

    @PostMapping(path = "/create")
    public String create() {
        return "user/create";
    }

    @GetMapping(path = "/query")
    public String query() {
        System.out.println("123faf");
        return "user/list";
    }

    @PostMapping(path = "/update")
    public String update() {
        return "user/update";
    }

    @GetMapping(path = "/delete")
    public String delete() {
        return "redirect:/admin/user/query";
    }
}
