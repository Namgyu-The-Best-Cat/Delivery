package com.bestcat.delivery.common.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FileUpload {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public List<String> uploadMultipleFile(List<MultipartFile> files) throws IOException {

        List<String> fileUrls = new ArrayList<>();

        for (MultipartFile multipartFile : files) {
            // 원본 파일 이름에서 확장자 추출
            String originalFilename = multipartFile.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // UUID로 고유한 파일 이름 생성
            String uniqueFilename = UUID.randomUUID().toString() + extension;

            // 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            // S3에 파일 업로드
            amazonS3.putObject(bucket, uniqueFilename, multipartFile.getInputStream(), metadata);
            String fileUrl = amazonS3.getUrl(bucket, uniqueFilename).toString();
            fileUrls.add(fileUrl);
        }
        return fileUrls; // 각 파일의 S3 URL을 리스트로 반환
    }

    public String uploadSingleFile(MultipartFile file) throws IOException {

        String fileUrl;

        // 원본 파일 이름에서 확장자 추출
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // UUID로 고유한 파일 이름 생성
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        // S3에 파일 업로드
        amazonS3.putObject(bucket, uniqueFilename, file.getInputStream(), metadata);
        fileUrl = amazonS3.getUrl(bucket, uniqueFilename).toString();

        return fileUrl; // 각 파일의 S3 URL을 리스트로 반환
    }
}
