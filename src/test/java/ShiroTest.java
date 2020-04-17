import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

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
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhangsan", "5155");


        //执行登录
        subject.login(usernamePasswordToken);
        System.out.println("认证状态"+subject.isAuthenticated());
        //注销
        subject.logout();
        System.out.println("认证状态"+subject.isAuthenticated());
        //用户名错误报错org.apache.shiro.authc.UnknownAccountException
        //密码错误报错org.apache.shiro.authc.IncorrectCredentialsException:
    }

}
