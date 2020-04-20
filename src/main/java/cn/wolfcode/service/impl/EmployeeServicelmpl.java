package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Employee;
import cn.wolfcode.mapper.EmployeeMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IEmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class EmployeeServicelmpl implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public void save(Employee employee, Long[] ids) {
        //密码加密,使用用户名作为加密的“盐”
        employee.setPassword(
                new Md5Hash(employee.getPassword(),employee.getName()).toString());
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
    public void batchDelete(Long[] ids) {
        employeeMapper.batchDelete(ids);
    }

    @Override
    public Employee selectByName(String name) {

        return employeeMapper.selectByName(name);
    }
//导出
    @Override
    public Workbook exportXls(){
        //创建excel文件
       Workbook wb = new HSSFWorkbook();
        //创建sheet
        Sheet sheet = wb.createSheet("员工名单");
        //标题行
        Row row = sheet.createRow(0);
        //设置内容到单元格中
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("邮箱");
        row.createCell(2).setCellValue("年龄");
        //查询员工数据
        List<Employee> employees = employeeMapper.selectAll();
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            //创建行(每个员工就是一行)
            row = sheet.createRow(i+1);
            //设置内容到单元格中
            row.createCell(0).setCellValue(employee.getName());
            row.createCell(1).setCellValue(employee.getEmail());
            row.createCell(2).setCellValue(employee.getAge());
        }
        return wb;
    }

    @Override
    public void importXls(MultipartFile file) throws Exception {
       //读excel
        Workbook wb = new HSSFWorkbook(file.getInputStream());
    //读一张表格
        Sheet sheet = wb.getSheetAt(0);
        //读行
        int lastRowNum = sheet.getLastRowNum();
        for(int i=1; i<=lastRowNum;i++){
            Row row = sheet.getRow(i);//每一行就是一条数据
            Employee employee = new Employee();
            String name = row.getCell(0).getStringCellValue();
            employee.setName(name);
            String Email = row.getCell(0).getStringCellValue();
            employee.setEmail(Email);
            //获取单元格的格式
            CellType cellType = row.getCell(2).getCellType();
            if (cellType.equals(CellType.STRING)){
                //文本类型单元格读取方式
                String age = row.getCell(0).getStringCellValue();
                employee.setAge(Integer.valueOf(age));
            }else {
                //数字类型单元格读取方式
                double numericCellValue = row.getCell(2).getNumericCellValue();
                employee.setAge((int) numericCellValue);
            }
            //设置mr密码
            employee.setPassword("1");
            //保存到数据库
            this.save(employee,null);
        }

    }


}
