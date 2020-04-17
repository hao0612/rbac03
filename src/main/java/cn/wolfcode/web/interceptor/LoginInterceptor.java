package cn.wolfcode.web.interceptor;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.util.UserContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取当前登录用户
        HttpSession session = request.getSession();
        // Object employee = session.getAttribute("EMPLOYEE_IN_SESSION");
        Employee employee = UserContext.getCurrentUser();
        //如果不为空,代表有登陆过
        if (employee != null) {
            return true;
        }
        //重定向到登录页面
        response.sendRedirect("/login.html");
        return false;
    }
}
