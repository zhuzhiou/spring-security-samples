package samples.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import samples.security.entity.UserPo;
import samples.security.service.UserService;

import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/add")
    public String add(Model model) {
        UserPo user = new UserPo();
        model.addAttribute("user", user);
        return "user/form";
    }

    @PostMapping(path = "/create")
    public String create(@ModelAttribute UserPo user) {
        userService.createUser(user);
        return "redirect:/user/query";
    }

    @GetMapping(path = "/query")
    public String query(Model model) {
        List<UserPo> users =  userService.getUsers();
        model.addAttribute("users", users);
        return "user/table";
    }

    @GetMapping(path = "/edit")
    public String edit(@RequestParam(name = "userName") String userName, Model model) {
        UserPo user = userService.getUser(userName);
        model.addAttribute("user", user);
        return "user/form";
    }

    @PostMapping(path = "/update")
    public String update(@ModelAttribute(name = "user") UserPo user) {
        userService.updateUser(user);
        return "redirect:/user/query";
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam(name = "userName") String userName) {
        userService.deleteUser(userName);
        return "redirect:/user/query";
    }
}
