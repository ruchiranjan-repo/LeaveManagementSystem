package com.leavemanagement.leavemanagementsystem.service.api;

import com.leavemanagement.leavemanagementsystem.dto.ApplyLeaveRequest;
import com.leavemanagement.leavemanagementsystem.dto.BalancedLeaveResponseDTO;
import com.leavemanagement.leavemanagementsystem.dto.SuccessMessageDTO;
import com.leavemanagement.leavemanagementsystem.dto.UsersAvailedLeavesResponse;
import com.leavemanagement.leavemanagementsystem.exception.FutureDateRequiredException;
import com.leavemanagement.leavemanagementsystem.exception.NoLeaveAvailableException;
import com.leavemanagement.leavemanagementsystem.exception.NoLeaveDetailsFoundException;
import com.leavemanagement.leavemanagementsystem.exception.UserNotFoundException;

/**
 * User service class.
 * 
 * @author Ruchi
 *
 */

public interface UserService {

	BalancedLeaveResponseDTO getBalancedLeaveByUser(Long id) throws NoLeaveDetailsFoundException, UserNotFoundException;

	SuccessMessageDTO applyLeave(Long id, ApplyLeaveRequest applyLeaveRequest) throws NoLeaveAvailableException,
			NoLeaveDetailsFoundException, UserNotFoundException, FutureDateRequiredException;

	UsersAvailedLeavesResponse getAvailedLeavesByUser(Long id) throws UserNotFoundException;

}
