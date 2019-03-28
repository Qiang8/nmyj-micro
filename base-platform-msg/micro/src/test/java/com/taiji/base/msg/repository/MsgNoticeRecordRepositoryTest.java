package com.taiji.base.msg.repository;

import com.taiji.base.msg.entity.MsgNoticeRecord;
import com.taiji.base.msg.enums.ReadFlagEnum;
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
public class MsgNoticeRecordRepositoryTest {
    protected static final Logger log = LoggerFactory.getLogger(MsgNoticeRecordRepositoryTest.class);

    @Autowired
    MsgNoticeRecordRepository repository;

    @Autowired

    @Test
    public void saveOrUpdate()
    {
        MsgNoticeRecord sample = repository.findOne("6715ace2-0167-15addd09-0003-40280d81");
        sample.setReadFlag(ReadFlagEnum.READ.getCode());
        repository.save(sample);
    }


}