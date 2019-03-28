package com.taiji.cdp.report.service;

import com.taiji.base.sys.vo.DicGroupItemVo;
import com.taiji.base.sys.vo.OrgVo;
import com.taiji.cdp.report.common.global.StatGlobal;
import com.taiji.cdp.report.feign.*;
import com.taiji.cdp.report.vo.*;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;



@Service
@AllArgsConstructor
public class StatService extends BaseService{

    StatClient statClient;
    OrgClient orgClient;
    DicItemClient dicItemClient;
    ConsInfoClient consInfoClient;
    ApprovalClient approvalClient;
    TaskClient taskClient;
    /**
     * 统计本单位上报舆情数量
     * @param vo getTimeCycle
     * 统计时间周期：1今日 2本周 3本月 4本年
     */
    public HashMap<String,String> findOwnReports(StatVQueryVo vo, OAuth2Authentication principal){
        HashMap<String,String> map = new HashMap<>();
        String timeCycle = vo.getTimeCycle();
        LocalDate localDate = getCurrrentTime().toLocalDate();
        switch (timeCycle){
            case StatGlobal.STAT_TYPE_YI:
                vo.setStartLocalDate(LocalDateTime.of(localDate, LocalTime.MIN));//当天开始时间
                vo.setEndLocalDate(LocalDateTime.of(localDate, LocalTime.MAX));//当天结束时间
                break;
            case StatGlobal.STAT_TYPE_ER:
                vo.setStartLocalDate(LocalDateTime
                        .of(localDate.plusWeeks(0)
                                .with(DayOfWeek.MONDAY), LocalTime.MIN));//获取本周的开始日期
                vo.setEndLocalDate(LocalDateTime
                        .of(localDate.plusWeeks(1)
                                .with(DayOfWeek.MONDAY).plusDays(-1), LocalTime.MAX));//获取本周的结束日期
                break;
            case StatGlobal.STAT_TYPE_SAN:
                vo.setStartLocalDate(LocalDateTime
                        .of(localDate.plusMonths(0)
                                .with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN));//获取本月的开始日期
                vo.setEndLocalDate(LocalDateTime
                        .of(localDate.plusMonths(1)
                                .with(TemporalAdjusters.firstDayOfMonth()).plusDays(-1), LocalTime.MAX));//获取本月的结束日期
                break;
            case StatGlobal.STAT_TYPE_SI:
                vo.setStartLocalDate(LocalDateTime
                        .of(localDate.plusYears(0)
                                .with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN));//获取本年的开始日期
                vo.setStartLocalDate(LocalDateTime
                        .of(localDate.plusYears(1)
                                .with(TemporalAdjusters.firstDayOfYear())
                                .plusDays(-1), LocalTime.MAX));//获取本年的结束日期
                break;
        }
        vo.setOrgId(getUserMap(principal).get("orgId").toString());
        vo.setStatus(Arrays.asList(StatGlobal.STAT_CONS_INFO_REPORT));

        ResponseEntity<List<ConsInfoVo>> result = statClient.findStatCons(vo);
        List<ConsInfoVo> consInfoVos = ResponseEntityUtils.achieveResponseEntityBody(result);
        map.put("infoTotalNum",consInfoVos.size()+"");
        return map;

    }
    /**
     * 统计所以接收的舆情数量（统计使用）
     * @param vo getStatFlag
     * 1：今日已接收，2：今日已处理，3：今日未处理，4：至今累计接收
     */
    public HashMap<String,String> findAllReports(StatVQueryVo vo){
        HashMap<String,String> map = new HashMap<>();
        LocalDateTime currrentTime = getCurrrentTime();
        LocalDate localDate = currrentTime.toLocalDate();
       String statFlag = vo.getStatFlag();
        if(StatGlobal.STAT_TYPE_SI.equals(statFlag)){
            vo.setStartLocalDate(currrentTime);
        }else {
            vo.setStartLocalDate(LocalDateTime.of(localDate, LocalTime.MIN));//当天开始时间
            vo.setEndLocalDate( LocalDateTime.of(localDate, LocalTime.MAX));//当天结束时间
        }
        switch (statFlag){
            case StatGlobal.STAT_TYPE_YI:
                vo.setStatus(Arrays.asList(StatGlobal.STAT_CONS_INFO_REPORT));
                break;
            case StatGlobal.STAT_TYPE_ER:
                vo.setStatus(Arrays.asList(StatGlobal.STAT_CONS_INFO_ACTION));
                break;
            case StatGlobal.STAT_TYPE_SAN:
                vo.setStatus(Arrays.asList(StatGlobal.STAT_CONS_INFO_TWO_NOT));
                break;
            case StatGlobal.STAT_TYPE_SI:
                vo.setStatus(Arrays.asList(StatGlobal.STAT_CONS_INFO_REPORT));
                break;
        }
        ResponseEntity<List<ConsInfoVo>> result = statClient.findStatCons(vo);
        List<ConsInfoVo> consInfoVos = ResponseEntityUtils.achieveResponseEntityBody(result);
        map.put("infoTotalNum",consInfoVos.size()+"");

        return map;

    }

