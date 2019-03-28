package com.taiji.base.sys;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>Title:BaseTest.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/8/27 14:03</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class BaseTest {


    @Before
    /**
     * 在测试执行之前把entityManager加入到事务同步管理器当中
     */
    public void before() {

    }

    @After
    /**
     * 在测试执行完毕之后，关闭entityManager，并且接触绑定
     */
    public void after() {
    }
}
