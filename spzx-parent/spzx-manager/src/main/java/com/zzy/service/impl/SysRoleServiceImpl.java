package com.zzy.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.dto.system.SysRoleDto;
import com.zzy.entity.system.SysRole;
import com.zzy.mapper.SysRoleMapper;
import com.zzy.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-26  10:18
 * @Description: 权限接口实现
 * @Version: 1.0
 */
@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    //权限分页查询+条件
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto);
//        log.info(JSON.toJSONString(sysRoleList));
        PageInfo<SysRole> pageInfo = new PageInfo(sysRoleList);
        return pageInfo;
    }

    //保存权限数据
    @Override
    public void saveSysRole(final SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    //修改权限信息
    @Override
    public void updateSysRole(final SysRole sysRole) {
        //已经删除的角色是无法再次进行修改操作的
        sysRoleMapper.updateSysRole(sysRole);
    }

    //删除角色信息
    @Override
    public void deleteById(final Long roleId) {
        sysRoleMapper.deleteSysRole(roleId);
    }

    //查询所有角色
    @Override
    public Map<String, Object> findAllRoles() {
        List<SysRole> sysRoleList = sysRoleMapper.findAllRoles() ;
        Map<String , Object> resultMap = new HashMap<>() ;
        resultMap.put("allRolesList" , sysRoleList) ;
        return resultMap;
    }
}
