package com.taiji.base.sample.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.base.sample.entity.Sample;
import com.taiji.micro.common.repository.UtilsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author scl
 *
 * @date 2018-03-01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleRepositoryTest {
    protected static final Logger log = LoggerFactory.getLogger(SampleRepositoryTest.class);

    @Autowired
    SampleRepository sampleRepository;

    @Autowired
    UtilsRepository utilsRepository;

    @Test
    public void saveOrUpdate()
    {
        Sample sample = new Sample();
        sample.setContent("test");
        sample.setTitle("title");
        Sample temp = sampleRepository.save(sample);
        temp.setContent("testaaa");
        sampleRepository.save(temp);
    }

    @Test
    public void testFindAll() throws JsonProcessingException {
        List<Sample> list = sampleRepository.findAll();

        ObjectMapper mapper = new ObjectMapper();
        for (Sample sample: list) {
            String json = mapper.writeValueAsString(sample);

            log.debug(json);
        }
    }


    @Test
    public void testFindByContent() throws JsonProcessingException {
        List<Sample> list = sampleRepository.findByContent("ten");

        ObjectMapper mapper = new ObjectMapper();
        for (Sample sample: list) {
            String json = mapper.writeValueAsString(sample);

            log.debug(json);
        }
    }

    @Test
    public void testFindByContentPage() throws JsonProcessingException {
        Pageable     pageable = new PageRequest(1, 5);
        Page<Sample> page     = sampleRepository.findByContent("ten", pageable);

        ObjectMapper mapper = new ObjectMapper();
        for (Sample sample: page.getContent()) {
            String json = mapper.writeValueAsString(sample);

            log.debug(json);
        }
    }

    @Test
    public void testDate()
    {
        log.debug(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(utilsRepository.nowMysql()));
    }
}