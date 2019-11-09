package com.hqgml.service;

import com.hqgml.domain.*;

import java.util.List;
import java.util.Map;

public interface LogFindServlet {

    public List<ManagerUser_log> findList( String uid);

    PageBean<ManagerUser_log> FindlogByPage(String currentPage, String rows, Map<String, String[]> parameterMap);

    public PageBean<SurperUser_log> FindSlogByPage(String currentPage, String rows, Map<String, String[]> parameterMap);
}
