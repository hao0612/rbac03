package cn.wolfcode.web.controller;

import cn.wolfcode.domain.Department;
import cn.wolfcode.domain.Employee;
import cn.wolfcode.domain.Role;
import cn.wolfcode.qo.EmployeeQueryObject;
import cn.wolfcode.service.IDepartmentService;
import cn.wolfcode.service.IEmployeeService;
import cn.wolfcode.service.IRoleService;
import cn.wolfcode.util.JsonResult;
import cn.wolfcode.util.RequestedPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IRoleService roleService;

    // @RequestedPermission(value = "员工页面", expression = "department:list")
    @RequestedPermission("员工页面")
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") EmployeeQueryObject qo) {
        model.addAttribute("PageInfo", employeeService.query(qo));
        //查询所有部门数据,存模型中
        List<Department> departments = departmentService.listAll();
        model.addAttribute("departments", departments);
        return "/employee/list";
    }

    //  @RequestedPermission(value = "员工删除", expression = "department:delete")
    @RequestedPermission("员工删除")

    @RequestMapping("/delete")
    public String delete(Long id) {
        if (id != null) {
            employeeService.delete(id);
        }
        return "redirect:/employee/list.do";
    }


    // @RequestedPermission(value = "员工插入", expression = "department:input")
    @RequestedPermission("员工插入")

    @RequestMapping("/input")
    public String input(Long id, Model model) {
        // 查询所有角色数据
        List<Role> roles = roleService.listAll();
        model.addAttribute("roles", roles);
        //查询所有部门数据,存在模型中
        List<Department> departments = departmentService.listAll();
        model.addAttribute("departments", departments);
        if (id != null) {
            //把修改的部门数据查询出来
            Employee employee = employeeService.get(id);
            model.addAttribute("employee", employee);
        }
        return "/employee/input";
    }

    //   @RequestedPermission(value = "员工增加/编辑页面", expression = "department:saveOrUpdate")
    @RequestedPermission("员工增加/编辑页面")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Employee employee, Long[] ids) {


        if (employee.getId() != null) {
            employeeService.update(employee, ids);
        } else {
            employeeService.save(employee, ids);
        }
        return new JsonResult();

    }


    @RequestedPermission("员工批量删除")
    @RequestMapping("/batchDelect")
    @ResponseBody
    public JsonResult batchDelect(/*@RequestParam("ids[]"),*/Long[] ids) {

        if (ids != null && ids.length > 0) {
            employeeService.batchDelect(ids);
        }
        return new JsonResult();
    }

    //插件要求返回结果需要为键值对形式 key为valid  ，value为boolean类型
    //   valid : true    代表验证通过(该用户名不存在)
    // valid：false  代表验证不通过(用户名已经存在)
    @RequestMapping("/checkName")
    @ResponseBody
    public Map<String, Boolean> checkname(String name, Long id) {//id为nul代表新增,id不为null代表修改
        /*//根据用户名查询数据库是否已经存在
        Employee employee = employeeService.selectByName(name);
        Map<String , Boolean> map = new HashMap<>();
        map.put("valid",employee==null);
        return map;*/
        Map<String, Boolean> map = new HashMap<>();
        if (id != null) {//编辑
            Employee employee = employeeService.get(id);
            if (name.equals(employee.getName())) {//名字相同
                map.put("valid", true);
                return map;
            }
        }
        //根据用户名查询数据库是否已经存在
        Employee employee = employeeService.selectByName(name);
        map.put("valid", employee == null);
        return map;

    }


}
