package cn.wolfcode.qo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeQueryObject extends QueryObject {
    private String keyword; //关键字
    private Long deptId = -1L; // 部门id
}
