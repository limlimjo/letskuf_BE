package com.letskuf.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * MultiFile을 S3에 업로드
     *
     * @return S3 Object Key
     * 예: team/xxxx-xxx-xx-x.jpg
     */
    public String uploadFileToS3(MultipartFile multipartFile, String filePath) throws IOException {

        String originalFileName = multipartFile.getOriginalFilename();

        String extension = "";

        if (originalFileName != null && originalFileName.contains(".")) {

            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String fileName = filePath + "/" + UUID.randomUUID() + extension;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                                            .bucket(bucket)
                                            .key(fileName)
                                            .contentType(multipartFile.getContentType())
                                            .build();

        s3Client.putObject(putObjectRequest,
                            RequestBody.fromInputStream(
                                    multipartFile.getInputStream(),
                                    multipartFile.getSize()
                            )
        );

        log.info("[S3Uploader] 파일 업로드 성공: {}", fileName);

        return fileName;
    }

    /**
     * S3 파일 삭제
     *
     * @param key S3 Object Key
     */
    public void deleteS3(String key) {

        if (key == null || key.isBlank()) {
            return;
        }

        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                                                        .bucket(bucket)
                                                        .key(key)
                                                        .build();

            s3Client.deleteObject(deleteObjectRequest);

            log.info("[S3Uploader] 파일 삭제 성공: {}", key);

        } catch (S3Exception e) {
            log.error("[S3Uploader] 파일 삭제 실패: {}", key, e);
        }
    }

    /**
     * S3 Object Key로 접근 URL 생성
     */
    public String getFileUrl(String key) {
        return s3Client.utilities()
                .getUrl(builder -> builder
                        .bucket(bucket)
                        .key(key))
                .toString();
    }
}
