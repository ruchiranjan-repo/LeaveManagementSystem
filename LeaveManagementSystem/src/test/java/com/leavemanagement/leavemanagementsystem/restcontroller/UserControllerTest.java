package com.leavemanagement.leavemanagementsystem.restcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.leavemanagement.leavemanagementsystem.TestData;
import com.leavemanagement.leavemanagementsystem.constant.LeaveType;
import com.leavemanagement.leavemanagementsystem.dto.ApplyLeaveRequest;
import com.leavemanagement.leavemanagementsystem.dto.BalancedLeaveResponseDTO;
import com.leavemanagement.leavemanagementsystem.dto.SuccessMessageDTO;
import com.leavemanagement.leavemanagementsystem.dto.UsersAvailedLeavesResponse;
import com.leavemanagement.leavemanagementsystem.entity.AvailedLeave;
import com.leavemanagement.leavemanagementsystem.entity.BalancedLeave;
import com.leavemanagement.leavemanagementsystem.entity.User;
import com.leavemanagement.leavemanagementsystem.exception.FutureDateRequiredException;
import com.leavemanagement.leavemanagementsystem.exception.NoLeaveAvailableException;
import com.leavemanagement.leavemanagementsystem.exception.NoLeaveDetailsFoundException;
import com.leavemanagement.leavemanagementsystem.exception.UserNotFoundException;
import com.leavemanagement.leavemanagementsystem.service.api.UserService;



@SpringBootTest
public class UserControllerTest {
	
	@Autowired
	UserRestController userRestController;
	
	@MockBean
	UserService  userService;
	
	AvailedLeave availedLeave, availedLeave1;
	User user;
	BalancedLeave balancedLeave;
	ApplyLeaveRequest applyLeaveRequest;
	

	@Value("${leavemanagement.successmessage}")
	private String successMessage;

	@Value("${leavemanagement.successcode}")
	private Integer successCode;
	
	@BeforeEach
	public void setup()
	{
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
		
		 applyLeaveRequest = new ApplyLeaveRequest();
		applyLeaveRequest.setFromDate(LocalDate.of(2020, 03, 27));
		applyLeaveRequest.setToDate(LocalDate.of(2020, 03, 28));
		applyLeaveRequest.setLeaveType(LeaveType.ANNUAL_LEAVE.toString());
	}
	
	@Test
	public void testGetBalancedLeavesByUserId() throws NoLeaveDetailsFoundException, UserNotFoundException
	{
		BalancedLeaveResponseDTO balancedLeaveResponseDTO = new BalancedLeaveResponseDTO();
		balancedLeaveResponseDTO.setId(1L);
		balancedLeaveResponseDTO.setAnnualLeaves(TestData.NO_OF_LEAVES);
		balancedLeaveResponseDTO.setMyLeaves(TestData.NO_OF_LEAVES);
		balancedLeaveResponseDTO.setRestrictedLeaves(TestData.NO_OF_LEAVES);
		
		when(userService.getBalancedLeaveByUser(user.getId())).thenReturn(balancedLeaveResponseDTO);
		
		ResponseEntity<BalancedLeaveResponseDTO> response= userRestController.getBalancedLeavesByUserId(user.getId());
		
		assertThat(response.getBody().getId()).isEqualTo(balancedLeave.getId());
		assertThat(response.getBody().getAnnualLeaves()).isEqualTo(balancedLeave.getAnnualLeaves());
		assertThat(response.getBody().getMyLeaves()).isEqualTo(balancedLeave.getMyLeaves());		
		assertThat(response.getBody().getRestrictedLeaves()).isEqualTo(balancedLeave.getRestrictedLeaves());
	}
	
	@Test
	public void testGetBalancedLeavesNoLeaveDetailsException() throws NoLeaveDetailsFoundException, UserNotFoundException
	{
				
		when(userService.getBalancedLeaveByUser(user.getId())).thenThrow(NoLeaveDetailsFoundException.class);
		
		assertThrows( NoLeaveDetailsFoundException.class,()->{userRestController.getBalancedLeavesByUserId(user.getId());});
		
	
	}
	
