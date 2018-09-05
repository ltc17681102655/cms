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
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by linxiaohui on 15/8/18.
 */
@Component
public class AuditorUtils implements AuditorAware<Long> , UserAware {


    public AuditorUtils(){
        ContextUtils.setDelegate(this);
    }


    @Override
    public Long getCurrentAuditor() {
        return 0L;
    }


    /**
     * 获取当前用户
     *
     * @return
     */
    @Override
    public User<Long> currentUser() {

        return new User() {
            @Override
            public Long getId() {
                return 0L;
            }

            @Override
            public List permissions(){

                String accessToken = WebUtils.findCookValue("TBSAccessToken");
                if( Strings.isNullOrEmpty(accessToken) ){
                    accessToken = WebUtils.findHeader("TBSAccessToken");
                }
                if( Strings.isNullOrEmpty(accessToken) ){
                    return Collections.EMPTY_LIST;
                }
                return Lists.newArrayList(Constants.AUTH_LOGIN);
            }
        };
    }
}
