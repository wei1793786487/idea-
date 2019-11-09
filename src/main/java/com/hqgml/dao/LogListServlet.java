package com.hqgml.dao;


import com.hqgml.domain.ManagerUser_log;
import com.hqgml.domain.PageBean;
import com.hqgml.domain.SurperUser_log;

import java.util.List;
import java.util.Map;

public interface LogListServlet {

    /**
     * 通过id寻找log日志
     */
    public List<ManagerUser_log> FindMLogById(String uid);

    int findManagerTotalCount(Map<String, String[]> parameterMap);

    List<ManagerUser_log> findManagerByPage(int start, int rows, Map<String, String[]> parameterMap);

    int findSurperTotalCount(Map<String, String[]> parameterMap);

    List<SurperUser_log> findSurperByPage(int start, int rows, Map<String, String[]> parameterMap);
}
