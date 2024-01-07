package com.huyin.dao;

import com.huyin.pojo.NewsHeadline;
import com.huyin.pojo.vo.HeadlineDetailVo;
import com.huyin.pojo.vo.HeadlinePageVo;
import com.huyin.pojo.vo.HeadlineQueryVo;

import java.util.List;

public interface NewsHeadLineDao {
    /**
     *
     * @param headlineQueryVo
     * @return
     */
    List<HeadlinePageVo> findPageList(HeadlineQueryVo headlineQueryVo);

    /**
     *
     * @param headlineQueryVo
     * @return
     */
    int findPageCount(HeadlineQueryVo headlineQueryVo);

    int increasePageViews(int hid);

    HeadlineDetailVo findHeadLineDeatilsById(int hid);

    int addNewsHeadLine(NewsHeadline newsHeadline);

    NewsHeadline findHeadlineByHid(int hid);

    int updateNewsHeadLine(NewsHeadline newsHeadline);

    int removeByHid(int hid);
}
