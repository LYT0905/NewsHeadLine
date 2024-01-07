package com.huyin.service;

import com.huyin.pojo.NewsHeadline;
import com.huyin.pojo.vo.HeadlineDetailVo;
import com.huyin.pojo.vo.HeadlineQueryVo;

import java.util.Map;

public interface NewsHeadlineService {
    /**
     * 分页查询头条新闻方法
     * @param headLineQueryVo
     * @return
     */
    Map<String, Object> findPage(HeadlineQueryVo headLineQueryVo);

    HeadlineDetailVo findHeadLineDeatilsById(int hid);

    int addNewsHeadLine(NewsHeadline newsHeadline);

    NewsHeadline findHeadlineByHid(int hid);

    int updateNewsHeadLine(NewsHeadline newsHeadline);

    int removeByHid(int hid);
}
