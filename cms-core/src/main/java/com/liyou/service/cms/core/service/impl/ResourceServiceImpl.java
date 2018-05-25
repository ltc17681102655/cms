package com.liyou.service.cms.core.service.impl;

import com.liyou.framework.base.criteria.Expressions;
import com.liyou.framework.base.criteria.IExpression;
import com.liyou.service.cms.core.BusinessRuntimeException;
import com.liyou.service.cms.core.entity.ResourceDefinitionEntity;
import com.liyou.service.cms.core.entity.ResourceItemEntity;
import com.liyou.service.cms.core.repository.ResourceDefinitionRepo;
import com.liyou.service.cms.core.repository.ResourceItemRepo;
import com.liyou.service.cms.core.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/***
 *
 *   @author linxiaohui
 *   @date 2018/5/7
 ***/
@Service
@Transactional(readOnly = true)
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDefinitionRepo definitionDao;

    @Autowired
    private ResourceItemRepo itemDao;

    @Override
    public ResourceDefinitionEntity findDefinitionByPosition(String position) {
        return definitionDao.findFirst(Expressions.eq("position",position));
    }

    @Override
    @Transactional
    public ResourceDefinitionEntity saveOrUpdateDefinition(ResourceDefinitionEntity definitionEntity) {

        if( null != definitionEntity.getId() ){
            ResourceDefinitionEntity entity =  definitionDao.findOne(definitionEntity.getId());
            if( entity != null ){
                entity.setStatus(ResourceDefinitionEntity.Status.edit);
                entity.setDefinition(definitionEntity.getDefinition());
                entity.setPosition(definitionEntity.getPosition());
                entity.setValue( definitionEntity.getValue() );
                entity.setDefaultValue( definitionEntity.getDefaultValue() );
                entity.setName( definitionEntity.getName() );
                return entity;
            }
        }

        return definitionDao.saveAndFlush(definitionEntity);
    }

    @Override
    @Transactional
    public void deleteDefinition(Long id) {

        ResourceDefinitionEntity entity = definitionDao.findOne(id);
        if( entity != null  ){
            if( entity.getStatus() == ResourceDefinitionEntity.Status.released ) {
                throw new BusinessRuntimeException("已发布资源不能删除!");
            }
            definitionDao.delete(entity);
        }

    }

    @Override
    @Transactional
    public ResourceDefinitionEntity releaseDefinition(Long id) {

        ResourceDefinitionEntity entity =  definitionDao.findOne(id);
        entity.setStatus(ResourceDefinitionEntity.Status.released);
        definitionDao.save(entity);
        return entity;
    }

    @Override
    public List<ResourceDefinitionEntity> findDefinition(IExpression<Boolean> expression) {
        return definitionDao.findAll(expression);
    }

    @Override
    public Page<ResourceDefinitionEntity> findDefinition(IExpression<Boolean> expression, Pageable pageable) {
        return definitionDao.findAll(expression,pageable);
    }

    @Override
    @Transactional
    public ResourceItemEntity saveOrUpdate(ResourceItemEntity itemEntity) {


        if( itemEntity.getId() != null ){
            ResourceItemEntity item =  itemDao.getOne(itemEntity.getId());
            item.setJson( itemEntity.getJson() );
            item.setScope( itemEntity.getScope() );
            item.setSort( itemEntity.getSort() );
            itemDao.save(item);
            return item;
        }else {
            itemDao.save(itemEntity);
            return itemEntity;
        }

    }

    @Override
    public List<ResourceItemEntity> findItem(IExpression<Boolean> expression) {

        return itemDao.findAll(expression,new Sort(Sort.Direction.DESC,"sort"));
    }

    @Override
    public List<ResourceItemEntity> findItem(IExpression<Boolean> expression, Integer limit) {

        return itemDao.findTop(expression,limit,new Sort(Sort.Direction.DESC,"sort"));
    }

    @Override
    public Page<ResourceItemEntity> findItem(IExpression<Boolean> expression, Pageable pageable) {
        return itemDao.findAll(expression,pageable);
    }

    @Override
    @Transactional
    public ResourceItemEntity deleteItem(Long id) {

        ResourceItemEntity itemEntity = itemDao.findOne(id);
        itemDao.delete(itemEntity);
        return itemEntity;
    }
}
