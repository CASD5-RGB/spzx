package com.zzy.service;

import com.github.pagehelper.PageInfo;
import com.zzy.dto.system.AssginRoleDto;
import com.zzy.dto.system.LoginDto;
import com.zzy.dto.system.SysUserDto;
import com.zzy.entity.system.SysUser;
import com.zzy.vo.system.LoginVo;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-12  17:05
 * @Description: TODO
 * @Version: 1.0
 */

public interface SysUserService {
    //登录接口
    LoginVo login(LoginDto loginDto);

    //获取用户的信息
    SysUser getUserInfo(String token);
    //退出功能
    void logout(String token);

    //分页+条件
    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);

    //添加用户信息
    void saveSysUser(SysUser sysUser);

    //修改用户信息
    void updateSysUser(SysUser sysUser);

    //删除用户信息
    void deleteById(Long userId);

    //保存角色数据
    void doAssign(AssginRoleDto assginRoleDto);
}
