package cn.wolfcode.service;

import cn.wolfcode.domain.Department;
import cn.wolfcode.domain.SystemDictionaryItem;
import cn.wolfcode.qo.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ISystemDictionaryItemService {
    void save(SystemDictionaryItem systemDictionaryItem);

    void delete(Long id);

    void update(SystemDictionaryItem systemDictionaryItem);

    SystemDictionaryItem get(Long id);

    List<SystemDictionaryItem> listAll();

    //添加分页查询的方法
    PageInfo<SystemDictionaryItem> query(QueryObject qo);


}
