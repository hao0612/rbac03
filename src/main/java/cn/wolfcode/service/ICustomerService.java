package cn.wolfcode.service;

import cn.wolfcode.domain.Customer;
import cn.wolfcode.domain.Department;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICustomerService {
    void save(Customer customer);
    void delete(Long id);
    void update(Customer customer);
    Customer get(Long id);
    List<Customer> listAll();
    //添加分页查询的方法
    PageInfo<Customer> query(QueryObject qo);
}
