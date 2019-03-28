package com.taiji.base.sys.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.base.sys.BaseTest;
import com.taiji.base.sys.vo.DicGroupItemVo;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>Title:DicControllerTest.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/11 11:38</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
public class DicControllerTest extends BaseTest {
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
    public void testAddItem() throws Exception {
        DicGroupItemVo dicGroupItemVo = new DicGroupItemVo();
        dicGroupItemVo.setItemName("itemName8");
        dicGroupItemVo.setItemCode("itemCode8");
        dicGroupItemVo.setDicCode("ceshi");
        dicGroupItemVo.setType("0");
        dicGroupItemVo.setOrders(1);
        dicGroupItemVo.setParentId("09d832ef-7fa3-4558-b511-3126f1e91c86");

        String dicGroupItemVoStr = objectMapper.writeValueAsString(dicGroupItemVo);

        this.mockMvc.perform(post("/dic/items").content(dicGroupItemVoStr).contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
