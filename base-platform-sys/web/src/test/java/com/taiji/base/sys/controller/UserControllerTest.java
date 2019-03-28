package com.taiji.base.sys.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.base.sys.BaseTest;
import com.taiji.base.sys.vo.UserProfileVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.micro.common.enums.SexTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>Title:UserControllerTest.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/11 9:33</p >
 *
 *  测试先启动1.eureka 2.oauth2 3.zuul 4.micro base sys 5. web base sys
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
public class UserControllerTest extends BaseTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

//        mockMvc = standaloneSetup(userController).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAddUser() throws Exception {
        UserVo userVo = new UserVo();
        userVo.setAccount("sys-user-test");

        UserProfileVo userProfileVo = new UserProfileVo();
        userProfileVo.setName("sys-user-test");
        userProfileVo.setEmail("suncla@mail.taiji.com.cn");
        userProfileVo.setMobile("13146943790");
        userProfileVo.setSex(SexTypeEnum.MALE.getCode());
        userProfileVo.setPosition("1");
        userProfileVo.setOrgId("1");
        userProfileVo.setOrgName("部门1");

        userVo.setProfile(userProfileVo);

        String userVoStr = objectMapper.writeValueAsString(userVo);

        this.mockMvc.perform(post("/users").content(userVoStr).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testFindUserById() throws Exception {
        this.mockMvc.perform(get("/users/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").value("2000"))
                .andExpect(jsonPath("$.data.id").value("1"));
    }

}
