package com.taiji.base.msg;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
    @Autowired
    /**
     * 需要声明这个对象
     */
    private EntityManagerFactory entityManagerFactory;

    @Before
    /**
     * 在测试执行之前把entityManager加入到事务同步管理器当中
     */
    public void before() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TransactionSynchronizationManager.bindResource(entityManagerFactory, new EntityManagerHolder(entityManager));

    }

    @After
    /**
     * 在测试执行完毕之后，关闭entityManager，并且接触绑定
     */
    public void after() {
        EntityManagerHolder holder = (EntityManagerHolder) TransactionSynchronizationManager.getResource(entityManagerFactory);
        EntityManagerFactoryUtils.closeEntityManager(holder.getEntityManager());
        TransactionSynchronizationManager.unbindResource(entityManagerFactory);
    }
}
