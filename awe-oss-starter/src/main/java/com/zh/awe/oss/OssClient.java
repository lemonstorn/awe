package com.zh.awe.oss;


import software.amazon.awssdk.transfer.s3.model.CompletedFileDownload;

import java.io.File;

/**
 * Oss 基础操作
 * 想要更复杂订单操作可以直接获取AmazonS3，通过AmazonS3 来进行复杂的操作
 */
public interface OssClient {
    OssClient getInstance();
    /**
     * 创建bucket
     * @param bucketName
     */
    void createBucket(String bucketName);

    /**
     * 分片上传  文件大于8m自动改为分片，建议使用这个
     * @param bucketName    桶名称
     * @param key           文件key
     */
    default void multipartUpload(String bucketName, String key,String filePath){
        multipartUpload(bucketName,key,new File(filePath));
    }
    void multipartUpload(String bucketName, String key,File file);

    /**
     * 获取url
     * @param bucketName
     * @param objectName
     * @return
     */
    String getObjectURL(String bucketName, String objectName);

    /**
     * 下载
     * @param bucketName
     * @param key
     * @param downloadedFileWithPath
     */
    default CompletedFileDownload multipartDownload(String bucketName, String key, String downloadedFileWithPath){
        return multipartDownload(bucketName,key,new File(downloadedFileWithPath));
    }
    CompletedFileDownload multipartDownload(String bucketName, String key, File downloadFile);

}
