package com.liyou.service.cms.core.entity;

import com.liyou.framework.jpa.AuditEntity;
import com.liyou.service.cms.core.UdcUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by linxiaohui on 15/8/18.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "cms_resource_item")
@EntityListeners({AuditingEntityListener.class})
public class ResourceItemEntity extends AuditEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String json;
    private Integer sort;

    @Column(name = "resource_id",updatable = false)
    private Long resourceId;

    private Long scope;


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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getScope() {
        return scope;
    }

    public void setScope(Long scope) {
        this.scope = scope;
    }

    @Transient
    public UdcInfoEntity getCity() {

        if( null == scope ){
            return null;
        }
        return UdcUtils.city(scope.intValue());
    }

}
