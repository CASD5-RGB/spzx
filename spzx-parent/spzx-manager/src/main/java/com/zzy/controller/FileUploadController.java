package com.zzy.controller;

import com.zzy.service.FileUploadService;
import com.zzy.vo.common.Result;
import com.zzy.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-26  13:45
 * @Description: 上传图片
 * @Version: 1.0
 */
@Tag(name = "图片上传")
@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService ;

    @Operation(summary = "上传图片的接口")
    @PostMapping(value = "/fileUpload")
    public Result<String> fileUploadService(@RequestParam(value = "file") MultipartFile multipartFile) {
        String fileUrl = fileUploadService.fileUpload(multipartFile) ;
        return Result.build(fileUrl , ResultCodeEnum.SUCCESS) ;
    }

}
