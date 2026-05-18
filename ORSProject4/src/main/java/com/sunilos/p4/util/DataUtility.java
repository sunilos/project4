package com.sunilos.p4.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Data Utility class to format data from one format to another
 *
 * @author Rays EdTech
 * @version 1.0
 * @Copyright (c) Rays EdTech
 */

public class DataUtility {

	/**
	 * Application Date Format
	 */
	public static final String APP_DATE_FORMAT = "MM/dd/yyyy";

	public static final String APP_TIME_FORMAT = "MM/dd/yyyy HH:mm:ss";

	// DateTimeFormatter is immutable and thread-safe — safe to share as static fields
	private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern(APP_DATE_FORMAT);

	private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern(APP_TIME_FORMAT);

	/**
	 * Trims trailing and leading spaces of a String
	 */
	public static String getString(String val) {
		if (DataValidator.isNotNull(val)) {
			return val.trim();
		} else {
			return val;
		}
	}

	/**
	 * Converts an Object to String
	 */
	public static String getStringData(Object val) {
		if (val != null) {
			return val.toString();
		} else {
			return "";
		}
	}

	/**
	 * Converts String into Integer
	 */
	public static int getInt(String val) {
		if (DataValidator.isInteger(val)) {
			return Integer.parseInt(val);
		} else {
			return 0;
		}
	}

	/**
	 * Converts String into Long
	 */
	public static long getLong(String val) {
		if (DataValidator.isLong(val)) {
			return Long.parseLong(val);
		} else {
			return 0;
		}
	}

	/**
	 * Parses a date string (MM/dd/yyyy) into a Date
	 */
	public static Date getDate(String val) {
		try {
			LocalDate ld = LocalDate.parse(val, DATE_FMT);
			return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Formats a Date into a String (MM/dd/yyyy)
	 */
	public static String getDateString(Date date) {
		if (date == null) return "";
		try {
			LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			return DATE_FMT.format(ld);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * Parses a timestamp string (MM/dd/yyyy HH:mm:ss) into a Timestamp
	 */
	public static Timestamp getTimestamp(String val) {
		try {
			LocalDateTime ldt = LocalDateTime.parse(val, TIME_FMT);
			return Timestamp.valueOf(ldt);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Converts a long epoch millis value into a Timestamp
	 */
	public static Timestamp getTimestamp(long l) {
		return new Timestamp(l);
	}

	/**
	 * Returns the current time as a Timestamp
	 */
	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * Returns the epoch millis of a Timestamp, or 0 if null
	 */
	public static long getTimestamp(Timestamp tm) {
		if (tm == null) return 0;
		return tm.getTime();
	}

	/**
	 * Converts an exception's stack trace into a String
	 */
	public static String exceptionToString(Exception e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(baos));
		return baos.toString();
	}

}
