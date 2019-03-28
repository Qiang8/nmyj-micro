package com.taiji.cdp.report.service;

import com.taiji.cdp.report.common.global.StatGlobal;
import com.taiji.cdp.report.entity.ConsInfo;
import com.taiji.cdp.report.entity.ConsJudgeInfo;
import com.taiji.cdp.report.repository.ConsJudgeRepository;
import com.taiji.cdp.report.repository.StatConsInfoRepository;
import com.taiji.cdp.report.vo.*;
import com.taiji.micro.common.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StatConsInfoService extends BaseService<ConsInfo,String>{

    StatConsInfoRepository repository;
    ConsJudgeRepository consJudgeRepository;
    /**
     * 根据不同条件 统计舆情数量 （统计使用）
     */
    public List<ConsInfo> findStatCons(StatVQueryVo statVQueryVo){
        List<ConsInfo> statOwn = repository.findStatCons(statVQueryVo);
        return statOwn;
    }


    /**
     * 舆情信息按新增舆情和重点舆情分类进行统计（近七日）
     */
    public StatTimeTypeVo findStatTypes(String orgId){
        LocalDate localDate = LocalDate.now();
        List<ConsInfo> consInfos = repository.findStatTypes(orgId,localDate);
        List<String> statTime = new ArrayList<>();//近七日的时间
        List<Integer> newTotalNum = new ArrayList<>();//新增舆情
        List<Integer> foucsTotalNum = new ArrayList<>();//重点舆情

        for(int i=0;i<7;i++){
            LocalDate localDate1 = localDate.plusDays(-i);
            LocalDateTime startTime =  LocalDateTime.of(localDate1, LocalTime.MIN);//当天开始时间
            LocalDateTime endTime =  LocalDateTime.of(localDate1, LocalTime.MAX);//当天结束时间
            Map<String,Long> consInfoGroupMap = new HashMap<>();
            if(!CollectionUtils.isEmpty(consInfos)){
                //根据近七日每天的时间进行过滤获得id集合
                List<String> consInfoIds = consInfos.stream()
                        .filter(role ->
                                role.getLastDealTime().isAfter(startTime)
                                        && role.getLastDealTime().isBefore(endTime))
                        .map(ConsInfo::getId).collect(Collectors.toList());

                if(!CollectionUtils.isEmpty(consInfoIds)) {
                    //根据id集合获得对应数据，并根据TagType   0：新增舆情 1：重点舆情进行分组
                    List<ConsJudgeInfo> statTypes = consJudgeRepository.findStatTypes(consInfoIds);
                    consInfoGroupMap = statTypes.stream().
                            collect(Collectors.groupingBy(ConsJudgeInfo::getTagType,Collectors.counting()));
                }
            }
            statTime.add(localDate1.toString());
            if(consInfoGroupMap.size()==0){//没数据
                newTotalNum.add(0);
                foucsTotalNum.add(0);
            }else {
                for (String map :consInfoGroupMap.keySet()) {
                    if(StatGlobal.STAT_TYPE_LING.equals(map)){//0：新增舆情
                        newTotalNum.add(consInfoGroupMap.get(map).intValue());
                        if(consInfoGroupMap.keySet().size()==1){
                            foucsTotalNum.add(0);
                        }
                    }else {//1：重点舆情进行分组
                        foucsTotalNum.add(consInfoGroupMap.get(map).intValue());
                        if(consInfoGroupMap.keySet().size()==1){
                            newTotalNum.add(0);
                        }
                    }
                }
            }
        }
        return new StatTimeTypeVo(statTime,newTotalNum,foucsTotalNum);
    }


}
