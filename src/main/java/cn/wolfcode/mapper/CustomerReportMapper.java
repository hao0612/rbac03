package cn.wolfcode.mapper;

import cn.wolfcode.qo.CustomerReportQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CustomerReportMapper {
    List<HashMap>selectCustomerReport(CustomerReportQuery qo);
}
