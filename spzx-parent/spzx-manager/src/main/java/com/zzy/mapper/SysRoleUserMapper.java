package com.zzy.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-26  15:11
 * @Description: TODO
 * @Version: 1.0
 */
@Mapper
public interface SysRoleUserMapper {

    //删除之前的所有的用户所对应的角色数据
    void deleteByUserId(Long userId);

    //分配新的角色数据
    void doAssign(Long userId, Long roleId);
}
