package com.liyou.service.cms.manager.controller;

import com.liyou.framework.base.IfValue;
import com.liyou.framework.base.criteria.Expressions;
import com.liyou.framework.base.criteria.predicate.CompoundPredicate;
import com.liyou.framework.base.model.Response;
import com.liyou.framework.jpa.support.JpaPageHelp;
import com.liyou.framework.page.PageRequestCustom;
import com.liyou.service.cms.core.entity.ResourceItemEntity;
import com.liyou.service.cms.core.service.ResourceService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by linxiaohui on 15/8/19.
 */
@Controller
@RequestMapping("resourceItem")
public class ResourceItemController{

    @Autowired
    private ResourceService service;



    @ResponseBody
    @RequestMapping("list")
    public Object list(Long resourceId, Integer scope, PageRequestCustom pageRequestCustom){

        CompoundPredicate predicate =  Expressions.and()
                .addEquals("resourceId",resourceId)
                .addEquals("scope",scope, IfValue.IF_VALUE_NOT_NULL);
        return service.findItem(predicate,
                JpaPageHelp.convert(pageRequestCustom,new Sort(Sort.Direction.DESC,"lastModifiedDate")));
    }


    @ResponseBody
    @RequestMapping("delete")
    public Object delete(Long id){
        return service.deleteItem(id);
    }



    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(ResourceItemEntity entity){

        try {
            service.saveOrUpdate(entity);
        }catch (Exception e){
            if( null !=e  && ExceptionUtils.getRootCauseMessage(e).contains("Data too long") ){
                return Response.failure("上传的数据或图片太大!");
            }
            return Response.failure(e.getMessage());
        }
        return Response.success();
    }
}
