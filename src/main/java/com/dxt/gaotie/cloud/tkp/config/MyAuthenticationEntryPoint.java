package com.dxt.gaotie.cloud.tkp.config;

import com.alibaba.fastjson.JSONObject;
import com.llx.util.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * security拦截输出
 * Created by Administrator on 2018-08-03.
 */
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        String ajaxHeader =  request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
        if(isAjax){
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write(JSONObject.toJSONString(new Result(false, "安全控制：" + e.getMessage())));
        }else{
            response.sendRedirect("/login.html");
        }
    }
}
