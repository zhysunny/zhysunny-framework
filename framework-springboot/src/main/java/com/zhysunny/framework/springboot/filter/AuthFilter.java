package com.zhysunny.framework.springboot.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.annotation.Order;

@Order(2)
@WebFilter(urlPatterns = { "/config/*" })
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpSession session = request.getSession();
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		if (session.getAttribute("user") != null) {
			// 表示已登录
			String user = session.getAttribute("user").toString();
			if ("admin".equals(user)) {
				filterChain.doFilter(servletRequest, servletResponse);
			} else {
				response.sendRedirect("/noAuthor");
			}
		} else {
			response.sendRedirect("/login");
		}
	}

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
