package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.mapper.EmployeeMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IEmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServicelmpl implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public void save(Employee employee, Long[] ids) {
        employeeMapper.insert(employee);
        //处理中间表
        if (ids != null && ids.length > 0) {
            for (Long rid : ids) {
                employeeMapper.insertRelation(employee.getId(), rid);
            }
        }
      /* if (ids != null && ids.length>0){
            employeeMapper.insertRelation2(employee.getId(), ids);
        }*/
    }

    @Override
    public void delete(Long id) {
        employeeMapper.deleteByPrimaryKey(id);
        //删除关联关系
        employeeMapper.deleteRelation(id);

    }

    @Override
    public void update(Employee employee, Long[] ids) {
        employeeMapper.updateByPrimaryKey(employee);
        //删除关联关系
        employeeMapper.deleteRelation(employee.getId());
        //重新关联关系
        if (ids != null && ids.length > 0) {
            employeeMapper.insertRelation2(employee.getId(), ids);
        }
    }

    @Override
    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> listAll() {
        return employeeMapper.selectAll();
    }

    //调用mapper接口的实现类方法查询数据,封装成Page result 对象返回
    @Override
    public PageInfo<Employee> query(QueryObject qo) {

        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<Employee> employee = employeeMapper.selectForList(qo);//sql里面不需要写limt

        return new PageInfo<Employee>(employee);
    }

    @Override
    public Employee login(String username, String password) {
        //根据username查询数据库是否存在
        Employee employee = employeeMapper.selectByusernameAndPassword(username, password);
        if (employee == null) {
            throw new RuntimeException("账号或密码不正确");
        }
        return employee;

    }

    @Override
    public void batchDelect(Long[] ids) {
        employeeMapper.batchDelect(ids);
    }

    @Override
    public Employee selectByName(String name) {
        return employeeMapper.selectByName(name);
    }
}
