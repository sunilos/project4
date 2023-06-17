package in.co.sunrays.proj4.exception;

/**
 * RecordNotFoundException thrown when a record not found occurred
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 * 
 */

public class RecordNotFoundException extends Exception{

	/**
	 * @param msg
	 *            error message
	 */
	public RecordNotFoundException(String msg) {
		super(msg);

	}
}
