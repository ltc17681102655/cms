package com.liyou.service.cms.manager.controller;

import com.liyou.framework.base.IfValue;
import com.liyou.framework.base.criteria.Expressions;
import com.liyou.framework.base.criteria.predicate.CompoundPredicate;
import com.liyou.framework.base.model.Response;
import com.liyou.framework.common.cache.Cacheable;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.framework.web.annotation.Authority;
import com.liyou.service.cms.manager.Resource;
import com.liyou.service.cms.core.UdcUtils;
import com.liyou.service.cms.core.entity.ResourceDefinitionEntity;
import com.liyou.service.cms.core.entity.ResourceItemEntity;
import com.liyou.service.cms.core.service.ResourceService;
import com.liyou.service.cms.manager.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

/**
 * Created by linxiaohui on 15/8/20.
 */
@Controller
@RequestMapping("resource")
@Authority(permissions = Constants.AUTH_LOGIN)
public class ResourceController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ResourceService service;

    @RequestMapping("json")
    @ResponseBody
    @Cacheable(cacheManager = Constants.CACHE_SCOPE_LOCAL)
    public String resource(String position, Integer scope) {

        try {
            ResourceDefinitionEntity definition = service.findDefinitionByPosition(position);

            CompoundPredicate predicate = Expressions.and()
                    .addEquals("resourceId", definition.getId())
                    .addEquals("scope", scope, IfValue.IF_VALUE_NOT_NULL);

            List<ResourceItemEntity> items = service.findItem(predicate);
            Collections.sort(items, (a,b) -> a.getSort().compareTo(b.getSort()));


            Resource resource = new Resource();
            resource.setCmd(definition.getPosition());
            resource.setVersion(System.currentTimeMillis());
            resource.setData(items);

            return JSONUtils.toJSON(resource, "{}");
        } catch (Exception e) {
            logger.warn("", e);
        }
        return "";
    }

    @RequestMapping("cities")
    @ResponseBody
    public Object resource() {

        try {
            return Response.success(UdcUtils.cities());
        } catch (Exception e) {
            logger.warn("", e);
            return Response.failure(e);
        }

    }

}
