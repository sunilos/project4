 package com.sunilos.pro4.controller;

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

import com.sunilos.pro4.util.ServletUtility;

//TODO: Auto-generated Javadoc
/**
 * Main Controller performs session checking and logging operations before
 * calling any application controller. It prevents any user to access
 * application without login.
 * 
 * 
 * @author Sunil OS
 */


@WebFilter(filterName = "FrontCtl", urlPatterns = { "/ctl/*", "/doc/*" })

public class FrontControler implements Filter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Fctl Do filter");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();

		if (session.getAttribute("user") == null) {
			// request.setAttribute("message", " Your Session has been Expired... Please
			// Login Again"); 
			ServletUtility.setErrorMessage(" Your Session has been Expired... Please Login Again", request);
			
			// Set the URI

			String uri = request.getRequestURI();
			request.setAttribute("URI", uri);

			System.out.println("URI  get kiya frontcontroler me" + uri);

			ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);

			return;
		} else {
			chain.doFilter(req, resp);
		}
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */

	public void init(FilterConfig conf) throws ServletException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}

}
