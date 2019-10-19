package com.easymall.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter",value = "/*")
public class EncodingFilter implements Filter {
    boolean use_encode=false;
    String encode=null;
    public void destroy() {
    }
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        请求乱码处理----装饰者模式
        HttpServletRequest request =use_encode? new MyHttpServletRequest((HttpServletRequest) req,encode): (HttpServletRequest) req;
//        响应乱码处理
        resp.setContentType("text/html;charset="+encode);
        chain.doFilter(request, resp);

    }

    public void init(FilterConfig config) throws ServletException {
        use_encode = Boolean.parseBoolean(config.getServletContext().getInitParameter("use_encode"));
        encode = config.getServletContext().getInitParameter("encode");
    }

}
