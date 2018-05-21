package com.liyou.service.cms.core;

import com.google.common.collect.Maps;
import com.liyou.framework.base.criteria.Expressions;
import com.liyou.framework.base.criteria.predicate.CompoundPredicate;
import com.liyou.framework.common.utils.SpringContextUtils;
import com.liyou.service.cms.core.entity.UdcInfoEntity;
import com.liyou.service.cms.core.repository.UdcInfoEntityRepo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/***
 *
 *   @author linxiaohui
 *   @date 2018/5/11
 ***/
public class UdcUtils {


    private static long lastModifyTime = 0;

    private static Map<Integer, UdcInfoEntity> CACHE = Maps.newLinkedHashMap();

    private static final Object LOCK = new Object();



    private static Map<Integer, UdcInfoEntity> findAll(){

        if (lastModifyTime == 0 ||
                lastModifyTime + TimeUnit.MILLISECONDS.convert(15, TimeUnit.MINUTES) > System.currentTimeMillis()) {

            synchronized (LOCK) {

                CACHE.clear();
                CompoundPredicate predicate = Expressions.and()
                        .addEquals("udcType", 5)
                        .addEquals("parentCode", 0);
                List<UdcInfoEntity> data = SpringContextUtils.getBean(UdcInfoEntityRepo.class).findAll(predicate);
                data.forEach(e -> CACHE.put(e.getUdcCode(), e));

                lastModifyTime = System.currentTimeMillis();
            }

        }
        return CACHE;
    }

    public static UdcInfoEntity city(Integer code) {


        UdcInfoEntity udc =  findAll().get(code);

        if( null == udc ){

            synchronized ( LOCK ){
                udc = SpringContextUtils.getBean(UdcInfoEntityRepo.class).findOne(code);
                if( udc != null ){
                    CACHE.put(code,udc);
                }
            }

        }
        return udc;
    }

    public static List<UdcInfoEntity> cities() {


        return findAll().values().stream().collect(Collectors.toList());
    }

}
