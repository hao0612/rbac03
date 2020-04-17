package cn.wolfcode.web.controller;

import cn.wolfcode.domain.Department;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IDepartmentService;
import cn.wolfcode.util.JsonResult;
import cn.wolfcode.util.RequestedPermission;
import com.github.pagehelper.PageInfo;
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

    @RequestedPermission("部门页面")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo) {
        PageInfo<Department> pageResult = departmentService.query(qo);
        model.addAttribute("PageInfo", pageResult);
        return "/department/list";
    }

    @RequestedPermission("部门删除")
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult list(Long id) {

        if (id != null) {
            departmentService.delete(id);
        }
        return new JsonResult();

    }

    /* @RequestedPermission("部门添加页面")
     @RequestMapping("/input")
     public String input(Long id, Model model) {
         if (id != null) {
             //把修改的部门数据查询出来
             Department department = departmentService.get(id);
             model.addAttribute("department", department);
         }
         return "/department/input";
     }*/
    @RequestedPermission("部门新增/编辑页面")
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
