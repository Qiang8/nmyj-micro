package com.taiji.cdp.daily.duty.service;

import com.taiji.cdp.daily.common.constant.DbUtil;
import com.taiji.cdp.daily.duty.vo.LeaderUserVo;
import com.taiji.cdp.daily.issue.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>Title:DutyService.java</p >
 * <p>Description: 值班对外service层 service</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/02/14 10:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class DutyService extends BaseService {

    /**
     * 获取当天值班信息
     *
     * @return
     */
    public List<Map<String, Object>> getTodyDuty() throws Exception {
        DbUtil dbUtil = new DbUtil();
        List<Map<String, Object>> todayDuty = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            //TODO 上线前需要修改为获取当天，为了测试方便 日期固定
            //String today = sdf.format(date);
            String today = "2018-09-28";
            String sql = "select ad.user_id AS userId,ad.duty AS dutyCode,su.humanname AS userName,su.sex,ad.is_bak AS isBak,ad.date AS dutyTime from ht_auto_duty ad,ht_sys_user su"
                    + " where ad.user_id = su.id and Date_format(ad.date,'%Y-%m-%d')= ? order by ad.seq asc ";
            todayDuty = dbUtil.executeQuery(sql, today);
        } finally {
            dbUtil.closeConnection();
        }
        return todayDuty;
    }

    /**
     * 获取值班小组信息
     *
     * @return
     */
    public List<Map<String, Object>> getDutyTeam(int isBak, String duty, Date startDate, Date endDate) throws Exception {
        DbUtil dbUtil = new DbUtil();
        List<Map<String, Object>> todayDuty = null;
        List<Map<String, Object>> returnList = new ArrayList<>();
        Object[] obj = new Object[]{isBak, duty, startDate, endDate};
        //如果没有传递开始时间，结束时间，查询所有的小组和组内人员
        if (null == startDate) {
            returnList = getDutyTeamAll(duty);
        } else {
            try {
                StringBuffer sql = new StringBuffer();
                sql.append(" SELECT ");
                sql.append(" 	DATE_FORMAT(ad.date, '%Y-%m-%d') dutyTime, ");
                sql.append(" 	ad.team_name teamName , ");
                sql.append(" 	ad.team_id teamId , ");
                sql.append(" 	u.humanname userName , ");
                sql.append(" 	ad.response_level responseLevel");
                sql.append(" FROM ");
                sql.append(" 	ht_auto_duty ad, ");
                sql.append(" 	ht_sys_user u ");
                sql.append(" WHERE ");
                sql.append(" 	ad.user_id = u.id ");
                sql.append(" AND ad.is_bak= ?");
                sql.append(" AND ad.duty = ? and ad.date >= ? and ad.date <= ?");
                sql.append(" ORDER BY ");
                sql.append(" 	ad.date, ");
                sql.append(" 	ad.seq ASC; ");
                todayDuty = dbUtil.executeQuery(sql.toString(), obj);
                if (null != todayDuty) {
                    Map<String, Object> returnMap = new HashMap<>();
                    returnMap.put("teamId", todayDuty.get(0).get("teamId"));
                    returnMap.put("dutyTime", todayDuty.get(0).get("dutyTime"));
                    returnMap.put("teamName", todayDuty.get(0).get("teamName"));
                    returnMap.put("responseLevel", todayDuty.get(0).get("responseLevel"));
                    StringBuffer usernames = new StringBuffer();
                    for (Map map : todayDuty) {
                        usernames.append(map.get("userName"));
                        usernames.append(",");
                    }
                    returnMap.put("userNames", usernames.toString().substring(0, usernames.toString().length() - 1));
                    returnList.add(returnMap);
                }
            } finally {
                dbUtil.closeConnection();
            }
        }
        return returnList;
    }

    /**
     * 获取当天代班领导信息
     *
     * @param dutyCode 岗位代号:1、带班办领导 2、带班处长
     * @param dutyType 值班班次:1、白班 2、夜班 0、全部
     * @return
     */
    public List<LeaderUserVo> getTodyLeader(int dutyCode, int dutyType) throws Exception {
        List<Map<String, Object>> nightLeader = getNigetLeaderDuty(dutyCode);
        List<Map<String, Object>> dayLeader = getDayLeaderDuty(dutyCode);
        List<LeaderUserVo> dutyVo = null;
        String userName = (String) nightLeader.get(0).get("userName");
        if (dutyType == 2) {
            dutyVo = getLeaderVo(nightLeader, 2, userName);
        }
        if (dutyType == 1) {
            dutyVo = getLeaderVo(dayLeader, 1, userName);
        }
        if (dutyType == 0) {
            dutyVo = getLeaderVo(dayLeader, 0, userName);
        }
        return dutyVo;
    }

    /**
     * 组装返回数据
     *
     * @param todayLeader
     * @return
     */
    private List<LeaderUserVo> getLeaderVo(List<Map<String, Object>> todayLeader, int dutyType, String userName) {
        List<LeaderUserVo> userVoList = new ArrayList<>();
        //夜班只有一个人，直接set dutyType 为2，
        if (dutyType == 2) {
            Map<String, Object> map = todayLeader.get(0);
            LeaderUserVo userVo = mapToVo(map, dutyType);
            userVoList.add(userVo);
        }
        //白班查询所有领导但是不包括上夜班这个人
        if (dutyType == 1) {
            for (int i = 0; i < todayLeader.size(); i++) {
                Map<String, Object> map = todayLeader.get(i);
                if (!map.get("userName").equals(userName)) {
                    LeaderUserVo userVo = mapToVo(map, dutyType);
                    userVoList.add(userVo);
                }
            }
        }
        // 查询所有领导并且包括上夜班这个人，既dutyType = 0；
        if (dutyType == 0) {
            for (int i = 0; i < todayLeader.size(); i++) {
                Map<String, Object> map = todayLeader.get(i);
                if (!map.get("userName").equals(userName)) {
                    LeaderUserVo userVo = mapToVo(map, 1);
                    userVoList.add(userVo);
                } else {
                    LeaderUserVo userVo = mapToVo(map, 2);
                    userVoList.add(userVo);
                }
            }
        }
        return userVoList;
    }

    /**
     * 获取当天夜班值班领导信息
     */
    private List<Map<String, Object>> getNigetLeaderDuty(int dutyCode) throws Exception {
        DbUtil dbUtil = new DbUtil();
        List<Map<String, Object>> nightLeader = null;
        String nightSql = "";
        Object[] obj = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        //TODO 上线前需要修改为获取当天，为了测试方便 日期固定
        //String today = sdf.format(date);
        String today = "2018-09-28";
        try {
            //夜班从值班表里获取排班领导
            nightSql = "SELECT " +
                    "ad.user_id AS userId," +
                    "ad.duty AS dutyCode," +
                    "su.humanname AS userName," +
                    "su.sex,ad.team_name AS teamName," +
                    "ad.team_id AS teamId," +
                    "su.telmobile1 AS mobile," +
                    "su.humancode AS account " +
                    "FROM ht_auto_duty ad," +
                    "ht_sys_user su " +
                    "WHERE ad.user_id = su.id " +
                    "AND Date_format(ad.date, '%Y-%m-%d') = ? " +
                    "AND ad.duty = ? " +
                    "ORDER BY ad.seq ASC";
            obj = new Object[]{today, dutyCode};
            nightLeader = dbUtil.executeQuery(nightSql, obj);
        } finally {
            dbUtil.closeConnection();
        }
        return nightLeader;
    }

    /**
     * 获取所有白班值班领导信息
     * 其实是查询所有领导信息，在代码中去掉夜班领导，即为白班领导信息
     */
    private List<Map<String, Object>> getDayLeaderDuty(int dutyCode) throws Exception {
        DbUtil dbUtil = new DbUtil();
        List<Map<String, Object>> dayLeader = null;
        Object[] obj = null;
        String daySql = "";
        try {
            daySql = "SELECT " +
                    "c.humanname AS userName," +
                    "c.id AS userId," +
                    "b.id AS teamId," +
                    "c.sex AS sex," +
                    "b.duty AS dutyCode," +
                    "c.telmobile1 AS mobile," +
                    "b.`name` AS teamName," +
                    "c.humancode AS account " +
                    "FROM ht_team_members a " +
                    "LEFT JOIN ht_teams b ON a.team_id = b.id " +
                    "LEFT JOIN ht_sys_user c ON a.user_id = c.id " +
                    "WHERE b.duty = ? " +
                    "AND c.validflag = 0";
            obj = new Object[]{dutyCode};
            dayLeader = dbUtil.executeQuery(daySql, obj);
        } finally {
            dbUtil.closeConnection();
        }
        return dayLeader;
    }

    /**
     * 从map往VO中set值
     */
    private LeaderUserVo mapToVo(Map<String, Object> map, int dutyType) {
        LeaderUserVo userVo = new LeaderUserVo();
        userVo.setAccount((String) map.get("account"));
        userVo.setDutyCode((String) map.get("dutyCode"));
        userVo.setDutyType(String.valueOf(dutyType));
        userVo.setSex((String) map.get("sex"));
        userVo.setTeamId((String) map.get("teamId"));
        userVo.setTeamName((String) map.get("teamName"));
        userVo.setUserName((String) map.get("userName"));
        userVo.setUserId((String) map.get("userId"));
        userVo.setMobile((String)map.get("mobile"));
        return userVo;
    }

    //当没有传递开始时间和结束时间的时候，获取全部的值班小组信息。
    private List<Map<String, Object>> getDutyTeamAll(String duty) throws Exception {
        DbUtil dbUtil = new DbUtil();
        List<Map<String, Object>> todayDuty = null;
        List<Map<String, Object>> returnList = new ArrayList<>();
        Object[] obj = new Object[]{duty};
        try {
            String sql = "SELECT " +
                    "a.id AS teamId," +
                    "a.`name` AS teamName," +
                    "c.humanname AS userName " +
                    "FROM ht_teams a " +
                    "LEFT JOIN ht_team_members b ON a.id = b.team_id " +
                    "LEFT JOIN ht_sys_user c ON b.user_id = c.id " +
                    "WHERE  a.duty =?";
            todayDuty = dbUtil.executeQuery(sql.toString(), obj);
            if (null != todayDuty) {
                for (Map map1 : todayDuty) {
                    StringBuffer usernames = new StringBuffer();
                    Map<String, Object> returnMap = null;
                    for (Map map2 : todayDuty) {
                        if (map1.get("teamId").equals(map2.get("teamId"))) {
                            returnMap = new HashMap<>();
                            returnMap.put("teamId", map2.get("teamId"));
                            returnMap.put("dutyTime", null);
                            returnMap.put("teamName", map2.get("teamName"));
                            returnMap.put("responseLevel", null);
                            usernames.append(map2.get("userName"));
                            usernames.append(",");
                            returnMap.put("userNames", usernames.toString().substring(0, usernames.toString().length() - 1));
                        }
                    }
                    if (!returnList.contains(returnMap)) {
                        returnList.add(returnMap);
                    }
                }
            }
        } finally {
            dbUtil.closeConnection();
        }
        return returnList;
    }

}