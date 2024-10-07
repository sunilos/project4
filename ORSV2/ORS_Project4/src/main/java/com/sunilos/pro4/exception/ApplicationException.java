package com.sunilos.pro4.exception;
/**
 * ApplicationException is propogated from Service classes when an business
 * logic exception occurered.
 * 
 * @author Sunil OS
 *
 */
public class ApplicationException extends Exception {

	public ApplicationException(String msg){
		super(msg);
	}

}
