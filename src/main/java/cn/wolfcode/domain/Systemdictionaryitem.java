package cn.wolfcode.domain;

import lombok.Data;

@Data
public class Systemdictionaryitem {
    private Long id;

    private Long parent_id;

    private String title;

    private Integer sequence;


}