package com.sunilos.proj4.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.sunilos.pro4.bean.MarksheetBean;
import com.sunilos.pro4.exception.ApplicationException;
import com.sunilos.pro4.exception.DuplicateRecordException;
import com.sunilos.pro4.model.MarksheetModel;


/**
 * Marksheet Model Test classes.
 * @author Sunil OS
 *
 */
public class MarksheetTest {

	public static MarksheetModel model = new MarksheetModel();

	public static void main(String[] args) {
		// testAdd();
		// testDelete();
		//testUpdate();
		// testFindByRollNo(); 
		// testFindByPK();
		 testSearch();
		// testList();
		// testMeritList();
	}

	public static void testAdd() {
		try {
			MarksheetBean bean = new MarksheetBean();

			bean.setRollNo("r4");
			bean.setPhysics(70);
			bean.setChemistry(60);
			bean.setMaths(50);
			bean.setStudentld(21L);
			System.out.println("model start");
			Long pk = model.add(bean);

			System.out.println("add End");

		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		try {
			MarksheetBean bean = new MarksheetBean();
			Long pk = 2L;
			bean.setId(pk);
			model.delete(bean);

			MarksheetBean deleteBean = model.findByPK(pk);
			if (deleteBean != null) {
				System.out.println("Test Delet fail");
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {
		try {
			MarksheetBean bean = model.findByPK(3L);

			bean.setStudentld(3L);
			bean.setRollNo("r5");
			bean.setChemistry(100);
			bean.setPhysics(20);
			bean.setMaths(50);

			model.update(bean);
			System.out.println("Update Record");
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByRollNo() {
		try {
			MarksheetBean bean = model.findByRollNo("r1");
			if (bean == null) {
				System.out.println("Test Find by rollNo fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testFindByPK() {
		try {
			MarksheetBean bean = new MarksheetBean();
			long pk = 3L;
			bean = model.findByPK(pk);
			if (bean == null) {
				System.out.println("Find By pk fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getName());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			//bean.setName("ram");
			bean.setId(9L);
			list = model.search(bean, 1, 10);
			if (list.size() < 0) {
				System.out.println("Test search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testList() {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			list = model.list(1, 6);
			if (list.size() < 0) {
				System.out.println("Test List fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testMeritList() {
		try {
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			list = model.list(1, 5);
			if (list.size() > 0) {
				System.out.println("Test List fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getName());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getMaths());
			}

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
