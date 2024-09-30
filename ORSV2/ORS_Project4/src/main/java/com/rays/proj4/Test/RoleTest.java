package com.rays.proj4.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.RoleBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.RoleModel;


/**
 * Role Model Test classes.
 * 
 * @author Sunil OS
 *
 */
public class RoleTest {

public static RoleModel model=new RoleModel();
	
	
	public static void main(String[] args)throws ParseException, ApplicationException {
	
		//testAdd();
		//testDelete();
		//testUpdate();
		//testFindByPK();
		//testFindByName();
		//testSearch();
		testList();
	}	
		
		public static void testAdd() throws ParseException {
		
			try{
				RoleBean bean=new RoleBean();
		//	bean.setId(1);
				bean.setName("akash");
				bean.setDescription("Student");
				bean.setCreatedBy("Admin");
				 bean.setModifiedBy("admin");
				bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			   
			    bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
				long pk=model.add(bean);
//				RoleBean addedbean=model.findByPK(pk);
//				if(addedbean==null){
//					System.out.println("Test Add fail");
//				}
			}catch(ApplicationException e){
				e.printStackTrace();
			}catch(DuplicateRecordException e){
				e.printStackTrace();
			}
		}
		
		
		
		
		public static void testDelete() throws ApplicationException{
		
			RoleBean bean = new RoleBean();
			
			bean.setId(1l);
			
			model.delete(bean);
			
			System.out.println("record deleted");
			
		}	
		public static void testUpdate(){
			try{
				RoleBean bean=model.findByPK(3L);
				bean.setName("piyush");
				bean.setDescription("ajjjjddj");
				model.update(bean);
				
				RoleBean updatedbean=model.findByPK(2L);
				
				if(!"ajay".equals(updatedbean.getName())){
					System.out.println("Test Update fill");
				}
				
			}catch(ApplicationException e){
				e.printStackTrace();
			}catch(DuplicateRecordException e){
				e.printStackTrace();
			}
		}
		public static void testFindByPK(){
			try{
			RoleBean bean =new RoleBean();
			long pk=2L;
			bean=model.findByPK(pk);
			if(bean==null){
				System.out.println("Test Find By PK fill");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			
			}catch(ApplicationException e){
				e.printStackTrace();
			}
			
		}
		public static void testFindByName(){
			try{
				RoleBean bean=new RoleBean();
				bean=model.findByName("ankit");
				if(bean==null){
					System.out.println("Test Find By Name fill");
				}
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}catch(ApplicationException e){
				e.printStackTrace();
			}
		}
		public static void testSearch(){
			try{
				RoleBean bean=new RoleBean();
				List list=new ArrayList();
				//bean.setName("ankit");
				list=model.search(bean,0,0);
				if(list.size() < 0){
					System.out.println("test Search fill");
				}
				Iterator it = list.iterator();
				
				while(it.hasNext()){
					bean=(RoleBean)it.next();
					System.out.println(bean.getId());
					System.out.println(bean.getName());
					System.out.println(bean.getDescription());
				}
				
			}catch(ApplicationException e){
				e.printStackTrace();
			}
		}
		public static void testList(){
			try{
				
				RoleBean bean=new RoleBean();
				List list=new ArrayList();
				list=model.list(1,10);
				if(list.size() >0){
					System.out.println("test List faill");
				}
				Iterator it=list.iterator();
				while(it.hasNext()){
					bean=(RoleBean)it.next();
					System.out.println(bean.getId());
					System.out.println(bean.getName());
					System.out.println(bean.getDescription());
				}
			}catch(ApplicationException e){
				e.printStackTrace();
			}
		}
	
}
