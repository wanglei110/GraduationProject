package com.wang.graduationproject.Dao;

import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="if_deleted")
    private Boolean ifDeleted;

    @Column(name="create_time")
    private LocalDateTime createTime;

    /**
     *课程名
     * */
    @Column(name="course_name")
    private String courseName;

    /**
     *课程代码
     * */
    @Column(name="course_code")
    private String courseCode;

    /**
     *课程类别
     * */
    @Column(name="course_type")
    private String courseType;

    /**
     *课程性质(必修，选修)
     * */
    @Column(name="course_nature")
    private String courseNature;

    /**
     *学分
     * */
    @Column(name="credit")
    private Float credit;

    /**
     *总课时数
     * */
    @Column(name="total_hours")
    private Integer totalHours;

    /**
     *学院
     * */
    @Column(name="academy")
    private String academy;

    /**
     *教学组
     * */
    @Column(name="teaching_group")
    private String teachingGroup;

    /**
     *面向专业
     * */
    @Column(name="for_professional")
    private String forProfessional;

    /**
     *开课学期
     * */
    @Column(name="semester")
    private Integer semester;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(
            name="course_ideological_elements",
            joinColumns = @JoinColumn(name="course_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="ideological_elements_id",referencedColumnName = "id")
    )
    private List<IdeologicalElements> elementsList=new ArrayList<>();

}
