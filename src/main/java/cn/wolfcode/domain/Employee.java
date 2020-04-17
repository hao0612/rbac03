package cn.wolfcode.domain;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
public class Employee {
    private Long id;
    private String name;
    private String password;
    private String email;
    private Integer age;
    private boolean admin;
    private Department dept;//可以封装部门的名称封装id
    private List<Role> roles = new ArrayList<>();//代表当前拥有员工角色的集合


}