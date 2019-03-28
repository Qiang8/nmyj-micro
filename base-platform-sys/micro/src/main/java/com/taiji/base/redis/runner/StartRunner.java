package com.taiji.base.redis.runner;

import com.taiji.base.redis.service.DicItemRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartRunner implements CommandLineRunner {

    @Autowired
    DicItemRedisService dicItemRedisService;

    @Override
    public void run(String... strings) throws Exception {
        dicItemRedisService.clearAllDicItems();
        System.out.println("字典项开始加载到redis......");
        long timeStart = System.currentTimeMillis();
        dicItemRedisService.createAllDicItems();
        long timeEnd = System.currentTimeMillis();
        System.out.println("字典项加载结束,时间共计"+(timeEnd-timeStart)+"毫秒");
    }
}
