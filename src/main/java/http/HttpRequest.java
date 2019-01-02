package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    private String method;
    private String uri;
    private String protocol;

    public HttpRequest(InputStream in) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(in));
        try {
            // 请求行
            String line = reader.readLine();
            if(line !=null && line.length()>0){
                System.out.println("收到一个请求");
                // 拆分请求行
                String[] data = line.split(" ");
                method = data[0];
                protocol = data[2];
                uri = data[1];
                String key = "/";
                // 默认请求资源
                if(key.equals(uri)){
                    uri = "/index.html";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
