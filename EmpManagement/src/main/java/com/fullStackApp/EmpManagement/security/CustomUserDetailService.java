package com.fullStackApp.EmpManagement.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fullStackApp.EmpManagement.entity.Users;
import com.fullStackApp.EmpManagement.exception.UserNotFound;
import com.fullStackApp.EmpManagement.repository.UsersRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UsersRepository usersRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Users user = null;
		try {
			user = usersRepository.findByEmail(email).orElseThrow(
					()-> new UserNotFound(String.format("user not found with this Email : %s", email)));
		} catch (UserNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Set<String> roles = new HashSet<>();
		roles.add("ROLE_ADMIN");
		return new User(user.getEmail(),user.getPassword(),getAuthorities(roles));
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(Set<String> roles){
		return roles.stream().map(
				role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}

	
}
