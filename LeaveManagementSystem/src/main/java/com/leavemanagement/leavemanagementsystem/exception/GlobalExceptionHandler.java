package com.leavemanagement.leavemanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.leavemanagement.leavemanagementsystem.dto.ExceptionMessageDTO;

/**
 * Global Exception handler class for Leave Management system.
 * 
 * @author Ruchi
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NoLeaveDetailsFoundException.class)
	public ResponseEntity<ExceptionMessageDTO> noLeaveDetailsExceptionHandler(
			NoLeaveDetailsFoundException noLeaveDetailsFoundException) {
		ExceptionMessageDTO exceptionMessageDTO = new ExceptionMessageDTO(noLeaveDetailsFoundException.getMessage(),
				noLeaveDetailsFoundException.getErrorCode());
		return new ResponseEntity<>(exceptionMessageDTO, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ExceptionMessageDTO> userNotFoundExceptionHandler(
			UserNotFoundException userNotFoundException) {
		ExceptionMessageDTO exceptionMessageDTO = new ExceptionMessageDTO(userNotFoundException.getMessage(),
				userNotFoundException.getErrorCode());
		return new ResponseEntity<>(exceptionMessageDTO, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(NoLeaveAvailableException.class)
	public ResponseEntity<ExceptionMessageDTO> noLeaveAvailableExceptionHandler(
			NoLeaveAvailableException noLeaveAvailableException) {
		ExceptionMessageDTO exceptionMessageDTO = new ExceptionMessageDTO(noLeaveAvailableException.getMessage(),
				noLeaveAvailableException.getErrorCode());
		return new ResponseEntity<>(exceptionMessageDTO, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(FutureDateRequiredException.class)
	public ResponseEntity<ExceptionMessageDTO> futureDateRequiredExceptionHandler(
			FutureDateRequiredException futureDateRequiredException) {
		ExceptionMessageDTO exceptionMessageDTO = new ExceptionMessageDTO(futureDateRequiredException.getMessage(),
				futureDateRequiredException.getErrorCode());
		return new ResponseEntity<>(exceptionMessageDTO, HttpStatus.BAD_REQUEST);

	}

}
