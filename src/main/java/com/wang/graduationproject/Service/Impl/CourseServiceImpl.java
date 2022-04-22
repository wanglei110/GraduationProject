package com.wang.graduationproject.Service.Impl;

import com.wang.graduationproject.Dao.Course;
import com.wang.graduationproject.Dao.IdeologicalElements;
import com.wang.graduationproject.Dao.User;
import com.wang.graduationproject.Pagination.PaginationResult;
import com.wang.graduationproject.Repository.CourseRepository;
import com.wang.graduationproject.Repository.UserRepository;
import com.wang.graduationproject.Service.CourseService;
import com.wang.graduationproject.Service.UserService;
import com.wang.graduationproject.To.CourseTo;
import com.wang.graduationproject.To.IdeologicalElementsTo;
import com.wang.graduationproject.To.QueryCourseTo;
import com.wang.graduationproject.Utils.LocalDateUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Resource
    CourseRepository courseRepository;

    @Resource
    UserRepository userRepository;

    @Resource
    UserService userService;

    @Override
    public boolean addCourse(CourseTo courseTo) {
        if(ObjectUtils.isEmpty(courseTo)){
            throw new RuntimeException("课程不能为空!");
        }
        if(!ObjectUtils.isEmpty(courseRepository.findCourseByCourseCodeAndIfDeletedFalse(courseTo.getCourseCode()))){
            throw new RuntimeException("已经存在相同课程代码的课程!");
        }
        Course course=new Course();
        BeanUtils.copyProperties(courseTo,course);
        User user=userRepository.findUserByIdAndIfDeletedFalse(courseTo.getUserTo().getId());
        if(ObjectUtils.isEmpty(user)){
            throw new RuntimeException("该用户不存在!");
        }
        course.setIfDeleted(false);
        course.setCreateTime(LocalDateTime.now());
        course.setUser(user);
        addIdeo(course,courseTo);
        courseRepository.saveAndFlush(course);
        return true;
    }

    @Override
    public boolean delCourse(CourseTo courseTo) {
        if(ObjectUtils.isEmpty(courseTo)&&courseTo.getId()==null){
            throw new RuntimeException("课程不能为空");
        }
        Course course=courseRepository.findCourseByIdAndIfDeletedFalse(courseTo.getId());
        if(ObjectUtils.isEmpty(course)){
            throw new RuntimeException("参数输入错误!");
        }
        course.setIfDeleted(true);
        courseRepository.saveAndFlush(course);
        return true;
    }

    @Override
    public boolean editCourse(CourseTo courseTo) {
        if(ObjectUtils.isEmpty(courseTo)||courseTo.getId()==null){
            throw new RuntimeException("课程不能为空!");
        }
        Course course=courseRepository.findCourseByIdAndIfDeletedFalse(courseTo.getId());
        if(ObjectUtils.isEmpty(course)){
            throw new RuntimeException("请输入正确的课程id");
        }
        if(!course.getUser().getId().equals(courseTo.getUserTo().getId())){
            throw new RuntimeException("只能编辑自己创建的课程!");
        }
        if(!ObjectUtils.isEmpty(courseRepository.findCourseByCourseCodeAndIfDeletedFalse(course.getCourseCode()))){
            throw new RuntimeException("课程代码不能重复!");
        }
        BeanUtils.copyProperties(courseTo,course);
        addIdeo(course,courseTo);
        for(long id:courseTo.getDeleteIdeoIds()){
            courseRepository.deleteCourseByIdeoId(id);
        }
        course.getElementsList().clear();
        courseRepository.saveAndFlush(course);
        return true;
    }

    public void addIdeo(Course course,CourseTo courseTo){
        for(IdeologicalElementsTo ideoTo: courseTo.getElementsList()){
            IdeologicalElements ideologicalElements=new IdeologicalElements();
            BeanUtils.copyProperties(ideoTo,ideologicalElements);
            ideologicalElements.setIfDeleted(false);
            ideologicalElements.setCreateTime(LocalDateTime.now());
            User userIdeo=userRepository.findUserByIdAndIfDeletedFalse(ideoTo.getUserTo().getId());
            if(ObjectUtils.isEmpty(userIdeo)){
                throw new RuntimeException("该用户不存在!");
            }
            ideologicalElements.setUser(userIdeo);
            course.getElementsList().add(ideologicalElements);
        }
    }

    @Override
    public PaginationResult queryCourse(QueryCourseTo queryCourseTo) {
        if(queryCourseTo.getOr()==null){
            queryCourseTo.setOr(false);
        }
        Page<Course> queryResult=courseRepository.findAll(
                (Specification<Course>)(root, query, CriteriaBuilder) ->{
                    List<Predicate> predicateAnd=new ArrayList<>();

                    predicateAnd.add(CriteriaBuilder.equal(root.get("ifDeleted"),false));
                    Predicate[] arrayAnd=new Predicate[predicateAnd.size()];
                    Predicate preAnd=CriteriaBuilder.and(predicateAnd.toArray(arrayAnd));
                    if(queryCourseTo.getOr()==true){
                        List<Predicate> predicatesOr = new ArrayList<>();
                        if(queryCourseTo.getCourseCode()!=null){
                            predicatesOr.add(CriteriaBuilder.like(root.get("knowledgePoint"),"%"+queryCourseTo.getCourseCode()+"%"));
                        }
                        if(queryCourseTo.getCourseType()!=null){
                            predicatesOr.add(CriteriaBuilder.like(root.join("user").get("name"),"%"+queryCourseTo.getCourseType()+"%"));
                        }
                        if(queryCourseTo.getCourseName()!=null){
                            predicatesOr.add(CriteriaBuilder.like(root.join("user").get("name"),"%"+queryCourseTo.getCourseName()+"%"));
                        }

                        Predicate[] arrayOr=new Predicate[predicatesOr.size()];
                        Predicate preOr=CriteriaBuilder.or(predicatesOr.toArray(arrayOr));
                        return query.where(preAnd,preOr).getRestriction();
                    }else{
                        return query.where(preAnd).getRestriction();
                    }
                },
                PageRequest.of((queryCourseTo.getPage()-1), queryCourseTo.getRows(), Sort.Direction.DESC,"id")
        );

        PaginationResult result=new PaginationResult();
        result.setTotal(queryResult.getTotalElements());
        result.setRows(queryResult.getContent().stream().map(this::getCourseTo).collect(Collectors.toList()));
        return result;
    }

    private CourseTo getCourseTo(Course course){
        CourseTo courseTo=new CourseTo();
        BeanUtils.copyProperties(course,courseTo);
        courseTo.setCreateTime(LocalDateUtils.format(course.getCreateTime(),LocalDateUtils.DATETIME_PATTERN));
        courseTo.setUserTo(userService.getUserTo(course.getUser()));
        List<IdeologicalElementsTo> elementsToList=new ArrayList<>();
        for(IdeologicalElements ideologicalElements:course.getElementsList()){
            IdeologicalElementsTo ideologicalElementsTo=new IdeologicalElementsTo();
            BeanUtils.copyProperties(ideologicalElements,ideologicalElementsTo);
            ideologicalElementsTo.setUserTo(userService.getUserTo(ideologicalElements.getUser()));
            ideologicalElementsTo.setCreateTime(LocalDateUtils.format(ideologicalElements.getCreateTime(),LocalDateUtils.DATETIME_PATTERN));
            elementsToList.add(ideologicalElementsTo);
        }
        courseTo.setElementsList(elementsToList);
        return courseTo;
    }

}
