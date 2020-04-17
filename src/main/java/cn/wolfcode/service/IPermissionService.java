package cn.wolfcode.service;

import cn.wolfcode.domain.Permission;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IPermissionService {
    void delete(Long id);

    Permission get(Long id);

    List<Permission> listAll();

    //添加分页查询的方法
    PageInfo<Permission> query(QueryObject qo);


    void reload();

    List<String> selectExpressionByEmpId(Long id);

}
