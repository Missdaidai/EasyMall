package com.easymall.listener;

import com.easymall.domain.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;

@WebListener()
public class RequestListener implements ServletRequestListener {

    // Public constructor is required by servlet spec
    public RequestListener() {
    }
    public Logger logger = Logger.getLogger(RequestListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        ServletRequest servletRequest = servletRequestEvent.getServletRequest();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String url = request.getRequestURL().toString();
//        用户ip地址
        String ip = request.getRemoteAddr();
//        访问名称
        String username = "游客";


        if (request.getSession(false)!=null&&request.getSession().getAttribute("user")!=null) {
            User user = (User) request.getSession().getAttribute("user");
            username=user.getUsername();
        }
        logger.info("用户【"+username+"】ip【"+ip+"】url【"+url+"】");
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {

    }
}
