package cn.wolfcode.shiro;

import cn.wolfcode.util.JsonResult;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Component("ajaxFormAuthenticationFilter")
public class AjaxFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    //
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {

        JsonResult json = new JsonResult();
        //使用response对象输出数据(json字符串)
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().print(JSON.toJSONString(new JsonResult()));
        return false;

    }


    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        JsonResult json = new JsonResult(false, "登录失败,请联系管理员");
        //使用response对象输出数据(json字符串)
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().print(JSON.toJSONString(json));
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        return false;
    }
}
