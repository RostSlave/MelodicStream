package com.example.kursovoi.service;


import com.example.kursovoi.models.User;
import com.example.kursovoi.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

	User save(UserRegistrationDto registrationDto);

	List<User> allUser();
	void saveUserById(User user);
	User getUserById(long id);
	void deleteUserById(long id);
	User getUserByEmail(String email);
	List<User> findByEmail(String email);
}
