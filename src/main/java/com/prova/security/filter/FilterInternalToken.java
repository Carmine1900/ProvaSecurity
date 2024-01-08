//package com.prova.security.filter;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.prova.model.User;
//import com.prova.security.cookie.JwtCookie;
//import com.prova.service.UserServiceImpl;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class FilterInternalToken extends OncePerRequestFilter
//{
//	
//	@Autowired
//	private JwtCookie jwtCookie;
//	
//	@Autowired
//	private UserServiceImpl userServiceImpl;
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//		try
//		{
//			String jwt = jwtCookie.getJwtFromCookies(request);
//			
//			if(jwt != null)
//			{
//				String username = jwtCookie.getUsernameFromToken(jwt);
//				
//				UserDetails userDetails = userServiceImpl.loadUserByUsername(username);
//				
//				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//				
//				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//				
//				  SecurityContextHolder.getContext().setAuthentication(authentication);
//			      
//			}
//			
//		}catch(Exception ex)
//		{
//			ex.printStackTrace();
//		}
//		
//		filterChain.doFilter(request, response);
//	}
//	
//}
