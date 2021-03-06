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
    private String createTime;
    private String courseName;
    private String courseCode;
    private String courseType;
    private String courseNature;
    private Float credit;
    private Integer totalHours;
    private String academy;
    private String teachingGroup;
    private String forProfessional;
    private Integer semester;
    private UserTo userTo;
    private List<IdeologicalElementsTo> elementsList=new ArrayList<>();
    private List<Long> deleteIdeoIds=new ArrayList<>();
}
