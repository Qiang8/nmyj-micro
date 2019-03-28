package com.taiji.base.sys.controller.mock;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.base.sys.BaseTest;
import com.taiji.base.sys.controller.UserController;
import com.taiji.base.sys.entity.User;
import com.taiji.base.sys.repository.UserRepository;
import com.taiji.base.sys.service.UserService;
import com.taiji.base.sys.vo.RoleVo;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.micro.common.enums.DelFlagEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @author scl
 * @date 2018-03-01
 */
@Slf4j
public class UserRestControllerTest extends BaseTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private User user;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

//        mockMvc = standaloneSetup(userController).build();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        user = new User();
        user.setAccount("account");
        user.setPassword("password");
        user.setDelFlag("1");
        user.setStatus("1");
        user.setFaultNum(0);
        user = userRepository.save(user);
    }

    @After
    public void tearDown() throws Exception {
        userRepository.delete(user.getId());
    }

    @Test
    public void testFindById() throws Exception {
        this.mockMvc.perform(get("/api/user/find/{id}", user.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(user.getId()))
                .andExpect(jsonPath("account").value(user.getAccount()))
                .andExpect(jsonPath("password").value(user.getPassword()));
    }

    @Test
    public void testFindAll() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("account", "admin");

        this.mockMvc.perform(post("/api/user/find/all").params(params))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testFindPage() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("page", "0");
        params.add("size", "10");

        this.mockMvc.perform(post("/api/user/find/page").params(params))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        {
            //测试保存成功
            UserVo userVo = new UserVo();
            userVo.setAccount("userAccountCreate");
            userVo.setPassword("userPasswordCreate");
            userVo.setDelFlag(DelFlagEnum.NORMAL.getCode());

            String jsonString = objectMapper.writeValueAsString(userVo);

            this.mockMvc.perform(post("/api/user/create").content(jsonString).contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
        {
            //测试密码不能为空
            UserVo userVo = new UserVo();
            userVo.setAccount("userAccountCreate");
            userVo.setDelFlag(DelFlagEnum.NORMAL.getCode());

            String jsonString = objectMapper.writeValueAsString(userVo);

            this.mockMvc.perform(post("/api/user/create").content(jsonString).contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(print());
//                    .andExpect(status().is4xxClientError());
        }
    }

    @Test
    public void testUpdate() throws Exception {
        {
            //用户id为1初始默认角色为1超级管理员
            MvcResult result = this.mockMvc.perform(get("/api/user/find/{id}", "1"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andReturn();

            UserVo userVo1 = objectMapper.readValue(result.getResponse().getContentAsString(),UserVo.class);

            List<RoleVo> roleVoList = new ArrayList<>();

            {
                RoleVo roleVo = new RoleVo();
                roleVo.setId("2");
                roleVoList.add(roleVo);
            }

            {
                RoleVo roleVo = new RoleVo();
                roleVo.setId("1");
                roleVoList.add(roleVo);
            }

            //更新用户角色
            userVo1.setRoleList(roleVoList);

            //更新用户信息
            userVo1.setPassword("userPasswordNew");
            String jsonString1 = objectMapper.writeValueAsString(userVo1);

            MvcResult result1 = this.mockMvc.perform(put("/api/user/update/{id}",userVo1.getId()).content(jsonString1).contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("password").value(userVo1.getPassword()))
                    .andReturn();

        }
    }

    @Test
    public void testDeleteLogic() throws Exception {
        {
            //测试保存成功
            UserVo userVo = new UserVo();
            userVo.setAccount("userAccountDeleteLogic");
            userVo.setPassword("userPasswordDeleteLogic");
            userVo.setDelFlag(DelFlagEnum.NORMAL.getCode());

            String jsonString = objectMapper.writeValueAsString(userVo);

            MvcResult result = this.mockMvc.perform(post("/api/user/create").content(jsonString).contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("delFlag").value(DelFlagEnum.NORMAL.getCode()))
                    .andReturn();


            UserVo userVo1 = objectMapper.readValue(result.getResponse().getContentAsString(),UserVo.class);

            userVo1.setDelFlag(DelFlagEnum.DELETE.getCode());
            String jsonString1 = objectMapper.writeValueAsString(userVo1);

            MvcResult result1 = this.mockMvc.perform(put("/api/user/update/{id}",userVo1.getId()).content(jsonString1).contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("delFlag").value(DelFlagEnum.DELETE.getCode()))
                    .andReturn();

        }
    }
}

