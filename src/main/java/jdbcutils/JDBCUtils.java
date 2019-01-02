package jdbcutils;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    private static Connection conn;
    private static Properties prop = null;
    private JDBCUtils() {}

    static {
        try {
            prop = new Properties();
            File file = new File("jdbc.properties");
            prop.load(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加载配置文件失败！");
        }
    }

    public static Connection getConnection(){
        try {
            String driver = prop.getProperty("driver");
            Class.forName(driver);

            String url = prop.getProperty("url");
            String name=prop.getProperty("user");
            String password=prop.getProperty("password");
            conn = DriverManager.getConnection(url,name,password );
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection conn, Statement stm, ResultSet rs){

        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                rs = null;
            }
        }

        if(stm != null){
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stm = null;
            }
        }

        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn = null;
            }
        }
    }
}
