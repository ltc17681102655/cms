//package com.liyou.service.cms.manager.util;
//
//import com.qiniu.common.QiniuException;
//import com.qiniu.storage.BucketManager;
//import com.qiniu.storage.UploadManager;
//import com.qiniu.util.Auth;
//import com.xmsj.arch.common.qiniu.QiniuConfiguration;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//
//
///**
// * Created by 林晓辉 on 2015/4/29.
// */
//public class QiniuUtils {
//
//    private static final Logger logger = LoggerFactory.getLogger(QiniuUtils.class);
//
//    private static final Auth auth=Auth.create(QiniuConfiguration.Mac.accessKey, QiniuConfiguration.Mac.secretKey);
//    private static final BucketManager bucketManager=new BucketManager(auth);
//    private static final UploadManager uploadManager = new UploadManager();
//
//
//    public static String fetch(QiniuConfiguration.NAMESPACE bucket,String url, String key) throws QiniuException {
//
//        String domain = com.xmsj.arch.common.qiniu.QiniuUtils.getServerUrl(bucket);
//        bucketManager.fetch(url, bucket.getValue(),key);
//        String newUrl = domain + key;
//
//        if( logger.isDebugEnabled() ){
//            logger.debug("上传文件 {} 到七牛服务器 {}", url,newUrl);
//        }
//        return newUrl;
//    }
//
//    public static String upload(QiniuConfiguration.NAMESPACE bucket,File file, String key) throws QiniuException {
//
//        bucketManager.delete(bucket.getValue(),key);
//        String token = auth.uploadToken(bucket.getValue(),key,24*3600*365*200,null);
//        com.qiniu.http.Response response=uploadManager.put(file, key, token);
//        String newUrl = com.xmsj.arch.common.qiniu.QiniuUtils.getServerUrl(bucket) + key;
//
//        if( logger.isDebugEnabled() ){
//            logger.debug("上传文件到七牛服务器 {}", newUrl);
//            logger.debug("上传文件到七牛服务器 {}", response.toString());
//        }
//
//        return newUrl;
//    }
//
//
//    public static String upload(QiniuConfiguration.NAMESPACE bucket,byte [] file, String key) throws QiniuException {
//
//        return upload(bucket, file, key,null);
//    }
//
//
//    public static String upload(QiniuConfiguration.NAMESPACE bucket,byte [] file, String key,String mime) throws QiniuException {
//
//        //,new StringMap().put("insertOnly",0)
//        String token = auth.uploadToken(bucket.getValue(),key,24*3600*365*200,null);
//        com.qiniu.http.Response response=uploadManager.put(file, key, token,null,mime,true);
//        String newUrl = com.xmsj.arch.common.qiniu.QiniuUtils.getServerUrl(bucket) + key;
//
//        if( logger.isDebugEnabled() ){
//            logger.debug("上传文件到七牛服务器 {}",newUrl);
//            logger.debug("上传文件到七牛服务器 {}", response.toString());
//        }
//
//        return newUrl;
//    }
//
//
//    public static String uploadText(QiniuConfiguration.NAMESPACE bucket,String text, String key) throws QiniuException {
//        return upload(bucket,text.getBytes(),key);
//    }
//
//    public static String uploadText(QiniuConfiguration.NAMESPACE bucket,String text, String key,String mime) throws QiniuException {
//        return upload(bucket,text.getBytes(),key,mime);
//    }
//
//    public static String getExtension(String fileName){
//        return fileName.substring( fileName.lastIndexOf(".") ).split("\\?")[0];
//    }
//
//}
