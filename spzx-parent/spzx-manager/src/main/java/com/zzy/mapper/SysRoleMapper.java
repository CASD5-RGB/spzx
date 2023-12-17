package com.zzy.mapper;

import com.zzy.dto.system.SysRoleDto;
import com.zzy.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-26  10:22
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface SysRoleMapper {
    //权限分页+条件
    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    //保存权限数据
    void saveSysRole(SysRole sysRole);

    //修改权限信息
    void updateSysRole(SysRole sysRole);

    //删除角色信息
    void deleteSysRole(Long roleId);

    //查询所有角色
    List<SysRole> findAllRoles();
}
