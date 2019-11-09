package com.hqgml.dao.impl;

import com.hqgml.dao.UesrDao;
import com.hqgml.domain.ManagerUser;
import com.hqgml.domain.ManagerUser_log;
import com.hqgml.domain.SurperUser;
import com.hqgml.utlis.JDBCutils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")

public class UserDaoImpl implements UesrDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCutils.getDataSource());

    @Override
    public ManagerUser GetManagerUser(String username, String password) {
        String sql = "select *from manageruser where name=? and pswd=?";
        try {
            ManagerUser managerUser = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<ManagerUser>(ManagerUser.class), username, password);
            return managerUser;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public SurperUser GetSurper(String user, String password) {
        String sql = "select *from surperuser where name =? and pswd=?";
        try {
            SurperUser surperUser = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<SurperUser>(SurperUser.class), user, password);
            return surperUser;
        } catch (DataAccessException e) {
            return null;
        }
    }


    @Override
    public int Updata_manager(ManagerUser mu) {
        String sql = "update manageruser set name=?,sex=?,idcard=?,phone=?,pswd=?,address=?,lasttime=? where name=? ";
        int update = jdbcTemplate.update(sql, mu.getName(), mu.getSex(), mu.getIdcard(), mu.getPhone(), mu.getPswd(), mu.getAddress(), mu.getLasttime(), mu.getName());
        return update;
    }

    @Override
    public int SaveUser(ManagerUser mu) {
        String sql = "insert into manageruser(name,pswd,phone) value(?,?,?)";
        int update = jdbcTemplate.update(sql, mu.getName(), mu.getPswd(), mu.getPhone());
        return update;
    }

    @Override
    public ManagerUser FindUserByName(String name) {
        String sql = "select * from manageruser where name =?";
        try {
            ManagerUser managerUser = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<ManagerUser>(ManagerUser.class), name);
            return managerUser;
        } catch (DataAccessException e) {
            return null;
        }

    }

    @Override
    public SurperUser FindSUserByName(String name) {
        String sql = "select * from surperuser where name =?";
        try {
            SurperUser surperUser = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<SurperUser>(SurperUser.class), name);
            return surperUser;
        } catch (DataAccessException e) {
            return null;
        }

    }

    @Override
    public ManagerUser FindMuserByCookie(String cookie) {
        String sql = "select * from manageruser where cookie=?";
        try {
            ManagerUser managerUser = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<ManagerUser>(ManagerUser.class), cookie);
            return managerUser;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public SurperUser FindSuserByCookie(String cookie) {
        String sql = "select * from surperuser where cookie=? ";
        try {
            SurperUser surperUser = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<SurperUser>(SurperUser.class), cookie);
            return surperUser;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public int DelectCookie(String userName, String cookie) {
        String sql = "UPDATE manageruser SET cookie=NULL WHERE name =? and cookie=?";
        int update = jdbcTemplate.update(sql, userName, cookie);
        return update;
    }

    @Override
    public int DelectSCookie(String userName, String cookie) {
        String sql = "UPDATE surperuser SET cookie=NULL WHERE name =? and cookie=?";
        int update = jdbcTemplate.update(sql, userName, cookie);
        return update;
    }

    @Override
    public int findManagerTotalCount(Map<String, String[]> parameterMap) {
        //查询总记录数
        //如果没有条件查询的参数的话也不会报异常
        String sql = "select count(*) from manageruser  where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> strings = parameterMap.keySet();//返回这个map的键值
        List<Object> ls = new ArrayList<Object>();
        for (String string : strings) {
            if ("currentPage".equals(string) || "rows".equals(string)) {
                continue;
            }
            String value = parameterMap.get(string)[0];//有的请求是有很多个 比如多选框 而这个确定就是只有一个数值
            //如果有值就拼接
            if (value != null && !"".equals(value)) {
                sb.append(" and " + string + " like ?");//需要加空格 不然会出现拼接问题
                //拼接完成就会把值放进来
                ls.add("%" + value + "%");
            }
        }
        //拼接完成之后的sql是StringBuilder的tostrinng方法
        sql = sb.toString();
        int integer = jdbcTemplate.queryForObject(sql, Integer.class, ls.toArray());//接受的参数就可变变参数 可变参数的本质就是数值
        return integer;
    }

    @Override
    public List<ManagerUser> findManagerByPage(int start, int rows, Map<String, String[]> parameterMap) {
        String sql = "select * from manageruser where 1=1";
        StringBuilder sb = new StringBuilder(sql);
        //遍历map
        Set<String> strings = parameterMap.keySet();//返回这个map的键值
        List<Object> ls = new ArrayList<Object>();
        for (String string : strings) {
            if ("currentPage".equals(string) || "rows".equals(string)) {
                continue;
            }
            String value = parameterMap.get(string)[0];//有的请求是有很多个 比如多选框 而这个确定就是只有一个数值
            //如果有值就拼接
            if (value != null && !"".equals(value)) {
                sb.append(" and " + string + " like ?");//需要加空格 不然会出现拼接问题
                //拼接完成就会把值放进来
                ls.add("%" + value + "%");
            }
        }
        //添加分页查询
        sb.append(" limit ?,? ");
        //下面的查询语句是给的list集合 所以要把start, rows一起添加过去
        ls.add(start);
        ls.add(rows);
        //拼接完成之后的sql是StringBuilder的tostrinng方法
        sql = sb.toString();
        List<ManagerUser> query = jdbcTemplate.query(sql, new BeanPropertyRowMapper<ManagerUser>(ManagerUser.class), ls.toArray());
        return query;
    }

    @Override
    public int Updata_Surper(SurperUser surperUser) {
        String sql = "update surperUser set name=?,pswd=?,address=?,lasttime=? where name=? ";
        int update = jdbcTemplate.update(sql,surperUser.getName(),surperUser.getPswd(),surperUser.getAddress(),surperUser.getLasttime(),surperUser.getName());
        return update;
    }
}