package com.rays.proj4.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.SubjectBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.SubjectModel;


/**
 * Subject Model Test classes.
 * 
 * @author Sunil OS
 *
 */
public class SubjectTest {

	public static SubjectModel model = new SubjectModel();

	public static void main(String[] args) throws Exception {
		//testadd();
		// testDelete();
		// testFindByName();
		// testUpdate();
		// testFindByPk();
		 testsearch();
		// testlist();

	}
  
	public static void testadd() throws DuplicateRecordException {

		try {
			SubjectBean bean = new SubjectBean();
			// bean.setId(1);
			bean.setSubjectName("css");
			bean.setDescription("programingL");
			bean.setCourseId(3);
			bean.setCourseName("ccssn");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			long pk = model.add(bean);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		try {
			SubjectBean bean = new SubjectBean();
			long pk = 1L;
			bean.setId(1);
			model.Delete(bean);
			System.out.println("Test Deleted");
			/*
			 * SubjectBean deleteBean=model.findByPK(pk); if(deleteBean == null) {
			 * System.out.println("Test Delete fail"); }
			 */

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByName() {
		try {
			SubjectBean bean = new SubjectBean();
			bean = model.findByName("css");
			if (bean == null) {
				System.out.println("test findBy Name fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		try {
			SubjectBean bean = model.FindByPK(2L);
			bean.setSubjectName("java");
			// bean.setDescription("commerce");
			model.update(bean);
			System.out.println("update succ");

			/*
			 * SubjectBean updateBean=model.testFindByPK(2L); if(!
			 * "ram".equals(updateBean.getSubjectName())){
			 * System.out.println("test update fail"); }
			 */

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPk() {
		try {
			SubjectBean bean = new SubjectBean();
			long pk = 2L;
			bean = model.FindByPK(pk);
			if (bean == null) {
				System.out.println("test findbypk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testsearch() throws DatabaseException {
		try {
			SubjectBean bean = new SubjectBean();
			//bean.setSubjectName("Java");
		    // bean.setId(2L);
			//bean.setCourseId(2L);
			bean.setCourseName("Computers Commarce");
			List list = new ArrayList();
			list = model.search(bean,1,10);

			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getDescription());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testlist() throws Exception {
		try {
			SubjectBean bean = new SubjectBean();
			List list = new ArrayList();
			list = model.list(1, 10);
			if (list.size() < 0) {
				System.out.println("test list fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getSubjectName());
				System.out.println(bean.getDescription());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());

			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
