package com.huyin.service.impl;

import com.huyin.dao.NewsUserDao;
import com.huyin.dao.impl.NewsUserDaoImpl;
import com.huyin.pojo.NewsUser;
import com.huyin.service.NewsUserService;
import com.huyin.util.JwtHelper;
import com.huyin.util.MD5Util;

public class NewsUserServiceImpl implements NewsUserService {
    private NewsUserDao userDao = new NewsUserDaoImpl();

    @Override
    public NewsUser findByUserName(String userName) {
        return userDao.findByUserName(userName);
    }

    @Override
    public NewsUser finByUid(int userId) {
        return userDao.findByUid(userId);
    }

    @Override
    public int registUser(NewsUser newsUser) {
        // 密码明文转密文
        newsUser.setUserPwd(MD5Util.encrypt(newsUser.getUserPwd()));
        return userDao.registUser(newsUser);
    }
}
