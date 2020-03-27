package com.leavemanagement.leavemanagementsystem.exception;

/**
 * Exception to be thrown when no leave details found for user with provided id.
 * 
 * @author Ruchi
 *
 */
public class NoLeaveDetailsFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private Integer errorCode;

	public NoLeaveDetailsFoundException(String message, Integer errorCode) {
		super(message);
		this.errorCode = errorCode;

	}

	public Integer getErrorCode() {
		return this.errorCode;
	}

}
