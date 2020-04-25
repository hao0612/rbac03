package cn.wolfcode.web.controller;


import cn.wolfcode.qo.CustomerReportQuery;
import cn.wolfcode.service.ICustomerReportService;
import cn.wolfcode.service.ICustomerTraceHistoryService;
import cn.wolfcode.util.MessageUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.util.Length;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customerReport")
public class CustomerReportController {
    @Autowired
    private ICustomerReportService customerReportService;

    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") CustomerReportQuery qo){
        PageInfo<HashMap> pageInfo = customerReportService.selectCustomerReport(qo);
        model.addAttribute("PageInfo", pageInfo);
        return "customerReport/list";
    }
    /*柱状图
    * */
    @RequestMapping("/listByBar")
    public String listByBar(Model model, @ModelAttribute("qo") CustomerReportQuery qo){
        //查询报表相关的数据
        List<HashMap> list = customerReportService.selectAll(qo);
        //提供柱状图需要的数据
        ArrayList<Object> XList = new ArrayList<>();
        ArrayList<Object>YList = new ArrayList<>();
        //遍历list集合 获取每个map
        for (HashMap map :list){
            XList.add(map.get("groupType"));
            YList.add(map.get("number"));
        }
        //共享数据
        model.addAttribute("XList" , JSON.toJSONString(XList));
        model.addAttribute("YList" , JSON.toJSONString(YList));
//  转为文字显示
        model.addAttribute("groupTypeName", MessageUtil.changMsg(qo));
        return "customerReport/listByBar";
    }
    /*饼状图
    * */
    @RequestMapping("/listByPie")
    public String listByPie(Model model, @ModelAttribute("qo") CustomerReportQuery qo){
        //查询报表相关的数据
        List<HashMap> list = customerReportService.selectAll(qo);
        //准备两个list分别存放数据
        ArrayList<Object> legendList = new ArrayList<>();
        ArrayList<Object> seriesList = new ArrayList<>();
        //遍历list集合 获取每个map
        for(HashMap map :list) {
            legendList.add(map.get("groupType"));
            Map temp = new HashMap();
            temp.put("name", map.get("groupType"));
            temp.put("value", map.get("number"));
            seriesList.add(temp);
        }
        model.addAttribute("legendList", JSON.toJSONString(legendList));
        model.addAttribute("seriesList", JSON.toJSONString(seriesList));
//  转为文字显示
        model.addAttribute("groupTypeName", MessageUtil.changMsg(qo));
        return "customerReport/listByPie";
    }


}
