package com.zzy.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.zzy.service.ValidateCodeService;
import com.zzy.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-12  20:38
 * @Description: 实现类
 * @Version: 1.0
 */
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //验证码的接口

    /**
     * 图片验证码的意义在于，防止有人通过特定程序暴力破解用户的密码
     * @return
     */
    @Override
    public ValidateCodeVo generateValidateCode() {

        // 使用hutool工具包中的工具类生成图片验证码
        //参数：宽  高  验证码位数 干扰线数量
        CircleCaptcha circleCaptcha = CaptchaUtil
                .createCircleCaptcha(150, 48,
                        4, 20);
        //获取验证码的code值
        String codeValue = circleCaptcha.getCode();
        //获取验证码Base64的图片流
        String imageBase64 = circleCaptcha.getImageBase64();
        //使用UUID随机一个key，作为redis中的验证码的key
        String codeKey = UUID.randomUUID().toString().replaceAll("-","");
        //设置该验证码在redis中，五分钟有效
        redisTemplate.opsForValue().set("user:login:" + codeKey,
                codeValue,
                5,
                TimeUnit.MINUTES);
        // 构建响应结果数据
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        //向前端返回验证码的值
        validateCodeVo.setCodeValue(codeValue);
        //返回用hutool生成的验证码图片
        validateCodeVo.setCodeKey("data:image/png;base64," + imageBase64);
        return validateCodeVo;
    }
}
