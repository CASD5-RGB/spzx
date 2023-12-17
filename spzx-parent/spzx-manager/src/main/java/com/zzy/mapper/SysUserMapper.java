package com.zzy.mapper;

import com.zzy.dto.system.SysUserDto;
import com.zzy.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-12  17:10
 * @Description: TODO
 * @Version: 1.0
 */

@Mapper
public interface SysUserMapper {

    //根据用户名查询用户信息，如果DB中用户数据比较大的情况下，可以考虑对用户名这个字段添加索引
    SysUser getInfoByUserName(String userName);

    //分页+条件
    List<SysUser> findByPage(SysUserDto sysUserDto);

    //添加用户信息
    void saveSysUser(SysUser sysUser);

    //根据前端传来的用户名，查询DB中是否存在
    SysUser findByUserName(String userName);

    //修改用户信息
    void updateSysUser(SysUser sysUser);

    //删除用户信息
    void deleteSysUser(Long userId);
}
