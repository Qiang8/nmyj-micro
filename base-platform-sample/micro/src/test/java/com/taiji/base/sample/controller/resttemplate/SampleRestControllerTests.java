package com.taiji.base.sample.controller.resttemplate;

import com.taiji.base.sample.entity.Sample;
import com.taiji.base.sample.service.SampleService;
import com.taiji.micro.common.entity.utils.RestPageImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;

/**
 * @author scl
 *
 * @date 2018-03-01
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleRestControllerTests {

    protected static final Logger log = LoggerFactory.getLogger(SampleRestControllerTests.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    SampleService sampleService;

    private Sample sample;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);


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
        {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("page",0);
            params.add("size",5);

            params.add("title","tit");
            params.add("content","tent");


            ResponseEntity entity = restTemplate.postForEntity("/api/sample/find/page", params, String.class);

            log.debug(entity.getBody().toString());
        }

        {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("page",0);
            params.add("size",5);

//            params.add("title","tit");
//            params.add("content","tent");

            ParameterizedTypeReference<RestPageImpl>     responseType  = new ParameterizedTypeReference<RestPageImpl>() { };
            RequestEntity<MultiValueMap<String, Object>> requestEntity = RequestEntity.post(new URI("/api/sample/page")).body(params, responseType.getType());
            ResponseEntity<RestPageImpl>                 entity        = restTemplate.exchange(requestEntity,responseType);

            log.debug(entity.getBody().toString());
        }

        {
            MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
            params.add("page",0);
            params.add("size",5);

            params.add("title","tit");
//            params.add("content","tent");

            ParameterizedTypeReference<RestPageImpl>     responseType  = new ParameterizedTypeReference<RestPageImpl>() { };
            RequestEntity<MultiValueMap<String, Object>> requestEntity = RequestEntity.post(new URI("/api/sample/page1")).body(params, responseType.getType());
            ResponseEntity<RestPageImpl>                 entity        = restTemplate.exchange(requestEntity,responseType);

            log.debug(entity.getBody().toString());
        }


//        Pageable page = new PageRequest(1, 5);
//        Sample sample = new Sample();
//
//        sample.setTitle("tit");
//        sample.setContent("cont");
//
//        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
////        params.add("page",1);
////        params.add("size",5);
//        params.add("page",page);
//        params.add("sample",sample);
//
//
//        HttpEntity<MultiValueMap<String, Object>>    httpEntity    = new HttpEntity<>(params, new HttpHeaders());
//        ParameterizedTypeReference<RestPageImpl>     responseType  = new ParameterizedTypeReference<RestPageImpl>() { };
//        RequestEntity<MultiValueMap<String, Object>> requestEntity = RequestEntity.post(new URI("/api/sample/page")).body(params, responseType.getType());
//        ResponseEntity<RestPageImpl>                         entity        = restTemplate.exchange(requestEntity,responseType);
//        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

//        log.debug(entity.getBody().toString());
    }
}
