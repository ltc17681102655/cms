package com.liyou.service.cms.manager;

import com.liyou.framework.base.store.LocalStore;
import com.liyou.framework.base.store.Store;
import com.liyou.framework.common.utils.TimeGapUtils;
import com.liyou.framework.jpa.extend.JpaRepositoryFactoryBeanExt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMappingCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.liyou")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableTransactionManagement
@EnableJpaAuditing
@EntityScan("com.liyou.service.cms.core")
@ImportResource("classpath:dubbo-producer.xml")

@EnableJpaRepositories(repositoryFactoryBeanClass = JpaRepositoryFactoryBeanExt.class,basePackages = "com.liyou.service.cms.core")
public class CmsServerApplication {

    @Bean(name = Constants.CACHE_SCOPE_LOCAL)
    public Store localCache(){
        return new LocalStore();
    }

    @Bean
    public EndpointHandlerMappingCustomizer endpointHandlerMappingCustomizer(){
        return new com.liyou.framework.web.actuator.EndpointWhileListHandlerMapping();
    }

    public static void main(String[] args) {
        TimeGapUtils.start("程序启动",true);
        SpringApplication.run(CmsServerApplication.class, args);
        TimeGapUtils.end("程序启动");
    }
}