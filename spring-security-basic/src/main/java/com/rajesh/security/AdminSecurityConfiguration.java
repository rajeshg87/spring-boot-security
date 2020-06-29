package com.rajesh.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@Configuration
@Order(2)
public class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private DigestAuthenticationEntryPoint getDigestEntryPoint() {
		DigestAuthenticationEntryPoint digestEntryPoint=new DigestAuthenticationEntryPoint();
		digestEntryPoint.setRealmName("admin-digest-realm");
		digestEntryPoint.setKey("admindigestkey");
		return digestEntryPoint;
	}
	
	private DigestAuthenticationFilter getDigestAuthenticationFilter() throws Exception {
		DigestAuthenticationFilter digestAuthenticationFilter=new DigestAuthenticationFilter();
		digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
		digestAuthenticationFilter.setAuthenticationEntryPoint(getDigestEntryPoint());
		return digestAuthenticationFilter;
	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("digestrajesh")
			.password(passwordEncoder().encode("digestsecret"))
			.roles("USER")
		.and()
			.withUser("admin")
			.password(passwordEncoder().encode("adminsecret"))
			.roles("ADMIN");
		
	}
	
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/admin/**")
			.addFilter(getDigestAuthenticationFilter())
			.exceptionHandling()
			.authenticationEntryPoint(getDigestEntryPoint())
			.and()
			.authorizeRequests()
			.antMatchers("/admin/**")
			.hasAnyRole("ADMIN", "USER");
		
	}
	
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		//return new BCryptPasswordEncoder();
		
		return NoOpPasswordEncoder.getInstance();
	}

}
