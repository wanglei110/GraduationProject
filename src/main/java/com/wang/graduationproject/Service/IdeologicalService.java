package com.wang.graduationproject.Service;

import com.wang.graduationproject.Pagination.PaginationResult;
import com.wang.graduationproject.To.IdeologicalElementsTo;
import com.wang.graduationproject.To.LoginResultTo;
import com.wang.graduationproject.To.LoginTo;
import com.wang.graduationproject.To.QueryIdeoTo;

public interface IdeologicalService {

    /**
     * 思政元素添加
     * */
    boolean addIdeological(IdeologicalElementsTo ideologicalElementsTo);

    /**
     * 思政元素删除
     * */
    boolean delIdeological(IdeologicalElementsTo ideologicalElementsTo);

    /**
     * 思政元素编辑
     * */
    boolean editIdeological(IdeologicalElementsTo ideologicalElementsTo);

    /**
     * 思政元素分页查询
     * */
    PaginationResult queryIdeo(QueryIdeoTo queryIdeoTo);
}
