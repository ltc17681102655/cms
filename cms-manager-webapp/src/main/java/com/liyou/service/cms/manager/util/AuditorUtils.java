package com.liyou.service.cms.manager.util;

import org.springframework.data.domain.AuditorAware;

/**
 * Created by linxiaohui on 15/8/18.
 */

public class AuditorUtils implements AuditorAware<Integer> {

    @Override
    public Integer getCurrentAuditor() {
        //TODO
        //return SysUserUtil.getCurrentUserId();
        return 0;
    }


}
