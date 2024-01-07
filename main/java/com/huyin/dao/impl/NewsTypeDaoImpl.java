package com.huyin.dao.impl;

import com.huyin.dao.BaseDao;
import com.huyin.dao.NewsTypeDao;
import com.huyin.pojo.NewsType;

import java.util.List;

public class NewsTypeDaoImpl extends BaseDao implements NewsTypeDao {
    @Override
    public List<NewsType> findAll() {
        String sql = "select tid,tname from news_type";
        return baseQuery(NewsType.class, sql);
    }
}
