package cn.wolfcode.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 客户
 */
@Setter
@Getter
public class Customer {
    public static final int STATUS_COMMON = 0;//潜在客户
    public static final int STATUS_NORMAL = 1;//正式客户
    public static final int STATUS_FAIL = 2;//开发失败
    public static final int STATUS_LOST = 3;//流失客户
    public static final int STATUS_POOL = 4;//客户池

    private Long id;
    //姓名
    private String name;
    //年龄
    private Integer age;
    //性别
    private Integer gender;
    //电话
    private String tel;
    //qq
    private String qq;
    //职业
    private SystemDictionaryItem job;
    //来源
    private SystemDictionaryItem source;
    //销售人员
    private Employee seller;
    //录入人
    private Employee inputuser;
    //录入时间
    private Date inputTime;
    //状态
    private Integer status = STATUS_COMMON;
    public String getStatusName() {
        switch (status) {
            case STATUS_NORMAL:
                return "正式客户";
            case STATUS_FAIL:
                return "开发失败客户";
            case STATUS_LOST:
                return "流失客户";
            case STATUS_POOL:
                return "客户池";
            default:
                return "潜在客户";
        }
    }



}
