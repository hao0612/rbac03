package cn.wolfcode.web.controller;

import cn.wolfcode.domain.SystemDictionaryItem;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.qo.SystemDictionaryItemQuery;
import cn.wolfcode.service.ISystemDictionaryItemService;
import cn.wolfcode.service.ISystemDictionaryService;
import cn.wolfcode.util.JsonResult;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/SystemDictionaryItem")
public class SystemDictionaryItemController {

    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;
    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"SystemDictionaryItem:list","字典明细页面"},logical=Logical.OR)
    public String list(Model model, @ModelAttribute("qo") SystemDictionaryItemQuery qo) {
        PageInfo<SystemDictionaryItem> pageResult = systemDictionaryItemService.query(qo);
        model.addAttribute("PageInfo", pageResult);
        //查询所有的字典目录
        model.addAttribute("systemDictionarys",systemDictionaryService.listAll());
        return "/SystemDictionaryItem/list";
    }

   @RequiresPermissions(value = {"SystemDictionaryItem:delete","字典明细删除"},logical=Logical.OR)
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult list(Long id) {

        if (id != null) {
            systemDictionaryItemService.delete(id);
        }
        return new JsonResult();

    }
    @RequiresPermissions(value = {"SystemDictionaryItem:saveOrUpdate","字典明细新增/编辑页面"},logical=Logical.OR)
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(SystemDictionaryItem systemDictionaryItem) {

        if (systemDictionaryItem.getId() != null) {
            systemDictionaryItemService.update(systemDictionaryItem);
        } else {
            systemDictionaryItemService.save(systemDictionaryItem);
        }
        return new JsonResult();
    }
}
