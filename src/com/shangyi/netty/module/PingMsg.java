package com.shangyi.netty.module;

/**
 * 心跳�?测消息类�?
 *
 * @author 徐飞
 * @version 2016/02/24 19:40
 */
public class PingMsg extends BaseMsg {
    public PingMsg() {
        super();
        setType(MsgType.PING);
    }
}
