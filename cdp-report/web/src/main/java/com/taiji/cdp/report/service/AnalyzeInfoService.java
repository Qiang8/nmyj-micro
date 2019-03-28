package com.taiji.cdp.report.service;

import com.taiji.cdp.report.vo.AnalyzeInfoDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnalyzeInfoService extends BaseService{


    //根据url获取舆情信息
    public AnalyzeInfoDTO getAnalyzeInfoByUrl(String infoUrl){
        //TODO 调用电子取证系统,根据url获取舆情信息

        return new AnalyzeInfoDTO();
    }

}
