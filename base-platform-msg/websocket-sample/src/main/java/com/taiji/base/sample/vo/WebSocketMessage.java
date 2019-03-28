package com.taiji.base.sample.vo;

/**
 * <p>Title:WebSocketMessage.java</p >
 * <p>Description: </p >
 * <p>Copyright: 公共服务与应急管理战略业务本部 Copyright(c)2018</p >
 * <p>Date:2018/10/12 15:16</p >
 *
 * @author scl (suncla@mail.taiji.com.cn)
 * @version 1.0
 */
public class WebSocketMessage {

    public WebSocketMessage() {
    }

    public WebSocketMessage(String message) {
        this.message = message;
    }

    public WebSocketMessage( String message, String toUser) {
        this.toUser = toUser;
        this.message = message;
    }

    private String fromUser;
    private String toUser;
    private String message;

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
