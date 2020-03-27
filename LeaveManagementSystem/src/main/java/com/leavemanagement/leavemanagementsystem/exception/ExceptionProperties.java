package com.leavemanagement.leavemanagementsystem.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExceptionProperties {

	@Value("${leavemanagement.exception.usernotfound.errorcode}")
	private Integer userNotFoundErrorCode;

	@Value("${leavemanagement.exception.noleavedetails.errorcode}")
	private Integer noLeaveDetailsErrorCode;

	@Value("${leavemanagement.exception.usernotfound.errormessage}")
	private String userNotFoundErrorMessage;

	@Value("${leavemanagement.exception.noleavedetails.errormessage}")
	private String noLeaveDetailsErrorMessage;

	@Value("${leavemanagement.exception.noleaveavailable.errorcode}")
	private Integer noLeaveAvailableErrorCode;

	@Value("${leavemanagement.exception.noleaveavailable.errormessage}")
	private String noLeaveAvailableErrorMessage;

	@Value("${leavemanagement.exception.futuredaterequired.errorcode}")
	private Integer futureDateRequiredErrorCode;

	@Value("${leavemanagement.exception.futuredaterequired.errormessage}")
	private String futureDateRequiredErrorMessage;

	public Integer getUserNotFoundErrorCode() {
		return userNotFoundErrorCode;
	}

	public Integer getNoLeaveDetailsErrorCode() {
		return noLeaveDetailsErrorCode;
	}

	public String getUserNotFoundErrorMessage() {
		return userNotFoundErrorMessage;
	}

	public String getNoLeaveDetailsErrorMessage() {
		return noLeaveDetailsErrorMessage;
	}

	public Integer getNoLeaveAvailableErrorCode() {
		return noLeaveAvailableErrorCode;
	}

	public String getNoLeaveAvailableErrorMessage() {
		return noLeaveAvailableErrorMessage;
	}

	public Integer getFutureDateRequiredErrorCode() {
		return futureDateRequiredErrorCode;
	}

	public String getFutureDateRequiredErrorMessage() {
		return futureDateRequiredErrorMessage;
	}

}
