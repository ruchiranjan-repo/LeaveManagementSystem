package com.leavemanagement.leavemanagementsystem.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.assertj.core.api.JUnitBDDSoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.leavemanagement.leavemanagementsystem.TestData;
import com.leavemanagement.leavemanagementsystem.constant.LeaveType;
import com.leavemanagement.leavemanagementsystem.dto.ApplyLeaveRequest;
import com.leavemanagement.leavemanagementsystem.dto.BalancedLeaveResponseDTO;
import com.leavemanagement.leavemanagementsystem.dto.SuccessMessageDTO;
import com.leavemanagement.leavemanagementsystem.dto.UsersAvailedLeavesResponse;
import com.leavemanagement.leavemanagementsystem.entity.AvailedLeave;
import com.leavemanagement.leavemanagementsystem.entity.BalancedLeave;
import com.leavemanagement.leavemanagementsystem.entity.User;
import com.leavemanagement.leavemanagementsystem.exception.ExceptionProperties;
import com.leavemanagement.leavemanagementsystem.exception.FutureDateRequiredException;
import com.leavemanagement.leavemanagementsystem.exception.NoLeaveAvailableException;
import com.leavemanagement.leavemanagementsystem.exception.NoLeaveDetailsFoundException;
import com.leavemanagement.leavemanagementsystem.exception.UserNotFoundException;
import com.leavemanagement.leavemanagementsystem.repository.AvailedLeaveRepository;
import com.leavemanagement.leavemanagementsystem.repository.BalancedLeaveRepository;
import com.leavemanagement.leavemanagementsystem.repository.UserRepository;
import com.leavemanagement.leavemanagementsystem.service.api.UserService;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	UserService userService;

	@MockBean
	UserRepository userRepository;

	@MockBean
	BalancedLeaveRepository balanceLeaveRepository;

	@MockBean
	AvailedLeaveRepository availedLeaveRepository;

	@Value("${leavemanagement.successmessage}")
	private String successMessage;

	@Value("${leavemanagement.successcode}")
	private Integer successCode;

	AvailedLeave availedLeave, availedLeave1;
	User user;
	BalancedLeave balancedLeave;

	@BeforeEach
	public void setup() {
		user = new User();
		user.setId(1L);
		user.setName(TestData.USER_NAME);

		balancedLeave = new BalancedLeave();
		balancedLeave.setId(1L);
		balancedLeave.setAnnualLeaves(TestData.NO_OF_LEAVES);
		balancedLeave.setMyLeaves(TestData.NO_OF_LEAVES);
		balancedLeave.setRestrictedLeaves(TestData.NO_OF_LEAVES);
		balancedLeave.setUser(user);

		availedLeave = new AvailedLeave();
		availedLeave.setId(1L);
		availedLeave.setLeaveType(LeaveType.MY_LEAVE.toString());
		availedLeave.setApprovalStatus(TestData.LEAVE_APPROVAL);
		availedLeave.setFromDate(LocalDate.of(2020, 03, 27));
		availedLeave.setToDate(LocalDate.of(2020, 03, 28));
		availedLeave.setUser(user);

		availedLeave1 = new AvailedLeave();
		availedLeave1.setId(2L);
		availedLeave1.setLeaveType(LeaveType.RESTRICTED_LEAVE.toString());
		availedLeave1.setApprovalStatus(TestData.LEAVE_APPROVAL);
		availedLeave1.setFromDate(LocalDate.of(2020, 03, 29));
		availedLeave1.setToDate(LocalDate.of(2020, 03, 29));
		availedLeave1.setUser(user);
	}

	@Test
	public void testGetBalancedLeaveByUser() throws NoLeaveDetailsFoundException, UserNotFoundException {

		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		when(balanceLeaveRepository.findByUserId(user.getId())).thenReturn(Optional.of(balancedLeave));

		BalancedLeaveResponseDTO balancedLeaveResponseDTO = userService.getBalancedLeaveByUser(user.getId());

		assertThat(balancedLeaveResponseDTO.getAnnualLeaves()).isEqualTo(balancedLeave.getAnnualLeaves());
		assertThat(balancedLeaveResponseDTO.getId()).isEqualTo(balancedLeave.getId());
		assertThat(balancedLeaveResponseDTO.getMyLeaves()).isEqualTo(balancedLeave.getMyLeaves());
		assertThat(balancedLeaveResponseDTO.getRestrictedLeaves()).isEqualTo(balancedLeave.getRestrictedLeaves());

	}

	@Test
	public void testGetBalancedLeaveUserNotFound() throws NoLeaveDetailsFoundException, UserNotFoundException {

		when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> {
			userService.getBalancedLeaveByUser(user.getId());
		});

	}

	@Test
	public void testGetBalancedLeaveNoLeaveDetailsFound() throws NoLeaveDetailsFoundException, UserNotFoundException {

		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		when(balanceLeaveRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

		assertThrows(NoLeaveDetailsFoundException.class, () -> {
			userService.getBalancedLeaveByUser(user.getId());
		});

	}

	@Test
	public void testApplyLeave() throws NoLeaveAvailableException, NoLeaveDetailsFoundException, UserNotFoundException,
			FutureDateRequiredException {
		ApplyLeaveRequest applyLeaveRequest = new ApplyLeaveRequest();
		applyLeaveRequest.setFromDate(LocalDate.of(2020, 03, 27));
		applyLeaveRequest.setToDate(LocalDate.of(2020, 03, 28));
		applyLeaveRequest.setLeaveType(LeaveType.ANNUAL_LEAVE.toString());

		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		when(balanceLeaveRepository.findByUserId(user.getId())).thenReturn(Optional.of(balancedLeave));
		BalancedLeave newBalancedLeave = balancedLeave;
		newBalancedLeave.setAnnualLeaves(8);
		when(balanceLeaveRepository.save(balancedLeave)).thenReturn(newBalancedLeave);
		when(availedLeaveRepository.save(availedLeave)).thenReturn(availedLeave);

		SuccessMessageDTO successMessageDTO = userService.applyLeave(user.getId(), applyLeaveRequest);

		assertNotNull(successMessageDTO);
		assertThat(successMessageDTO.getMessage()).isEqualTo(successMessage);
		assertThat(successMessageDTO.getSuccessCode()).isEqualTo(successCode);

	}

	@Test
	public void testApplyNoLeaveAvailableException() throws NoLeaveAvailableException, NoLeaveDetailsFoundException,
			UserNotFoundException, FutureDateRequiredException {
		ApplyLeaveRequest applyLeaveRequest = new ApplyLeaveRequest();
		applyLeaveRequest.setFromDate(LocalDate.of(2020, 03, 27));
		applyLeaveRequest.setToDate(LocalDate.of(2020, 03, 28));
		applyLeaveRequest.setLeaveType(LeaveType.ANNUAL_LEAVE.toString());

		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		when(balanceLeaveRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

		assertThrows(NoLeaveDetailsFoundException.class, () -> {
			userService.applyLeave(user.getId(), applyLeaveRequest);
		});

	}

	@Test
	public void testApplyLeaveFutureDateException() throws NoLeaveAvailableException, NoLeaveDetailsFoundException,
			UserNotFoundException, FutureDateRequiredException {
		ApplyLeaveRequest applyLeaveRequest = new ApplyLeaveRequest();
		applyLeaveRequest.setFromDate(LocalDate.of(2020, 03, 27));
		applyLeaveRequest.setToDate(LocalDate.of(2020, 03, 26));
		applyLeaveRequest.setLeaveType(LeaveType.ANNUAL_LEAVE.toString());

		assertThrows(FutureDateRequiredException.class, () -> {
			userService.applyLeave(user.getId(), applyLeaveRequest);
		});

	}

	@Test
	public void testApplyNoLeavesException() throws NoLeaveAvailableException, NoLeaveDetailsFoundException,
			UserNotFoundException, FutureDateRequiredException {
		ApplyLeaveRequest applyLeaveRequest = new ApplyLeaveRequest();
		applyLeaveRequest.setFromDate(LocalDate.of(2020, 03, 27));
		applyLeaveRequest.setToDate(LocalDate.of(2020, 03, 29));
		applyLeaveRequest.setLeaveType(LeaveType.ANNUAL_LEAVE.toString());

		balancedLeave.setAnnualLeaves(2);

		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		when(balanceLeaveRepository.findByUserId(user.getId())).thenReturn(Optional.of(balancedLeave));

		assertThrows(NoLeaveAvailableException.class, () -> {
			userService.applyLeave(user.getId(), applyLeaveRequest);
		});

	}

	@Test
	public void testGetAvailedLeavesByUser() throws UserNotFoundException {
		List<AvailedLeave> availedList = new ArrayList<AvailedLeave>();
		availedList.add(availedLeave);
		availedList.add(availedLeave1);

		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		when(availedLeaveRepository.findByUserId(user.getId())).thenReturn(availedList);

		UsersAvailedLeavesResponse usersAvailedLeavesResponse = userService.getAvailedLeavesByUser(user.getId());

		assertNotNull(usersAvailedLeavesResponse);
		assertThat(usersAvailedLeavesResponse.getUserId()).isEqualTo(user.getId());
		assertThat(usersAvailedLeavesResponse.getUserName()).isEqualTo(user.getName());
		assertFalse(CollectionUtils.isEmpty(usersAvailedLeavesResponse.getAvailedLeavesResponse()));
		assertThat(usersAvailedLeavesResponse.getAvailedLeavesResponse().size()).isEqualTo(availedList.size());
	}

	@Test
	public void testGetAvailedLeavesForUserNotFoundException() throws UserNotFoundException {

		when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> {
			userService.getAvailedLeavesByUser(user.getId());
		});
		

		
	}

}
