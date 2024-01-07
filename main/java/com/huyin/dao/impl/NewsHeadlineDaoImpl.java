package com.huyin.dao.impl;

import com.huyin.dao.BaseDao;
import com.huyin.dao.NewsHeadLineDao;
import com.huyin.pojo.NewsHeadline;
import com.huyin.pojo.vo.HeadlineDetailVo;
import com.huyin.pojo.vo.HeadlinePageVo;
import com.huyin.pojo.vo.HeadlineQueryVo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NewsHeadlineDaoImpl extends BaseDao implements NewsHeadLineDao {
    /*
     *
     *       private Integer hid;
             private String title;
             private Integer type;
             private Integer pageViews;
             private Long pastHours;
             private Integer publisher;
             *
             *
             *
             *       private String keyWords;
                     private Integer type ;
                     private Integer pageNum;
                     private Integer pageSize;
             *
             * */
    @Override
    public List<HeadlinePageVo> findPageList(HeadlineQueryVo headlineQueryVo) {
        List<Object> params = new ArrayList<>();

        String sql  = """
                select 
                    hid ,
                    title,
                    type,
                    page_views pageViews,
                    TIMESTAMPDIFF(HOUR,create_time,now()) pastHours ,
                    publisher 
                from 
                    news_headline
                where 
                    is_deleted = 0
                    
                """;
        if (headlineQueryVo.getType() != 0 ){
            sql = sql.concat(" and type = ? ");
            params.add(headlineQueryVo.getType());

        }
        System.out.println(headlineQueryVo.getKeyWords());
        if(headlineQueryVo.getKeyWords() != null && !headlineQueryVo.getKeyWords().equals("")){
            sql = sql.concat(" and title like ? ");
            params.add("%"+headlineQueryVo.getKeyWords()+"%");
        }

        sql=sql.concat(" order by pastHours ASC , page_views DESC ");
        sql=sql.concat(" limit ? , ? ");
        params.add((headlineQueryVo.getPageNum()-1)*headlineQueryVo.getPageSize());
        params.add(headlineQueryVo.getPageSize());
        return baseQuery(HeadlinePageVo.class,sql,params.toArray());
    }

    @Override
    public int findPageCount(HeadlineQueryVo headlineQueryVo) {
        List<Object> params = new ArrayList<>();

        String sql  = """
                select 
                    count(1)
                from 
                    news_headline
                where 
                    is_deleted = 0
                    
                """;
        if (headlineQueryVo.getType() != 0 ){
            sql = sql.concat(" and type = ? ");
            params.add(headlineQueryVo.getType());

        }
        if(headlineQueryVo.getKeyWords() != null && !headlineQueryVo.getKeyWords().equals("")){
            sql = sql.concat(" and title like ? ");
            params.add("%"+headlineQueryVo.getKeyWords()+"%");
        }
        Long count = baseQueryObject(Long.class, sql, params.toArray());
        return count.intValue();
    }

    @Override
    public int increasePageViews(int hid) {
        String sql = "update news_headline set page_views = page_views + 1 where hid = ?";
        return baseUpdate(sql, hid);
    }

    @Override
    public HeadlineDetailVo findHeadLineDeatilsById(int hid) {
        String sql = """
                select hid,title,article,type, tname typeName ,page_views pageViews,
                TIMESTAMPDIFF(HOUR,create_time,NOW()) pastHours,publisher,
                nick_name author from news_headline h left join  news_type t 
                on h.type = t.tid left join 
                news_user u  on h.publisher = u.uid where hid = ?
                """;
        List<HeadlineDetailVo> headlineDetailVos = baseQuery(HeadlineDetailVo.class, sql, hid);
        if (headlineDetailVos != null && headlineDetailVos.size() > 0){
            return headlineDetailVos.get(0);
        }
        return null;
    }

    @Override
    public int addNewsHeadLine(NewsHeadline newsHeadline) {
        String sql = """
                insert into news_headline values(DEFAULT,?,?,?,?,0,NOW(),NOW(),0)
                """;
        return baseUpdate(sql, newsHeadline.getTitle(), newsHeadline.getArticle(), newsHeadline.getType(), newsHeadline.getPublisher());
    }

    @Override
    public NewsHeadline findHeadlineByHid(int hid) {
        String sql = """
                select hid,title,article,type,publisher,
                page_views pageViews from news_headline where hid =?
                """;
        List<NewsHeadline> newsHeadline = baseQuery(NewsHeadline.class, sql, hid);
        if (newsHeadline != null && newsHeadline.size() > 0){
            return newsHeadline.get(0);
        }
        return null;
    }

    @Override
    public int updateNewsHeadLine(NewsHeadline newsHeadline) {
        String sql = """
                update news_headline set title = ? ,article = ?,type = ?, update_time = now() where hid = ?
                """;
        return baseUpdate(sql, newsHeadline.getTitle(), newsHeadline.getArticle(), newsHeadline.getType(), newsHeadline.getHid());
    }

    @Override
    public int removeByHid(int hid) {
        String sql = """
                update news_headline set is_deleted = 1, update_time = now() where hid = ?
                """;
        return baseUpdate(sql, hid);
    }
}
