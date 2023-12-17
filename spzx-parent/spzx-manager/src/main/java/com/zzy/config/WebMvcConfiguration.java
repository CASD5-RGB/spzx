package com.zzy.config;

import com.zzy.properties.UserAuthProperties;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-12  20:34
 * @Description: 解决跨域的配置类
 * @Version: 1.0
 * 先将跨域的配置类添加到spring的容器中，然后实现WebMvcConfigurer
 * 中的addCorsMappings方法，进而解决项目中出现跨域的问题
 */
@Component  //将该配置类交给spring容器进行管理
public class WebMvcConfiguration implements WebMvcConfigurer {

    //解决跨域
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")   // 添加路径规则
                .allowCredentials(true)     // 是否允许在跨域的情况下传递Cookie
                .allowedOriginPatterns("*")// 允许请求来源的域规则
                .allowedMethods("*")
                .allowedHeaders("*");      // 允许所有的请求头
    }

    @Autowired
    private UserAuthProperties userAuthProperties;        // 注入实体类对象
    @Autowired
    private LoginAuthInterceptor loginAuthInterceptor;

    //拦截器进行路径的拦截
//    @Override
//    public void addInterceptors(@NotNull InterceptorRegistry registry) {
//        registry.addInterceptor(loginAuthInterceptor)
////                不拦截登录和获取验证码的路径
//                .excludePathPatterns(userAuthProperties.getNoAuthUrls())
//                .addPathPatterns("/**");
//    }

}
