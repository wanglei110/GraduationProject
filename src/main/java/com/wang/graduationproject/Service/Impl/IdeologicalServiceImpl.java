package com.wang.graduationproject.Service.Impl;

import com.wang.graduationproject.Dao.IdeologicalElements;
import com.wang.graduationproject.Dao.User;
import com.wang.graduationproject.Enum.RoleEnum;
import com.wang.graduationproject.Pagination.PaginationResult;
import com.wang.graduationproject.Repository.IdeoRepository;
import com.wang.graduationproject.Repository.UserRepository;
import com.wang.graduationproject.Service.IdeologicalService;
import com.wang.graduationproject.To.IdeologicalElementsTo;
import com.wang.graduationproject.To.QueryIdeoTo;
import com.wang.graduationproject.To.UserTo;
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
public class IdeologicalServiceImpl implements IdeologicalService {
    @Resource
    IdeoRepository ideoRepository;

    @Resource
    UserRepository userRepository;

    /**
     * 添加思政元素
     * */
    @Override
    public boolean addIdeological(IdeologicalElementsTo ideo) {
        if(ObjectUtils.isEmpty(ideo)){
            throw new RuntimeException("思政元素不能为空!");
        }
        if(!ObjectUtils.isEmpty(ideoRepository.findIdeologicalElementsByKnowledgePointAndContentAndIfDeletedFalse(ideo.getKnowledgePoint(),ideo.getContent()))){
            throw new RuntimeException("已存在知识点和内容都相同的思政融入点，请不要重复添加!");
        }
        IdeologicalElements ideologicalElements=new IdeologicalElements();
        BeanUtils.copyProperties(ideo,ideologicalElements);
        ideologicalElements.setIfDeleted(false);
        ideologicalElements.setCreateTime(LocalDateTime.now());
        User user=userRepository.findUserByIdAndIfDeletedFalse(ideo.getUserTo().getId());
        if(ObjectUtils.isEmpty(user)){
            throw new RuntimeException("该用户不存在!");
        }
        ideologicalElements.setUser(user);
        ideoRepository.saveAndFlush(ideologicalElements);
        return true;
    }

    /**
     * 删除思政元素
     * */
    @Override
    public boolean delIdeological(IdeologicalElementsTo elementsTo) {
        if(ObjectUtils.isEmpty(elementsTo.getId())){
            throw new RuntimeException("思政元素Id不能为空");
        }
        IdeologicalElements ideologicalElements=ideoRepository.findIdeologicalElementsByIdAndIfDeletedFalse(elementsTo.getId());
        if(!ideologicalElements.getUser().getId().equals(elementsTo.getUserTo().getId())){
            throw new RuntimeException("只能删除自己创建的思政元素!");
        }
        ideologicalElements.setIfDeleted(true);
        ideoRepository.saveAndFlush(ideologicalElements);
        return true;
    }

    /**
     * 思政元素编辑
     * */
    @Override
    public boolean editIdeological(IdeologicalElementsTo elementsTo) {
        if(ObjectUtils.isEmpty(elementsTo.getId())){
            throw new RuntimeException("思政元素Id不能为空");
        }
        IdeologicalElements ideologicalElements=ideoRepository.findIdeologicalElementsByIdAndIfDeletedFalse(elementsTo.getId());
        if(!ideologicalElements.getUser().getId().equals(elementsTo.getUserTo().getId())){
            throw new RuntimeException("只能编辑自己创建的思政元素!");
        }
        ideologicalElements.setContent(elementsTo.getContent());
        ideologicalElements.setKnowledgePoint(elementsTo.getKnowledgePoint());
        ideoRepository.saveAndFlush(ideologicalElements);

        return true;
    }

    /**
     * 思政元素模糊查询
     * */
    @Override
    public PaginationResult queryIdeo(QueryIdeoTo queryIdeoTo) {

        if(queryIdeoTo.getOr()==null){
            queryIdeoTo.setOr(false);
        }
        Page<IdeologicalElements> queryResult=ideoRepository.findAll(
                (Specification<IdeologicalElements>)(root, query, CriteriaBuilder) ->{
                    List<Predicate> predicateAnd=new ArrayList<>();

                    predicateAnd.add(CriteriaBuilder.equal(root.get("ifDeleted"),false));
                    Predicate[] arrayAnd=new Predicate[predicateAnd.size()];
                    Predicate preAnd=CriteriaBuilder.and(predicateAnd.toArray(arrayAnd));
                    if(queryIdeoTo.getOr()==true){
                        List<Predicate> predicatesOr = new ArrayList<>();
                        if(queryIdeoTo.getKnowledgePoint()!=null){
                            predicatesOr.add(CriteriaBuilder.like(root.get("knowledgePoint"),"%"+queryIdeoTo.getKnowledgePoint()+"%"));
                        }
                        if(queryIdeoTo.getCreator()!=null){
                            predicatesOr.add(CriteriaBuilder.like(root.join("user").get("name"),"%"+queryIdeoTo.getCreator()+"%"));
                        }

                        Predicate[] arrayOr=new Predicate[predicatesOr.size()];
                        Predicate preOr=CriteriaBuilder.or(predicatesOr.toArray(arrayOr));
                        return query.where(preAnd,preOr).getRestriction();
                    }else{
                        return query.where(preAnd).getRestriction();
                    }
                },
                PageRequest.of((queryIdeoTo.getPage()-1), queryIdeoTo.getRows(), Sort.Direction.DESC,"id")
        );

        PaginationResult result=new PaginationResult();
        result.setTotal(queryResult.getTotalElements());
        result.setRows(queryResult.getContent().stream().map(this::getIdeoTo).collect(Collectors.toList()));
        return result;
    }

    /**
     * 将IdeologicalElements转成IdeologicalElementsTO
     * */
    private IdeologicalElementsTo getIdeoTo(IdeologicalElements ideologicalElements){
        IdeologicalElementsTo to=new IdeologicalElementsTo();
        to.setContent(ideologicalElements.getContent());
        to.setCreateTime(LocalDateUtils.format(ideologicalElements.getCreateTime(),LocalDateUtils.DATETIME_PATTERN));
        to.setId(ideologicalElements.getId());
        to.setKnowledgePoint(ideologicalElements.getKnowledgePoint());
        UserTo userTo=new UserTo();
        userTo.setUserName(ideologicalElements.getUser().getUserName());
        userTo.setAcademy(ideologicalElements.getUser().getAcademy());
        userTo.setId(ideologicalElements.getUser().getId());
        userTo.setName(ideologicalElements.getUser().getName());
        userTo.setRole(RoleEnum.match(ideologicalElements.getUser().getRole()).getRoleName());
        to.setUserTo(userTo);
        return to;
    }
}
