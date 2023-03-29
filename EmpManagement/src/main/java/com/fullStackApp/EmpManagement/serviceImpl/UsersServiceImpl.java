package com.fullStackApp.EmpManagement.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullStackApp.EmpManagement.entity.Users;
import com.fullStackApp.EmpManagement.exception.UserNotFound;
import com.fullStackApp.EmpManagement.payload.UserDto;
import com.fullStackApp.EmpManagement.repository.UsersRepository;
import com.fullStackApp.EmpManagement.service.UsersService;

@Service
public class UsersServiceImpl  implements UsersService{
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UsersRepository usersRepository;
	
	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		Users user = modelMapper.map(userDto, Users.class);
		Users savedUser = usersRepository.save(user);
		return modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto deleteUserById(Long userId) {
		
		Users user = null;
		try {
			user = usersRepository.findById(userId).orElseThrow(
						()-> new UserNotFound(String.format("user id %d is not found", userId)));
		} catch (UserNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 usersRepository.deleteById(userId);
		return modelMapper.map(user,UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<Users> users = usersRepository.findAll();
		return users.stream().map(
				user ->modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
	}

	@Override
	public UserDto getUserById(Long userId) {
		try {
			Users user = usersRepository.findById(userId).orElseThrow(
					()-> new UserNotFound(String.format("user id %d is not found", userId)));
		} catch (UserNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Optional<Users> FetchedUser = usersRepository.findById(userId);
		return modelMapper.map(FetchedUser, UserDto.class);
	}

}
