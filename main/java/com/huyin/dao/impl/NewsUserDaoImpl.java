package com.huyin.dao.impl;

import com.huyin.dao.BaseDao;
import com.huyin.dao.NewsUserDao;
import com.huyin.pojo.NewsUser;

import java.util.List;

public class NewsUserDaoImpl extends BaseDao implements NewsUserDao {
    @Override
    public NewsUser findByUserName(String userName) {
        String sql = "select uid,nick_name nickName,username,user_pwd userPwd from news_user where username = ?";
        List<NewsUser> newsUserList = baseQuery(NewsUser.class, sql, userName);
        // 如果找到,返回集合中的第一个数据(其实就一个)
        if (newsUserList != null && newsUserList.size() > 0){
            return newsUserList.get(0);
        }
        return null;
    }

    @Override
    public NewsUser findByUid(int userId) {
        String sql = "select uid,username,user_pwd userPwd,nick_name nickName from news_user where uid = ?";
        List<NewsUser> newsUserList = baseQuery(NewsUser.class, sql, userId);
        if (newsUserList != null && newsUserList.size() > 0){
            return newsUserList.get(0);
        }
        return null;
    }

    @Override
    public int registUser(NewsUser newsUser) {
        String sql = "insert into news_user values(Default,?,?,?)";
        return baseUpdate(sql, newsUser.getUsername(), newsUser.getUserPwd(), newsUser.getNickName());
    }
}
