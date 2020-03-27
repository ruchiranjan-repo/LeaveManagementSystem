package com.leavemanagement.leavemanagementsystem.dto;
/**
 * Response DTO for exceptions. 
 * @author Ruchi
 *
 */
public class ExceptionMessageDTO {
	
	private String errorMessage;
	
	private Integer errorCode;
	
	public ExceptionMessageDTO()
	{
		
	}

	public ExceptionMessageDTO(String errorMessage, Integer errorCode) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	
	
}
