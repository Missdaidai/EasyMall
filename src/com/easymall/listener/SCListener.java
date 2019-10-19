package com.easymall.listener;

import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class SCListener implements ServletContextListener {

    // Public constructor is required by servlet spec
    public SCListener() {
    }

    public  Logger logger = Logger.getLogger(SCListener.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
       logger.info("esaymall应用启动");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("esaymall应用关闭");
    }
}
