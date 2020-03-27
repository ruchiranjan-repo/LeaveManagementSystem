package com.leavemanagement.leavemanagementsystem.exception;

/**
 * Exception to be thrown when no leave available for the user to avail.
 * 
 * @author Ruchi
 *
 */
public class FutureDateRequiredException extends Exception {

	private static final long serialVersionUID = 1L;

	private final Integer errorCode;

	public FutureDateRequiredException(String message, Integer errorCode) {
		super(message);
		this.errorCode = errorCode;

	}

	public Integer getErrorCode() {
		return this.errorCode;
	}

}
