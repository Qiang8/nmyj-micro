package com.taiji.base.sample.vo;

import com.taiji.micro.common.vo.BaseTimeVo;
import lombok.Getter;
import lombok.Setter;

public class SampleVo extends BaseTimeVo<String>{
    public SampleVo() {
    }

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String content;
}
