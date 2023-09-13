package com.sunilos.p4.ctl;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Main Controller performs session checking and logging operations before
 * calling any application controller. It prevents any user to access
 * application without login.
 * 
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

@WebFilter("/*")
public class PerformanceMonitor implements Filter {

	@Override
	public void init(FilterConfig conf) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;

		String url = request.getRequestURI();

		long start = new Date().getTime();
		chain.doFilter(req, resp);
		long end = new Date().getTime();

		double diff = (end - start) / 1000;
		System.out.println(url + " executed in " + diff + " milli sec");

	}

	@Override
	public void destroy() {
	}

}