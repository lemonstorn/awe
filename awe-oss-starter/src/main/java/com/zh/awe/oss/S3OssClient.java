package com.zh.awe.oss;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.*;
import software.amazon.awssdk.transfer.s3.progress.LoggingTransferListener;

import javax.naming.OperationNotSupportedException;
import java.io.File;

/**
 * s3 是一个协议
 * S3是Simple Storage Service的缩写，即简单存储服务
 */
@Slf4j
public class S3OssClient implements OssClient {
    private static volatile S3OssClient ossClient;
    private final S3Client s3Client = DependencyFactory.s3Client();
    //  异步S3 传输管理器
    private final S3TransferManager transferManager = S3TransferManager.builder().s3Client(DependencyFactory.s3AsyncClient()).build();

    private S3OssClient() {
    }
    /**
     * 单例双重检验锁生成
     */
    public static S3OssClient newInstance() {
        if (ossClient == null){
            synchronized (S3OssClient.class){
                if (ossClient == null){
                    ossClient = new S3OssClient() ;
                }
            }
        }
        return ossClient;
    }

    @Override
    public OssClient getInstance() {
        return newInstance();
    }

    @Override
    public void createBucket(String bucketName) {
        try {
            S3Waiter s3Waiter = s3Client.waiter();
            CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            s3Client.createBucket(bucketRequest);
            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            // Wait until the bucket is created and print out the response.
            WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
            waiterResponse.matched().response().ifPresent(System.out::println);
            System.out.println(bucketName +" is ready");

        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    @Override
    public void multipartUpload(String bucketName, String key,File file) {
        UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
                .putObjectRequest(b -> b
                        .bucket(bucketName)
                        .key(key))
                .source(file)
                .build();
        FileUpload fileUpload = transferManager.uploadFile(uploadFileRequest);
        fileUpload.completionFuture().join();
        transferManager.close();
    }

    @Override
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName) {
//        PutObjectRequest objectRequest = PutObjectRequest.builder()
//                .bucket(bucketName)
//                .key(keyName)
//                .contentType("text/plain")
//                .build();
//
//        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
//                .signatureDuration(Duration.ofMinutes(10))
//                .putObjectRequest(objectRequest)
//                .build();
//
//        PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
//        String myURL = presignedRequest.url().toString();
//        System.out.println("Presigned URL to upload a file to: " +myURL);
//        System.out.println("Which HTTP method needs to be used when uploading a file: " + presignedRequest.httpRequest().method());
//
//        return url.toString();
        throw new OperationNotSupportedException();
    }

    @Override
    public CompletedFileDownload multipartDownload(String bucketName, String key, File downloadFile) {
        DownloadFileRequest downloadFileRequest =
                DownloadFileRequest.builder()
                        .getObjectRequest(b -> b.bucket(bucketName).key(key))
                        .addTransferListener(LoggingTransferListener.create())
                        .destination(downloadFile)
                        .build();

        FileDownload file = transferManager.downloadFile(downloadFileRequest);

        CompletedFileDownload downloadResult = file.completionFuture().join();
        log.info("Content length [{}]", downloadResult.response().contentLength());
        return downloadResult;
    }


}
