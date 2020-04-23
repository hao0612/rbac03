package cn.wolfcode.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
public class CustomerTraceHistory {
    private Long id;
    //跟进时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date traceTime;
    //跟进内容
    private String traceDetails;
    //跟进方式
    private SystemDictionaryItem traceType;
    //跟进结果
    private Integer traceResult;
    //客户
    private Customer customer;
    //录入人
    private Employee inputUser;
    //录入时间
    private Date inputTime;
}