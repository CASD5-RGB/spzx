package com.zzy.controller;

import com.zzy.dto.system.LoginDto;
import com.zzy.entity.system.SysUser;
import com.zzy.service.SysUserService;
import com.zzy.service.ValidateCodeService;
import com.zzy.utils.AuthContextUtil;
import com.zzy.vo.common.Result;
import com.zzy.vo.common.ResultCodeEnum;
import com.zzy.vo.system.LoginVo;
import com.zzy.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-12  17:04
 * @Description: 用户接口控制层
 * @Version: 1.0
 */
@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ValidateCodeService validateCodeService;

    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto);
        //登录之后，回向前端返回一个token的值
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "验证码的接口")
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }


    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
        //获取前端传来的token值，从redis中根据token值获取用户的信息
        SysUser sysUser = sysUserService.getUserInfo(token) ;
        return Result.build(sysUser , ResultCodeEnum.SUCCESS) ;
    }
//    @Operation(summary = "获取用户信息")
//    @GetMapping(value = "/getUserInfo")
//    public Result<SysUser> getUserInfo() {
//        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS);
//    }

    @Operation(summary = "退出功能")
    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(value = "token") String token) {
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
