package cn.wolfcode.web.controller;

import cn.wolfcode.domain.Department;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IDepartmentService;
import cn.wolfcode.util.JsonResult;
import cn.wolfcode.util.RequestedPermission;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

   // @RequestedPermission("部门页面")
    @RequestMapping("/list")
    @RequiresPermissions(value = {"department:list","部门页面"},logical=Logical.OR)
    public String list(Model model, @ModelAttribute("qo") QueryObject qo) {
        PageInfo<Department> pageResult = departmentService.query(qo);
        model.addAttribute("PageInfo", pageResult);
        return "/department/list";
    }

   // @RequestedPermission("部门删除")
   @RequiresPermissions(value = {"department:delete","部门删除"},logical=Logical.OR)
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult list(Long id) {

        if (id != null) {
            departmentService.delete(id);
        }
        return new JsonResult();

    }
   // @RequestedPermission("部门新增/编辑页面")
    @RequiresPermissions(value = {"department:saveOrUpdate","部门新增/编辑页面"},logical=Logical.OR)
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Department department) {

        if (department.getId() != null) {
            departmentService.update(department);
        } else {
            departmentService.save(department);
        }
        return new JsonResult();
    }
}
