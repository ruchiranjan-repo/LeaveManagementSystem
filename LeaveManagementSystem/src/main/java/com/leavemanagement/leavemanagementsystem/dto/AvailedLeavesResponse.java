package com.leavemanagement.leavemanagementsystem.dto;

/**
 * Response DTO for availed leaves.
 * @author Ruchi
 */

import java.time.LocalDate;

public class AvailedLeavesResponse {
	
	private Long id;	

	private String leaveType;	
	
	private LocalDate fromDate;	

	private LocalDate toDate;	
	
	private String approvalStatus;
	
	private Integer noOfDays;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Integer getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}
		

}
