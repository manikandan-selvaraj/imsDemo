package com.cognizant.ims.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureSecurity(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("customer").password("{noop}customer").roles("USER").and()
				.withUser("admin").password("{noop}admin").roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/login").permitAll()
				  .antMatchers(HttpMethod.GET, "/products/**").access("hasRole('USER')")
				  .antMatchers(HttpMethod.PUT,"/products/updateProduct").access(
				  "hasRole('ADMIN')")
				 .and().formLogin().and().httpBasic();
	}

}
