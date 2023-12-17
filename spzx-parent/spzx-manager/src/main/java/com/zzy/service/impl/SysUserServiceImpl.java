package com.zzy.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.dto.system.AssginRoleDto;
import com.zzy.dto.system.LoginDto;
import com.zzy.dto.system.SysUserDto;
import com.zzy.entity.system.SysUser;
import com.zzy.exception.GuiguException;
import com.zzy.mapper.SysRoleUserMapper;
import com.zzy.mapper.SysUserMapper;
import com.zzy.service.SysUserService;
import com.zzy.vo.common.ResultCodeEnum;
import com.zzy.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-12  17:06
 * @Description: 用户的接口业务实现
 * @Version: 1.0
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 登录接口
    @Override
    public LoginVo login(LoginDto loginDto) {
        //获取输入验证码和存储到redis的key名称  loginDto获取到
        //loginDto中这两个值是在渲染页面的的时候，调用后台验证码的接口，
        // 同时会返回给前端验证码在redis中的key，以及验证码的图片
        String captcha = loginDto.getCaptcha();  //用户提交的验证码
        String codeKey = loginDto.getCodeKey();      //前端传来的验证码对应的key
        //从redis中根据codeKey获取验证码的值
        String codeRedis = redisTemplate.opsForValue().get("user:login:" + codeKey);
        //将前端传来的验证码的值与redis中的真实值进行比较
        if (StrUtil.isEmpty(codeRedis) || !StrUtil.equalsIgnoreCase(codeRedis, captcha)) {
            // 如果不一致，提示用户，校验失败
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        // 如果一致，就删除对应的验证码，然后就对用户名及密码进行判断
        redisTemplate.delete("user:login:" + codeKey);
        //根据用户名查询用户信息
        SysUser sysUser = sysUserMapper.getInfoByUserName(loginDto.getUserName());
        //如果用户信息为空就抛出异常
        if (sysUser == null) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        //不为空，则继续验证密码是否正确
        //接收用户输入的密码
        String input_password = loginDto.getPassword();
        //DB中加密之后的密码
        String DBPassword = sysUser.getPassword();
        //对用户传来的密码进行MD5加密处理
        String md5Password = DigestUtils
                .md5DigestAsHex(input_password.getBytes());
        //如果用户输入的密码加密之后，与DB中的密码不一致，则抛出异常
        if (!DBPassword.equals(md5Password)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //密码验证成功之后，要生成一个token用来作为用户的唯一标识

        //通过uuid中的雪花算法生成唯一的标识，用这个标识作为用户的token
        String token = UUID.randomUUID().toString()
                .replace("-", "");

        /*
         将生成的用户token放进redis中去
         以"user:login:"+token作为key，并将sysUser对象的信息，
         通过JSON的工具类转换为String类型，以及设置token的过期是时间
         */
        redisTemplate.opsForValue().set("user:login:" + token,
                JSON.toJSONString(sysUser), //使用这种方式，在redis中是以json的形式进行存储
//                String.valueOf(sysUser),这种格式存放在redis是以对象的方式进行存储--SysUser(userName=admin, password=96e79218965eb72c92a549dd5a330112, name=admin, phone=15011113652, avatar=https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg, description=null, status=1)
                30,
                TimeUnit.MINUTES);
        // 构建响应结果对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;
    }

    //获取用户的信息
    @Override
    public SysUser getUserInfo(final String token) {
        //这个前缀必须要跟登录接口中的前缀一致 =="user:login:"==
        String userJSON = redisTemplate.opsForValue().get("user:login:" + token);
        //将userJSON转换为SysUser对象,并将SysUser信息返回给前端
        SysUser sysUser = JSON.parseObject(userJSON, SysUser.class);
        return sysUser;
    }

    //退出功能
    @Override
    public void logout(final String token) {
        Boolean aBoolean = redisTemplate.hasKey("user:login:" + token);
        if (aBoolean) {
            redisTemplate.delete("user:login:" + token);
        } else {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

    }

    //分页+条件
    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> sysUserList = sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> pageInfo = new PageInfo(sysUserList);
        return pageInfo;
    }

    //添加用户信息
    @Override
    public void saveSysUser(final SysUser sysUser) {
        // 根据输入的用户名查询用户
        SysUser dbSysUser = sysUserMapper.findByUserName(sysUser.getUserName());
        //如果DB中存在该用户名的数据，则抛出异常
        if (dbSysUser != null) {
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        // 对密码进行加密
        String password = sysUser.getPassword();
        //对前端传来的密码进行加密之后的数据
        String digestPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(digestPassword);
        //并设置该用户的默认状态为1（正常）
        sysUser.setStatus(1);
        sysUserMapper.saveSysUser(sysUser);
    }

    //修改用户信息
    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.updateSysUser(sysUser);
    }

    //删除用户信息
    @Override
    public void deleteById(final Long userId) {
        sysUserMapper.deleteSysUser(userId);
    }

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Transactional
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {

        // 删除之前的所有的用户所对应的角色数据
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId());

        // 分配新的角色数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        roleIdList.forEach(roleId -> {
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),
                    roleId);
        });
    }
}
