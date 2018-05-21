package com.liyou.service.cms.manager.controller;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.liyou.framework.base.model.Response;
import com.liyou.framework.base.utils.DateUtils;
import com.liyou.service.cms.manager.Constants;
import com.liyou.service.cms.manager.util.ImgUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.Map;

/**
 * Created by linxiaohui on 15/8/22.
 */
@Controller
@RequestMapping("upload")
public class FileUploadController {

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.bucketname}")
    private String bucketName;

    @Value("${image_server_host:img.2boss.cn}")
    private String imageServerHost;

    private ClientConfiguration config = new ClientConfiguration();


    @ResponseBody
    @RequestMapping("img")
    public Object upload(String base64Data){


        if(Strings.isNullOrEmpty(base64Data) ){
            return Response.failure("上传数据不能为空!");
        }

        OSSClient ossClient = null;
        try {
            ImgUtils.Img img= ImgUtils.base64Data2Img(base64Data);
            String key = Constants.IMG_DOMAIN+img.getMd5()+".jpg";
            String url= "https://"+ imageServerHost +"/"+key;
            Map result= Maps.newHashMap();
            result.put("url",url);
            result.put("mime",img.getType());
            result.put("size",img.getData().length);


            CredentialsProvider provider = new DefaultCredentialProvider(accessKeyId,accessKeySecret);
            ossClient = new OSSClient(endpoint, provider, config);
            ossClient.putObject(bucketName,key,new ByteArrayInputStream(img.getData()));

            return Response.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.failure("上传数据失败!"+e.getMessage());
        }finally {
            if( ossClient != null ){
                ossClient.shutdown();;
            }
        }
    }
}
