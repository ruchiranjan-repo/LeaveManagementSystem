package com.leavemanagement.leavemanagementsystem.dto;

/**
 * Response DTo for sucess response.
 * 
 * @author Ruchi
 *
 */
public class SuccessMessageDTO {

	private String message;

	private Integer successCode;

	public SuccessMessageDTO() {

	}

	public SuccessMessageDTO(String message, Integer successCode) {
		super();
		this.message = message;
		this.successCode = successCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getSuccessCode() {
		return successCode;
	}

	public void setSuccessCode(Integer successCode) {
		this.successCode = successCode;
	}

}
