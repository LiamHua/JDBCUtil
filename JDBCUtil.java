package pers.liam.DB;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {

    private static String driver;
    private static String dbURL;
    private static String username;
    private static String password;

    //静态语句块(自动执行）
    static {
        //加载资源文件
        InputStream resource = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
        Properties p = new Properties();
        try {
            p.load(resource);
        } catch (IOException e) {
            System.out.println("加载流文件失败！");
            e.printStackTrace();
        }

        driver = p.getProperty("driver");
        dbURL = p.getProperty("dbURL");
        username = p.getProperty("username");
        password = p.getProperty("password");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("加载驱动失败！");
            e.printStackTrace();
        }
    }

    public static Connection get_conn() {
        try {
            return DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException e) {
            System.out.println("连接数据库失败！");
            e.printStackTrace();
            return null;
        }
    }

    public static void close(Connection conn,PreparedStatement pstmt,ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
                resultSet = null;
            }

            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }

            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
