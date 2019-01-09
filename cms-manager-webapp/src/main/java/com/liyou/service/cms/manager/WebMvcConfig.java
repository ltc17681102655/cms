package com.liyou.service.cms.manager;

import com.liyou.framework.web.interceptor.AnnotationAuthorityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

//        //权限拦截器
//        registry.addInterceptor(new AnnotationAuthorityInterceptor());
//        super.addInterceptors(registry);
    }
}