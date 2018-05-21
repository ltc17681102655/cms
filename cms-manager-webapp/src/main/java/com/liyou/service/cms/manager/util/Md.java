package com.liyou.service.cms.manager.util;

import com.liyou.framework.base.utils.DigestUtils;

/***
 *
 *   @author linxiaohui
 *   @date 2018/5/4
 ***/
public class Md {
    public static void main(String[] args) {
        System.out.println(DigestUtils.digest("XMSJ_&_ER_P123456", DigestUtils.Digest.MD5_32));
    }
}
