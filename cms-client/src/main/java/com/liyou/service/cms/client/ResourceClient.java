package com.liyou.service.cms.client;

import com.liyou.framework.base.model.Response;
import com.liyou.framework.page.PageCustom;
import com.liyou.framework.page.PageRequestCustom;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/***
 *
 *   @author linxiaohui
 *   @date 2018/5/21
 ***/
public interface ResourceClient {


    /**
     * 查找资源选项
     * @param scope
     * @param position
     * @return
     */
     Response<List<ResourceItem>> findResourceItem(Integer scope, @NotBlank String position);


    /**
     * 分页查找资源
     * @param scope
     * @param position
     * @param pageable
     * @return
     */
    Response<PageCustom<ResourceItem>> findResourceItem(Integer scope,@NotBlank String position, PageRequestCustom pageable);
}
