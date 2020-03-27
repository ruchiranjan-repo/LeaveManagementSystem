package com.leavemanagement.leavemanagementsystem.service.impl;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.leavemanagement.leavemanagementsystem.constant.LeaveType;
import com.leavemanagement.leavemanagementsystem.dto.ApplyLeaveRequest;
import com.leavemanagement.leavemanagementsystem.dto.AvailedLeavesResponse;
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

/**
 * Implementation class for user service.
 * 
 * @author Ruchi
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	BalancedLeaveRepository balanceLeaveRepository;

	@Autowired
	AvailedLeaveRepository availedLeaveRepository;

	@Autowired
	ExceptionProperties exceptionProperties;

	@Value("${leavemanagement.successmessage}")
	private String successMessage;

	@Value("${leavemanagement.successcode}")
	private Integer successCode;

	Logger log = Logger.getLogger(UserServiceImpl.class);

	/**
	 * Method used to check the details of balanced leave.
	 * 
	 * @param id user id
	 * @throws NoLeaveDetailsFoundException noLeaveDetailsFoundException
	 * @throws UserNotFoundException        userNotFoundException
	 */
	@Override
	public BalancedLeaveResponseDTO getBalancedLeaveByUser(Long id)
			throws NoLeaveDetailsFoundException, UserNotFoundException {
		findUserByUserId(id);
		BalancedLeave balancedLeave = findBalancedLeaveByUserId(id);
		BalancedLeaveResponseDTO balancedLeaveResponseDTO = new BalancedLeaveResponseDTO(balancedLeave.getId(),
				balancedLeave.getAnnualLeaves(), balancedLeave.getMyLeaves(), balancedLeave.getRestrictedLeaves());
		log.info("Balance leave details found for user with user id " + id);

		return balancedLeaveResponseDTO;
	}

	/**
	 * Method used to apply the leave.
	 * 
	 * @param id                userId
	 * @param ApplyLeaveRequest applyLeaveRequest
	 * @throws NoLeaveAvailableException
	 * @throws UserNotFoundException
	 * @throws NoLeaveDetailsFoundException
	 * @throws FutureDateRequiredException
	 * 
	 * 
	 */

	@Override
	public SuccessMessageDTO applyLeave(Long id, ApplyLeaveRequest applyLeaveRequest) throws NoLeaveAvailableException,
			NoLeaveDetailsFoundException, UserNotFoundException, FutureDateRequiredException {

		if (applyLeaveRequest.getToDate().isBefore(applyLeaveRequest.getFromDate())) {
			log.warn("to date should be after from date.");
			throw new FutureDateRequiredException(exceptionProperties.getFutureDateRequiredErrorMessage(),
					exceptionProperties.getFutureDateRequiredErrorCode());
		}
		User user = findUserByUserId(id);
		BalancedLeave balancedLeave = findBalancedLeaveByUserId(id);
		Long numberOfDays = ChronoUnit.DAYS.between(applyLeaveRequest.getFromDate(),
				applyLeaveRequest.getToDate().plusDays(1));

		if (applyLeaveRequest.getLeaveType().equals(LeaveType.ANNUAL_LEAVE.toString())
				&& balancedLeave.getAnnualLeaves() >= numberOfDays) {
			balancedLeave.setAnnualLeaves(balancedLeave.getAnnualLeaves() - numberOfDays.intValue());
		} else if (applyLeaveRequest.getLeaveType().equals(LeaveType.MY_LEAVE.toString())
				&& balancedLeave.getMyLeaves() >= numberOfDays) {
			balancedLeave.setMyLeaves(balancedLeave.getMyLeaves() - numberOfDays.intValue());
		} else if (applyLeaveRequest.getLeaveType().equals(LeaveType.RESTRICTED_LEAVE.toString())
				&& balancedLeave.getRestrictedLeaves() >= numberOfDays) {
			balancedLeave.setRestrictedLeaves(balancedLeave.getRestrictedLeaves() - numberOfDays.intValue());
		} else {
			log.warn(" No sufficient leave available for the user id: " + id);
			throw new NoLeaveAvailableException(
					new StringBuffer(exceptionProperties.getNoLeaveAvailableErrorMessage()).append(id).toString(),
					exceptionProperties.getNoLeaveAvailableErrorCode());
		}
		balanceLeaveRepository.save(balancedLeave);
		AvailedLeave availedLeave = new AvailedLeave(applyLeaveRequest.getLeaveType(), applyLeaveRequest.getFromDate(),
				applyLeaveRequest.getToDate(), "APPROVED", user);
		availedLeaveRepository.save(availedLeave);
		log.info("Leave applied successfully for user id: " + id);

		return new SuccessMessageDTO(successMessage, successCode);

	}

	/**
	 * Method to see the list of availed leaves of the user.
	 * 
	 * @param id user id
	 * @throws UserNotFoundException userNotFoundException
	 */

	@Override
	public UsersAvailedLeavesResponse getAvailedLeavesByUser(Long id) throws UserNotFoundException {
		User user = findUserByUserId(id);
		List<AvailedLeave> availedLeaves = availedLeaveRepository.findByUserId(id);
		List<AvailedLeavesResponse> availedLeavesResponses = new ArrayList<AvailedLeavesResponse>();

		if (!CollectionUtils.isEmpty(availedLeaves)) {
			
			for (AvailedLeave availedLeave : availedLeaves) {
				AvailedLeavesResponse availedLeavesResponse = new AvailedLeavesResponse();
				BeanUtils.copyProperties(availedLeave, availedLeavesResponse);
				Long noOfDays = ChronoUnit.DAYS.between(availedLeavesResponse.getFromDate(),
						availedLeavesResponse.getToDate().plusDays(1));
				availedLeavesResponse.setNoOfDays(noOfDays.intValue());
				availedLeavesResponses.add(availedLeavesResponse);
			}

		}

		UsersAvailedLeavesResponse usersAvailedLeavesResponse = new UsersAvailedLeavesResponse(id, user.getName(),
				availedLeavesResponses);
		log.info("Leaves availed by the user with user id " + id + " is :" + availedLeavesResponses.size());

		return usersAvailedLeavesResponse;
	}

	private User findUserByUserId(Long id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {

			log.warn("User with provided user id " + id + " does not exists.");
			throw new UserNotFoundException(
					new StringBuffer(exceptionProperties.getUserNotFoundErrorMessage()).append(id).toString(),
					exceptionProperties.getUserNotFoundErrorCode());

		}
		log.info("User details found for user id:" + id + ".");
		return user.get();
	}

	private BalancedLeave findBalancedLeaveByUserId(Long id) throws NoLeaveDetailsFoundException {
		Optional<BalancedLeave> balancedLeave = balanceLeaveRepository.findByUserId(id);
		if (!balancedLeave.isPresent()) {
			log.warn("No leave details found for user with provided user id " + id + ".");
			throw new NoLeaveDetailsFoundException(
					new StringBuffer(exceptionProperties.getNoLeaveDetailsErrorMessage()).append(id).toString(),
					exceptionProperties.getNoLeaveDetailsErrorCode());
		}
		log.info("Balanced leave details found for user id:" + id + ".");
		return balancedLeave.get();
	}

}
