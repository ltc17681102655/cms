package com.liyou.service.cms.manager.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.liyou.framework.base.IfValue;
import com.liyou.framework.base.criteria.Expressions;
import com.liyou.framework.base.criteria.predicate.CompoundPredicate;
import com.liyou.framework.base.model.KeyTrimMap;
import com.liyou.framework.base.model.Response;
import com.liyou.framework.common.json.RawValue;
import com.liyou.framework.common.utils.JSONUtils;
import com.liyou.framework.web.annotation.Authority;
import com.liyou.service.cms.manager.Constants;
import com.liyou.service.cms.manager.Resource;
import com.liyou.service.cms.core.entity.ResourceDefinitionEntity;
import com.liyou.service.cms.core.service.ResourceService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by linxiaohui on 15/8/17.
 */
@Controller
@RequestMapping("resourceDefinition")
@Authority(permissions = Constants.AUTH_LOGIN)
public class ResourceDefinitionController {


    @Autowired
    private ResourceService resourceService;

    @RequestMapping("list")
    @ResponseBody
    public Object list(String name,String position, ResourceDefinitionEntity.Status status) throws IOException {

        try {
            CompoundPredicate predicate = Expressions.and()
                    .addEquals("status",status, IfValue.IF_VALUE_NOT_NULL)
                    .addEquals("position",position, IfValue.IF_VALUE_NOT_BLANK)
                    .addContainIn("name",name, IfValue.IF_VALUE_NOT_BLANK);

            List<ResourceDefinitionEntity> entityList = resourceService.findDefinition(predicate);
            return Response.success(entityList);
        }catch (Exception e){
            return Response.failure(e);
        }
    }


    @ResponseBody
    @RequestMapping("saveOrUpdate")
    public Object saveOrUpdate(ResourceDefinitionEntity entity){
        try {


            JsonNode node= JSONUtils.toJSON(entity.getDefinition());
            if( node.isArray() ){
                return Response.failure("资源的顶级节点不能是数组");
            }


            Map jsonMap=JSONUtils.toObject(entity.getDefinition(), KeyTrimMap.class);
            entity.setDefinition(JSONUtils.toJSON(jsonMap));
            resourceService.saveOrUpdateDefinition(entity);

            Resource resource = new Resource();
            resource.setVersion(System.currentTimeMillis());
            resource.setCmd(entity.getPosition());
            resource.getData().add(new RawValue(entity.getDefaultValue()));

        }catch (Exception e){
            if( null !=e  && ExceptionUtils.getRootCauseMessage(e).contains("Duplicate entry") ){
                return Response.failure("已存在相同资源位置的资源定义!");
            }
            return Response.failure(e);
        }
        return Response.success();
    }


    @ResponseBody
    @RequestMapping("delete")
    public Object delete(Long id){
        try {
            resourceService.deleteDefinition(id);
        }catch (Exception e){
            return Response.failure(e);
        }
        return Response.success();
    }

    @ResponseBody
    @RequestMapping("release")
    public Object release(Long id){

        try {
            resourceService.releaseDefinition(id);
        }catch (Exception e){
            return Response.failure(e);
        }
        return Response.success();
    }

}
