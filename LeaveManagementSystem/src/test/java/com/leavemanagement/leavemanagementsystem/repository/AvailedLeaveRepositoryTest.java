package com.leavemanagement.leavemanagementsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.util.CollectionUtils;

import com.leavemanagement.leavemanagementsystem.TestData;
import com.leavemanagement.leavemanagementsystem.constant.LeaveType;
import com.leavemanagement.leavemanagementsystem.entity.AvailedLeave;
import com.leavemanagement.leavemanagementsystem.entity.User;

@DataJpaTest
public class AvailedLeaveRepositoryTest {

	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	AvailedLeaveRepository availedLeaveRepository;

	AvailedLeave availedLeave, availedLeave1;
	User user;

	@BeforeEach
	public void setup() {
		user = new User();
		user.setName(TestData.USER_NAME);

		availedLeave = new AvailedLeave();
		availedLeave.setLeaveType(LeaveType.MY_LEAVE.toString());
		availedLeave.setApprovalStatus(TestData.LEAVE_APPROVAL);
		availedLeave.setFromDate(LocalDate.of(2020, 03, 27));
		availedLeave.setToDate(LocalDate.of(2020, 03, 28));
		availedLeave.setUser(user);

		availedLeave1 = new AvailedLeave();
		availedLeave1.setLeaveType(LeaveType.RESTRICTED_LEAVE.toString());
		availedLeave1.setApprovalStatus(TestData.LEAVE_APPROVAL);
		availedLeave1.setFromDate(LocalDate.of(2020, 03, 29));
		availedLeave1.setToDate(LocalDate.of(2020, 03, 29));
		availedLeave1.setUser(user);

	}

	@Test
	public void testFindByUserId() {
		User savedUser = testEntityManager.persist(user);
		testEntityManager.persist(availedLeave);
		testEntityManager.persist(availedLeave1);

		List<AvailedLeave> availedLeaves = availedLeaveRepository.findByUserId(savedUser.getId());

		assertFalse(CollectionUtils.isEmpty(availedLeaves));
		assertThat(availedLeaves.size()).isEqualTo(2);

	}

	@Test
	public void testFindByUserIdNotFound() {
		testEntityManager.persist(user);
		testEntityManager.persist(availedLeave);
		testEntityManager.persist(availedLeave1);

		List<AvailedLeave> availedLeaves = availedLeaveRepository.findByUserId(100L);

		assertTrue(CollectionUtils.isEmpty(availedLeaves));

	}

}
