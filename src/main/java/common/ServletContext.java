package common;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServletContext {
    // 协议
    public static String protocol;
    // 端口
    public static int port;
    // 初始线程数
    public static int maxThread;
    // 请求资源根目录
    public static String webRoot;
    // 404页面
    public static String notFoundPage;
    // 响应数据类型
    public static Map<String, String> typeMap = new HashMap();
    static {
        init();
    }

    /**
     * 初始化服务器
     */
    private static void init() {
        SAXReader reader = new SAXReader();
        // 读取配置文件
        InputStream in = ServletContext.class.getResourceAsStream("../web.xml");
        try {
            // 获取配置文件dom树
            Document doc = reader.read(in);
            // 获取根元素server
            Element server = doc.getRootElement();
            // service
            Element service = server.element("service");
            // connector
            Element connector = service.element("connector");
            // 初始化参数
            protocol = connector.attributeValue("protocol");
            port = Integer.parseInt(connector.attributeValue("port"));
            maxThread = Integer.parseInt(connector.attributeValue("maxThread"));
            webRoot = service.elementText("webroot");
            notFoundPage = service.elementText("not-found-page");
            // 获取所有type
            List<Element> list = server.element("type-mapping").elements();
            for (Element e : list) {
                String ext = e.attributeValue("ext");
                String type = e.attributeValue("type");
                typeMap.put(ext,type);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
