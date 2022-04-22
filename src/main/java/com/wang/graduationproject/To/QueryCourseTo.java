package com.wang.graduationproject.To;

import com.wang.graduationproject.Dao.IdeologicalElements;
import lombok.Data;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Data

public class QueryCourseTo {

    private Integer page;
    private Integer rows;
    private String courseName;
    private String courseCode;
    private String courseType;
    private String courseNature;
    private Float credit;
    private String academy;
    private String forProfessional;
    private Integer semester;
    private UserTo userTo;
    private Boolean or;
}
