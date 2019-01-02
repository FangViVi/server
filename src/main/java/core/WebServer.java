package core;

import common.ServletContext;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    private ServerSocket server;
    private ExecutorService threadPool;

    public WebServer(){
        try {
            server = new ServerSocket(ServletContext.port);
            threadPool = Executors.newFixedThreadPool(ServletContext.maxThread);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("启动服务器失败！");
        }
    }

    public void start(){
        try {
            while (true) {
                Socket client = server.accept();
                threadPool.execute(new ClientHandler(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务停止");
        }
    }

    public static void main(String[] args) {
        WebServer server = new WebServer();
        System.out.println("服务器启动");
        server.start();
    }

}
