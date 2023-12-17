package com.zzy.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-26  13:45
 * @Description: TODO
 * @Version: 1.0
 */
public interface FileUploadService {
    //上传图片的接口
    String fileUpload(MultipartFile multipartFile);
}
