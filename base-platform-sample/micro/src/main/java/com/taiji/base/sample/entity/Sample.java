package com.taiji.base.sample.entity;

import com.taiji.micro.common.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author scl
 *
 * @date 2018-02-07
 */
@Entity
@Table(name = "TB_SAMPLE")
public class Sample extends BaseTimeEntity<String>
{

    public Sample() {
    }

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String content;
}
