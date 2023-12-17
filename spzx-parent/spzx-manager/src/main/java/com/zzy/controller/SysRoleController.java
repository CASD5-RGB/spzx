package com.zzy.controller;

import com.github.pagehelper.PageInfo;
import com.zzy.dto.system.SysRoleDto;
import com.zzy.entity.system.SysRole;
import com.zzy.service.SysRoleService;
import com.zzy.vo.common.Result;
import com.zzy.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-26  10:16
 * @Description: 权限控制层
 * @Version: 1.0
 */
@Tag(name = "权限接口")
@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Operation(summary = "权限分页查询+条件（查看有多少个权限角色）")
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findByPage(@RequestBody(required = true) SysRoleDto sysRoleDto,
                                                @PathVariable(value = "pageNum") Integer pageNum,
                                                @PathVariable(value = "pageSize") Integer pageSize) {
        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "保存权限数据")
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole SysRole) {
        sysRoleService.saveSysRole(SysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "修改权限信息")
    @PutMapping(value = "/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateSysRole(sysRole) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "删除角色信息")
    @DeleteMapping(value = "/deleteById/{roleId}")
    public Result deleteById(@PathVariable(value = "roleId") Long roleId) {
        sysRoleService.deleteById(roleId) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    @Operation(summary = "查询所有角色")
    @GetMapping(value = "/findAllRoles")
    public Result findAllRoles() {
        Map<String, Object> resultMap = sysRoleService.findAllRoles();
        return Result.build(resultMap , ResultCodeEnum.SUCCESS)  ;
    }

}
