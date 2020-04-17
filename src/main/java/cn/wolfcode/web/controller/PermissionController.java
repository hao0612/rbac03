package cn.wolfcode.web.controller;

import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IPermissionService;
import cn.wolfcode.util.JsonResult;
import cn.wolfcode.util.RequestedPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @RequestedPermission("权限页面")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo) {
        model.addAttribute("PageInfo", permissionService.query(qo));
        return "/permission/list";
    }

    @RequestedPermission("权限删除")
    @RequestMapping("/delete")
    public String list(Long id) {
        if (id != null) {
            permissionService.delete(id);
        }
        return "redirect:/permission/list.do";
    }

    @RequestMapping("/reload")
    @ResponseBody//返回json
    public JsonResult reload() {
        permissionService.reload();//执行业务
        return new JsonResult(); //成功

    }


}
