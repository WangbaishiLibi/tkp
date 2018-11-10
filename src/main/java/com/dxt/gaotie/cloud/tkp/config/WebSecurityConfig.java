package com.dxt.gaotie.cloud.tkp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


/**
 * Security配置
 *  三种方法级权限控制 
 * @author admin 
 * 1.securedEnabled: Spring Security’s native annotation 
 * 2.jsr250Enabled: standards-based and allow simple role-based constraints 
 * 3.prePostEnabled: expression-based 
 */
@Configuration("webSecurityConfigPM")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true) 
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AppConfig appConfig;

//	@Autowired
//	MyUserDetailService myUserDetailService;

	
	@Bean
	public SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler() {
		return new AuthenticationSuccessHandler();
	}
	
	
	@Bean
	public SimpleUrlAuthenticationFailureHandler authenticationFailureHandler() {
		return new AuthenticationFailureHandler();
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(new LoginAuthenticationProvider(appConfig));
	}



	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/res/**", "/t/**").permitAll()
				.anyRequest().authenticated()
//				.anyRequest().access("@securityResourceDecisionHandler.auth(authentication, request)")
				.and()
				.headers().frameOptions().disable()
				.and()
//				.exceptionHandling().accessDeniedPage()
				.formLogin()
				.failureHandler(authenticationFailureHandler())
				.successHandler(authenticationSuccessHandler())
				.loginProcessingUrl("/login.do")
				.loginPage("/login.html").permitAll().usernameParameter("username").passwordParameter("password")
				.and().logout().logoutUrl("/logout")
				.invalidateHttpSession(true).clearAuthentication(true).permitAll();
		http.exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
