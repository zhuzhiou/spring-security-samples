package samples.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/user")
@Controller
public class UserController {

    @PostMapping(path = "/add")
    public String add() {
        return "form";
    }

    @GetMapping(path = "/list")
    public String list() {

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
