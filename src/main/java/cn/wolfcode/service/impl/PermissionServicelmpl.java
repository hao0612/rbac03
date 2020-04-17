package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Permission;
import cn.wolfcode.mapper.PermissionMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IPermissionService;
import cn.wolfcode.util.RequestedPermission;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServicelmpl implements IPermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private ApplicationContext ctx; //spring 上下文对象

    @Override
    public void delete(Long id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Permission get(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Permission> listAll() {
        return permissionMapper.selectAll();
    }

    //调用mapper接口的实现类方法查询数据,封装成Page result 对象返回
    @Override
    public PageInfo<Permission> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Permission> permissions = permissionMapper.selectForList(qo);//sql里面不需要写limt

        return new PageInfo<Permission>(permissions);
    }

    @Override
    public void reload() {
        //获取数据库中所有的权限表达式
        List<String> expressions = permissionMapper.selectAllexpression();
        //根据注解从容器中获取多个bean对象
        Map<String, Object> beans = ctx.getBeansWithAnnotation(Controller.class);
        for (Object controller : beans.values()) {
            //获取controller字节码对象
            Class<?> clazz = controller.getClass();
            //获取每个controller中的方法
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                //判断方法上面是否有贴权限注解
                RequestedPermission annotation = method.getAnnotation(RequestedPermission.class);
                if (annotation != null) {
                    //如果有.就需要处理
                    //方式一
                    //String expression = annotation.expression();//权限表达式
                    //方式二

                    String simpleName = clazz.getSimpleName();//DepartmentController
                    simpleName = simpleName.replace("Controller", "");//Department
                    //uncapitalize方法可以把首字母变为小写
                    simpleName = StringUtils.uncapitalize(simpleName);//department
                    String methodName = method.getName();
                    String expression = simpleName + ":" + methodName;
                    //拿到权限注解中的属性,封装成权限对象
                    //判断表达式是否已经存在数据库.如果不存在才插入
                    if (!expressions.contains(expression)) {
                        String name = annotation.value();//权限名称
                        Permission permission = new Permission();
                        permission.setName(name);
                        permission.setExpression(expression);
                        //把权限保存到数据库中
                        permissionMapper.insert(permission);
                    }
                }
                //如果没有贴注解就不用处理
            }
        }
    }

    @Override
    public List<String> selectExpressionByEmpId(Long id) {
        return permissionMapper.selectExpressionByEmpId(id);
    }
}
