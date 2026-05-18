package com.sunilos.p4.util;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * This class validates input data
 *
 * @author Rays EdTech
 * @version 1.0
 * @Copyright (c) Rays EdTech
 */

public class DataValidator {

	// Compiled once; handles user+tag@sub.example.com and standard addresses
	private static final Pattern EMAIL_PATTERN = Pattern.compile(
			"^[_A-Za-z0-9+\\-]+(\\.[_A-Za-z0-9+\\-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

	/**
	 * Checks if value is Null
	 */
	public static boolean isNull(String val) {
		return val == null || val.trim().isEmpty();
	}

	/**
	 * Checks if value is NOT Null
	 */
	public static boolean isNotNull(String val) {
		return !isNull(val);
	}

	/**
	 * Checks if value is an Integer
	 */
	public static boolean isInteger(String val) {
		if (isNotNull(val)) {
			try {
				Integer.parseInt(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Checks if value is Long
	 */
	public static boolean isLong(String val) {
		if (isNotNull(val)) {
			try {
				Long.parseLong(val);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * Checks if value is a valid Email ID
	 */
	public static boolean isEmail(String val) {
		return isNotNull(val) && EMAIL_PATTERN.matcher(val).matches();
	}

	/**
	 * Checks if value is a valid Date
	 */
	public static boolean isDate(String val) {
		if (isNotNull(val)) {
			Date d = DataUtility.getDate(val);
			return d != null;
		}
		return false;
	}

}
