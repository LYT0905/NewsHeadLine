package com.huyin.filters;


import com.huyin.common.Result;
import com.huyin.common.ResultCodeEnum;
import com.huyin.util.JwtHelper;
import com.huyin.util.WebUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/headline/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("token");
        boolean flag = false;
        if (token != null){
            boolean isExpiration = JwtHelper.isExpiration(token);
            if (!isExpiration){
                flag = true;
            }
        }
        if (flag){
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            WebUtil.writeJson((HttpServletResponse) servletResponse, Result.build(null, ResultCodeEnum.NOTLOGIN));
        }
    }
}