    /**
     * 统计下发给本单位的任务数量（本年度）
     * cj_task_exeorg表中的org_id=登录人所属部门
     */
    public HashMap<String,String> findStatTasks(OAuth2Authentication principal){
        StatVQueryVo vo = new StatVQueryVo();
        vo.setOrgId(getUserMap(principal).get("orgId").toString());
        LocalDate localDate = getCurrrentTime().toLocalDate();
        vo.setStartLocalDate(LocalDateTime
                .of(localDate.plusYears(0)
                        .with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN));//获取本年的开始日期
        vo.setStartLocalDate(LocalDateTime
                .of(localDate.plusYears(1)
                        .with(TemporalAdjusters.firstDayOfYear())
                        .plusDays(-1), LocalTime.MAX));//获取本年的结束日期
        ResponseEntity<List<TaskVo>> statTasks = statClient.findStatTasks(vo);
        List<TaskVo> taskVos = ResponseEntityUtils.achieveResponseEntityBody(statTasks);
        HashMap<String,String> map = new HashMap<>();
        map.put("taskTotalNum",taskVos.size()+"");
        return map;

    }

    /**
     * 舆情信息上报来源统计(本年度)
     * @param vo  getIsOwnOrg
     * 是否统计本单位：0本单位 1全部
     * cr_cons_info表的source_type_id为来源类型ID(字典表),&status=【1-8】的记录
     */
    public SourceTypeStatVo findStatSources(StatVQueryVo vo, OAuth2Authentication principal){
        String isOwnOrg = vo.getIsOwnOrg();
        if(StatGlobal.STAT_TYPE_LING.equals(isOwnOrg)){
            vo.setOrgId(getUserMap(principal).get("orgId").toString());
        }
        LocalDate localDate = getCurrrentTime().toLocalDate();
        LocalDateTime today_start =  LocalDateTime
                .of(localDate.plusYears(0)
                        .with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN);
        LocalDateTime today_end = LocalDateTime
                .of(localDate.plusYears(1)
                        .with(TemporalAdjusters.firstDayOfYear()).plusDays(-1), LocalTime.MAX);
        vo.setStartLocalDate(today_start);
        vo.setEndLocalDate(today_end);
        vo.setStatus(Arrays.asList(StatGlobal.STAT_CONS_INFO_REPORT));
        ResponseEntity<List<ConsInfoVo>> result = statClient.findStatCons(vo);
        List<ConsInfoVo> consInfoVos = ResponseEntityUtils.achieveResponseEntityBody(result);
        Map<String,Long> consInfoGroupMap =
                consInfoVos.stream().collect(Collectors.groupingBy(ConsInfoVo::getSourceTypeName,Collectors.counting()));
        List<String> sourceType = new ArrayList<>();//来源类型
        List<String> totalNum = new ArrayList<>(); //舆情总数量
        for (String map :consInfoGroupMap.keySet()) {
            sourceType.add(map);
            totalNum.add(consInfoGroupMap.get(map)+"");
        }
        return new SourceTypeStatVo(sourceType,totalNum);
    }


