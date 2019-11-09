package com.hqgml.service;


import com.hqgml.domain.ManagerUser;
import com.hqgml.domain.Meeting;
import com.hqgml.domain.PageBean;
import com.hqgml.domain.SurperUser;

import java.util.Map;

@SuppressWarnings("ALL")
public interface UserService {
    /**
     * 查询管理员用户的方法
     *
     * @return
     */
    public ManagerUser GetManagerUser(String username, String password);

    /**
     * 查询超级管理员方法
     *
     * @return
     */
    public SurperUser GetSurper(String username, String password);

    /**
     * 保存注册信息
     */
    public boolean SaveUser(ManagerUser mu);

    /**
     * 更新用户信息
     */

    public boolean Updata(ManagerUser mu);

    /**
     * 根据用户查找超管铺管
     *
     * @param name
     * @return
     */
    public boolean FindUserByName(String name);

    public boolean FindMUserByName(String name);

    ManagerUser FindMByCookie(String cookie);

    SurperUser FindSByCookie(String cookie);

    boolean DelectCookie(String userName, String name);

    boolean DelectSCookie(String userName, String cookie);

    public PageBean<ManagerUser> FindUserByPage(String currentPage, String rows, Map<String, String[]> parameterMap);

    boolean  upDateS(SurperUser surperUser);
}
