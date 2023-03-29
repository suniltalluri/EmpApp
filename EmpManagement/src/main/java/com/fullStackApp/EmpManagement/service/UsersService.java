package com.fullStackApp.EmpManagement.service;

import java.util.List;

import com.fullStackApp.EmpManagement.payload.UserDto;

public interface UsersService {
	public UserDto createUser(UserDto userDto);

	public UserDto deleteUserById(Long userId);

	public List<UserDto> getAllUsers();

	public UserDto getUserById(Long userId);
}
