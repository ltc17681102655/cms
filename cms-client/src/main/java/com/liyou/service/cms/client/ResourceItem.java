package com.liyou.service.cms.client;

import com.liyou.framework.common.utils.JSONUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

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
    private Date lastModifiedDate;

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

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public <T> T convert(Class<T> cla){

        T obj = null;
        try {
            obj = JSONUtils.toObject(this.json,cla);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public <T> T convert(Class<T> cla,Convert<T> convert){

        T obj = null;
        try {
            obj = JSONUtils.toObject(this.json,cla);
            convert.convert(obj,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
