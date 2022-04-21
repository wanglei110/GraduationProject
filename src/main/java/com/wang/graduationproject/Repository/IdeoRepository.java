package com.wang.graduationproject.Repository;

import com.wang.graduationproject.Dao.IdeologicalElements;
import com.wang.graduationproject.Dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IdeoRepository extends JpaRepository<IdeologicalElements, Long>, JpaSpecificationExecutor<IdeologicalElements> {

    IdeologicalElements findIdeologicalElementsByKnowledgePointAndContentAndIfDeletedFalse(String knowledgePoint,String content);

    IdeologicalElements findIdeologicalElementsByIdAndIfDeletedFalse(Long id);
}
