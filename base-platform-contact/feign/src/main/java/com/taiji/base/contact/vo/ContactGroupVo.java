package com.taiji.base.contact.vo;

import com.taiji.micro.common.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * <p>Title:ContactGroupVo.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2019/1/15 0:04</p >
 *
 * @author firebody (dangxb@mail.taiji.com.cn)
 * @version 1.0
 */
public class ContactGroupVo extends BaseVo<String> {
    public ContactGroupVo()
    {}

    /**
     * 分组名称
     */
    @Length(max = 50,message = "分组名称name字段最大长度50")
    @Getter
    @Setter
    private String name;

    /**
     * 用户Id
     */
    @Length(max = 36,message = "用户userId字段最大长度36")
    @Getter
    @Setter
    private String userId;

    /**
     * 顺序
     */
    @Getter
    @Setter
    private Integer orders;

    /**
     * 删除标志
     */
    @Getter
    @Setter
    @Length(max = 1, message = "删除标识delFlag字段最大长度1")
    private String delFlag;

}
