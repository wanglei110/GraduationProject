package com.wang.graduationproject.Service;

import com.wang.graduationproject.Pagination.PaginationResult;
import com.wang.graduationproject.To.CourseTo;
import com.wang.graduationproject.To.IdeologicalElementsTo;
import com.wang.graduationproject.To.QueryCourseTo;
import com.wang.graduationproject.To.QueryIdeoTo;

public interface CourseService {

    /**
     * 课程添加
     * */
    boolean addCourse(CourseTo courseTo);

    /**
     * 课程删除
     * */
    boolean delCourse(CourseTo courseTo);

    /**
     * 课程编辑
     * */
    boolean editCourse(CourseTo courseTo);

    /**
     * 课程分页模糊查询
     * */
    PaginationResult queryCourse(QueryCourseTo queryCourseTo);
}
