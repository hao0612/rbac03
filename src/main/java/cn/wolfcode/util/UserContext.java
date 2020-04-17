package cn.wolfcode.util;

import cn.wolfcode.domain.Employee;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


public abstract class UserContext {

    public static final String EMPLOYEE_IN_SESSION = "EMPLOYEE_IN_SESSION";
    public static final String EXPRESSION_IN_SESSION = "EXPRESSION_IN_SESSION";

    /*
     * 获取session方法
     * */
    public static HttpSession getSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest().getSession();
    }

    /*
     * 从session取出当前用户
     * */
    public static Employee getCurrentUser() {
        return (Employee) getSession().getAttribute(EMPLOYEE_IN_SESSION);
    }

    /*
     *
     * 设置当前登录用户到session中
     * @param employee
     * */
    public static void setCurrentUser(Employee employee) {
        //Springmvc提供的获取请求相关属性的工具类(可以很方便在任意地方获取 request response session)
        getSession().setAttribute(EMPLOYEE_IN_SESSION, employee);
    }

    /*
     *  从session获取登录用户的权限信息
     *
     * */
    public static List<String> getPermissionUser() {
        return (List<String>) getSession().getAttribute(EXPRESSION_IN_SESSION);
    }

    /*
     *  往session存入登录用户的权限信息
     *
     * */
    public static void setPermissionUser(List<String> permissions) {
        getSession().setAttribute(EXPRESSION_IN_SESSION, permissions);
    }


}
