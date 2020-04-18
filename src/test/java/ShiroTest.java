import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

public class ShiroTest {
    @Test
        public void  testshiro() throws Exception {
        //加载shiro.ini配置文件，得到配置中的用户信息（账号+密码）

        IniSecurityManagerFactory factory =
         new IniSecurityManagerFactory("classpath:shiro.ini"); //创建Shiro的安全管理器
        SecurityManager securityManager = factory.getInstance();//将创建的安全管理器添加到运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();//无论有没有登录都可以拿到对象
        System.out.println("认证状态"+subject.isAuthenticated());
        //创建登录的令牌
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("lisi", "666");


        //执行登录
        subject.login(usernamePasswordToken);
        System.out.println("认证状态"+subject.isAuthenticated());
        //判断用户是否有rolel角色
        System.out.println("判断用户是否有role1的角色"+subject.hasRole("role1"));
        System.out.println("判断用户是否有role2的角色"+subject.hasRole("role2"));
        System.out.println("判断用户是否同时有role2角色和rloe1角色"+subject.hasAllRoles(Arrays.asList("role1", "role2")));
        //抛异常的方法
      //  subject.checkRole("role1");
        System.out.println("-----------------");
        subject.checkRole("role2");
        //判断用户是否有某个权限
        System.out.println("-----------------");
        System.out.println("判断用户是否有user:update权限"+subject.isPermitted("user:update"));
        System.out.println("判断用户是否有user:delete权限"+subject.isPermitted("user:delete"));
        //抛异常的方法
        System.out.println("-----------------");
       subject.checkPermission("user:delete");
        //注销
        subject.logout();
        System.out.println("认证状态"+subject.isAuthenticated());
        //用户名错误报错org.apache.shiro.authc.UnknownAccountException
        //密码错误报错org.apache.shiro.authc.IncorrectCredentialsException:
    }

}
