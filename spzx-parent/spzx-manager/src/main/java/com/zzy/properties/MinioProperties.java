package com.zzy.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-26  13:48
 * @Description: TODO
 * @Version: 1.0
 */
@Data
@ConfigurationProperties(prefix="spzx.minio") //读取节点
public class MinioProperties {

    private String endpointUrl;
    private String accessKey;
    private String secreKey;
    private String bucketName;

}
