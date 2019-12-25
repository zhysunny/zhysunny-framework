package com.zhysunny.framework.springboot.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

@Order(0)
@WebFilter(urlPatterns = { "/config/*", "/monitor/*", "/statistic/*", "/login/*", "/tools/*", "/debug/*" })
public class RequestFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String url = request.getRequestURI();
		Enumeration<String> parameterNames = request.getParameterNames();
		String userIp = request.getRemoteAddr();
		StringBuffer log = new StringBuffer();
		log.append(userIp).append("发送请求：").append(url);
		if (parameterNames.hasMoreElements()) {
			log.append(",请求参数：");
			String name = null;
			while (parameterNames.hasMoreElements()) {
				name = parameterNames.nextElement();
				log.append(name).append('=').append(request.getParameter(name)).append(',');
			}
			if (log.toString().endsWith(",")) {
				log.delete(log.length() - 1, log.length());
			}
		}
		LOGGER.info(log.toString());
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void destroy() {
	}

}
