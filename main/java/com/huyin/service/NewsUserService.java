package com.huyin.service;

import com.huyin.pojo.NewsUser;

public interface NewsUserService {
    NewsUser findByUserName(String userName);

    NewsUser finByUid(int userId);

    int registUser(NewsUser newsUser);
}
