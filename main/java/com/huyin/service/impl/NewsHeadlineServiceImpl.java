package com.huyin.service.impl;

import com.huyin.dao.NewsHeadLineDao;
import com.huyin.dao.impl.NewsHeadlineDaoImpl;
import com.huyin.pojo.NewsHeadline;
import com.huyin.pojo.vo.HeadlineDetailVo;
import com.huyin.pojo.vo.HeadlinePageVo;
import com.huyin.pojo.vo.HeadlineQueryVo;
import com.huyin.service.NewsHeadlineService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsHeadlineServiceImpl implements NewsHeadlineService {
    private NewsHeadLineDao headlineDao =new NewsHeadlineDaoImpl();
    /*
        totalPage:1,
        totalSize:1
        pageData:[
                    {
                       "hid":"1",                     // 新闻id
                        "title":"尚硅谷宣布 ... ...",   // 新闻标题
                        "type":"1",                    // 新闻所属类别编号
                        "pageViews":"40",              // 新闻浏览量
                        "pastHours":"3",              // 发布时间已过小时数
                        "publisher":"1"
                   }
                 ],
        pageNum:1,
        pageSize:1,


     */
    @Override
    public Map findPage(HeadlineQueryVo headlineQueryVo) {
        int pageNum = headlineQueryVo.getPageNum();
        int pageSize = headlineQueryVo.getPageSize();
        List<HeadlinePageVo> pageData = headlineDao.findPageList(headlineQueryVo);
        int totalSize = headlineDao.findPageCount(headlineQueryVo);

        int totalPage =totalSize % pageSize ==0 ? totalSize/pageSize: totalSize/pageSize+1;
        Map<String, Object> pageInfo= new HashMap();
        pageInfo.put("pageNum",pageNum);
        pageInfo.put("pageSize",pageSize);
        pageInfo.put("totalSize",totalSize);
        pageInfo.put("totalPage",totalPage);
        pageInfo.put("pageData",pageData);
        return pageInfo;
    }

    @Override
    public HeadlineDetailVo findHeadLineDeatilsById(int hid) {
        headlineDao.increasePageViews(hid);
        return headlineDao.findHeadLineDeatilsById(hid);
    }

    @Override
    public int addNewsHeadLine(NewsHeadline newsHeadline) {
        return headlineDao.addNewsHeadLine(newsHeadline);
    }

    @Override
    public NewsHeadline findHeadlineByHid(int hid) {
        return headlineDao.findHeadlineByHid(hid);
    }

    @Override
    public int updateNewsHeadLine(NewsHeadline newsHeadline) {
        return headlineDao.updateNewsHeadLine(newsHeadline);
    }

    @Override
    public int removeByHid(int hid) {
        return headlineDao.removeByHid(hid);
    }
}
