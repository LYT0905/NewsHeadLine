package com.huyin.controller;

import com.huyin.common.Result;
import com.huyin.common.ResultCodeEnum;
import com.huyin.pojo.NewsUser;
import com.huyin.service.NewsUserService;
import com.huyin.service.impl.NewsUserServiceImpl;
import com.huyin.util.JwtHelper;
import com.huyin.util.MD5Util;
import com.huyin.util.WebUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet("/user/*")
public class NewsUserController extends BaseController{
    private NewsUserService newsUserService = new NewsUserServiceImpl();

    public void login(HttpServletRequest request, HttpServletResponse response){
        /**
         * 登录验证
         * @param req
         * @param resp
         * @throws ServletException
         * @throws IOException
         */
        NewsUser newsUser = WebUtil.readJson(request, NewsUser.class);
        Result result = null;
        NewsUser loginUser = newsUserService.findByUserName(newsUser.getUsername());
        if (null != loginUser){
            // 判断密码
            if (loginUser.getUserPwd().equals(MD5Util.encrypt(newsUser.getUserPwd()))){
                // 密码正确
                Map<String, Object> data = new HashMap<>();
                // 生成口令
                String  token = JwtHelper.createToken(loginUser.getUid().longValue());
                data.put("token", token);
                result = Result.ok(data);
            }else {
                result = Result.build(null, ResultCodeEnum.PASSWORD_ERROR);
            }
        }else {
            result = Result.build(null, ResultCodeEnum.USERNAME_ERROR);
        }
        WebUtil.writeJson(response, result);
    }

    public void getUserInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("token");
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
        if (token != null){
            if (!JwtHelper.isExpiration(token)){
                int userId = JwtHelper.getUserId(token).intValue();
                NewsUser newsUser = newsUserService.finByUid(userId);
                newsUser.setUserPwd("");
                Map data = new HashMap<>();
                data.put("loginUser", newsUser);
                result = Result.ok(data);
            }
        }
        WebUtil.writeJson(resp, result);
    }


    public void checkUserName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        NewsUser newsUser = newsUserService.findByUserName(username);
        Result result = null;
        if (newsUser != null){
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }else {
            result = Result.ok(null);
        }
        WebUtil.writeJson(resp, result);
    }

    public void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NewsUser newsUser = WebUtil.readJson(req, NewsUser.class);
        NewsUser newsUser1 = newsUserService.findByUserName(newsUser.getUsername());
        Result result = null;
        if (newsUser1 != null){
            result = Result.build(null, ResultCodeEnum.USERNAME_USED);
        }else {
            newsUserService.registUser(newsUser);
            result = Result.ok(null);
        }
        WebUtil.writeJson(resp, result);
    }


    public void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("token");
        Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
        if (!JwtHelper.isExpiration(token)){
            result = Result.ok(null);
        }
        WebUtil.writeJson(resp, result);
    }
}