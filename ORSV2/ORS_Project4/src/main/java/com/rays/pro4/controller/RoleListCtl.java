package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.RoleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.RoleModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;


//TODO: Auto-generated Javadoc
/**
* Role List functionality Controller. Performs operation for list, search
* operations of Role
* 
*  @author Sunil OS
*/
@ WebServlet(name="RoleListCtl",urlPatterns={"/ctl/RoleListCtl"})
public class RoleListCtl extends BaseCtl{

	 /** The log. */
    private static Logger log = Logger.getLogger(RoleListCtl.class);

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void preload(HttpServletRequest request){
       
       RoleModel rmodel = new RoleModel();
    		
    	try{
    	    List rlist= rmodel.list();    
    	    request.setAttribute("RoleList",rlist);
            }
            catch(ApplicationException e){
            e.printStackTrace();
            }
            }
    
    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        RoleBean bean = new RoleBean();
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setId(DataUtility.getLong(request.getParameter("roleid")));
        return bean;
    }

    /**
     * Contains Display logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("RoleListCtl doGet Start");
        List list = null;
        
        List nextList=null;

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
        
        RoleBean bean = (RoleBean) populateBean(request);
        String op = DataUtility.getString(request.getParameter("operation"));
       
//        String[] ids = request.getParameterValues("ids");
        RoleModel model = new RoleModel();
        
        try {
        	
            list = model.search(bean, pageNo, pageSize);
            
           nextList=model.search(bean,pageNo+1,pageSize);
			
			request.setAttribute("nextlist", nextList.size());
//			 ServletUtility.setList(list, request);
            
			 if (list == null || list.size() == 0) {
                ServletUtility.setErrorMessage("No record found ", request);
            }
            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            
           ServletUtility.forward(getView(), request, response);
        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
        log.debug("RoleListCtl doGet End");
    }

    /**
     * Contains Submit logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("RoleListCtl doPost Start");
        List list = null;
        
        List nextList=null;

        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
       
        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
                .getValue("page.size")) : pageSize;
        
        RoleBean bean = (RoleBean) populateBean(request);
        String op = DataUtility.getString(request.getParameter("operation"));

        String[] ids = request.getParameterValues("ids");
        RoleModel model = new RoleModel();

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }
                else if (OP_NEW.equalsIgnoreCase(op)) {
                    ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
                    return;
                    }
                    else if (OP_RESET.equalsIgnoreCase(op)) {
                        ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
                        return;
                    }
                    else if (OP_DELETE.equalsIgnoreCase(op)) {
                    	pageNo = 1;
                    	if (ids != null && ids.length > 0) {
                    		RoleBean deletebean = new RoleBean();
                        
                        for (String id : ids) {
                            deletebean.setId(DataUtility.getInt(id));
                            try {
    							model.delete(deletebean);
    						} catch (ApplicationException e) {
    							log.error(e);
    				            ServletUtility.handleException(e, request, response);
    				            return;
    						}ServletUtility.setSuccessMessage("Role is Deleted Successfully ", request);       
                        }
                    } else {
                        ServletUtility.setErrorMessage("Select at least one record", request);
                    }
                }
            try {
				list = model.search(bean, pageNo, pageSize);
				
				 nextList=model.search(bean,pageNo+1,pageSize);
					
					request.setAttribute("nextlist", nextList.size());
					
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
            if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found ", request);
            }
            ServletUtility.setList(list, request);
            ServletUtility.setBean(bean, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        
        log.debug("RoleListCtl doPost End");
    }

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#getView()
     */
    @Override
    protected String getView() {
        return ORSView.ROLE_LIST_VIEW;
    }
	
}
