package com.firefly.cloud.adapter.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.firefly.cloud.adapter.config.OssClientFactory;
import com.firefly.cloud.adapter.config.OssConfig;
import com.firefly.cloud.adapter.service.OssService;
import com.firefly.cloud.repository.dao.CspConfigDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class OssServiceImpl implements OssService {

    @Autowired
    private OssClientFactory ossClientFactory;
    
    @Autowired
    private CspConfigDao cspConfigDao;
    
    private final OssConfig ossConfig;

    @Override
    public boolean createFolder(String bucket, String folderPath) {
        try {
            // 确保文件夹路径以/结尾
            if (!folderPath.endsWith("/")) {
                folderPath = folderPath + "/";
            }

            // 获取OSS客户端
            OSS ossClient = ossClientFactory.getOssClient(bucket);
            if (ossClient == null) {
                throw new RuntimeException("获取OSS客户端失败");
            }

            // 创建一个空对象，模拟文件夹
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                bucket,
                folderPath,
                new ByteArrayInputStream(new byte[0])
            );

            // 设置对象的元数据
            putObjectRequest.setMetadata(new com.aliyun.oss.model.ObjectMetadata());
            putObjectRequest.getMetadata().setContentLength(0);

            // 上传空对象
            ossClient.putObject(putObjectRequest);
            
            log.info("成功创建文件夹: {}", folderPath);
            return true;
        } catch (Exception e) {
            log.error("创建文件夹失败: {}", folderPath, e);
            return false;
        }
    }

    @Override
    public File downloadFile(String objectName) {
        try {
            OSSObject ossObject = ossClientFactory.getOssClient(ossConfig.getBucketName()).getObject(
                new GetObjectRequest(ossConfig.getBucketName(), objectName)
            );

            File file = new File(objectName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = ossObject.getObjectContent().read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }
            return file;
        } catch (IOException e) {
            log.error("文件下载失败", e);
            throw new RuntimeException("文件下载失败", e);
        }
    }
} 