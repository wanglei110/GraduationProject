package com.wang.graduationproject.To;

import com.wang.graduationproject.Dao.IdeologicalElements;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data

public class CourseTo {


    private Long id;
    private LocalDateTime createTime;
    private String courseName;
    private String courseCode;
    private String courseType;
    private String courseNature;
    private float credit;
    private int totalHours;
    private String academy;
    private String teachingGroup;
    private String forProfessional;
    private int semester;
    private UserTo userTo;
    private List<IdeologicalElements> elementsList=new ArrayList<>();

}
