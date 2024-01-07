package com.huyin.controller;

import com.huyin.common.Result;
import com.huyin.pojo.NewsHeadline;
import com.huyin.service.NewsHeadlineService;
import com.huyin.service.impl.NewsHeadlineServiceImpl;
import com.huyin.util.JwtHelper;
import com.huyin.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/headline/*")
public class NewsHeadlineController extends BaseController {
    private NewsHeadlineService newsHeadlineService = new NewsHeadlineServiceImpl();

    public void publish(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsHeadline newsHeadline = WebUtil.readJson(req, NewsHeadline.class);
        String token = req.getHeader("token");
        Long userId = JwtHelper.getUserId(token);
        newsHeadline.setPublisher(userId.intValue());
        // 将新闻存入数据库
        newsHeadlineService.addNewsHeadLine(newsHeadline);
        WebUtil.writeJson(resp, Result.ok(null));
    }


    public void findHeadlineByHid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int hid = Integer.parseInt(req.getParameter("hid"));
        NewsHeadline newsHeadline = newsHeadlineService.findHeadlineByHid(hid);
        Map<String, Object> data = new HashMap<>();
        data.put("headline", newsHeadline);
        WebUtil.writeJson(resp, Result.ok(data));
    }

    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsHeadline newsHeadline = WebUtil.readJson(req, NewsHeadline.class);
        newsHeadlineService.updateNewsHeadLine(newsHeadline);
        WebUtil.writeJson(resp, Result.ok(null));
    }

    public void removeByHid(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int hid = Integer.parseInt(req.getParameter("hid"));
        newsHeadlineService.removeByHid(hid);
        WebUtil.writeJson(resp, Result.ok(null));
    }
}
