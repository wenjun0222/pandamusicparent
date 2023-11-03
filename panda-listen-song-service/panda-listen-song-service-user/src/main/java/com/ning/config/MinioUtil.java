package com.ning.config;

import io.minio.PutObjectArgs;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class MinioUtil {
    @Resource
    private MinioProperties minioProperties;
    @Resource
    private MinioConfig minioConfig;
    /**
     * 文件上传
     *
     * @param file 文件
     * @return Boolean
     */
    public String uploadAvatar(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String fileType=originalFilename.substring(originalFilename.indexOf("."),originalFilename.length());
        String filename= UUID.randomUUID().toString()+fileType;
        String filepath="avatar/"+filename;
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(filepath)
                    .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build();
            //文件名称相同会覆盖
            minioConfig.minioClient().putObject(objectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minioProperties.getEndpoint()+"/"+minioProperties.getBucketName()+"/"+filepath;
    }
}
