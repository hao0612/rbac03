package cn.wolfcode.web.controller;


import cn.wolfcode.domain.Customer;
import cn.wolfcode.domain.Department;
import cn.wolfcode.domain.Employee;
import cn.wolfcode.qo.CustomerQuery;
import cn.wolfcode.service.ICustomerService;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.util.JsonResult;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.image.Kernel;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;


    @RequiresPermissions(value = {"customer:potentialList","潜在客户管理页面"},logical = Logical.OR)
    @RequestMapping("/potentialList")

        public String list(Model model, @ModelAttribute("qo") CustomerQuery qo) {
        //查询潜在客户状态数据
        qo.setStatus(Customer.STATUS_COMMON);
        //管理员或者经理进来潜在客户页面,可以看到所有,如果普通销售人员只能看自己
        Subject subject = SecurityUtils.getSubject();
        if (!(subject.hasRole("Admin")||subject.hasRole("Market_Manager"))){
            Employee employee = (Employee) subject.getPrincipal();
            qo.setSellerId(employee.getId());
        }
            PageInfo<Customer> pageResult = customerService.query(qo);
            model.addAttribute("PageInfo", pageResult);
            //查询销售人员的下拉框数据

        return "customer/potentialList";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"customer:delete","潜在客户管理删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            customerService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"customer:saveOrUpdate","潜在客户管理新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(Customer customer){
        if (customer.getId() != null) {
            customerService.update(customer);
        }else {
            customerService.save(customer);
        }
        return new JsonResult();
    }
}
