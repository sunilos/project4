package com.sunilos.p4.model.test;

import com.sunilos.p4.exception.ApplicationException;
import com.sunilos.p4.util.EmailMessage;
import com.sunilos.p4.util.EmailUtility;


/**
 * Testcase to test EmailUtility class
 * 
 * @author Rays Technologies
 * @version 1.0
 * @Copyright (c) Rays Technologies
 */

public class EmailUtilityTest {

	/**
	 * Main method to call test methods.
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		testHTMLEmail();
		testTextEmail();
		testEmailTORecipient();
		testEmailCCRecipient();
		testEmailBCCRecipient();
		testEmailMultipleTORecipient();
		testEmailMultipleCCRecipient();
		testEmailMultipleBCCRecipient();

	}

	/**
	 * Send HTML Email
	 */

	public static void testHTMLEmail() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setTo("sunrayssunilbook@gmail.com");
			msg.setSubject("Test : testHTMLEmail");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send TEXT Email
	 */

	public static void testTextEmail() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setTo("sunrayssunilbook@gmail.com");
			msg.setSubject("Test : testTextEmail");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.TEXT_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send Email to Single TO Recipient
	 */
	public static void testEmailTORecipient() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setTo("sunrayssunilbook@gmail.com");
			msg.setSubject("Test : testEmailTORecipient");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send Email to Single CC Recipient
	 */
	public static void testEmailCCRecipient() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setCc("sunrayssunilbook@gmail.com");
			msg.setSubject("Test : testEmailCCRecipient");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.TEXT_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send Email to Single BCC Recipient
	 */
	public static void testEmailBCCRecipient() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setBcc("sunrayssunilbook@gmail.com");
			msg.setSubject("Test : testEmailBCCRecipient");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send Email to Multiple To Recipient
	 */

	public static void testEmailMultipleTORecipient() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setTo("vipinchandore@gmail.com,vipin.chandore@nenosystems.com");
			msg.setSubject("Test : testEmailMultipleTORecipient");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send Email to Multiple CC Recipient
	 */
	public static void testEmailMultipleCCRecipient() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setCc("vipinchandore@gmail.com,vipin.chandore@nenosystems.com");
			msg.setSubject("Test : testEmailMultipleCCRecipient");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send Email to Multiple BCC Recipient
	 */
	public static void testEmailMultipleBCCRecipient() {
		try {
			EmailMessage msg = new EmailMessage();
			msg.setBcc("vipinchandore@gmail.com,vipin.chandore@nenosystems.com");
			msg.setSubject("Test : testEmailMultipleBCCRecipient");
			msg.setMessage("<h1>Hello world</h1>");
			msg.setMessageType(EmailMessage.HTML_MSG);
			EmailUtility.sendMail(msg);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}
