package com.taiji.auth.service;


import com.taiji.auth.entity.User;
import com.taiji.auth.repository.UserRepository;
import com.taiji.auth.util.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * <p>Title:UserDetailServiceImpl.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/12 18:51</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
@AllArgsConstructor
@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.set("account",account);
        User userVo = repository.findOne(params);

        UserDetails userDetails = new UserDetailsImpl(userVo);
        userDetails.getAuthorities();
        return userDetails;
    }
}
