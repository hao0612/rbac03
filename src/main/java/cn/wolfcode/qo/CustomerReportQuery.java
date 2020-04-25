package cn.wolfcode.qo;

import cn.wolfcode.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class CustomerReportQuery  extends  QueryObject{
    private String groupType = "e.name" ;//默认按照员工姓名分组
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;//开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;//结束时间
    public Date getEndDate() { // 获取结束时间当天最晚的时候
        return DateUtil.getEndDate(endDate);
    }
}
