package cn.wolfcode.web.controller;

import cn.wolfcode.domain.SystemDictionary;
import cn.wolfcode.qo.QueryObject;
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
@RequestMapping("/SystemDictionary")
public class SystemDictionaryController {

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequestMapping("/list")
    @RequiresPermissions(value = {"SystemDictionary:list","字典目录页面"},logical=Logical.OR)
    public String list(Model model, @ModelAttribute("qo") QueryObject qo) {
        PageInfo<SystemDictionary> pageResult = systemDictionaryService.query(qo);
        model.addAttribute("PageInfo", pageResult);
        return "/Systemdictionary/list";
    }

   @RequiresPermissions(value = {"SystemDictionary:delete","字典目录删除"},logical=Logical.OR)
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult list(Long id) {

        if (id != null) {
            systemDictionaryService.delete(id);
        }
        return new JsonResult();

    }
    @RequiresPermissions(value = {"SystemDictionary:saveOrUpdate","字典目录新增/编辑页面"},logical=Logical.OR)
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(SystemDictionary systemDictionary) {

        if (systemDictionary.getId() != null) {
            systemDictionaryService.update(systemDictionary);
        } else {
            systemDictionaryService.save(systemDictionary);
        }
        return new JsonResult();
    }
}
