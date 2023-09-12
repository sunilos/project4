package com.sunilos.p4.util;

import java.util.Locale;
import java.util.ResourceBundle;

public final class MessageSource {

	public static String ENGLISH_LANG_CODE = "en";
	public static String HINDI_LANG_CODE = "hi";
	public static String DEFAULT_LANGUAGE = ENGLISH_LANG_CODE;

	private static MessageSource ms = null;

	private ResourceBundle rb = null;

	private MessageSource() {
		this(DEFAULT_LANGUAGE);
	}

	private MessageSource(String lang) {
		rb = getBundle(lang);
	}

	private MessageSource(Locale locale) {
		this(locale.getLanguage());
	}

	private ResourceBundle getBundle(String lang) {
		return ResourceBundle.getBundle("message", new Locale(lang));
	}

	public static MessageSource getInstance() {
		return getInstance(DEFAULT_LANGUAGE);
	}

	public static MessageSource getInstance(String languageCode) {
		if (ms == null) {
			ms = new MessageSource(languageCode);
		}
		return ms;
	}

	public void setLocale(String lang) {
		rb = getBundle(lang);
	}

	public String getLanguage() {
		return rb.getLocale().getLanguage();
	}

	public String get(String key) {
		String val = "no value";
		try {
			val = rb.getString(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}

	public static void main(String[] args) {
		MessageSource ms = MessageSource.getInstance();
		ms.setLocale("hi");
		String val = ms.get("login.userid1");
		System.out.println("-->" + val);
	}
}
