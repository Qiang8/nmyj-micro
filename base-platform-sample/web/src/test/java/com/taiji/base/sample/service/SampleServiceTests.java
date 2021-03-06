package com.taiji.base.sample.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.base.sample.vo.SampleVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * @author scl
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTests {

    protected static final Logger log = LoggerFactory.getLogger(SampleServiceTests.class);

    @Autowired
    SampleService sampleService;

    @Test
    public void testFindAll() throws JsonProcessingException {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("page", 0);
        params.add("size", 10);

        Page<SampleVo> list = sampleService.findPage(params);

        ObjectMapper mapper = new ObjectMapper();
        for (SampleVo sample: list) {
            String json = mapper.writeValueAsString(sample);

            log.debug(json);
        }
    }
}
