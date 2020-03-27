package com.leavemanagement.leavemanagementsystem.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.leavemanagement.leavemanagementsystem.TestData;
import com.leavemanagement.leavemanagementsystem.entity.User;
import com.leavemanagement.leavemanagementsystem.repository.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	UserRepository userRepository;

	User user;

	@BeforeEach
	public void setUp() {
		user = new User();
		user.setName(TestData.USER_NAME);
	}

	@Test
	public void testFindById() {
		User savedUser = testEntityManager.persist(user);

		Optional<User> optionaUser = userRepository.findById(savedUser.getId());

		assertTrue(optionaUser.isPresent());
		assertThat(optionaUser.get().getId()).isEqualTo(savedUser.getId());
		assertThat(optionaUser.get().getName()).isEqualTo(savedUser.getName());
	}

	@Test
	public void testFindByIdNotFound() {
		testEntityManager.persist(user);

		Optional<User> optionaUser = userRepository.findById(100L);

		assertFalse(optionaUser.isPresent());
	}

}
