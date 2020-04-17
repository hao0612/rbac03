package cn.wolfcode.web.controller;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.service.IEmployeeService;
import cn.wolfcode.service.IPermissionService;
import cn.wolfcode.util.JsonResult;
import cn.wolfcode.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/login")
    @ResponseBody
    public JsonResult login(String username, String password, HttpSession session) {
        try {
            Employee employee = employeeService.login(username, password);
            //把当前登录用户存放到session中
            //  session.setAttribute("EMPLOYEE_IN_SESSION",employee);
            UserContext.setCurrentUser(employee);
            if (!employee.isAdmin()) { //如果不是管理员,就需要查询权限信息
                //把登录用户的权限数据存在session中
                List<String> permissions = permissionService.selectExpressionByEmpId(employee.getId());
                //  session.setAttribute("EXPRESSION_IN_SESSION", permissions);
                UserContext.setPermissionUser(permissions);
            }
            return new JsonResult();//登录成功
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(false, e.getMessage());//登陆失败
        }

    }

    @RequestMapping("/logout")

    public String logout(HttpSession session) {
        session.invalidate();//销毁绘画
        return "redirect:/login.html";
    }

}
