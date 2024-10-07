
package com.sunilos.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilos.pro4.bean.BaseBean;
import com.sunilos.pro4.bean.StudentBean;
import com.sunilos.pro4.exception.ApplicationException;
import com.sunilos.pro4.model.CollegeModel;
import com.sunilos.pro4.model.StudentModel;
import com.sunilos.pro4.util.DataUtility;
import com.sunilos.pro4.util.PropertyReader;
import com.sunilos.pro4.util.ServletUtility;


//TODO: Auto-generated Javadoc
/**
* Student List functionality Controller. Performs operation for list, search
* and delete operations of Student
* 
*  @author Sunil OS
*/
@WebServlet (name = "StudentListCtl" , urlPatterns = {"/ctl/StudentListCtl"})
public class StudentListCtl extends BaseCtl{

	/** The log. */
    private static Logger log = Logger.getLogger(StudentListCtl.class);
    
    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void preload(HttpServletRequest request){
       
       
    	CollegeModel cmodel= new CollegeModel();
    	
    	try{
    	    List clist= cmodel.list(0,0);
    	    
    	    request.setAttribute("CollegeList",clist);
          
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

        StudentBean bean = new StudentBean();

        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
        bean.setEmail(DataUtility.getString(request.getParameter("email")));
        bean.setCollegeId(DataUtility.getLong(request.getParameter("collegename")));
         
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
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("StudentListCtl doGet Start");
        List list;

        int pageNo = 1;
        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        StudentBean bean = (StudentBean) populateBean(request);
        StudentModel model = new StudentModel();
        
        String op = DataUtility.getString(request.getParameter("operation"));
     //   String[] ids = request.getParameterValues("ids");

        try {
            list = model.search(bean, pageNo, pageSize);
 //            ServletUtility.setList(list, request);
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
        log.debug("StudentListCtl doGet End");
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
        log.debug("StudentListCtl doPost Start");
        List list = null;
        String op = DataUtility.getString(request.getParameter("operation"));
        
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

        StudentBean bean = (StudentBean) populateBean(request);
        
        String[] ids = request.getParameterValues("ids");
        StudentModel model = new StudentModel();


                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
                    pageNo--;
                }
                else if (OP_NEW.equalsIgnoreCase(op)) {
        			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
        			return;
        		}else if (OP_RESET.equalsIgnoreCase(op)) {
        			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
        			return;
        		}  
                else if (OP_DELETE.equalsIgnoreCase(op)) {
                    pageNo = 1;
                    if (ids != null && ids.length > 0) {
                    	StudentBean deletebean = new StudentBean();
                   
                        for (String id : ids) {
                            deletebean.setId(DataUtility.getInt(id));
                            try {
                				model.delete(deletebean);
    							
    						} catch (ApplicationException e) {
    							e.printStackTrace();
    							ServletUtility.handleException(e, request, response);
    							return;
    						}System.out.println("20");
                            ServletUtility.setSuccessMessage(" Student Data Successfully Deleted", request);
                        }
                    } 
                    else {
                        ServletUtility.setErrorMessage(
                                "Select at least one record", request);
                    }
                }

           
            try {
				list = model.search(bean, pageNo, pageSize);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
            if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found ", request);
            }
            ServletUtility.setBean(bean, request);
            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        log.debug("StudentListCtl doGet End");
    }

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#getView()
     */
    @Override
    protected String getView() {
        return ORSView.STUDENT_LIST_VIEW;
    }
	
}
