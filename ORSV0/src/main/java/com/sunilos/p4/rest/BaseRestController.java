package com.sunilos.p4.rest;

import javax.servlet.http.HttpServlet;

import com.sunilos.p4.bean.BaseBean;
import com.sunilos.p4.model.BaseModel;

public abstract class BaseRestController<B extends BaseBean, M extends BaseModel> extends HttpServlet {

	protected abstract M getModel();
}
