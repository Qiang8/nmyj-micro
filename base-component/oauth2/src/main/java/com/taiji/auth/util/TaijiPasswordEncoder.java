package com.taiji.auth.util;


import com.taiji.micro.common.utils.PasswordUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

/**
 * <p>Title:TaijiPasswordEncoder.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/9/18 11:52</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
@Slf4j
public class TaijiPasswordEncoder implements PasswordEncoder {

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public TaijiPasswordEncoder()
    {
    }


    @Override
    public String encode(CharSequence charSequence) {
        String base64Md5SaltPassword = "";
        if(null != charSequence && charSequence.toString().equals("userNotFoundPassword"))
        {
            base64Md5SaltPassword = charSequence.toString();
        }
        else if(null != charSequence && !charSequence.toString().equals("userNotFoundPassword"))
        {
            base64Md5SaltPassword = PasswordUtils.decodeBase64Md5SaltPassword(charSequence.toString(),"yjgl");
        }
        else
        {
            throw new IllegalArgumentException("Bad Argument");
        }

        return bCryptPasswordEncoder.encode(base64Md5SaltPassword);
    }

    @Override
    public boolean matches(CharSequence charSequence, String encodedPassword) {
        if (encodedPassword != null && encodedPassword.length() != 0) {
            String rawPassword = charSequence.toString();
            if(rawPassword.startsWith("{taiji}"))
            {
                rawPassword = PasswordUtils.decodeBase64Md5SaltPassword(rawPassword,"yjgl");
            }
            else
            {
                rawPassword = PasswordUtils.encodePasswordMd5(rawPassword);
            }

            return bCryptPasswordEncoder.matches(rawPassword,encodedPassword);
        } else {
            log.warn("Empty encoded password");
            return false;
        }
    }

    public static void main(String[] args)
    {
        String rawPassword = "YzRjYTQyMzhhMGI5MjM4MjBkY2M1MDlhNmY3NTg0OWJ5amds";
        String encodePassword = "$2a$10$3NFKSomxt6jmGe3FR//l6O8n0FVJgfsyUouw2JoaPpGCF29NCJ5ey";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        rawPassword = PasswordUtils.decodeBase64Md5SaltPassword(rawPassword,"yjgl");
        System.out.println(rawPassword);
        Boolean result = bCryptPasswordEncoder.matches(rawPassword,encodePassword);
        System.out.println(result);

        String password = "1";
        String md5 = PasswordUtils.encodePasswordMd5(password);
        System.out.println(md5);

        md5 = bCryptPasswordEncoder.encode(md5);
        System.out.println(md5);

        String base64 = md5+"yjgl";
        base64 = Base64Utils.encodeToString(base64.getBytes());
        System.out.println(base64);
        byte[] bytes = Base64Utils.decodeFromString(base64);
        System.out.println(new String(bytes));


        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = passwordEncoder.encode("password");
        log.info("加密后的密码:" + encode);
        log.info("bcrypt密码对比:" + passwordEncoder.matches("password", encode));

        String md5Password = "{MD5}88e2d8cd1e92fd5544c8621508cd706b";//MD5加密前的密码为:password
        log.info("MD5密码对比:" + passwordEncoder.matches("password", encode));

    }
}
