package com.wang.graduationproject.Controller;

import com.wang.graduationproject.Annotation.PassToken;
import com.wang.graduationproject.Pagination.PaginationResult;
import com.wang.graduationproject.Service.CourseService;
import com.wang.graduationproject.Service.IdeologicalService;
import com.wang.graduationproject.To.CourseTo;
import com.wang.graduationproject.To.IdeologicalElementsTo;
import com.wang.graduationproject.To.QueryCourseTo;
import com.wang.graduationproject.To.QueryIdeoTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("course")
public class CourseController {

    @Resource
    CourseService courseService;

    @PassToken
    @PostMapping("add")
    @ResponseBody
    public Boolean add(@RequestBody CourseTo courseTo){
        return courseService.addCourse(courseTo);
    }

    @PassToken
    @PostMapping("del")
    @ResponseBody
    public Boolean del(@RequestBody CourseTo courseTo){
        return courseService.delCourse(courseTo);
    }

    @PassToken
    @PostMapping("edit")
    @ResponseBody
    public Boolean edit(@RequestBody CourseTo courseTo){
        return courseService.editCourse(courseTo);
    }

    @PassToken
    @PostMapping("query")
    @ResponseBody
    public PaginationResult query(@RequestBody QueryCourseTo queryCourseTo){
        return courseService.queryCourse(queryCourseTo);
    }
}