	@Test
	public void testGetBalancedLeavesUserNotFoundException() throws NoLeaveDetailsFoundException, UserNotFoundException
	{
				
		when(userService.getBalancedLeaveByUser(user.getId())).thenThrow(UserNotFoundException.class);
		
		assertThrows( UserNotFoundException.class,()->{userRestController.getBalancedLeavesByUserId(user.getId());});
		
	
	}
	
	@Test
	public void testApplyLeave() throws NoLeaveAvailableException, NoLeaveDetailsFoundException, UserNotFoundException, FutureDateRequiredException
	{
		SuccessMessageDTO successMessageDTO= new SuccessMessageDTO(successMessage, successCode);
		when(userService.applyLeave(user.getId(),applyLeaveRequest)).thenReturn(successMessageDTO);
		
		ResponseEntity<SuccessMessageDTO> responseDTO  = userRestController.applyLeaves(user.getId(),applyLeaveRequest);
		
		assertNotNull(responseDTO.getBody());
		assertThat(responseDTO.getBody().getMessage()).isEqualTo(successMessage);
		assertThat(responseDTO.getBody().getSuccessCode()).isEqualTo(successCode);
	}
	@Test
	public void testApplyLeaveNoLeaveAvailable() throws NoLeaveAvailableException, NoLeaveDetailsFoundException, UserNotFoundException, FutureDateRequiredException
	{
		
		when(userService.applyLeave(user.getId(),applyLeaveRequest)).thenThrow(NoLeaveAvailableException.class);
		
		assertThrows( NoLeaveAvailableException.class,()->{userRestController.applyLeaves(user.getId(),applyLeaveRequest);});
	}	
	
	@Test
	public void testApplyLeaveNoLeaveDetailsAvailable() throws NoLeaveAvailableException, NoLeaveDetailsFoundException, UserNotFoundException, FutureDateRequiredException
	{
		
		when(userService.applyLeave(user.getId(),applyLeaveRequest)).thenThrow(NoLeaveDetailsFoundException.class);
		
		assertThrows( NoLeaveDetailsFoundException.class,()->{userRestController.applyLeaves(user.getId(),applyLeaveRequest);});
	}	
	
	@Test
	public void testApplyLeaveUserNotFound() throws NoLeaveAvailableException, NoLeaveDetailsFoundException, UserNotFoundException, FutureDateRequiredException
	{
		
		when(userService.applyLeave(user.getId(),applyLeaveRequest)).thenThrow(UserNotFoundException.class);
		
		assertThrows( UserNotFoundException.class,()->{userRestController.applyLeaves(user.getId(),applyLeaveRequest);});
	}	
	
	@Test
	public void testApplyFutureDateRequired() throws NoLeaveAvailableException, NoLeaveDetailsFoundException, UserNotFoundException, FutureDateRequiredException
	{
		
		when(userService.applyLeave(user.getId(),applyLeaveRequest)).thenThrow(FutureDateRequiredException.class);
		
		assertThrows( FutureDateRequiredException.class,()->{userRestController.applyLeaves(user.getId(),applyLeaveRequest);});
	}
	
	@Test
	public void testGetAvailedLeavesByUser() throws UserNotFoundException {
		
		UsersAvailedLeavesResponse usersAvailedLeavesResponse= new UsersAvailedLeavesResponse();
		usersAvailedLeavesResponse.setUserId(user.getId());
		usersAvailedLeavesResponse.setUserName(user.getName());
		
		when(userService.getAvailedLeavesByUser(user.getId())).thenReturn(usersAvailedLeavesResponse);

		ResponseEntity<UsersAvailedLeavesResponse> response= userRestController.getAvailedLeaves(user.getId());

		assertNotNull(response);
		assertThat(response.getBody().getUserId()).isEqualTo(user.getId());
		assertThat(response.getBody().getUserName()).isEqualTo(user.getName());
		
	}
	
	@Test
	public void testGetAvailedLeaveUserNotFound() throws  UserNotFoundException
	{
		
		when(userService.getAvailedLeavesByUser(user.getId())).thenThrow(UserNotFoundException.class);
		
		assertThrows( UserNotFoundException.class,()->{userRestController.getAvailedLeaves(user.getId());});
	}	
	
	

}
