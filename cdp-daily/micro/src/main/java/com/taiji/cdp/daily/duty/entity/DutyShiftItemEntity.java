package com.taiji.cdp.daily.duty.entity;

import com.taiji.micro.common.entity.IdEntity;
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
 * <p>Title:DutyShiftItemEntity.java</p >
 * <p>Description: 交接班信息实体类</p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2019</p >
 * <p>Date:2019/01/21 17:20</p >
 *
 * @author xwk (xuwk@mail.taiji.com.cn)
 * @version 1.0
 */
@DynamicInsert
@DynamicUpdate
@SelectBeforeUpdate
@Entity
@Table(name = "WB_DUTYSHIFT_ITEM")
public class DutyShiftItemEntity extends IdEntity<String> {

    @Getter
    @Setter
    @NotBlank
    @Length(max = 36, message = "交接班表id")
    private String dutyId;

    @Getter
    @Setter
    @NotBlank
    @Length(max = 36, message = "舆情信息id")
    private String infoId;

}