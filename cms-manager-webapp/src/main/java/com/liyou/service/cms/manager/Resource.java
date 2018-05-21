package com.liyou.service.cms.manager;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by linxiaohui on 15/8/19.
 */
public class Resource {

    private Long version;
    private String cmd;
    private List data = Lists.newArrayListWithCapacity(0);

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
