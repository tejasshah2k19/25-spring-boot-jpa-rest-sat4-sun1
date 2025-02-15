package com.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.entity.UserEntity;
import com.repository.UserRepository;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class SessionCheckFilter implements Filter {

	@Autowired
	UserRepository userRepository;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// login?
		// not login?
		// private
		// public
		// how to check which url is access by user?
		HttpServletRequest req = (HttpServletRequest) (request);
		String url = req.getRequestURL().toString();
		String uri = req.getRequestURI();

		String token = req.getHeader("token");

		System.out.println("SessionCheckFilter");
		System.out.println(url);
		System.out.println(uri);
		System.out.println(token);

		if (uri.startsWith("/public/")) {
			System.out.println("no need to check for login : public ");
			chain.doFilter(request, response);// go ahead
		} else {
			// private
			// need to check login !!
			System.out.println("check for login");

			if (token == null) {
				// go back
				System.out.println("token is null");
			} else {
				Optional<UserEntity> op = userRepository.findByToken(token);
				if (op.isEmpty()) {
					// go back
					System.out.println("invalid token");
				} else {
					chain.doFilter(request, response);// go ahead
				}
			}

		}
	}
}
