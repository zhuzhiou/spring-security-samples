package samples.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import samples.security.entity.RolePo;
import samples.security.entity.UserPo;
import samples.security.service.RoleService;
import samples.security.service.UserService;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@RequestMapping("/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping(path = "/form")
    public String edit(@RequestParam(name = "userId", required = false) String userId, Model model) {
        List<RolePo> roles = roleService.getRoles();
        model.addAttribute("roles", roles);

        UserPo user = null;
        if (isNotBlank(userId)) {
            user = userService.getUser(userId);
        }
        if (user == null) {
            user = new UserPo();
        }
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

    @PostMapping(path = "/update")
    public String update(@ModelAttribute(name = "user") UserPo user) {
        userService.updateUser(user);
        return "redirect:/user/query";
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam(name = "userId") String userId) {
        userService.deleteUser(userId);
        return "redirect:/user/query";
    }
}
