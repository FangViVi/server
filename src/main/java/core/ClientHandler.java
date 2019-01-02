package core;

import common.HttpContext;
import common.ServletContext;
import http.HttpRequest;
import http.HttpResponse;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket client;

    public ClientHandler(Socket client) {
        this.client = client;
    }
    @Override
    public void run() {
        try {
            HttpRequest request  = new HttpRequest(client.getInputStream());
            HttpResponse response = new HttpResponse(client.getOutputStream());
            // 获取请求资源路径
            String uri = request.getUri();
            System.out.println("uri:"+uri);
            File file = new File(ServletContext.webRoot+uri);
            if(!file.exists()){
                response.setStatus(404);
                file = new File(ServletContext.webRoot+"/"+ServletContext.notFoundPage);
            } else {
                response.setStatus(200);
            }
            // 协议
            response.setProtocol(ServletContext.protocol);
            // 请求资源类型
            response.setContentType(getFileTypeByExt(file));
            // 请求资源长度
            response.setContentLength((int)file.length());
            readAndSend(file,response,client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取请求资源并发送给浏览器
     * @param file
     * @param response
     * @param client
     * @throws IOException
     */
    private void readAndSend(File file, HttpResponse response, Socket client) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] bs = new byte[(int)file.length()];
        bis.read(bs);
        //OutputStream out = client.getOutputStream(); 此方法没有发送状态行
        OutputStream out = response.getOutputStream();
        out.write(bs);
        out.flush();
        client.close();
    }

    /**
     * 获取请求资源的类型
     * @return
     */
    private String getFileTypeByExt(File file) {
        String name = file.getName();
        String ext = name.substring(name.lastIndexOf(".")+1);
        String type = ServletContext.typeMap.get(ext);
        return type;
    }
}
