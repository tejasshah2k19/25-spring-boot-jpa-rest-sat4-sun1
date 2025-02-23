package com.filter;

import java.awt.image.RescaleOp;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.entity.UserEntity;
import com.repository.UserRepository;
import com.util.JwtUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionCheckFilter implements Filter {

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtUtil jwt;
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// login?
		// not login?
		// private
		// public
		// how to check which url is access by user?
		HttpServletRequest req = (HttpServletRequest) (request);
		HttpServletResponse res = (HttpServletResponse)(response);
		String url = req.getRequestURL().toString();
		String uri = req.getRequestURI();

		String token = req.getHeader("Authorization");
		

		System.out.println("SessionCheckFilter");
		System.out.println(url);
		System.out.println(uri);
		System.out.println(token);

		if (uri.startsWith("/public/") || uri.contains("swag") || uri.contains("/v3/api-docs")) {
			System.out.println("no need to check for login : public ");
			chain.doFilter(request, response);// go ahead
		} else {
			// private
			// need to check login !!
			System.out.println("check for login");

			if (token == null) {
				// go back
				System.out.println("token is null");
				res.setStatus(401);
				
			} else {
			
		
				String authToken = token.substring(7);
				String email = jwt.extractUsername(authToken);
				
				
				Optional<UserEntity> op = userRepository.findByEmail(email);
				
				if (op.isEmpty()) {
					// go back
					System.out.println("invalid token : "+email);
					res.setStatus(401);
					

				} else {
					UserEntity user = op.get();
					if(user.getRole() == null) {
						System.out.println("USER ROLE NULL "+email);
						res.setStatus(403);
					}
					else if( uri.startsWith("/admin/") && user.getRole().equals("ADMIN")) {
						//url access admin
						chain.doFilter(request, response);// go ahead
						
					}else if(uri.startsWith("/user/") && user.getRole().equals("USER")) {
						//url access user 
						chain.doFilter(request, response);// go ahead

					}else{
						System.out.println("USER ROLE is =>  "+user.getRole()+" For "+email);

						res.setStatus(403);
					}
				}
			}
//jwt
		}
	}
}
