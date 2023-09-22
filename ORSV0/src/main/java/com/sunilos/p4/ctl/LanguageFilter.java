package com.sunilos.p4.ctl;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.sunilos.p4.util.DataUtility;
import com.sunilos.p4.util.DataValidator;
import com.sunilos.p4.util.MessageSource;

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
public class LanguageFilter implements Filter {

	@Override
	public void init(FilterConfig conf) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		MessageSource messagesource = MessageSource.getInstance();

		String lang = DataUtility.getString(request.getParameter("lang"));
		if (DataValidator.isNotNull(lang)) {
			messagesource.setLocale(lang);
		}

		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
	}

}