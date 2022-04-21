package com.wang.graduationproject.Service.Impl;

import com.wang.graduationproject.Dao.IdeologicalElements;
import com.wang.graduationproject.Dao.User;
import com.wang.graduationproject.Pagination.PaginationResult;
import com.wang.graduationproject.Repository.IdeoRepository;
import com.wang.graduationproject.Repository.UserRepository;
import com.wang.graduationproject.Service.IdeologicalService;
import com.wang.graduationproject.To.IdeologicalElementsTo;
import com.wang.graduationproject.To.QueryIdeoTo;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
@Transactional
public class IdeologicalServiceImpl implements IdeologicalService {
    @Resource
    IdeoRepository ideoRepository;

    @Resource
    UserRepository userRepository;

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
        User user=userRepository.findUserByIdAndIfDeletedFalse(ideo.getUserId());
        if(ObjectUtils.isEmpty(user)){
            throw new RuntimeException("该用户不存在!");
        }
        ideologicalElements.setUser(user);
        ideoRepository.saveAndFlush(ideologicalElements);
        return true;
    }

    @Override
    public boolean delIdeological(IdeologicalElementsTo elementsTo) {
        if(ObjectUtils.isEmpty(elementsTo.getId())){
            throw new RuntimeException("思政元素Id不能为空");
        }
        IdeologicalElements ideologicalElements=ideoRepository.findIdeologicalElementsByIdAndIfDeletedFalse(elementsTo.getId());
        if(!ideologicalElements.getUser().getId().equals(elementsTo.getUserId())){
            throw new RuntimeException("只能删除自己创建的思政元素!");
        }
        ideologicalElements.setIfDeleted(true);
        ideoRepository.saveAndFlush(ideologicalElements);
        return true;
    }

    @Override
    public boolean editIdeological(IdeologicalElementsTo ideologicalElementsTo) {
        return false;
    }

    @Override
    public PaginationResult queryIdeo(QueryIdeoTo queryIdeoTo) {
        return null;
    }
}
