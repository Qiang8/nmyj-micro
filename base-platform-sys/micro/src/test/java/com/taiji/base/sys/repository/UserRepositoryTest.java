package com.taiji.base.sys.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taiji.base.sys.BaseTest;
import com.taiji.base.sys.entity.User;
import com.taiji.base.sys.entity.UserProfile;
import com.taiji.micro.common.entity.utils.DelFlag;
import com.taiji.micro.common.enums.DelFlagEnum;
import com.taiji.micro.common.enums.StatusEnum;
import com.taiji.micro.common.repository.UtilsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author scl
 * @date 2018-03-01
 */
@Slf4j
public class UserRepositoryTest extends BaseTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    UtilsRepository utilsRepository;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void testFindAll() throws JsonProcessingException {
        List<User> list = userRepository.findAll("admin", "");

        for (User user : list) {
            String json = mapper.writeValueAsString(user);
            log.debug(json);
        }
    }

    @Test
    public void testFindPage() throws JsonProcessingException {
        Pageable   pageable = new PageRequest(0, 10);
        Page<User> list     = userRepository.findPage("", "", pageable);

        for (User user : list) {
            String json = mapper.writeValueAsString(user);
            log.debug(json);
        }
    }

    @Test
    public void testSave() throws JsonProcessingException {
        {
            User user = new User();
            user.setAccount("account");
            user.setPassword("password");
            user.setDelFlag(DelFlagEnum.NORMAL.getCode());
            user.setStatus(StatusEnum.ENABLE.getCode());
            user.setFaultNum(0);

            UserProfile userProfile = new UserProfile();
            userProfile.setEmail("test@mail.taiji.com.cn");
            userProfile.setMobile("13112345678");
            userProfile.setName("account");
            userProfile.setSex("1");
            userProfile.setPosition("局长");
            userProfileRepository.save(userProfile);

            user.setProfile(userProfile);
            userRepository.save(user);

            User tempUser = userRepository.findOne(user.getId());
            log.debug(mapper.writeValueAsString(tempUser));

            userRepository.deleteLogic(tempUser, DelFlagEnum.DELETE);

            tempUser = userRepository.findOne(user.getId());
            log.debug(mapper.writeValueAsString(tempUser));

        }
    }

    @Test
    public void testCheckUnique() {
        {
            Boolean result = userRepository.checkUnique("", "admin");
            log.debug("admin checkUnique result={}", result);
            assertFalse(result);
        }

        {
            Boolean result = userRepository.checkUnique("", "testadmin");
            log.debug("testadmin checkUnique result={}", result);
            assertTrue(result);
        }
    }


    @Test
    public void testDate() {
        log.debug(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(utilsRepository.nowMysql()));
    }
}