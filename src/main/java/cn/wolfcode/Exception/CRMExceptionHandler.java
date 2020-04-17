package cn.wolfcode.Exception;

import cn.wolfcode.util.JsonResult;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * 对控制器进行增强
 *
 * */
@ControllerAdvice
public class CRMExceptionHandler {
    /**
     * 捕获运行过程中出现的运行时异常
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception ex, HandlerMethod handlerMethod, HttpServletResponse response) throws IOException {//进入该方法,就代表捕获到异常
        //把异常信息打印出,方便我们开发的时候找bug
        ex.printStackTrace();
        //handlerMethod就是出现异常的处理方法
        //判断当前执行的方法是否是ajax的方法(@ResponseBody)
        if (handlerMethod.hasMethodAnnotation(ResponseBody.class)) {//如果是,则返回jsonresult
            JsonResult json = new JsonResult(false, "系统异常,请联系管理员");
            //使用response对象输出数据(json字符串)
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(json));
            return null;
        } else {//如果不是,则返回错误视图
            return "/common/error";
        }


    }

}
