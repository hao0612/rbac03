package cn.wolfcode.domain;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.util.HashMap;

@Data
public class SystemDictionary {
    private Long id;

    private String sn;

    private String title;

    private String intro;
    public String getJson() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("sn", sn);
        map.put("title", title);
        map.put("intro", intro);
        //不能直接传this进去,会出现死循环
        return JSON.toJSONString(map);
    }

}