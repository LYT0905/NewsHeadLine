package com.huyin.service.impl;

import com.huyin.dao.NewsTypeDao;
import com.huyin.dao.impl.NewsTypeDaoImpl;
import com.huyin.pojo.NewsType;
import com.huyin.service.NewsTypeService;

import java.util.List;

public class NewsTypeServiceImpl implements NewsTypeService {
    private NewsTypeDao newsTypeDao = new NewsTypeDaoImpl();

    @Override
    public List<NewsType> findAll() {
        return newsTypeDao.findAll();
    }
}
