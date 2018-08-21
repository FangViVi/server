package core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 此类负责启动服务器，并为新建的连接通道分配处理线程
 * @author weiwei
 * @date 2018/08/22
 */
public class WebServer {
    /**
     * 此对象代表服务器端
     */
    private ServerSocket server;
    /**
     * 维护连接通道的线程池
     */
    private ExecutorService threadPool;

    /**
     * 构造，初始化服务器
     */
    public WebServer() {
        try {
            // 绑定8080端口
            server = new ServerSocket(8080);
            // 初始化线程池
            threadPool = Executors.newFixedThreadPool(20);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败！");
        }
    }

    /**
     * 运行服务器
     */
    public void start(){
        try {
            // 持续监听请求
            while (true) {
                // 获取一个来自浏览器的请求，建立连接通道
                Socket socket = server.accept();
                // 为连接通道分配线程
                threadPool.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务断开连接！");
        }
    }

    public static void main(String[] args) {
        WebServer server = new WebServer();
        System.out.println("服务器启动。。。");
        server.start();
    }
}
