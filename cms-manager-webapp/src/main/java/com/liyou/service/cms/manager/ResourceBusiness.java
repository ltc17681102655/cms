package com.liyou.service.cms.manager;

import com.liyou.framework.base.IfValue;
import com.liyou.framework.base.criteria.Expressions;
import com.liyou.framework.base.criteria.predicate.CompoundPredicate;
import com.liyou.framework.base.model.Response;
import com.liyou.framework.jpa.support.JpaPageHelp;
import com.liyou.framework.page.PageCustom;
import com.liyou.framework.page.PageRequestCustom;
import com.liyou.service.cms.client.ResourceClient;
import com.liyou.service.cms.client.ResourceItem;
import com.liyou.service.cms.core.entity.ResourceDefinitionEntity;
import com.liyou.service.cms.core.entity.ResourceItemEntity;
import com.liyou.service.cms.core.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/***
 *
 *   @author linxiaohui
 *   @date 2018/5/21
 ***/
@Service
public class ResourceBusiness implements ResourceClient {

    @Autowired
    private ResourceService resourceService;

    /**
     * 查找资源选项
     *
     * @param scope
     * @param position
     * @return
     */
    @Override
    public Response<List<ResourceItem>> findResourceItem(Integer scope, String position) {


        try {
            ResourceDefinitionEntity definition =  resourceService.findDefinitionByPosition(position);

            CompoundPredicate or =  Expressions.or()
                    .addEquals("scope",scope,IfValue.IF_VALUE_NOT_NULL)
                    .addIsNull("scope");

            CompoundPredicate predicate =  Expressions.and()
                    .addEquals("resourceId",definition.getId())
                    .add(or);

            List<ResourceItemEntity>  data = resourceService.findItem( predicate );

            return Response.success(data.stream().map(this::convert).collect(Collectors.toList()));
        }catch (Exception e){
            return Response.failure(e);
        }
    }

    /**
     * 分页查找资源
     *
     * @param scope
     * @param position
     * @param pageable
     * @return
     */
    @Override
    public Response<PageCustom<ResourceItem>> findResourceItem(Integer scope, String position, PageRequestCustom pageable) {


        try {
            ResourceDefinitionEntity definition =  resourceService.findDefinitionByPosition(position);

            CompoundPredicate or =  Expressions.or()
                    .addEquals("scope",scope,IfValue.IF_VALUE_NOT_NULL)
                    .addIsNull("scope");

            CompoundPredicate predicate =  Expressions.and()
                    .addEquals("resourceId",definition.getId())
                    .add(or);

            Page<ResourceItemEntity> data = resourceService.findItem( predicate, JpaPageHelp.convert(pageable,new Sort("sort")) );

            return Response.success(JpaPageHelp.convert(data.map(this::convert),pageable));
        }catch (Exception e){
            return Response.failure(e);
        }
    }

    private ResourceItem convert(ResourceItemEntity itemEntity){

        ResourceItem resourceItem = new ResourceItem();
        resourceItem.setId( itemEntity.getId() );
        resourceItem.setJson( itemEntity.getJson() );
        resourceItem.setScope( itemEntity.getScope() );
        resourceItem.setSortNumber( itemEntity.getSort() );

        return resourceItem;
    }
}
