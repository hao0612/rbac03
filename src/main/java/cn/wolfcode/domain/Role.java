package cn.wolfcode.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
 * 角色
 *
 * */
@Data
public class Role {
    private Long id;
    //
    private String name;
    //
    private String sn;
    //角色权限
    private List<Permission> permission = new ArrayList<>();

}