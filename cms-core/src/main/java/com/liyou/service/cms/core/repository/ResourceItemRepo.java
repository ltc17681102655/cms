package com.liyou.service.cms.core.repository;


import com.liyou.framework.jpa.extend.JpaSpecificationExecutorExt;
import com.liyou.service.cms.core.entity.ResourceItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by linxiaohui on 15/8/18.
 */
public interface ResourceItemRepo extends JpaRepository<ResourceItemEntity,Long>
        ,JpaSpecificationExecutorExt<ResourceItemEntity> {
}
