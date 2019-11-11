package com.hqgml.dao;

import com.hqgml.domain.ManagerUser;
import com.hqgml.domain.Meeting;
import com.hqgml.domain.MeetingUers;

import java.util.List;
import java.util.Map;

public interface MeetingDao {
    /**
     * 添加的方法
     *
     * @return
     */
    public int meeting_add(Meeting mt);

    /**
     * 查找总条数
     *
     * @param parameterMap
     * @return
     */
    public int findTotalCount(Map<String, String[]> parameterMap);

    /***
     * 分页查询
     * @param start
     * @param rows
     * @param parameterMap
     * @return
     */
    public List<Meeting> findByPage(int start, int rows, Map<String, String[]> parameterMap);

    /**
     * 通过id找会议
     *
     * @return
     */
    public Meeting FindMeetingById(int id);

    /**
     * 通过会议id找会议人数列表
     *
     * @return
     */
    public List<MeetingUers> FindPeopleByUid(String s, String mid);

    /**
     * 通过会议id寻找签到成功的人
     *
     * @return
     */
    public List<MeetingUers> findCheckByMid(String id);

    /**
     * 通过会议寻找未签到成功的人
     *
     * @return
     */
    public List<MeetingUers> findUnCheckByMid(String id);

    /**
     * 通过id删除会议
     *
     * @param id
     * @return
     */
    public int delectSelect(String id);

    /**
     * 根据人员id删除renyuan
     *
     * @param id
     * @return
     */
    public int delectPeople(String id);

    /**
     * 根据id寻找一个会议
     *
     * @param id
     * @return
     */
    public MeetingUers findMeetingUserById(String id);


    MeetingUers isCheckByPeopleName(String name,String mid);


}
