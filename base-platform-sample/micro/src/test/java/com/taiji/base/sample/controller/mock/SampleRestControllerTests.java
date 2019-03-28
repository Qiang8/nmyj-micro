package com.taiji.base.sample.controller.mock;


import com.taiji.base.sample.controller.SampleController;
import com.taiji.base.sample.entity.Sample;
import com.taiji.base.sample.service.SampleService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author scl
 *
 * @date 2018-03-01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleRestControllerTests {

    protected static final Logger log = LoggerFactory.getLogger(SampleRestControllerTests.class);

    @Autowired
    SampleController sampleController;

    @Autowired
    SampleService sampleService;

    private MockMvc mockMvc;

    private Sample sample;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = standaloneSetup(sampleController).build();
        sample = new Sample();
        sample.setTitle("testSample");
        sample.setContent("testContent");
        sample = sampleService.save(sample);
    }

    @After
    public void tearDown() throws Exception {
        sampleService.delete(sample.getId());
    }

    @Test
    public void testGetSample() throws Exception {
        this.mockMvc.perform(get("/api/sample/find/{id}", sample.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(sample.getId()))
                .andExpect(jsonPath("title").value(sample.getTitle()))
                .andExpect(jsonPath("content").value(sample.getContent()));
    }
}

