package com.wang.graduationproject.Controller;

import com.wang.graduationproject.Annotation.PassToken;
import com.wang.graduationproject.Pagination.PaginationResult;
import com.wang.graduationproject.Service.AdminService;
import com.wang.graduationproject.Service.IdeologicalService;
import com.wang.graduationproject.To.IdeologicalElementsTo;
import com.wang.graduationproject.To.QueryIdeoTo;
import com.wang.graduationproject.To.UserTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("ideological")
public class IdeoController {

    @Resource
    IdeologicalService ideologicalService;

    @PassToken
    @PostMapping("add")
    @ResponseBody
    public Boolean add(@RequestBody IdeologicalElementsTo ideologicalElementsTo){
        return ideologicalService.addIdeological(ideologicalElementsTo);
    }

    @PassToken
    @PostMapping("del")
    @ResponseBody
    public Boolean del(@RequestBody IdeologicalElementsTo ideologicalElementsTo){

        return ideologicalService.delIdeological(ideologicalElementsTo);
    }

    @PassToken
    @PostMapping("edit")
    @ResponseBody
    public Boolean edit(@RequestBody IdeologicalElementsTo ideologicalElementsTo){
        return ideologicalService.editIdeological(ideologicalElementsTo);
    }

    @PassToken
    @PostMapping("query")
    @ResponseBody
    public PaginationResult query(@RequestBody QueryIdeoTo queryIdeoTo){
        return ideologicalService.queryIdeo(queryIdeoTo);
    }
}
