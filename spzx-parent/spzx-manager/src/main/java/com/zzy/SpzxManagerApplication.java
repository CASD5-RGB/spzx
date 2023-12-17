package com.zzy;

import com.zzy.properties.MinioProperties;
import com.zzy.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {UserAuthProperties.class, MinioProperties.class})
@SpringBootApplication
public class SpzxManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpzxManagerApplication.class, args);
    }

}
