package com.sunilos.p4.ctl;

import javax.servlet.annotation.WebServlet;

import org.apache.log4j.Logger;

import com.sunilos.p4.model.BaseModel;

/**
 * Welcome functionality Controller. Performs operation for Show Welcome page
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

@WebServlet("/WelcomeCtl")
public class WelcomeCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(WelcomeCtl.class);

	@Override
	protected String getView() {
		return getView(null);
	}

	@Override
	protected String getView(String op) {
		return ORSView.WELCOME_VIEW;
	}

	@Override
	protected BaseModel getModel() {
		return null;
	}

}