package com.graduate.hospital.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageListVo<T> {
    private Integer count;
    private List<T> list;
}
