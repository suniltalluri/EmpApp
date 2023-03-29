package com.fullStackApp.EmpManagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFound extends Exception{
	private String  message;

	public UserNotFound(String message) {
		super();
		this.message = message;
	}
	
}
