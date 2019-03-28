package com.taiji.base.sample.vo;

import lombok.Getter;
import lombok.Setter;

public class MessageVo {
    @Getter
    @Setter
    private String image;
    @Getter
    @Setter
    private String iamgeAlt;
    @Getter
    @Setter
    private String fromUser;
    @Getter
    @Setter
    private String content;

    public MessageVo(){}

    public MessageVo(String image, String iamgeAlt, String fromUser, String content) {
        this.image = image;
        this.iamgeAlt = iamgeAlt;
        this.fromUser = fromUser;
        this.content = content;
    }
}