    /**
     * 舆情信息按新增舆情和重点舆情分类进行统计（近七日）
     * @param isOwnOrg
     * 是否统计本单位：0本单位 1全部
     * cr_cons_info表的status=【1-7】，cj_cons_judge表的tag_type舆情标签类型：0新增舆情 1重点舆情
     */
    public StatTimeTypeVo findStatTypes(String isOwnOrg, OAuth2Authentication principal){
        String orgId = StatGlobal.STAT_ORG_ID;
        if(StatGlobal.STAT_TYPE_LING.equals(isOwnOrg)){
            orgId = getUserMap(principal).get("orgId").toString();
        }
        return statClient.findStatTypes(orgId).getBody();

    }
    /**
     * 统计各渠道上报舆情数量（本年度）
     * （cr_cons_info表的status=【1-8】，createOrgId对应的组织CODE分类）
     */
    public StatWayVo findStatWays(){
        StatVQueryVo vo = new StatVQueryVo();
        LocalDate localDate = getCurrrentTime().toLocalDate();
        vo.setStartLocalDate(LocalDateTime
                .of(localDate.plusYears(0)
                        .with(TemporalAdjusters.firstDayOfYear()), LocalTime.MIN));//获取本年的开始日期
        vo.setStartLocalDate(LocalDateTime
                .of(localDate.plusYears(1)
                        .with(TemporalAdjusters.firstDayOfYear())
                        .plusDays(-1), LocalTime.MAX));//获取本年的结束日期

        ResponseEntity<List<ConsInfoVo>> result = statClient.findStatCons(vo);
        List<ConsInfoVo> consInfoVos = ResponseEntityUtils.achieveResponseEntityBody(result);
        //获取时间段内的数据
        //拿到ORGID 集合并查询到所以org
        Map<String,Long> consInfoGroupMap = consInfoVos.stream().
                collect(Collectors.groupingBy(ConsInfoVo::getCreateOrgId,Collectors.counting()));

        List<String> orgs = consInfoVos.stream().map(ConsInfoVo::getCreateOrgId).collect(Collectors.toList());
        ResponseEntity<List<OrgVo>> orgInfo = orgClient.findOrgInfo(orgs);
        List<OrgVo> orgInfoBody = ResponseEntityUtils.achieveResponseEntityBody(orgInfo);
        //查询字典表
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("dicCode","dicOrgLevel");
        List<DicGroupItemVo> list = dicItemClient.findList(multiValueMap).getBody();

        //循环获得数据
        Map<String,Integer> maps = new HashMap<>();
        for (OrgVo orgVo:orgInfoBody) {
            for (DicGroupItemVo dic : list) {
                if(dic.getId().equals(orgVo.getOrgLevelId())){
                    if(maps.get(dic.getItemName()) == null){
                        maps.put(dic.getItemName(),consInfoGroupMap.get(orgVo.getId()).intValue());
                    }else {
                        maps.put(dic.getItemName(),maps.get(dic.getItemName())
                                +consInfoGroupMap.get(orgVo.getId()).intValue());
                    }
                }
            }
        }
        //组装数据返回
        List<String> reportWay = new ArrayList<>();//ORG组织类型
        List<Integer> infoTotalNum = new ArrayList<>();//对应数量
        for (String ma :maps.keySet()) {
            reportWay.add(ma);
            infoTotalNum.add(maps.get(ma));
        }
        return new StatWayVo(reportWay,infoTotalNum);
    }

    /**
     * 统计舆情报送有效率（本年度）
     * （cr_cons_info表的status,总数【1-8】，有效率【1/2/3/4/6/7】）
     */
    public HashMap<String, Integer> findStatValids(){
        StatVQueryVo vo = new StatVQueryVo();

        vo.setStatus(Arrays.asList(StatGlobal.STAT_CONS_INFO_REPORT));
        ResponseEntity<List<ConsInfoVo>> result = statClient.findStatCons(vo);
        List<ConsInfoVo> totalNum = ResponseEntityUtils.achieveResponseEntityBody(result);
        vo.setStatus(Arrays.asList(StatGlobal.STAT_CONS_INFO_TWO));
        ResponseEntity<List<ConsInfoVo>> result1 = statClient.findStatCons(vo);
        List<ConsInfoVo> validNum = ResponseEntityUtils.achieveResponseEntityBody(result1);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("totalNum",totalNum.size());
        map.put("validNum",validNum.size());
        return map;
    }

