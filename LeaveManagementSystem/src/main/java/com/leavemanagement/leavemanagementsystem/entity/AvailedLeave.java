package com.leavemanagement.leavemanagementsystem.entity;

/**
 * Entity class for availed leave details.
 * @author Ruchi
 */

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="AvailedLeave")
public class AvailedLeave implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="leaveType")
	private String leaveType;
	
	@Column(name="fromDate")
	private LocalDate fromDate;
	
	@Column(name="toDate")
	private LocalDate toDate;
	
	@Column(name="approvalStatus")
	private String approvalStatus;
	
	@OneToOne
	@JoinColumn(name="userId")
	User user;
	
	public AvailedLeave()
	{
		
	}
	

	public AvailedLeave(String leaveType, LocalDate fromDate, LocalDate toDate, String approvalStatus, User user) {
		super();
		this.leaveType = leaveType;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.approvalStatus = approvalStatus;
		this.user = user;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
		

}
