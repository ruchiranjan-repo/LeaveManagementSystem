package com.leavemanagement.leavemanagementsystem.dto;

/**
 * Response DTo for users availed leave.
 * @author Ruchi
 */
import java.util.ArrayList;
import java.util.List;

public class UsersAvailedLeavesResponse {
	
	private Long userId;
	
	private String userName;
	
	private List<AvailedLeavesResponse> availedLeavesResponse = new ArrayList<>();

	public UsersAvailedLeavesResponse()
	{
		
	}
	
	public UsersAvailedLeavesResponse(Long userId, String userName, List<AvailedLeavesResponse> availedLeavesResponse) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.availedLeavesResponse = availedLeavesResponse;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<AvailedLeavesResponse> getAvailedLeavesResponse() {
		return availedLeavesResponse;
	}

	public void setAvailedLeavesResponse(List<AvailedLeavesResponse> availedLeavesResponse) {
		this.availedLeavesResponse = availedLeavesResponse;
	}	

}
