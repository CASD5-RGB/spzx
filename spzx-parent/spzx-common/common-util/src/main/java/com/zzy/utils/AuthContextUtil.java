package com.zzy.utils;

import com.zzy.entity.system.SysUser;

/**
 * @Author: 赵祖银
 * @CreateTime: 2023-11-25  21:43
 * @Description: 对ThreadLocal进行了封装
 * @Version: 1.0
 */
public class AuthContextUtil {

    // 创建一个ThreadLocal对象
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>() ;

    // 定义存储数据的静态方法
    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    // 定义获取数据的方法
    public static SysUser get() {
        return threadLocal.get() ;
    }

    // 删除数据的方法
    public static void remove() {
        threadLocal.remove();
    }

}
