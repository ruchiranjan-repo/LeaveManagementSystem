package com.leavemanagement.leavemanagementsystem.exception;

/**
 * Exception to be thrown when user with provided user id does not exists.
 * 
 * @author Ruchi
 *
 */
public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	private final Integer errorCode;

	public UserNotFoundException(String message, Integer errorCode) {
		super(message);
		this.errorCode = errorCode;

	}

	public Integer getErrorCode() {
		return this.errorCode;
	}

}
