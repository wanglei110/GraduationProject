package com.wang.graduationproject.Pagination;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PaginationResult<T> implements Serializable {

    public static final long serialVersionUID=1L;

    /**
     * 数据总条数
     * */
    private long total;

    /**
     * 数据
     * */
    private List<T> rows;

}
