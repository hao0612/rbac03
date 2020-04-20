package cn.wolfcode.service.impl;


import cn.wolfcode.domain.SystemDictionary;
import cn.wolfcode.mapper.SystemDictionaryMapper;
import cn.wolfcode.qo.QueryObject;
import cn.wolfcode.service.ISystemDictionaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemDictionaryServicelmpl implements ISystemDictionaryService {
    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;


    @Override
    public void save(SystemDictionary systemDictionary) {
        systemDictionaryMapper.insert(systemDictionary);
    }

    @Override
    public void delete(Long id) {
        systemDictionaryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(SystemDictionary systemDictionary) {
        systemDictionaryMapper.updateByPrimaryKey(systemDictionary);
    }

    @Override
    public SystemDictionary get(Long id) {
        return systemDictionaryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDictionary> listAll() {
        return systemDictionaryMapper.selectAll();
    }

    //调用mapper接口的实现类方法查询数据,封装成Page result 对象返回
    @Override
    public PageInfo<SystemDictionary> query(QueryObject qo) {
//使用分页插件,传入当前页,每页显示数量
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<SystemDictionary> systemDictionaries = systemDictionaryMapper.selectForList(qo);//sql里面不需要写limt

        return new PageInfo<SystemDictionary>(systemDictionaries);
    }
}
