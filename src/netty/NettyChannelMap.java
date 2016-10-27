package netty;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 徐飞
 * @version 2016/02/24 19:48
 */
public class NettyChannelMap {
    private static Map<String,SocketChannel> map=new ConcurrentHashMap<>();

    public static void add(String clientId,SocketChannel socketChannel){
        map.put(clientId,socketChannel);
    }

    public static Channel get(String clientId){
        return map.get(clientId);
    }

    public static void remove(SocketChannel socketChannel){

        for (Map.Entry entry:map.entrySet()){
            if (entry.getValue()==socketChannel){
                String key = (String) entry.getKey();
                System.out.println("通道"+ key +"已被移除。");
                map.remove(key);
            }
        }
    }

}