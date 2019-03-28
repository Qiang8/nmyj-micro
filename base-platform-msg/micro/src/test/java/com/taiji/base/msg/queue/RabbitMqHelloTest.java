package com.taiji.base.msg.queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>Title:RabbitMqHelloTest.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/11/7 10:37</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqHelloTest {

    @Autowired
    private MsgSender msgSender;

    @Test
    public void hello() throws Exception {
        msgSender.send();
    }

}