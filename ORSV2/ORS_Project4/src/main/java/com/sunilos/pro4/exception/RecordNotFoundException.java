package com.sunilos.pro4.exception;
/**
 * RecordNotFoundException thrown when a record not found occurred.
 * 
 * @author Sunil OS
 * 
 */
public class RecordNotFoundException extends Exception {
	public RecordNotFoundException(String msg){
		super(msg);
	}
}
