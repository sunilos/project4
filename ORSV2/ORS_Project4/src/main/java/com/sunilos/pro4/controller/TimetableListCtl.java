package com.sunilos.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilos.pro4.bean.BaseBean;
import com.sunilos.pro4.bean.CourseBean;
import com.sunilos.pro4.bean.SubjectBean;
import com.sunilos.pro4.bean.TimeTableBean;
import com.sunilos.pro4.exception.ApplicationException;
import com.sunilos.pro4.model.CourseModel;
import com.sunilos.pro4.model.SubjectModel;
import com.sunilos.pro4.model.TimeTableModel;
import com.sunilos.pro4.util.DataUtility;
import com.sunilos.pro4.util.PropertyReader;
import com.sunilos.pro4.util.ServletUtility;

//TODO: Auto-generated Javadoc
/**
* The Class TimeTableListCtl.
* 
*  @author Sunil OS
* 
*/
@WebServlet(name = "TimeTableListCtl", urlPatterns = {"/ctl/TimeTableListCtl"})
public class TimetableListCtl extends BaseCtl{


	/* The log. /
	private static Logger log = Logger.getLogger(TimeTableListCtl.class);

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	protected void preload(HttpServletRequest request) {

		CourseModel model = new CourseModel();
		SubjectModel smodel = new SubjectModel();
		TimeTableModel tmodel = new TimeTableModel();
		List<CourseBean> list = null;
		List<SubjectBean> list2 = null;
		List<TimeTableBean>list3=null;
		try {
			list = model.list();
			list2 = smodel.list();
			list3 = tmodel.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("courseList", list);
		request.setAttribute("subjectList", list2);
		request.setAttribute("examtime", list3);

	}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {
		TimeTableBean bean = new TimeTableBean();

       //bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setCourseId(DataUtility.getLong(request.getParameter("clist")));
		
		bean.setSubjectId(DataUtility.getInt(request.getParameter("slist")));
		
		//bean.setSubjectName(DataUtility.getString(request.getParameter("slist")));
		
		bean.setExamDate(DataUtility.getDate(request.getParameter("Exdate")));
		
		bean.setExamTime(DataUtility.getString(request.getParameter("elist")));

	//	System.out.println(request.getParameter("Exdate"));
		
		//System.out.println("populate bean==========>>>> " + bean.getExamDate());
		populateDTO(bean, request);
		return bean;
	}
    
    /**
     * Contains display logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list  = null ;
       
		List nextList=null;
		
		int pageNo = 1;
		
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
          
		//System.out.println(pageSize+"hhhhhhh");
		
       TimeTableModel model = new TimeTableModel();
		
       TimeTableBean bean =(TimeTableBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));
      String[] ids = request.getParameterValues("ids");
	    

		try {
			list = model.search(bean, pageNo, pageSize);
			System.out.println("listgjdj----------<<<<<"+list);
		
			nextList=model.search(bean,pageNo+1,pageSize);
			
			request.setAttribute("nextlist", nextList.size());
			
			ServletUtility.setBean(bean, request);
			
			ServletUtility.setList(list, request);
			
			if (list==null && list.size()==0) {
				
				ServletUtility.setErrorMessage("No record Found", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);


		} catch (ApplicationException e) {
			e.printStackTrace();
			/*
			 * log.error(e);
			 */	ServletUtility.handleException(e, request, response);
		}
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

		List list=null;
		List nextList=null;
		
		String op = DataUtility.getString(request.getParameter("operation"));

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		
		pageNo = (pageNo == 0) ? 1 : pageNo;
		
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;


		TimeTableBean bean = (TimeTableBean) populateBean(request);	
		
		TimeTableModel model = new TimeTableModel();
		
		String[] ids = (String[]) request.getParameterValues("ids");
				
			        if (OP_SEARCH.equalsIgnoreCase(op)) {
				    pageNo = 1;
					} else if (OP_NEXT.equalsIgnoreCase(op)) {
						pageNo++;	
					} 
			        
					else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
						pageNo--;
					}
					else if (OP_NEW.equalsIgnoreCase(op)) 
					{
						ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
						return ;
					}
					
					else if (OP_RESET.equalsIgnoreCase(op)||OP_BACK.equalsIgnoreCase(op)) {
						ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
						return;
					}
					else if (OP_DELETE.equalsIgnoreCase(op)) {
						pageNo=1;
						if (ids != null && ids.length > 0) {
							TimeTableBean bean3 = new TimeTableBean();

							for (String id2 : ids) {
								int id1 = DataUtility.getInt(id2);
								bean3.setId(id1);
								try {
									model.delete(bean3);
								} catch (ApplicationException e) {
									e.printStackTrace();
									ServletUtility.handleException(e, request, response);
									return;
								}
								ServletUtility.setSuccessMessage("Data Deleted Succesfully", request);
							}
						
						}else{
							ServletUtility.setErrorMessage("Select at least one Record", request);
						}
					}
			    	try {
						list = model.search(bean, pageNo, pageSize);
						System.out.println("list---------------->>>>>>>>"+list);
						
						nextList=model.search(bean,pageNo+1,pageSize);
						
						request.setAttribute("nextlist", nextList.size());
						
						ServletUtility.setBean(bean, request);
					}
					catch(ApplicationException e){	
						ServletUtility.handleException(e, request, response);
						return;
					}
			   if(list==null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op)) 
			{
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
	        ServletUtility.forward(getView(), request, response);			
		}

	/* (non-Javadoc)
	 * @see in.co.rays.ors.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.TIMETABLE_LIST_VIEW;
	}

	
}
