package com.liyou.service.cms.core.repository;


import com.liyou.framework.jpa.extend.JpaSpecificationExecutorExt;
import com.liyou.service.cms.core.entity.ResourceDefinitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by linxiaohui on 15/8/18.
 */
public interface ResourceDefinitionRepo extends JpaRepository<ResourceDefinitionEntity,Long>,
        JpaSpecificationExecutorExt<ResourceDefinitionEntity> {

}