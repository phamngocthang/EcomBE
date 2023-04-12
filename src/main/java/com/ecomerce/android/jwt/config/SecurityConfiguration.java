package com.ecomerce.android.jwt.config;

import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecomerce.android.jwt.service.JwtAuthenticationTokenFilter;




@Configuration
@EnableWebSecurity
public class SecurityConfiguration   {
	
	@Autowired
	private  JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
	
	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Disable crsf cho đường dẫn /rest/**
//		http.csrf().ignoringRequestMatchers("/api/user/**");
//		http.authorizeHttpRequests().requestMatchers("/api/user/login**").permitAll();
//		http.authorizeHttpRequests().requestMatchers("/show-id").permitAll();
//		http.authorizeHttpRequests().requestMatchers("/api/product/**").permitAll();
//		http.authorizeHttpRequests().requestMatchers("/api/user/SignUp**").permitAll();
//		//http.authorizeHttpRequests().requestMatchers("/api/brand**").permitAll();
//		http.authorizeHttpRequests().requestMatchers("/api/user/users/**").permitAll();
//		http.authorizeHttpRequests().requestMatchers("/api/user/SignUp/Verify**").permitAll();
//		http.httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
//				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests()
//				.requestMatchers(HttpMethod.GET, "/api/user/users**").hasAnyRole("ADMIN")
//				.requestMatchers(HttpMethod.GET, "/api/user/**").hasRole("ADMIN")
//				.requestMatchers(HttpMethod.DELETE, "/api/user/**").hasRole("ADMIN").and()
//				.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
//				.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
//		return http.build();
		http.csrf().ignoringRequestMatchers("/api/**");
		http.authorizeHttpRequests().requestMatchers("/api/user/SignUp**").permitAll();
		http.authorizeHttpRequests().requestMatchers("/api/user/login**").permitAll();
		http.authorizeHttpRequests().requestMatchers("/api/user/SignUp/Verify**").permitAll();
		http.httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeHttpRequests()
				.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN","USER")
				.requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("ADMIN","USER")
				.requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("ADMIN","USER")
				.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN").and()
				.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
		return http.build();
	}
}
