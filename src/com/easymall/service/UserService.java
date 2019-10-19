package com.easymall.service;

import com.easymall.dao.UserDao;
import com.easymall.domain.User;
import com.easymall.exception.MsgException;

public class UserService {
//    service需要访问数据库，所以创建dao
    UserDao userDao = new UserDao();
    /**
     * 这是一个用户注册
     * @param user  用户信息对象
     */
    public void registUser(User user) {
//        用户名是否存在
        boolean findUser=userDao.findUserByUserName(user.getUsername());
        if (findUser){
//            用户名存在给出提示
//            自定义异常
            throw new MsgException("用户名已存在");
        }else {
            userDao.addUser(user);
        }
    }

    /**\
     * 登录功能
     * @param username
     * @param password
     * @return  用户
     */
    public User LoginUser(String username, String password) {
//        成功、失败
        User user=userDao.findUserByUserNameAndPassword(username,password);
        if (user != null){
            return user;
        }else{
            throw new MsgException("用户名或密码不正确");
        }
    }

    public boolean CheckUserName(String username) {
        //        用户名是否存在
        boolean findUser=userDao.findUserByUserName(username);
        if (findUser){
//            用户名存在给出提示
//            自定义异常
           return false;
        }else {
            return true;
        }
    }
}
