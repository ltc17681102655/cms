package com.liyou.service.cms.manager.util;

import com.liyou.framework.base.utils.DigestUtils;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by linxiaohui on 15/8/22.
 */
public class ImgUtils {



    public static class Img{
        private final String type;
        private final byte [] data;
        private final String md5;

        Img(String type,byte [] data,String md5){
            this.data = data;
            this.type = type;
            this.md5 = md5;
        }

        public String getType() {
            return type;
        }

        public byte[] getData() {
            return data;
        }

        public String getMd5() {
            return md5;
        }
    }

    private static final Pattern pattern= Pattern.compile("data:(\\S*);base64,");

    public static Img base64Data2Img(String base64Data) throws IOException {

        String contentType= findContentType(base64Data);
        if( !contentType.contains("image") ){
            throw new IllegalArgumentException("不是一个图片!");
        }
        BASE64Decoder base64Decoder=new BASE64Decoder();
        byte [] data=base64Decoder.decodeBuffer(base64Data.replaceFirst(pattern.pattern(),""));
        String md5=new String(DigestUtils.digest(data, DigestUtils.Digest.MD5_32));
        return new Img(contentType,data,md5);
    }

    public static String findContentType(String base64Data){
        Matcher matcher=pattern.matcher(base64Data);
        matcher.find();
        return matcher.group(1);
    }
}
