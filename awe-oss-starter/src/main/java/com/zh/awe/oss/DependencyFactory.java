package com.zh.awe.oss;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

import static software.amazon.awssdk.transfer.s3.SizeConstant.MB;

/**
 * s3工厂
 * @author zh 2023/9/17 22:48
 */
public class DependencyFactory {

    private DependencyFactory() {}

    /**
     * @return an instance of S3Client
     */
    public static S3Client s3Client() {
        return S3Client.builder()
                .region(Region.CN_NORTH_1)
                .httpClientBuilder(ApacheHttpClient.builder())
                .build();
    }

    public static S3AsyncClient s3AsyncClient(){
        return S3AsyncClient.crtBuilder()
                .credentialsProvider(DefaultCredentialsProvider.create())
                .region(Region.CN_NORTH_1)
                .targetThroughputInGbps(20.0)
                .minimumPartSizeInBytes(8 * MB)
                .build();
    }
}
