package cn.wolfcode.util;

import cn.wolfcode.domain.Employee;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


public abstract class UserContext {
/**
 *
 * 从subject获取当前登录用户
 */

   public static Employee getCurrentUser(){
       Subject subject = SecurityUtils.getSubject();
       return (Employee)subject.getPrincipal();
   }


}
