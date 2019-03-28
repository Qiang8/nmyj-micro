package com.taiji.cdp.base.caseMa.service;
import com.taiji.cdp.base.caseMa.feign.CaseClient;
import com.taiji.cdp.base.caseMa.vo.CaseSaveVo;
import com.taiji.cdp.base.caseMa.vo.CaseVo;
import com.taiji.cdp.base.midAtt.feign.MidAttClient;
import com.taiji.cdp.base.midAtt.service.BaseService;
import com.taiji.cdp.base.midAtt.vo.MidAttSaveVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CaseService extends BaseService {

    CaseClient client;
    MidAttClient midAttClient;

    //新增任务信息
    public CaseVo addCaseVo(CaseSaveVo saveVo,
                            OAuth2Authentication principal){
        CaseVo vo = saveVo.getCaseVo();
        ResponseEntity<CaseVo> result = client.create(vo);
        CaseVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        //获取文件List
        List<String> fileIds = saveVo.getFileIds();
        List<String> fileDeleteIds = saveVo.getFileDeleteIds();
        if(!(CollectionUtils.isEmpty(fileIds)&&CollectionUtils.isEmpty(fileDeleteIds))){
            //更新附件部分
            ResponseEntity<Void> fileResult = midAttClient.saveMidAtts(new MidAttSaveVo(resultVo.getId(),fileIds,fileDeleteIds));
            ResponseEntityUtils.achieveResponseEntityBody(fileResult);
        }
        return resultVo;
    }

    //根据ID查询单个案例信息
    public CaseVo finCaseById(String id) {
        ResponseEntity<CaseVo> result = client.finCaseById(id);
        CaseVo caseVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        return caseVo;
    }
    //根据ID删除单个案例信息
    public void deleteById(String id) {
        ResponseEntity<CaseVo> result = client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }
    //修改对应id的案例信息
    public CaseVo update(CaseSaveVo saveVo, String id, OAuth2Authentication principal) {

        CaseVo vo = saveVo.getCaseVo();
        ResponseEntity<CaseVo> result = client.update(vo,id);
        CaseVo resultVo = ResponseEntityUtils.achieveResponseEntityBody(result);
        //获取文件List
        List<String> fileIds = saveVo.getFileIds();
        List<String> fileDeleteIds = saveVo.getFileDeleteIds();
        if(!(CollectionUtils.isEmpty(fileIds)&&CollectionUtils.isEmpty(fileDeleteIds))){
            //更新附件部分
            ResponseEntity<Void> fileResult = midAttClient.saveMidAtts(new MidAttSaveVo(resultVo.getId(),fileIds,fileDeleteIds));
            ResponseEntityUtils.achieveResponseEntityBody(fileResult);
        }
        return resultVo;
    }

    //根据条件查询任务信息列表-分页
    public RestPageImpl<CaseVo> findCases(Map<String, Object> map, OAuth2Authentication principal) {
        ResponseEntity<RestPageImpl<CaseVo>> result = client.findPage(convertMap2MultiValueMap(map));
        return ResponseEntityUtils.achieveResponseEntityBody(result);
    }
}
