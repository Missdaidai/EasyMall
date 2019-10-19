package com.easymall.web;


import com.easymall.exception.MsgException;
import com.easymall.service.UserService;
import com.easymall.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/AjaxCheckUsername")
public class AjaxCheckUsername extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
//        1.获取请求参数username
        String username = request.getParameter("username");
//        2.在数据库中查询用户名是否存在
        /*Connection conn = null;
        PreparedStatement ps= null;
        ResultSet rs=null;
        try {
            conn = (Connection) JDBCUtils.getConnection();
            ps = conn.prepareStatement("select * from USER where username=?;");
            ps.setString(1,username);
            rs= ps.executeQuery();
            if (rs.next()){
                response.getWriter().write("用户名已存在");
            }else{
                response.getWriter().write("用户名可以使用");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn,ps,rs);
        }*/
        UserService userService= new UserService();
            Boolean b =userService.CheckUserName(username);
            if (b){
//                request.setAttribute("msg","用户名可以使用");
                response.getWriter().write("用户名可以使用");
            }else{
//                request.setAttribute("msg","用户名已存在");
                response.getWriter().write("用户名已存在");
            }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
