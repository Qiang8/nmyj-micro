package com.taiji.base.contact.vo;

import com.taiji.micro.common.vo.IdVo;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * <p>Title:ContactMidVo.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2019/1/15 0:04</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
public class ContactMidVo extends IdVo<String> {
    public ContactMidVo()
    {}

    public ContactMidVo(String memberId,String groupId){
        this.member = new ContactMemberVo();
        this.member.setId(memberId);

        this.group = new ContactGroupVo();
        this.group.setId(groupId);

    }

    @Getter
    @Setter
    private ContactMemberVo member;

    @Getter
    @Setter
    private ContactGroupVo group;
}
