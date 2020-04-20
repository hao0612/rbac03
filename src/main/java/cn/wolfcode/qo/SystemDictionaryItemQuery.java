package cn.wolfcode.qo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemDictionaryItemQuery  extends QueryObject{
    private Long parentId = -1L;//目录id
}
