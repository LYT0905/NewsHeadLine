package com.huyin.controller;

import com.huyin.common.Result;
import com.huyin.pojo.NewsType;
import com.huyin.pojo.vo.HeadlineDetailVo;
import com.huyin.pojo.vo.HeadlineQueryVo;
import com.huyin.service.NewsHeadlineService;
import com.huyin.service.NewsTypeService;
import com.huyin.service.impl.NewsHeadlineServiceImpl;
import com.huyin.service.impl.NewsTypeServiceImpl;
import com.huyin.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/portal/*")
public class PortalController extends BaseController{
    private NewsHeadlineService headlineService = new NewsHeadlineServiceImpl();
    private NewsTypeService typeService = new NewsTypeServiceImpl();

    public void findAllTypes(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<NewsType> newsTypeList = typeService.findAll();
        WebUtil.writeJson(response, Result.ok(newsTypeList));
    }

    public void findNewsPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 接收请求中的参数
        HeadlineQueryVo headlineQueryVo = WebUtil.readJson(req, HeadlineQueryVo.class);

        Map<String, Object> pageInfo =headlineService.findPage(headlineQueryVo);
        Map data =new HashMap();
        data.put("pageInfo",pageInfo);

        // 将分页查询的结果转换成json响应给客户端
        WebUtil.writeJson(resp,Result.ok(data));
    }

    public void showHeadlineDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int hid = Integer.parseInt(req.getParameter("hid"));
        HeadlineDetailVo headlineDetailVo = headlineService.findHeadLineDeatilsById(hid);
        Map<String, Object> data = new HashMap<>();
        data.put("headline", headlineDetailVo);
        WebUtil.writeJson(resp, Result.ok(data));
    }
}
