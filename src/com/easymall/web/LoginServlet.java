package com.easymall.web;

import com.easymall.domain.User;
import com.easymall.exception.MsgException;
import com.easymall.service.UserService;
import com.easymall.utils.MD5Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

/*
  登录servlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        1.请求响应参数的乱码处理
//        request.setCharacterEncoding("utf-8");
//        response.setContentType("text/html;charset=utf-8");
//        2.获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remname = request.getParameter("remname");//记住用户名选项
        String autologin = request.getParameter("autologin");//30天自动登录
//        3.记住用户名
        if ("true".equals(remname)){
            Cookie cookie = new Cookie("remname", URLEncoder.encode(username,"utf-8"));
            cookie.setMaxAge(60*60*24*30);
            cookie.setPath(request.getContextPath()+"/");
            response.addCookie(cookie);
        }else{
//            删除cookie
            Cookie remnameCookie = new Cookie("remname", "");
            remnameCookie.setMaxAge(0);
            remnameCookie.setPath(request.getContextPath()+"/");
            response.addCookie(remnameCookie);

        }
//        30天自动登录
        if("true".equals(autologin)){
            Cookie cookie = new Cookie("autologin",URLEncoder.encode(username,"utf-8")+"#"+ MD5Utils.md5(password));
            cookie.setPath(request.getContextPath()+"/");
            cookie.setMaxAge(3600*24*30);
            response.addCookie(cookie);
        }
/*//        4.与数据库中的用户名和密码判断  能匹配登录
        Connection conn = null;
        PreparedStatement ps=null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement("select * from USER  WHERE username=? and password=?;");
            ps.setString(1,username);
            ps.setString(2,password);
            rs = ps.executeQuery();
            if (rs.next()){
//                保留登录状态
                HttpSession session = request.getSession();
//              创建user对象，user是一个javabean可以用于封装
                User user = new User();
                user.setUsername(username);//用于封装
                session.setAttribute("user",user);
            }else{
                request.setAttribute("msg","用户名或者密码不正确");
                request.getRequestDispatcher("/login.jsp").forward(request,response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(conn,ps,rs);
        }*/
        UserService userService = new UserService();
        try {
            User user=userService.LoginUser(username, MD5Utils.md5(password));
//            保存用户登录状态
           HttpSession session= request.getSession();
           session.setAttribute("user",user);
        } catch (MsgException e) {
            request.setAttribute("msg",e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        }
//        5.登录成功   跳转首页
        response.sendRedirect("http://www.easymall.com");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
