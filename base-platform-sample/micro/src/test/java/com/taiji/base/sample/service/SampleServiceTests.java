package com.taiji.base.sample.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.taiji.base.sample.entity.Sample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author scl
 *
 * @date 2018-03-01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTests {

    protected static final Logger log = LoggerFactory.getLogger(SampleServiceTests.class);

    @Autowired
    SampleService sampleService;

    @Test
    public void testFindAll() throws JsonProcessingException {
        List<Sample> list = sampleService.findAll();

        ObjectMapper mapper = new ObjectMapper();
        for (Sample sample: list) {
            String json = mapper.writeValueAsString(sample);

            log.debug(json);
        }
    }
}
