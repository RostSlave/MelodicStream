package com.example.kursovoi.controllers;


import com.example.kursovoi.models.Role;
import com.example.kursovoi.models.User;
import com.example.kursovoi.repositories.UsersRepository;
import com.example.kursovoi.service.UserService;
import com.example.kursovoi.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {


	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UsersRepository usersRepository;
	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}

	public String getCurrentUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	@ModelAttribute("user")
	public UserRegistrationDto UserRegistrationDto()
	{
		return new UserRegistrationDto();
	}

	@GetMapping
	public String showRegistrationForm()
	{
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto)
	{
		User user=new User(registrationDto.getName(),
				registrationDto.getSurname(),
				registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()),
				Collections.singletonList(new Role("ROLE_USER")));
		user.setRoyalty("50%");
		usersRepository.save(user);
		return "redirect:/registration?success";
	}
	
}
