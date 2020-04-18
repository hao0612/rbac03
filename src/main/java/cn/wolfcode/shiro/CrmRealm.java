package cn.wolfcode.shiro;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.mapper.EmployeeMapper;
import cn.wolfcode.mapper.PermissionMapper;
import cn.wolfcode.mapper.RoleMapper;
import com.github.pagehelper.PageHelper;
import jdk.nashorn.internal.objects.AccessorPropertyDescriptor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@Component("crmRealm")
public class CrmRealm extends AuthorizingRealm {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //准备数据
      Employee employee=  employeeMapper.selectByName((String) token.getPrincipal());
        //判断token里面的用户名是否存在,是否与username相同 ,getprincipal()方法其实就是获取token里面用户名
        if(employee != null){
            //用户名,密码, 盐,当前数据源的名字(标记)
            return new SimpleAuthenticationInfo(employee,employee.getPassword(),
                    ByteSource.Util.bytes(employee.getName()),"realm");

        }
        return null;
    }
    /*提供角色权限信息
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("-------------开始查询-----------");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前用户id
     //   Subject subject = SecurityUtils.getSubject();
       // Employee employee = (Employe) subject.getPrincipal();

        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();
        if(employee.isAdmin()){//是管理员
            info.addStringPermission("*:*");
            info.addRole("Admin");

        }else{
            //根据员工id查询该员工拥有的权限
            List<String> permissions = permissionMapper.selectExpressionByEmpId(employee.getId());
            info.addStringPermissions(permissions);
            //根据员工id查询该员工拥有的角色
            List<String> role= roleMapper.selectSnByEmpId(employee.getId());
            info.addRoles(role);
        }
        System.out.println("-------------结束查询-----------");

        return info;






       /* info.addRole("Hr");
        info.addStringPermission("employee:list");
        return info;*/




    }


}
