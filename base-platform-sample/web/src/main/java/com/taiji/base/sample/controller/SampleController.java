package com.taiji.base.sample.controller;

import com.taiji.base.sample.service.SampleService;
import com.taiji.base.sample.vo.SampleVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author scl
 *
 * @date 2018-02-07
 */
@Controller
@RequestMapping("/sample")
public class SampleController extends BaseController {

    protected static final Logger log = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    SampleService sampleService;

    /**
     *  加载page数据
     */
    @RequestMapping(value = "/sample-list.action", method = RequestMethod.GET)
    public String toSampleList(ModelMap modelMap){
        Map<String,String> operations = new HashMap<>();

        operations.put("add","add");
        operations.put("view","view");
        operations.put("edit","edit");
        operations.put("delete","delete");

        modelMap.addAttribute("operations",operations);
        return "sample/sample-list";
    }

    /**
     * 获取多个Sample
     */
    @RequestMapping(value = "/sample-list-load.action", method = RequestMethod.POST)
    @ResponseBody
    public Page<SampleVo> findSamples(SampleVo sampleVo, @PageableDefault Pageable page) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("page", page.getPageNumber());
        params.add("size", page.getPageSize());

        String title = sampleVo.getTitle();
        String content = sampleVo.getContent();

        if (null != sampleVo && !StringUtils.isEmpty(title)) {
            params.add("title", sampleVo.getTitle());
        }

        if (null != sampleVo && !StringUtils.isEmpty(content)) {
            params.add("content", sampleVo.getContent());
        }

        Page<SampleVo> result = sampleService.findPage(params);
        return result;
    }

    @RequestMapping(value = "/sample-view.action", method = RequestMethod.GET)
    public String toSampleView(ModelMap modelMap,String id){
        SampleVo sampleVo = sampleService.find(id);
        modelMap.addAttribute("model",sampleVo);
        return "sample/sample-view";
    }

    @RequestMapping(value = "/sample-edit.action", method = RequestMethod.GET)
    public String toSampleEdit(ModelMap modelMap,String id){
        if(id != null)
        {
            SampleVo sampleVo = sampleService.find(id);
            modelMap.addAttribute("model",sampleVo);
        }

        return "sample/sample-edit";
    }

    @ResponseBody
    @RequestMapping(value = "/sample-edit-save.action", method = RequestMethod.POST)
    public Map<String, Object> sampleEditSave(SampleVo sampleVo){

        Map<String, Object> map = new HashMap<>(1);
        try {
            if(sampleVo != null)
            {
                sampleService.save(sampleVo);

                map.put("result", true);
            }
            else
            {
                map.put("result", false);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            map.put("result", false);
        }

        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/sample-delete.action", method = RequestMethod.POST)
    public Map<String, Object> sampleDelete(String id){

        Map<String, Object> map = new HashMap<>(1);
        try {
            if(StringUtils.hasText(id))
            {
                sampleService.delete(id);

                map.put("result", true);
            }
            else
            {
                map.put("result", false);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            map.put("result", false);
        }

        return map;
    }
}
