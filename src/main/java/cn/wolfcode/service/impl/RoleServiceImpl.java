package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Role;
import cn.wolfcode.mapper.RoleMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;


    @Override
    public PageInfo<Role> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Role> role = roleMapper.selectForList(qo);//sql里面不需要写limt

        return new PageInfo<Role>(role);
    }

    @Override
    public List<Role> listAll() {

        return roleMapper.selectAll();
    }

    @Override
    public Role getById(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Role role, Long[] ids) {
        //删除关系
        roleMapper.deleteRelation(role.getId());

        //更新
        roleMapper.updateByPrimaryKey(role);
        //处理中间表
        if (ids != null && ids.length > 0) {
            for (Long pid : ids) {
                roleMapper.insertRelation(role.getId(), pid);
            }
        }
    }

    @Override
    public void save(Role role, Long[] ids) {
        roleMapper.insert(role);
        //处理中间表
        if (ids != null && ids.length > 0) {
            for (Long pid : ids) {
                roleMapper.insertRelation(role.getId(), pid);
            }
        }
    }

    @Override
    public void delete(Long id) {

        roleMapper.deleteByPrimaryKey(id);
    }
}
