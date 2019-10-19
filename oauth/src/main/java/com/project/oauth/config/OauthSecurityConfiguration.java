package com.project.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class OauthSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/resources/**");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/getEmployeesList")
		.hasAnyRole("ADMIN").anyRequest().authenticated().and().formLogin()
        .permitAll().and().logout().permitAll();

		http.csrf().disable();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder authMgr) throws Exception{
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		authMgr.inMemoryAuthentication().withUser("admin").password(encoder.encode("admin"))
        .authorities("ROLE_ADMIN");
	}
}
