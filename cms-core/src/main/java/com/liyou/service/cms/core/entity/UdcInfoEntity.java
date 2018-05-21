package com.liyou.service.cms.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author hylexus
 * createdAt 2018/2/6
 */

@Entity
@Table(name = "udc_info")
public class UdcInfoEntity implements Serializable {

    @Id
    @Column(name = "udc_code")
    private Integer udcCode;

    @Column(name = "udc_type")
    private Integer udcType;

    @Column(name = "udc_name")
    private String udcName;


    @Column(name = "pinyin")
    private String pinyin;

    @Column(name = "parent_code")
    private Integer parentCode;

    public Integer getParentCode() {
        return parentCode;
    }

    public void setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getUdcCode() {
        return udcCode;
    }

    public void setUdcCode(Integer udcCode) {
        this.udcCode = udcCode;
    }

    public String getUdcName() {
        return udcName;
    }

    public void setUdcName(String udcName) {
        this.udcName = udcName;
    }

    public Integer getUdcType() {
        return udcType;
    }

    public void setUdcType(Integer udcType) {
        this.udcType = udcType;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UdcInfoEntity{");
        sb.append("udcCode=").append(udcCode);
        sb.append(", udcType=").append(udcType);
        sb.append(", udcName='").append(udcName).append('\'');
        sb.append(", pinyin='").append(pinyin).append('\'');
        sb.append(", parentCode=").append(parentCode);
        sb.append('}');
        return sb.toString();
    }
}
