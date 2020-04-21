package cn.wolfcode.qo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerQuery  extends QueryObject{
    private Integer status;//状态
    private  Long sellerId = -1L; //销售员

}
