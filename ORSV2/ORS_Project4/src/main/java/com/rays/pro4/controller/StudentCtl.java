package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.StudentBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.CollegeModel;
import com.rays.pro4.Model.StudentModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

//TODO: Auto-generated Javadoc
/**
* Student functionality Controller. Performs operation for add, update, delete
* and get Student
* 
*  @author Sunil OS
*/
@WebServlet(name="StudentCtl", urlPatterns = {"/ctl/StudentCtl"})
public class StudentCtl extends BaseCtl{

	/** The log. */
    private static Logger log = Logger.getLogger(StudentCtl.class);

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void preload(HttpServletRequest request) {
        CollegeModel model = new CollegeModel();
        try {
            List l = model.list();
            request.setAttribute("collegeList", l);
        } catch (ApplicationException e) {
            log.error(e);
        }

    }

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
    	System.out.println("validate started ... std ctl");
        log.debug("StudentCtl Method validate Started");
        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("firstname"))) {
            request.setAttribute("firstname",PropertyReader.getValue("error.require", "First Name"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("firstname"))) {
        	  request.setAttribute("firstname",PropertyReader.getValue("error.name", "First name"));
              pass = false;
		}
        if (DataValidator.isNull(request.getParameter("lastname"))) {
            request.setAttribute("lastname",PropertyReader.getValue("error.require", "Last Name"));
            pass = false;
        }else if (!DataValidator.isName(request.getParameter("lastname"))) {
      	  request.setAttribute("lastname",PropertyReader.getValue("error.name", "Last name"));
          pass = false;
	}
        if (DataValidator.isNull(request.getParameter("mobile"))) {
            request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile No"));
            pass = false;
        }else if (!DataValidator.isMobileNo(request.getParameter("mobile"))) {
      	  request.setAttribute("mobile", "Mobile No. must be 10 Digit and No. Series start with 6-9");
          pass = false;
	}
        if (DataValidator.isNull(request.getParameter("email"))) {
            request.setAttribute("email", PropertyReader.getValue("error.require", "Email "));
            pass = false;
        } else if (!DataValidator.isEmail(request.getParameter("email"))) {
            request.setAttribute("email", PropertyReader.getValue("error.email", "Login Id"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        }else if (!DataValidator.isAge(request.getParameter("dob"))) {
                request.setAttribute("dob", "Student Age must be Greater then 18 year ");
                pass = false;
            }  
        
        if (DataValidator.isNull(request.getParameter("collegename"))) {
            request.setAttribute("collegename", PropertyReader.getValue("error.require", "College Name"));
            pass = false;
        } 
        System.out.println("validate over ,.... Student ctl");
        log.debug("StudentCtl Method validate Ended");
        return pass;
    }

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("StudentCtl Method populatebean Started");

        StudentBean bean = new StudentBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setFirstName(DataUtility.getString(request.getParameter("firstname")));
        bean.setLastName(DataUtility.getString(request.getParameter("lastname")));
        bean.setDob(DataUtility.getDate(request.getParameter("dob")));
        System.out.println("dob"+bean.getDob());
        bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));
        bean.setEmail(DataUtility.getString(request.getParameter("email")));
        bean.setCollegeId(DataUtility.getLong(request.getParameter("collegename")));
        populateDTO(bean, request);
        log.debug("StudentCtl Method populatebean Ended");
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

        log.debug("StudentCtl Method doGet Started");

        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));

        // get model

        StudentModel model = new StudentModel();
        if (id > 0 || op != null) {
            StudentBean bean;
            try {
                bean = model.findByPK(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
        log.debug("StudentCtl Method doGett Ended");
    }

    /**
     * Contains Submit logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        log.debug("StudentCtl Method doPost Started");

        String op = DataUtility.getString(request.getParameter("operation"));

        long id = DataUtility.getLong(request.getParameter("id"));
        // get model

        StudentModel model = new StudentModel();

        if (OP_SAVE.equalsIgnoreCase(op)|| OP_UPDATE.equalsIgnoreCase(op)) {
            StudentBean bean = (StudentBean) populateBean(request);
            try {
                if (id > 0) {
                    model.Update(bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage(" Student is successfully Updated",request);
                } else {
                    long pk = model.add(bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage(" Student is successfully Added",request);
             //      bean.setId(pk);
                }
                ServletUtility.setBean(bean, request);
               // ServletUtility.setSuccessMessage(" Student is successfully Added",request);
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Student Email Id already exists", request);
            }
        } 
        else if ( OP_RESET.equalsIgnoreCase(op)) {
            
         	ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
             return;
         }
        else if (OP_CANCEL.equalsIgnoreCase(op) ) {
            
         	ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
             return;
         }
/*
        else if (OP_DELETE.equalsIgnoreCase(op)) {

            StudentBean bean = (StudentBean) populateBean(request);
            try {
                model.delete(bean);
                ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
                return;

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
*/        ServletUtility.forward(getView(), request, response);

        log.debug("StudentCtl Method doPost Ended");
    }

    /* (non-Javadoc)
     * @see in.co.rays.ors.controller.BaseCtl#getView()
     */
    @Override
    protected String getView() {
        return ORSView.STUDENT_VIEW;
    }

	
}
