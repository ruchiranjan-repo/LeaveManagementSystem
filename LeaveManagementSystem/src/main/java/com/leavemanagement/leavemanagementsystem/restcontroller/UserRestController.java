package com.leavemanagement.leavemanagementsystem.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leavemanagement.leavemanagementsystem.dto.ApplyLeaveRequest;
import com.leavemanagement.leavemanagementsystem.dto.BalancedLeaveResponseDTO;
import com.leavemanagement.leavemanagementsystem.dto.SuccessMessageDTO;
import com.leavemanagement.leavemanagementsystem.dto.UsersAvailedLeavesResponse;
import com.leavemanagement.leavemanagementsystem.exception.FutureDateRequiredException;
import com.leavemanagement.leavemanagementsystem.exception.NoLeaveAvailableException;
import com.leavemanagement.leavemanagementsystem.exception.NoLeaveDetailsFoundException;
import com.leavemanagement.leavemanagementsystem.exception.UserNotFoundException;
import com.leavemanagement.leavemanagementsystem.service.api.UserService;

/**
 * Rest controller for user services.
 * 
 * @author Ruchi
 *
 */

@RestController
@RequestMapping(value = "/users/{userId}")
public class UserRestController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/leaves", method = RequestMethod.GET)
	public ResponseEntity<BalancedLeaveResponseDTO> getBalancedLeavesByUserId(@PathVariable("userId") Long userId)
			throws NoLeaveDetailsFoundException, UserNotFoundException {

		BalancedLeaveResponseDTO balancedLeaveResponseDTO = userService.getBalancedLeaveByUser(userId);
		return new ResponseEntity<>(balancedLeaveResponseDTO, HttpStatus.OK);

	}

	@RequestMapping(value = "/leaves", method = RequestMethod.POST)
	public ResponseEntity<SuccessMessageDTO> applyLeaves(@PathVariable("userId") Long userId,
			@RequestBody ApplyLeaveRequest applyLeaveRequest) throws NoLeaveDetailsFoundException,
			UserNotFoundException, NoLeaveAvailableException, FutureDateRequiredException {

		SuccessMessageDTO successMessageDTO = userService.applyLeave(userId, applyLeaveRequest);
		return new ResponseEntity<>(successMessageDTO, HttpStatus.CREATED);

	}

	@RequestMapping(value = "/availedleaves", method = RequestMethod.GET)
	public ResponseEntity<UsersAvailedLeavesResponse> applyLeaves(@PathVariable("userId") Long userId)
			throws UserNotFoundException {

		UsersAvailedLeavesResponse usersAvailedLeavesResponse = userService.getAvailedLeavesByUser(userId);
		return new ResponseEntity<>(usersAvailedLeavesResponse, HttpStatus.OK);

	}

}
