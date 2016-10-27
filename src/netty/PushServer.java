package netty;

import com.shangyi.netty.module.PushMsg;

import io.netty.channel.socket.SocketChannel;

/**
 * netty推送服务端
 *
 * @author 徐飞
 * @version 2016/02/25 16:38
 */
public class PushServer {

    public static void start(){
        try {
            new NettyServerBootstrap(9999);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    public static void push(String phoneNum,String content){
        SocketChannel channel = (SocketChannel) NettyChannelMap.get(phoneNum);
        if (channel != null) {
            PushMsg pushMsg = new PushMsg();
            pushMsg.setPhoneNum(phoneNum);
            pushMsg.setTitle(phoneNum);
            pushMsg.setContent(content);
            channel.writeAndFlush(pushMsg);
        }
    }
}
