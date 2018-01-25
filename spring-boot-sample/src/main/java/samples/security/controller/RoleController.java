package samples.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import samples.security.entity.RolePo;
import samples.security.service.RoleService;

import java.util.List;

@RequestMapping("/role")
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping(path = "/add")
    public String add(Model model) {
        RolePo role = new RolePo();
        model.addAttribute("role", role);
        return "role/form";
    }

    @PostMapping(path = "/create")
    public String create(@ModelAttribute RolePo role) {
        roleService.createRole(role);
        return "redirect:/role/query";
    }

    @GetMapping(path = "/query")
    public String query(Model model) {
        List<RolePo> roles =  roleService.getRoles();
        model.addAttribute("roles", roles);
        return "role/table";
    }

    @GetMapping(path = "/edit")
    public String edit(@RequestParam(name = "roleName") String roleName, Model model) {
        RolePo role = roleService.getRole(roleName);
        model.addAttribute("role", role);
        return "role/form";
    }

    @PostMapping(path = "/update")
    public String update(@ModelAttribute(name = "role") RolePo role) {
        roleService.updateRole(role);
        return "redirect:/role/query";
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam(name = "roleName") String roleName) {
        roleService.deleteRole(roleName);
        return "redirect:/role/query";
    }
}
