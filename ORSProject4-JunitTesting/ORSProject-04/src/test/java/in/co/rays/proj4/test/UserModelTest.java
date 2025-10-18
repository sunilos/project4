package in.co.rays.proj4.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.UserModel;

public class UserModelTest {

	private UserModel model;
	private UserBean bean;

	@BeforeEach
	void setUp() {
		model = new UserModel();

		bean = new UserBean();
		bean.setFirstName("Ayush");
		bean.setLastName("Dabi");
		bean.setLogin("hari@gmail.com");
		bean.setPassword("Pass@1234");
		bean.setDob(new Date());
		bean.setMobileNo("9589359987");
		bean.setRoleId(2);
		bean.setGender("Male");
		System.out.println("har method ke pele chlegi");
	}

	/*
	 * @Test void testAddUser() throws ApplicationException,
	 * DuplicateRecordException { long pk = model.add(bean); assertTrue(pk > 0,
	 * "User should be added successfully");
	 * 
	 * }
	 */

	/*
	 * @Test void testDeleteUser() throws Exception { UserBean bean =
	 * model.findByPk(42); assertNotNull(bean, "User not found with given ID");
	 * 
	 * model.delete(bean);
	 * 
	 * assertNull(model.findByPk(42), "User should be deleted from database");
	 * System.out.println("User deleted successfully!"); }
	 */

	/*
	 * @Test void testUpdateUser() throws Exception { UserBean bean =
	 * model.findByPk(38); bean.setFirstName("Amit"); model.update(bean);
	 * assertEquals("Amit", bean.getFirstName());
	 * System.out.println("User Update successfully!"); }
	 */

	/*
	 * @Test void testFindByLogin() throws ApplicationException { String login =
	 * "Udaydabi7@gmail.com";
	 * 
	 * UserBean bean = model.findByLogin(login);
	 * 
	 * if (bean != null) { System.out.println("User found: " + bean.getFirstName() +
	 * " " + bean.getLastName()); assertEquals(login, bean.getLogin(),
	 * "Login should match"); } else {
	 * System.out.println("No user found with login: " + login); } }
	 */

	/*
	 * @Test void testAuthenticate() throws Exception { UserBean authUser =
	 * model.authenticate(bean.getLogin(), bean.getPassword());
	 * assertNotNull(authUser, "Authentication should be successful");
	 * System.out.println(" User authenticated successfully!"); }
	 */

	/*
	 * @Test void testSearchUser() throws Exception { UserBean searchBean = new
	 * UserBean(); searchBean.setFirstName("Uday"); List<UserBean> list =
	 * model.search(searchBean, 1, 10); assertNotNull(list,
	 * "Search result should not be null"); assertTrue(list.size() >= 0,
	 * "Search should return list"); System.out.println("Search returned " +
	 * list.size() + " records:----  " + bean.getFirstName()); }
	 */

	/*
	 * @Test void testForgetPassword() throws Exception { String login =
	 * "Udaydabi7@gmail.com"; boolean result = model.forgetPassword(login);
	 * assertTrue(result, "Password email should be sent successfully");
	 * System.out.println("Forget password  successfully!"); }
	 */
	
	
	
	/*
	 * @Test void testChangePassword() throws Exception { long userId = 41; String
	 * oldPass = "1234"; String newPass = "Pass@123";
	 * 
	 * boolean changed = model.changePassword(userId, oldPass, newPass);
	 * assertTrue(changed, "Password can't change");
	 * System.out.println("Password changed successfully!"); }
	 */
	 
}
