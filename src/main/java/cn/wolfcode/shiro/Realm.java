package cn.wolfcode.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class Realm extends AuthorizingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //准备数据
        String username= "admin";
        String password = "1";
        //判断token里面的用户名是否存在,是否与username相同 ,getprincipal()方法其实就是获取token里面用户名
        if(username.equals(authenticationToken.getPrincipal())){
            //用户名,密码,当前数据源的名字(标记)
            return new SimpleAuthenticationInfo(username,password,"realm");

        }
        return null;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


}
