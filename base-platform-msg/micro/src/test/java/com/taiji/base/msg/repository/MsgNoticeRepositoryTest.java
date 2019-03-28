package com.taiji.base.msg.repository;

import com.taiji.base.msg.entity.MsgNotice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author scl
 *
 * @date 2018-03-01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MsgNoticeRepositoryTest {
    protected static final Logger log = LoggerFactory.getLogger(MsgNoticeRepositoryTest.class);

    @Autowired
    MsgNoticeRepository repository;

    @Autowired

    @Test
    public void saveOrUpdate()
    {
        MsgNotice sample = repository.findOne("6711dd43-0167-11df2dcf-0004-40280d81");
        sample.setMsgContent("testttt");
        repository.save(sample);
    }


}