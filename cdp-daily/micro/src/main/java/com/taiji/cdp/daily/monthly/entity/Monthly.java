package com.taiji.cdp.daily.monthly.entity;

import com.taiji.micro.common.entity.BaseEntity;
import com.taiji.micro.common.entity.utils.DelFlag;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * <p>Title:Monthly.java</p >
 * <p>Description: 月报管理实体类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/07 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "WB_MONTHLY")
public class Monthly extends BaseEntity<String> implements DelFlag {

    @Getter
    @Setter
    @NotBlank
    @Length(max = 200, message = "月报标题")
    private String title;

    @Getter
    @Setter
    @NotBlank
    @Length(max = 1, message = "月报状态（0未发布，1已发布）")
    private String status;

    @Getter
    @Setter
    @NotBlank
    @Length(max = 1, message = "月报删除标记（0已删除，1未删除）")
    private String delFlag;
}