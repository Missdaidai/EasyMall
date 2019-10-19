package com.easymall.filter;

import com.easymall.domain.User;
import com.easymall.exception.MsgException;
import com.easymall.service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLDecoder;

@WebFilter(filterName = "LoginFilter",value = "/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        判断是否存在登录状态，未登录状态在完成登录
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
//        从session中读取user域属性
        session.getAttribute("user");
        Cookie autologin = null;
        if (session.getAttribute("user")==null){
//        拦截请求，从请求中取出cookie信息，完成登录
            Cookie[] cookies = request.getCookies();
            if (cookies!=null) {
                for (Cookie c : cookies) {
                    if ("autologin".equals(c.getName())){
                        autologin = c;
                    }
                }
            }
//            在自动 登录cookie尊在的情况下，在完成登录
            if (autologin != null){
                String value = autologin.getValue();
                String[] arr = value.split("#");
                String username = URLDecoder.decode(arr[0],"utf-8");
                String password = arr[1];
//                在userService提供了登录功能的实现
                UserService userService = new UserService();
                try {
                    User user = userService.LoginUser(username, password);
                    session.setAttribute("user",user);
                }catch (MsgException e){

                }

            }
        }
        chain.doFilter(request, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
