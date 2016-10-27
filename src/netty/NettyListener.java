package netty;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class NettyListener
 *
 */
@WebListener
public class NettyListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public NettyListener() {
    }

	/**
	 * 当TomcatService停止的时候执行此方法
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
    }

	/**
	 *当服务器运行起来的时候执行此方法
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  {
    	System.out.println("Netty服务器开始启动..");
        PushServer.start();
        System.out.println("Netty服务器启动成功");
    }
	
}
