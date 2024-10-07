package com.rays.pro4.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.CourseBean;
import com.rays.pro4.Bean.SubjectBean;
import com.rays.pro4.Bean.TimeTableBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.CourseModel;
import com.rays.pro4.Model.SubjectModel;
import com.rays.pro4.Model.TimeTableModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

//TODO: Auto-generated Javadoc
/**
* The Class TimeTableCtl.
**  @author Sunil OS
*/
@WebServlet(name = "TimeTableCtl", urlPatterns = {"/ctl/TimeTableCtl"})
public class TimetableCtl extends BaseCtl{

	
	/* The Constant serialVersionUID. /
	private static final long serialVersionUID = 1L;
	
	/* The log. /
	private static Logger log = Logger.getLogger(TimeTableCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	protected void preload(HttpServletRequest request) {
		CourseModel cmodel = new CourseModel();
		SubjectModel smodel = new SubjectModel();
		List<CourseBean> clist = new ArrayList<CourseBean>();
		List<SubjectBean> slist = new ArrayList<SubjectBean>();
		try {
			clist = cmodel.list();
			request.setAttribute("CourseList", clist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			slist = smodel.list();
			request.setAttribute("SubjectList", slist);				
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean validate(HttpServletRequest request) {
		/*
		 * log.debug("validate method of TimeTable Ctl started");
		 */boolean pass = true;

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExDate"))) {
			request.setAttribute("ExDate", PropertyReader.getValue("error.require", "Exam Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("ExTime"))) {
			request.setAttribute("ExTime", PropertyReader.getValue("error.require", "Exam Time"));
			pass = false;
		}
		
		
		/*
		 * log.debug("validate method of TimeTable Ctl End");
		 */return pass;
	}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected TimeTableBean populateBean(HttpServletRequest request) {
		/*
		 * log.debug("populateBean method of TimeTable Ctl start");
		 */	TimeTableBean bean = new TimeTableBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
	//	bean.setSubjectName(DataUtility.getString(request.getParameter("subjectname")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
//		bean.setCourseName(DataUtility.getString(request.getParameter("coursename")));
		bean.setSemester(DataUtility.getString(request.getParameter("semester")));
	//	bean.setDescription(DataUtility.getString(request.getParameter("description")));
		bean.setExamDate(DataUtility.getDate(request.getParameter("ExDate")));
		bean.setExamTime(DataUtility.getString(request.getParameter("ExTime")));
		
		/*System.out.println("<<<<<<__________>>>>>>>>");
		System.out.println(request.getParameter("ExDate"));
		System.out.println("<<<<<<__________>>>>>>>>");
*/		populateDTO(bean, request);
		/*
		 * log.debug("populateBean method of TimeTable Ctl End");
		 */	return bean;
	}
    
    /**
     * Contains Display logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * log.debug("do Get method of TimeTable Ctl Started");
		 *///System.out.println("Timetable ctl .do get started........>>>>>");

//		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
	
		TimeTableModel model = new TimeTableModel();
		TimeTableBean bean = null;
		if (id > 0) {
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				/*
				 * log.error(e);
				 */	ServletUtility.handleException(e, request, response);
			}
		}

		/*
		 * log.debug("do Get method of TimeTable Ctl End");
		 */	System.out.println("Timetable ctl .do get End........>>>>>");
		ServletUtility.forward(getView(), request, response);
	}
    
    /**
     * Contains Submit logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * log.debug("do post method of TimeTable Ctl start");
		 */
		List list;
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));

		TimeTableModel model = new TimeTableModel();
	
		
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) 
		{
			TimeTableBean bean = (TimeTableBean)populateBean(request);
			try {
				if(id>0){
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage(" TimeTable is Successfully Updated", request);
					
				}else{

				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(" TimeTable is Successfully Added", request);
				
	
				} 
				/*ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage(" TimeTable is Successfully Saved", request);
				*/
			}catch (ApplicationException e) {
				/*
				 * log.error(e);
				 */	e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			} catch (DuplicateRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (OP_CANCEL.equalsIgnoreCase(op))
		{
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		}
		else if ( OP_RESET.equalsIgnoreCase(op))
		{
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
		
	
		ServletUtility.forward(getView(), request, response);
	}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}
	
}

