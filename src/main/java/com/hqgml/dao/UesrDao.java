package com.hqgml.dao;

import com.hqgml.domain.ManagerUser;
import com.hqgml.domain.ManagerUser_log;
import com.hqgml.domain.SurperUser;

import java.util.List;
import java.util.Map;

/**
 * 用户登录持久层操作
 */
public interface UesrDao {
    /**
     * 查询单个管理员用户的方法
     *
     * @return
     */
    public ManagerUser GetManagerUser(String user, String password);

    /**
     * 查询超级管理员方法
     *
     * @return
     */
    public SurperUser GetSurper(String user, String password);

    /**
     * 更新用户信息
     */

    public int Updata_manager(ManagerUser mu);

    /**
     * 注册用户
     *
     * @param mu
     * @return
     */
    public int SaveUser(ManagerUser mu);

    /**
     * 根据用户名找铺管用户
     *
     * @param name
     * @return
     */
    public ManagerUser FindUserByName(String name);


    /**
     * 根据用户名找超管用户
     *
     * @param name
     * @return
     */
    public SurperUser FindSUserByName(String name);

    /**
     * 根据cookie寻找普管
     *
     * @return
     */
    public ManagerUser FindMuserByCookie(String cookie);

    /**
     * 根据cookie寻找超管管
     *
     * @return
     */
    public SurperUser FindSuserByCookie(String cookie);

    /**
     * 删除铺管cookie
     *
     * @param userName
     * @param cookie
     * @return
     */
    public int DelectCookie(String userName, String cookie);

    /**
     * 删除超管cookie
     *
     * @param userName
     * @param cookie
     * @return
     */
    public int DelectSCookie(String userName, String cookie);

    /**
     * 寻找铺管总的人数总数
     * @param parameterMap
     * @return
     */
    int findManagerTotalCount(Map<String, String[]> parameterMap);

    /**
     * 分页查找铺管
     * @param start
     * @param rows
     * @param parameterMap
     * @return
     */
    List<ManagerUser> findManagerByPage(int start, int rows, Map<String, String[]> parameterMap);

    int Updata_Surper(SurperUser surperUser);
}
