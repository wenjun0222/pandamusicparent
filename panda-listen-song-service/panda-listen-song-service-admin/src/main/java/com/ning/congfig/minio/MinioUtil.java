package com.ning.congfig.minio;

import io.minio.PutObjectArgs;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Component
public class MinioUtil {
    @Resource
    private MinioProperties minioProperties;
    @Resource
    private MinioConfig minioConfig;
    /**
     * 歌手头像文件
     */
    public String uploadSingerAvatar(MultipartFile file) {
        String packages="singer";
        return uploadFile(file,packages);
    }
    /**
     * 上传歌曲文件
     * */
    public String uploadSongFile(MultipartFile file){
        String packages="music/song";
        return uploadFile(file,packages);
    }
    /**
     * 上传歌词文件
     * */
    public String uploadLrcFile(MultipartFile file){
        String packages="music/lrc";
        return uploadFile(file,packages);
    }
    /**
     * 上传歌词文件
     * */
    public String uploadFileTest(MultipartFile file){
        String packages="test";
        return uploadFile(file,packages);
    }
    /**
     * 将文件上传到minio系统中
     * */
    private String uploadFile(MultipartFile file,String packages){
        String originalFilename = file.getOriginalFilename();
        String fileType=originalFilename.substring(originalFilename.indexOf("."),originalFilename.length());
        String filename= originalFilename.substring(0,originalFilename.indexOf("."));
        String filepath=packages+"/"+filename+fileType;
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
