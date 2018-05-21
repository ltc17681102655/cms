package com.liyou.service.cms.client;

import java.io.Serializable;

/***
 *
 *   @author linxiaohui
 *   @date 2018/5/21
 ***/
public class ResourceItem implements Serializable{

    private Long id;
    private String json;
    private Long scope;
    private Integer sortNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Long getScope() {
        return scope;
    }

    public void setScope(Long scope) {
        this.scope = scope;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }
}
