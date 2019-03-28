package com.taiji.base.sys.service;

import com.taiji.base.sys.feign.UserClient;
import com.taiji.base.sys.vo.UserVo;
import com.taiji.micro.common.entity.utils.RestPageImpl;
import com.taiji.micro.common.utils.ResponseEntityUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * <p>Title:UserService.java</p >
 * <p>Description: 用户信息服务类</p >
 * <p>Copyright: 公共服务与应急管理战略本部 Copyright(c)2018</p >
 * <p>Date:2018年08月23</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
@Service
@AllArgsConstructor
public class UserService extends BaseService{

    UserClient client;

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    public UserVo findById(String id){
        Assert.hasText(id, "id不能为null值或空字符串。");

        ResponseEntity<UserVo> result = client.find(id);
        UserVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    public UserVo findOneUser(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        MultiValueMap<String, Object> valueMap= super.convertMap2MultiValueMap(params);
        ResponseEntity<UserVo> result = client.findOne(valueMap);
        UserVo body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 新增用户信息
     * @param vo
     * @return
     */
    public void create(UserVo vo){
        Assert.notNull(vo, "UserVo不能为null值。");

        ResponseEntity<UserVo> result = client.create(vo);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 更新用户信息
     * @param vo
     * @param id
     */
    public void update(UserVo vo, String id){
        Assert.notNull(vo,"vo不能为null!");
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<UserVo> result = client.update(vo,id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 逻辑删除用户
     * @param id
     */
    public void delete(String id){
        Assert.hasText(id,"id不能为null或空字符串!");

        ResponseEntity<Void> result =  client.deleteLogic(id);
        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    /**
     * 查询分页列表
     * @param params
     * @return
     */
    public RestPageImpl<UserVo> findUsers(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<RestPageImpl<UserVo>> result = client.findPage(super.convertMap2MultiValueMap(params));
        RestPageImpl<UserVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 查询列表
     * @param params
     * @return
     */
    public List<UserVo> findUsersAll(Map<String, Object> params) {
        Assert.notNull(params, "params不能为null值");

        ResponseEntity<List<UserVo>> result = client.findList(super.convertMap2MultiValueMap(params));
        List<UserVo> body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return body;
    }

    /**
     * 检查用户登录名的重复性
     * @param account
     * @return
     */
    public Boolean checkAccount(String account){
        Assert.hasText(account, "account不能为null或空字符串!");

        ResponseEntity<Boolean> result= client.checkUnique("",account);
        Boolean body = ResponseEntityUtils.achieveResponseEntityBody(result);

        return  body;
    }

    /**
     * 用户密码重置
     * @param userId
     */
    public void resetPassword(String userId){
        Assert.hasText(userId,"id不能为null或空字符串!");

        ResponseEntity<Void> result =  client.resetPassword(userId);

        ResponseEntityUtils.achieveResponseEntityBody(result);
    }

    public void changePassword(String userId,String oldPassword,String newPassword){

        Assert.hasText(userId,"id不能为null或空字符串!");
        Assert.hasText(oldPassword,"oldPassword不能为null或空字符串!");
        Assert.hasText(newPassword,"newPassword不能为null或空字符串!");

        ResponseEntity<Void> result =  client.updatePassword(userId,oldPassword,newPassword);

        ResponseEntityUtils.achieveResponseEntityBody(result);

    }
}
