package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Department;
import cn.wolfcode.mapper.DepartmentMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IDepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServicelmpl implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;


    @Override
    public void save(Department department) {
        departmentMapper.insert(department);
    }

    @Override
    public void delete(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Department department) {
        departmentMapper.updateByPrimaryKey(department);
    }

    @Override
    public Department get(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Department> listAll() {
        return departmentMapper.selectAll();
    }

    //调用mapper接口的实现类方法查询数据,封装成Page result 对象返回
    @Override
    public PageInfo<Department> query(QueryObject qo) {
//使用分页插件,传入当前页,每页显示数量
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Department> department = departmentMapper.selectForList(qo);//sql里面不需要写limt

        return new PageInfo<Department>(department);
    }
}
