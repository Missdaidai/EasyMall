package com.easymall.dao;

import com.easymall.domain.User;
import com.easymall.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {
    /**
     * 插入一条新的用户数据
     * @param user  用户信息对象
     */
    public void addUser(User user) {
        Connection conn=null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement("insert into user values (null,?,?,?,?)");
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getNickname());
            ps.setString(4,user.getEmail());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn,ps,null);
        }
    }

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return   用户名是否存在的布尔值
     */
    public boolean findUserByUserName(String username) {
        Connection conn=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps=conn.prepareStatement("select * from USER WHERE username=?;");
            ps.setString(1,username);
            rs = ps.executeQuery();
            if (rs.next()){
                return true;//查询到用户
            }else{
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            JDBCUtils.close(conn,ps,rs);
        }
    }

    public User findUserByUserNameAndPassword(String username, String password) {
        Connection conn=null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps=conn.prepareStatement("select * from USER WHERE username=? AND  password=?;");
            ps.setString(1,username);
            ps.setString(2,password);
            rs = ps.executeQuery();
            if (rs.next()){//查询到用户
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setNickname(rs.getString("nickname"));
                user.setEmail(rs.getString("email"));
                return user;
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            JDBCUtils.close(conn,ps,rs);
        }
    }
}
