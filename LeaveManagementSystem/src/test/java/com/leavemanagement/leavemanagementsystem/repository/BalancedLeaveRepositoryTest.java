package com.leavemanagement.leavemanagementsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.leavemanagement.leavemanagementsystem.TestData;
import com.leavemanagement.leavemanagementsystem.entity.BalancedLeave;
import com.leavemanagement.leavemanagementsystem.entity.User;

@DataJpaTest
public class BalancedLeaveRepositoryTest {

	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	BalancedLeaveRepository balancedLeaveRepository;

	User user;
	BalancedLeave balancedLeave;

	@BeforeEach
	public void setup() {

		user = new User();
		user.setName(TestData.USER_NAME);

		balancedLeave = new BalancedLeave();
		balancedLeave.setAnnualLeaves(TestData.NO_OF_LEAVES);
		balancedLeave.setMyLeaves(TestData.NO_OF_LEAVES);
		balancedLeave.setRestrictedLeaves(TestData.NO_OF_LEAVES);
		balancedLeave.setUser(user);

	}

	@Test
	public void testFindByUserId() {
		User savedUser = testEntityManager.persist(user);
		BalancedLeave savedBalancedLeave = testEntityManager.persist(balancedLeave);

		Optional<BalancedLeave> balancedLeave = balancedLeaveRepository.findByUserId(savedUser.getId());
		assertThat(balancedLeave.get().getId()).isEqualTo(savedBalancedLeave.getId());
		assertThat(balancedLeave.get().getAnnualLeaves()).isEqualTo(savedBalancedLeave.getAnnualLeaves());
		assertThat(balancedLeave.get().getMyLeaves()).isEqualTo(savedBalancedLeave.getMyLeaves());
		assertThat(balancedLeave.get().getRestrictedLeaves()).isEqualTo(savedBalancedLeave.getRestrictedLeaves());
		assertThat(balancedLeave.get().getUser().getId()).isEqualTo(savedBalancedLeave.getUser().getId());

	}

	@Test
	public void testFindByUserIdNotFound() {
		testEntityManager.persist(user);
		testEntityManager.persist(balancedLeave);

		Optional<BalancedLeave> balancedLeave = balancedLeaveRepository.findByUserId(100L);

		assertFalse(balancedLeave.isPresent());

	}

}
