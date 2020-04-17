package cn.wolfcode.web.controller;


import cn.wolfcode.domain.Permission;
import cn.wolfcode.domain.Role;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IPermissionService;
import cn.wolfcode.service.IRoleService;
import cn.wolfcode.util.RequestedPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;


    @Autowired
    private IPermissionService permissionService;

    @RequestedPermission("角色增加")

    @RequestMapping("/list")
    private String lest(Model model, @ModelAttribute("qo") QueryObject qo) {
        model.addAttribute("PageInfo", roleService.query(qo));
        return "/role/list";
    }

    @RequestMapping("/input")
    @RequestedPermission("角色编辑页面")

    public String input(Long id, Model model) {


        //查询所有的权限
        List<Permission> permissions = permissionService.listAll();
        model.addAttribute("permissions", permissions);
        if (id != null) {
            Role role = roleService.getById(id);
            model.addAttribute("role", role);
        }
        return "/role/input";
    }

    @RequestedPermission("角色编辑页面")
    @RequestMapping("/saveOrUpdate")
    public String saveOrUpdate(Role role, Long[] ids) {
        if (role.getId() != null) {
            roleService.update(role, ids);
        } else {
            roleService.save(role, ids);
        }
        return "redirect:/role/list.do";

    }

    @RequestedPermission("角色删除")

    @RequestMapping("/delete")
    public String list(Long id) {
        if (id != null) {
            roleService.delete(id);
        }
        return "redirect:/role/list.do";
    }
}
