package cn.wolfcode.service;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IEmployeeService {
    void save(Employee employee, Long[] ids);

    void delete(Long id);

    void update(Employee employee, Long[] ids);

    Employee get(Long id);

    List<Employee> listAll();

    //添加分页查询的方法
    PageInfo<Employee> query(QueryObject qo);


    Employee login(String username, String password);

    void batchDelete(Long[] ids);

    Employee selectByName(String name);

    Workbook exportXls();

    void importXls(MultipartFile file) throws Exception;

    List<Employee> selectByRoleSn(String ... sns);
}
