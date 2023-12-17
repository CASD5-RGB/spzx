package com.zzy.service;

import com.github.pagehelper.PageInfo;
import com.zzy.dto.system.SysRoleDto;
import com.zzy.entity.system.SysRole;

import java.util.Map;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-26  10:17
 * @Description: 权限接口
 * @Version: 1.0
 */
public interface SysRoleService {
    //权限分页查询+条件
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize);

    //保存权限数据
    void saveSysRole(SysRole sysRole);

    //修改权限信息
    void updateSysRole(SysRole sysRole);

    //删除角色信息
    void deleteById(Long roleId);

    //查询所有角色
    Map<String, Object> findAllRoles();
}
