package com.taiji.cdp.daily.issue.entity;

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
 * @program: nmyj-micro
 * @description:
 * @author: TaiJi.WangJian
 * @create: 2019-01-07 16:03
 **/
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "WB_ISSUE")
public class Issue  extends BaseEntity<String> implements DelFlag {

    @Getter
    @Setter
    @Length(max = 200,message = "专刊标题字段最大长度200")
    private String title;

    @Getter
    @Setter
    @Length(max = 1,message = "专刊状态（0未发布，1已发布）字段最大长度1")
    private String status;

    @Getter
    @Setter
    @Length(max = 1,message = "删除标志最大长度1（删除标记（0未删除，1已删除））")
    private String delFlag;
}