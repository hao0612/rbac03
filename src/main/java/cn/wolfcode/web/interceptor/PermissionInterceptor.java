package cn.wolfcode.web.interceptor;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.util.RequestedPermission;
import cn.wolfcode.util.UserContext;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1获取当前登录用户
        //  Employee employee = (Employee)request.getSession().getAttribute("EMPLOYEE_IN_SESSION");
        Employee employee = UserContext.getCurrentUser();
        //2判断是否超级管理员,是就放行
        if (employee.isAdmin()) {
            return true;
        }
        //3.获取当前要访问的方法（controller里面的某个方法） department/list.do
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //4.判断该方法上是否有贴注解 没有贴就放行
        RequestedPermission annotation = handlerMethod.getMethodAnnotation(RequestedPermission.class);
        if (annotation == null) {
            return true;
        }
        // 5.获取该方法对应的权限表达式（跟之前权限加载的方式一致）
        //  String expression = annotation.expression();
        //通过反射来获取权限表达式
        String simpleName = handlerMethod.getBean().getClass().getSimpleName().replace("Controller", "");
        String expression = StringUtils.uncapitalize(simpleName) + ":" + handlerMethod.getMethod().getName();
        // 6.获取当前登录用户自身已经拥有的权限表达式列表


        //List permissions = (List) request.getSession().getAttribute("EXPRESSION_IN_SESSION");
        List<String> permissions = UserContext.getPermissionUser();
        // 7.判断该方法对应的权限表达式是否存在用户已经拥有的权限表达式列表中 如果存在就放行
        if (permissions.contains(expression)) {
            return true;
        }
        // 8.不存在就跳转到错误页面*/
        request.getRequestDispatcher("/WEB-INF/views/common/nopermission.jsp").forward(request, response);
        return false;
    }
}
