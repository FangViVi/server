package http;

import common.HttpContext;
import common.ServletContext;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {
    private String protocol;
    private int status;
    private String contentType;
    private int contentLength;
    // 输出流
    private OutputStream outputStream;
    // 状态码描述信息
    private Map<Integer, String> descMap = new HashMap();
    // 初始化状态码描述信息
    public HttpResponse(OutputStream outputStream) {
        descMap.put(HttpContext.CODE_OK, HttpContext.DESC_OK);
        descMap.put(HttpContext.CODE_NOT_FOUND, HttpContext.DESC_NOT_FOUND);
        descMap.put(HttpContext.CODE_ERROR, HttpContext.DESC_ERROR);
        this.outputStream = outputStream;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    private boolean isSend;
    public OutputStream getOutputStream() {
        if(!isSend){
            PrintStream ps = new PrintStream(outputStream);
            ps.println(protocol+" "+status+" "+descMap.get(status));
            ps.println(contentType);
            ps.println(contentLength);
            ps.println();
            isSend = true;
        }
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
