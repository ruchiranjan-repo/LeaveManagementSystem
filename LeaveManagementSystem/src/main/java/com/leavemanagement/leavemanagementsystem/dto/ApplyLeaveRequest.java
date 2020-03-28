package com.leavemanagement.leavemanagementsystem.dto;

/**
 * Request DTO for applying leaves.
 * @author Ruchi
 */
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

public class ApplyLeaveRequest {
	
	@NotNull
	private String leaveType;
	
	@NotNull
	private LocalDate fromDate;
	
	@NotNull
	private LocalDate toDate;

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

}
