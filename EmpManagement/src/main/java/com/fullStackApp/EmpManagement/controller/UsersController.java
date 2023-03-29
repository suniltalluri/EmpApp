package com.fullStackApp.EmpManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullStackApp.EmpManagement.payload.UserDto;
import com.fullStackApp.EmpManagement.service.UsersService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000" )
public class UsersController {
	
	@Autowired
	private UsersService usersService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(
			@RequestBody UserDto userDto){
		return new ResponseEntity<UserDto>(usersService.createUser(userDto),HttpStatus.CREATED);
	}
	
	@GetMapping("/AllUsers")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return new ResponseEntity<List<UserDto>>(usersService.getAllUsers(),HttpStatus.OK);
	}
	
	@GetMapping("/AllUsers/{userId}")
	public ResponseEntity<UserDto> getUserById(
			@PathVariable("userId") Long userId
			){
		return new ResponseEntity<UserDto>(usersService.getUserById(userId),HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<UserDto> deleteUserById(
			@PathVariable("userId") Long userId){
		return new ResponseEntity<UserDto>(usersService.deleteUserById(userId),HttpStatus.OK);
	}
	
	
}