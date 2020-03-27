package com.leavemanagement.leavemanagementsystem.dto;

/**
 * DTO object for balanced leave response.
 * @author Ruchi
 *
 */

public class BalancedLeaveResponseDTO {	

	private Long id;
	
	private Integer annualLeaves;	
	
	private Integer myLeaves;	
	
	private Integer restrictedLeaves;

	public BalancedLeaveResponseDTO() {
		
	}
	
	public BalancedLeaveResponseDTO(Long id, Integer annualLeaves, Integer myLeaves, Integer restrictedLeaves) {
		super();
		this.id = id;
		this.annualLeaves = annualLeaves;
		this.myLeaves = myLeaves;
		this.restrictedLeaves = restrictedLeaves;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAnnualLeaves() {
		return annualLeaves;
	}

	public void setAnnualLeaves(Integer annualLeaves) {
		this.annualLeaves = annualLeaves;
	}

	public Integer getMyLeaves() {
		return myLeaves;
	}

	public void setMyLeaves(Integer myLeaves) {
		this.myLeaves = myLeaves;
	}

	public Integer getRestrictedLeaves() {
		return restrictedLeaves;
	}

	public void setRestrictedLeaves(Integer restrictedLeaves) {
		this.restrictedLeaves = restrictedLeaves;
	}
	
	
}
