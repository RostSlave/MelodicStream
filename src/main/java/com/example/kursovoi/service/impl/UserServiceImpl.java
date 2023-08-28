package com.example.kursovoi.service.impl;

import com.example.kursovoi.models.Role;
import com.example.kursovoi.models.User;
import com.example.kursovoi.repositories.UsersRepository;
import com.example.kursovoi.service.UserService;
import com.example.kursovoi.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


	private UsersRepository usersRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UsersRepository usersRepository) {
		super();
		this.usersRepository = usersRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user=new User(registrationDto.getName(),
							registrationDto.getSurname(),
							registrationDto.getEmail(),
							passwordEncoder.encode(registrationDto.getPassword()),
				Collections.singletonList(new Role("ROLE_USER")));
		
		return usersRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		
		User user= usersRepository.findByEmail(username);
		System.out.println(usersRepository.findAll());
		
		if(user==null)
		{
			throw new UsernameNotFoundException("Invalid Username or password");			
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	
	}

	@Override
	public List<User> allUser(){
		return usersRepository.findAll();
	}


	@Override
	public void saveUserById(User user) {
		this.usersRepository.save(user);
	}

	@Override
	public void deleteUserById(long id) {
		this.usersRepository.deleteById(id);
	}

	@Override
	public User getUserById(long id) {
		Optional<User> optional = usersRepository.findById(id);
		User user = null;
		if (optional.isPresent()) {
			user = optional.get();
		} else {
			throw new RuntimeException(" User not found for id :: " + id);
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {

		User user = usersRepository.findByEmail(email);
		if(user==null)
		{
			throw new UsernameNotFoundException("User not found for email: " + email);
		}
		return user;
	}

	@Override
	public List<User> findByEmail(String keyword) {
			List<User> userList = usersRepository.findByEmailAndFirstNameAndLastName(keyword);
		return userList;
	}
}