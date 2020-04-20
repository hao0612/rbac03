package cn.wolfcode.service.impl;

import cn.wolfcode.domain.Department;
import cn.wolfcode.domain.SystemDictionaryItem;
import cn.wolfcode.mapper.DepartmentMapper;
import cn.wolfcode.mapper.SystemDictionaryItemMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.IDepartmentService;
import cn.wolfcode.service.ISystemDictionaryItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemDictionaryItemServicelmpl implements ISystemDictionaryItemService {
    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;


    @Override
    public void save(SystemDictionaryItem systemDictionaryItem) {
        systemDictionaryItemMapper.insert(systemDictionaryItem);
    }

    @Override
    public void delete(Long id) {
        systemDictionaryItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(SystemDictionaryItem systemDictionaryItem) {
        systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
    }

    @Override
    public SystemDictionaryItem get(Long id) {
        return systemDictionaryItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDictionaryItem> listAll() {
        return systemDictionaryItemMapper.selectAll();
    }

    //调用mapper接口的实现类方法查询数据,封装成Page result 对象返回
    @Override
    public PageInfo<SystemDictionaryItem> query(QueryObject qo) {
//使用分页插件,传入当前页,每页显示数量
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<SystemDictionaryItem> systemDictionaryItems = systemDictionaryItemMapper.selectForList(qo);//sql里面不需要写limt

        return new PageInfo<SystemDictionaryItem>(systemDictionaryItems);
    }
}
