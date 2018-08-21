package core;

import java.io.*;
import java.net.Socket;

/**
 * 维护当前线程分配到连接通道
 * 处理请求和发送响应信息
 * @author weiwei
 * @date 2018/08/22
 */
public class ClientHandler implements Runnable{
    /**
     * 此对象代表浏览器客户端
     */
    private Socket client;

    /**
     * 构造，将当前连接通道和一个线程绑定
     * @param client
     */
    public ClientHandler(Socket client) {
        this.client = client;
    }

    /**
     * 处理请求和发送响应信息
     */
    @Override
    public void run() {
        try {
            // 获得输入流，读取请求行
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(client.getInputStream()));
            // 获得请求行 GET /index.html HTTP/1.1
            String line = reader.readLine();
            // 防止请求行数据错误
            if (line != null && line.length()>0) {
                System.out.println("收到一个请求！");
                // 根据空格拆分请求行
                String[] data = line.split(" ");
                // 获得请求资源路径
                String uri = data[1];
                System.out.println("uri:"+uri);
                String key = "/";
                // 如果没有制定资源就跳转到主页
                if (key.equals(uri)) {
                    uri = "/index.html";
                }
                // 获取页面文件对象
                File file = new File("src/main/webapp"+uri);
                // 声明读取文件的字节缓冲流
                BufferedInputStream bis = new BufferedInputStream(
                        new FileInputStream(file));
                // 存放字节数据的缓存
                byte[] buff = new byte[(int)file.length()];
                // 读取页面文件的字节数据
                bis.read(buff);
                // 获得输出流
                PrintStream ps = new PrintStream(client.getOutputStream());
                // 发送状态行
                ps.println("HTTP/1.1 200 OK");
                // 发送响应数据类型
                ps.println("Content-Type:text/html");
                // 发送响应数据长度
                ps.println("Content-Length:"+file.length());
                // 发送空白行
                ps.println();
                // 向浏览器发送数据
                ps.write(buff);
                // 写出缓冲区的数据
                ps.flush();
                // 释放连接资源
                client.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
