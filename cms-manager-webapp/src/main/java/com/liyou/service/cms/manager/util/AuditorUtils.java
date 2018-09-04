package com.liyou.service.cms.manager.util;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.liyou.framework.base.user.User;
import com.liyou.framework.base.user.UserAware;
import com.liyou.framework.base.utils.ContextUtils;
import com.liyou.framework.web.util.WebUtils;
import com.liyou.service.cms.manager.Constants;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by linxiaohui on 15/8/18.
 */
@Component
public class AuditorUtils implements AuditorAware<Integer> , UserAware {


    public AuditorUtils(){
        ContextUtils.setDelegate(this);
    }


    @Override
    public Integer getCurrentAuditor() {
        return 0;
    }


    /**
     * 获取当前用户
     *
     * @return
     */
    @Override
    public User currentUser() {

        return new User() {
            @Override
            public Serializable getId() {
                return 0;
            }

            @Override
            public List permissions(){

                String accessToken = WebUtils.findHeader("TBSAccessToken");
                if( Strings.isNullOrEmpty(accessToken) ){
                    return Collections.emptyList();
                }
                return Lists.newArrayList(Constants.AUTH_LOGIN);
            }
        };
    }
}
