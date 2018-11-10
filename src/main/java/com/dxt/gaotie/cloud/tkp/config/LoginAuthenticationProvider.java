package com.dxt.gaotie.cloud.tkp.config;


import com.dxt.gaotie.cloud.tkp.entity.UUser;
import com.dxt.gaotie.cloud.tkp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018-07-12.
 */
@Component
public class LoginAuthenticationProvider  implements AuthenticationProvider {

    @Autowired
    AppConfig appConfig;

    @Autowired
    UserRepository userRepository;


    public LoginAuthenticationProvider(AppConfig appConfig) {
        this.appConfig = appConfig;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new BadCredentialsException("账户或密码不能为空");
        }

        String errMsg = null;

        if(username.equals(appConfig.getSuperUsername()) ){
            if(!password.equals(appConfig.getSuperPassword()))
                errMsg = "超级管理员密码错误";
        }else{

            UUser user = userRepository.findByEmpno(username);
            if(user == null)    errMsg = "用户不存在";
            else if(!user.getPassword().equals(password))   errMsg = "密码错误";
        }

        if(errMsg != null)  throw new BadCredentialsException(errMsg);

        return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
