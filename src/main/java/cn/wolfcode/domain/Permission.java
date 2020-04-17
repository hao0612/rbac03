package cn.wolfcode.domain;

import lombok.Getter;
import lombok.Setter;

/*
 * 权限
 * */
@Getter
@Setter
public class Permission {
    private Long id;
    //名称
    private String name;
    //表达式
    private String expression;

}