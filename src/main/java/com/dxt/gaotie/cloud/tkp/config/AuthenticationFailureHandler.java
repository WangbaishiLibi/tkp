package com.dxt.gaotie.cloud.tkp.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.alibaba.fastjson.JSONObject;

public class AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
		System.out.println("login failure==========>" + exception.getClass());
		isAjax = true;
		if (isAjax) {   
			JSONObject returnObj = new JSONObject();
			returnObj.put("res", false);
			returnObj.put("msg", exception.getMessage());
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(returnObj.toString());
			response.getWriter().flush();
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

}
