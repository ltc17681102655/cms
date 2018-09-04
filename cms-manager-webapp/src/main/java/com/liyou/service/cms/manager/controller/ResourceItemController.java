package com.liyou.service.cms.manager.controller;

import com.google.common.base.Strings;
import com.liyou.framework.base.IfValue;
import com.liyou.framework.base.criteria.Expressions;
import com.liyou.framework.base.criteria.predicate.CompoundPredicate;
import com.liyou.framework.base.model.Response;
import com.liyou.framework.jpa.support.JpaPageHelp;
import com.liyou.framework.page.PageRequestCustom;
import com.liyou.framework.web.annotation.Authority;
import com.liyou.service.cms.core.entity.ResourceItemEntity;
import com.liyou.service.cms.core.service.ResourceService;
import com.liyou.service.cms.manager.Constants;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * Created by linxiaohui on 15/8/19.
 */
@Controller
@RequestMapping("resourceItem")
@Authority(permissions = Constants.AUTH_LOGIN)
public class ResourceItemController{

    @Autowired
    private ResourceService service;



    @ResponseBody
    @RequestMapping("list")
    public Object list(Long resourceId, Integer scope, PageRequestCustom pageRequestCustom,
                       String sortName,String sortOrder,String includePublic){

        String sortField = Strings.isNullOrEmpty(sortName)
                ? "lastModifiedDate"
                : sortName.replace("$extends.","");

        if( Objects.equals("cityName",sortField) ){
            sortField = "scope";
        }

        Sort.Direction dir = Objects.equals("asc",sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;

        CompoundPredicate predicate =  Expressions.and().addEquals("resourceId",resourceId);

        if( scope == null ){
            predicate.addIsNull("scope");
        }else if( scope != null && Strings.isNullOrEmpty(includePublic)){
            predicate.addEquals("scope",scope);
        }else {
            CompoundPredicate or = Expressions.or()
                    .addEquals("scope",scope)
                    .addIsNull("scope");
            predicate.add(or);
        }

        return service.findItem(predicate, JpaPageHelp.convert(pageRequestCustom,new Sort(dir,sortField)));
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
