package com.liyou.service.cms.core.entity;

import com.liyou.framework.jpa.AuditEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by linxiaohui on 15/8/18.
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "cms_resource_definition")
@EntityListeners({AuditingEntityListener.class})
public class ResourceDefinitionEntity extends AuditEntity<Long> {

    public enum Status{
        edit,released
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 资源名称(唯一索引) **/
    private String name;

    /** 资源定义 **/
    private String definition;

    /** 资源定位 **/
    private String position;

    /** 状态 **/
    @Enumerated(EnumType.STRING)
    private Status status= Status.edit;

    @Column(name = "default_value")
    private String defaultValue;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
