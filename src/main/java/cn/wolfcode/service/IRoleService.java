package cn.wolfcode.service;

import cn.wolfcode.domain.Role;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IRoleService {
    //添加分页查询的方法
    PageInfo<Role> query(QueryObject qo);


    List<Role> listAll();

    Role getById(Long id);

    void update(Role role, Long[] ids);

    void save(Role role, Long[] ids);

    void delete(Long id);
}
