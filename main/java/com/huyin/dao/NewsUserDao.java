package com.huyin.dao;

import com.huyin.pojo.NewsUser;

public interface NewsUserDao {
    NewsUser findByUserName(String userName);

    NewsUser findByUid(int userId);

    int registUser(NewsUser newsUser);
}
