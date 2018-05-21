package com.liyou.service.cms.core.repository;

import com.liyou.framework.jpa.extend.JpaSpecificationExecutorExt;
import com.liyou.service.cms.core.entity.UdcInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 *
 *   @author linxiaohui
 *   @date 2018/5/11
 ***/
public interface UdcInfoEntityRepo extends JpaRepository<UdcInfoEntity,Integer>, JpaSpecificationExecutorExt<UdcInfoEntity> {
}
