package com.firefly.cloud.adapter.service;

import java.io.File;
import java.io.InputStream;

public interface OssService {

    /**
     * 上传文件到OSS
     * @param inputStream 要上传的文件流
     * @param fileName 文件名
     * @return 文件的访问URL
     */
    String uploadFile(InputStream inputStream, String fileName);

    /**
     * 从OSS下载文件
     * @param objectName OSS中的对象名称
     * @return 下载的文件
     */
    File downloadFile(String objectName);

    /**
     * 创建文件夹
     * @param bucket 存储桶名称
     * @param folderPath 文件夹路径，例如：folder1/folder2/
     * @return 是否创建成功
     */
    boolean createFolder(String bucket, String folderPath);

} 