    /**
     * 统计本单位上报舆情总数量  盟市使用
     * 0待上报 1已上报
     */
    public List<StatTableVo> findStatReports(OAuth2Authentication principal){
        Map<String,Object> map = new HashMap<>();
        List<StatTableVo> list = new ArrayList<>();
        String createOrgId = getUserMap(principal).get("orgId").toString();
        map.put("orgId",createOrgId);
        //待上报
        map.put("statuses", Arrays.asList(StatGlobal.STAT_CONS_INFO_REPORT_NOT));
        list.add(new StatTableVo(0,consInfoClient.findRecList(convertMap2MultiValueMap(map)).getBody().size()));
        //已上报
        map.put("statuses",Arrays.asList(StatGlobal.STAT_CONS_INFO_REPORT));
        list.add(new StatTableVo(1,consInfoClient.findRecList(convertMap2MultiValueMap(map)).getBody().size()));
        return list;
    }

    public List<StatTableVo> findStatReportRecs(){
        Map<String,Object> map = new HashMap<>();
        List<StatTableVo> list = new ArrayList<>();
        //0:待处理
        map.put("statuses",Arrays.asList(StatGlobal.STAT_CONS_INFO_TO_BE_PROCESSED));
        int size = ResponseEntityUtils.achieveResponseEntityBody(
                consInfoClient.findRecList(convertMap2MultiValueMap(map))).size();
        list.add(new StatTableVo(0,size));
        //1:已处理
        map.put("statuses",Arrays.asList(StatGlobal.STAT_CONS_INFO_DISPOSED_OF));
        int size1 = ResponseEntityUtils.achieveResponseEntityBody(
                consInfoClient.findRecList(convertMap2MultiValueMap(map))).size();
        list.add(new StatTableVo(1,size1));
        //2:已退回
        map.put("statuses",Arrays.asList(StatGlobal.STAT_CONS_INFO_BOUNCE));
        int size2 = ResponseEntityUtils.achieveResponseEntityBody(
                consInfoClient.findRecList(convertMap2MultiValueMap(map))).size();
        list.add(new StatTableVo(2,size2));
        return list;
    }


    /**
     * 统计审批舆情总数(自治区)
     * 00个人待审批 列表，01个人已审批列表
     */
    public List<StatTableVo> findStatApprovals(OAuth2Authentication principal){
        Map<String,Object> map = new HashMap<>();
        List<StatTableVo> list = new ArrayList<>();
        map.put("id",getUserMap(principal).get("id"));
        map.put("tagFlag",StatGlobal.STAT_TYPE_LING_LING);//00待审批查询
        list.add(new StatTableVo(0,approvalClient.searchAllStat(convertMap2MultiValueMap(map)).getBody().size()));
        map.put("tagFlag",StatGlobal.STAT_TYPE_LING_YI);//01已审批查询
        list.add(new StatTableVo(1,approvalClient.searchAllStat(convertMap2MultiValueMap(map)).getBody().size()));
        return list;
    }


    /**
     * 统计本单位接收任务总数（盟市）
     * 0未接收 1已接收
     */
    public List<StatTableVo> findStatTaskRecs(OAuth2Authentication principal){
        Map<String,Object> map = new HashMap<>();
        List<StatTableVo> list = new ArrayList<>();
        LinkedHashMap<String, Object> userMap = getUserMap(principal);
        //map.put("createOrgId",userMap.get("orgId").toString());
        map.put("receiveId",userMap.get("id").toString());
        map.put("tagFlag",StatGlobal.STAT_TYPE_LING);
        list.add(new StatTableVo(0,taskClient.searchAllStat(convertMap2MultiValueMap(map)).getBody().size()));
        map.put("tagFlag",StatGlobal.STAT_TYPE_YI);
        list.add(new StatTableVo(1,taskClient.searchAllStat(convertMap2MultiValueMap(map)).getBody().size()));
        return list;
    }


}
