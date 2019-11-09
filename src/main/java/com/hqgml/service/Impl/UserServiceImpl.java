package com.hqgml.service.Impl;

import com.hqgml.dao.*;
import com.hqgml.dao.impl.UserDaoImpl;
import com.hqgml.domain.ManagerUser;
import com.hqgml.domain.Meeting;
import com.hqgml.domain.PageBean;
import com.hqgml.domain.SurperUser;
import com.hqgml.service.UserService;

import javax.sql.rowset.RowSetProvider;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    UesrDao ud = new UserDaoImpl();


    @Override
    public ManagerUser GetManagerUser(String username, String password) {
        ManagerUser managerUser = ud.GetManagerUser(username, password);
        return managerUser;
    }

    @Override
    public SurperUser GetSurper(String username, String password) {
        SurperUser surperUser = ud.GetSurper(username, password);
        return surperUser;
    }

    @Override
    public boolean SaveUser(ManagerUser mu) {
        int i = ud.SaveUser(mu);
        if (i > 0) {
            return true;
        } else {

            return false;
        }
    }


    @Override
    public boolean Updata(ManagerUser mu) {
        int i = ud.Updata_manager(mu);
        if (i > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean FindUserByName(String name) {
        ManagerUser managerUser = ud.FindUserByName(name);
        if (managerUser == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean FindMUserByName(String name) {
        SurperUser surperUser = ud.FindSUserByName(name);
        if (surperUser==null){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public ManagerUser FindMByCookie(String cookie) {
       return ud.FindMuserByCookie(cookie);
    }

    @Override
    public SurperUser FindSByCookie(String cookie) {
        return ud.FindSuserByCookie(cookie);
    }

    @Override
    public boolean DelectCookie(String userName, String cookie) {
        int isdelect = ud.DelectCookie(userName,cookie);
        if (isdelect>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean DelectSCookie(String userName, String cookie) {
        int isdelect = ud.DelectSCookie(userName,cookie);
        if (isdelect>0){
            return true;
        }else {
            return false;
        }
    }
    @Override
    public PageBean<ManagerUser> FindUserByPage(String _currentPage, String _rows, Map<String, String[]> parameterMap) {
        //防止空指针异常
        if (_currentPage == null) {
            _currentPage = "1";
        }
        if (_rows == null) {
            _rows = "5";
        }
        //转换
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        PageBean<ManagerUser> pb = new PageBean<ManagerUser>();
        //查询总记录数
        int managerTotalCount = ud.findManagerTotalCount(parameterMap);
        pb.setTotalcont(managerTotalCount);
        //计算总页码
        int totalPage = managerTotalCount % rows == 0 ? managerTotalCount / rows : managerTotalCount / rows + 1;
        pb.setTotal(totalPage);
        //防止越界点击
        if (currentPage < 1) {
            currentPage = 1;
        }
        if (rows < 1) {
            rows = 1;
        }
        if (currentPage > totalPage) {
            currentPage = totalPage;
        }
        if (rows > managerTotalCount) {
            rows = managerTotalCount;
        }
        pb.setCurrentPage(currentPage);
        pb.setRows(rows);
        //调用dao来查询数据
        //需要查询开始页面的索引
        int start = (currentPage - 1) * rows;

        List<ManagerUser> managerByPage = ud.findManagerByPage(start,rows, parameterMap);
        pb.setList(managerByPage);
        return pb;
    }

    @Override
    public boolean upDateS(SurperUser surperUser) {
        int i = ud.Updata_Surper(surperUser);
        if (i > 0) {
            return true;
        } else {
            return false;
        }

    }

}
