package com.liyou.service.cms.core.service;

import com.liyou.framework.base.criteria.IExpression;
import com.liyou.framework.base.criteria.predicate.OperatorPredicate;
import com.liyou.service.cms.core.entity.ResourceDefinitionEntity;
import com.liyou.service.cms.core.entity.ResourceItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/***
 *
 *   @author linxiaohui
 *   @date 2018/5/7
 ***/
public interface ResourceService {


    ResourceDefinitionEntity findDefinitionByPosition(String position);


    ResourceDefinitionEntity saveOrUpdateDefinition(ResourceDefinitionEntity definitionEntity);


    void deleteDefinition(Long id);


    ResourceDefinitionEntity releaseDefinition(Long id);


    List<ResourceDefinitionEntity> findDefinition(IExpression<Boolean> expression);


    Page<ResourceDefinitionEntity> findDefinition(IExpression<Boolean> expression, Pageable pageable);


    ResourceItemEntity saveOrUpdate(ResourceItemEntity entity);

    List<ResourceItemEntity> findItem(IExpression<Boolean> expression);

    List<ResourceItemEntity> findItem(IExpression<Boolean> expression,Integer offset,Integer limit);

    Page<ResourceItemEntity> findItem(IExpression<Boolean> expression,Pageable pageable);

    ResourceItemEntity deleteItem(Long id);
}
