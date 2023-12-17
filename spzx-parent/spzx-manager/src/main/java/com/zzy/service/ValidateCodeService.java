package com.zzy.service;

import com.zzy.vo.system.ValidateCodeVo;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-12  20:38
 * @Description: 验证码实现类接口
 * @Version: 1.0
 */
public interface ValidateCodeService {

    //验证码的接口
    ValidateCodeVo generateValidateCode();
}
