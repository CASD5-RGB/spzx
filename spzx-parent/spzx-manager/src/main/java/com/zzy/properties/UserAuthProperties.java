package com.zzy.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-25  22:02
 * @Description: TODO
 * @Version: 1.0
 */

@Data
@ConfigurationProperties(prefix = "spzx.auth")      // 前缀不能使用驼峰命名
public class UserAuthProperties {
    private List<String> noAuthUrls ;
}
