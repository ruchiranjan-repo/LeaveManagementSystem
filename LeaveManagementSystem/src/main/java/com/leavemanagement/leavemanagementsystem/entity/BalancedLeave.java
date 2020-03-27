package com.leavemanagement.leavemanagementsystem.entity;

/**
 * Entity class for Balanced leave details.
 * @author Ruchi
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BalancedLeave")
public class BalancedLeave implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "annualLeaves")
	private Integer annualLeaves;
	
	@Column(name = "myLeaves")
	private Integer myLeaves;
	
	@Column(name = "restrictedLeaves")
	private Integer restrictedLeaves;
	
	@OneToOne
	@JoinColumn(name = "userId")
	User user;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	

}
