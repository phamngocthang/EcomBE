package com.ecomerce.android.jwt.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ecomerce.android.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter  {
	private final static String TOKEN_HEADER = "authorization";
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@Override
	protected void doFilterInternal( @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authToken = httpRequest.getHeader(TOKEN_HEADER);
		if (jwtService.validateTokenLogin(authToken)) {
			String username = jwtService.getUsernameFromToken(authToken);
			Optional<com.ecomerce.android.model.User> user = userService.loadUserByEmail(username);
			if (user != null) {
				boolean enabled = true;
				boolean accountNonExpired = true;
				boolean credentialsNonExpired = true;
				boolean accountNonLocked = true;
				UserDetails userDetail = new User(username, user.get().getPassword(), enabled, accountNonExpired,
						credentialsNonExpired, accountNonLocked, user.get().getAuthorities());
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
						null, userDetail.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
		filterChain.doFilter(request, response);
	}
	
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		String authToken = httpRequest.getHeader(TOKEN_HEADER);
//		if (jwtService.validateTokenLogin(authToken)) {
//			String username = jwtService.getUsernameFromToken(authToken);
//			Optional<project.ute.model.User> user = userService.loadUserByEmail(username);
//			if (user != null) {
//				boolean enabled = true;
//				boolean accountNonExpired = true;
//				boolean credentialsNonExpired = true;
//				boolean accountNonLocked = true;
//				UserDetails userDetail = new User(username, user.get().getPassword(), enabled, accountNonExpired,
//						credentialsNonExpired, accountNonLocked, user.get().getAuthorities());
//				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
//						null, userDetail.getAuthorities());
//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
//				SecurityContextHolder.getContext().setAuthentication(authentication);
//			}
//		}
//		chain.doFilter(request, response);
//	}
}
