package netty;

import java.util.Date;

import com.shangyi.netty.module.BaseMsg;
import com.shangyi.netty.module.LoginMsg;
import com.shangyi.netty.module.MsgType;
import com.shangyi.netty.module.PingMsg;
import com.shangyi.netty.module.PushMsg;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;

/**
 * @author 徐飞
 * @version 2016/02/25 12:00
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<BaseMsg> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //channel失效，从Map中移除
        NettyChannelMap.remove((SocketChannel) ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.out.println("服务器端出现异常！");
    }

    //这里是从客户端过来的消息
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, BaseMsg baseMsg) throws Exception {
        if (MsgType.LOGIN.equals(baseMsg.getType())) {
            LoginMsg loginMsg = (LoginMsg) baseMsg;
            if (isLogin(loginMsg)) {
                //登录成功,把channel存到服务端的map中
                NettyChannelMap.add(loginMsg.getPhoneNum(), (SocketChannel) channelHandlerContext.channel());
                System.out.println("client：" + loginMsg.getPhoneNum() + " 登录成功");
                new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						PushServer.push(loginMsg.getPhoneNum(),"成功登陆服务器");
					}
				}).start();
            }
        } else {
            if (NettyChannelMap.get(baseMsg.getPhoneNum()) == null) {
                //说明未登录，或者连接断了，服务器向客户端发起登录请求，让客户端重新登录
                LoginMsg loginMsg = new LoginMsg();
                channelHandlerContext.channel().writeAndFlush(loginMsg);
            }
        }
        switch (baseMsg.getType()) {
            case PING:
                PingMsg pingMsg = (PingMsg) baseMsg;
                PingMsg replyPing = new PingMsg();
                NettyChannelMap.get(pingMsg.getPhoneNum()).writeAndFlush(replyPing);
                System.out.println("收到PING类型" 
                		+ new Date());
                break;
            case PUSH:
            	PushMsg pushMsg=(PushMsg)baseMsg;
            	System.out.println("客户端"+pushMsg.getPhoneNum()+":"+pushMsg.getContent());
                break;
            default:
                System.out.println("default。。");
                break;
        }
        ReferenceCountUtil.release(baseMsg);
    }

    private boolean isLogin(LoginMsg loginMsg) {
        String[] userNames = new String[]{"robin", "xufei01", "xufei02"};
        String[] passwords = new String[]{"yao", "pwd01", "pwd02"};
        for (int i = 0; i < userNames.length; i++) {
            try {
                if (userNames[i].equals(loginMsg.getUserName()) && passwords[i].equals(loginMsg.getPassword())) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }
}