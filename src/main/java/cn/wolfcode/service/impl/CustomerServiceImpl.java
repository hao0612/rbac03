package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Customer;
import cn.wolfcode.domain.Department;
import cn.wolfcode.domain.Employee;
import cn.wolfcode.mapper.CustomerMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.ICustomerService;
import cn.wolfcode.util.UserContext;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.kerberos.KerberosKey;
import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public void save(Customer customer) {
        //获取当前登录用户
        Employee currentUser = UserContext.getCurrentUser();
        //设置当前登录用户为录入人,销售员
        customer.setInputUser(currentUser);
        customer.setSeller(currentUser);
        //设置录入时间
        customer.setInputTime(new Date());
        customerMapper.insert(customer);
    }

    @Override
    public void delete(Long id) {
        customerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Customer customer) {
        customerMapper.updateByPrimaryKey(customer);
    }

    @Override
    public Customer get(Long id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Customer> listAll() {
        return customerMapper.selectAll();
    }

    @Override
    public PageInfo<Customer> query(QueryObject qo) {
//使用分页插件,传入当前页,每页显示数量
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize(),"input_time desc" );
        List<Customer> customers = customerMapper.selectForList(qo);//sql里面不需要写limt

        return new PageInfo<Customer>(customers);
    }
}
