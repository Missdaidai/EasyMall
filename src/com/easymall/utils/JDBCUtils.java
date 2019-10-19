package com.easymall.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


//工厂类
public class JDBCUtils {
    //不允许创建对象
    private JDBCUtils() {

    }

    //能够读取properties配置文件的类型
    private static ComboPooledDataSource source =new ComboPooledDataSource();

  /*  static {
        //加载配置信息到prop对象身上
        try {
            prop.load(new FileInputStream(new File(
                    //JDBCUtils.class获取类的字节码对象
                    //getClassLoader()获取类加载器
                    //getResource("src目录中的任意文件名")获取当前工程的src目录
                    //getPath()将uri类型变为String类型
                    JDBCUtils.class.getClassLoader().getResource("conf.properties").getPath()
            )));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    //通过类调用方法
    //创建连接
    public static Connection getConnection() throws Exception {
        return source.getConnection();
    }

    //关闭资源
    public static void close(Connection conn, Statement stat, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                rs = null;
            }
        }

        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                stat = null;
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                conn = null;
            }
        }

    }
}
