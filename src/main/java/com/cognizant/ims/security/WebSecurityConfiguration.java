package com.cognizant.ims.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String BASE_PRODUCT_PATH = "/products/**";
	private static final String USER_ROLE = "USER";
	private static final String ADMIN_ROLE = "ADMIN";

	@Autowired
	public void configureSecurity(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication().withUser("customer").password("{noop}customer").roles(USER_ROLE).and()
				.withUser("admin").password("{noop}admin").roles(USER_ROLE, ADMIN_ROLE);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, BASE_PRODUCT_PATH, "/purchase/addProduct").hasRole(ADMIN_ROLE)
				.antMatchers(HttpMethod.PUT, BASE_PRODUCT_PATH).hasRole(ADMIN_ROLE)
				.antMatchers(HttpMethod.PATCH, BASE_PRODUCT_PATH, "/sale/**").hasRole(ADMIN_ROLE)
				.antMatchers(HttpMethod.DELETE, BASE_PRODUCT_PATH).hasRole(ADMIN_ROLE).anyRequest()
				.hasAnyRole(USER_ROLE, ADMIN_ROLE).and().formLogin().and().httpBasic();
	}

}
