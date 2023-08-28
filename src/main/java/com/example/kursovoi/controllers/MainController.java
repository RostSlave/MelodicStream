package com.example.kursovoi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String home() {
		return "index";
	}

	@GetMapping("/contact")
	public String contact(){return "contact";}

	@GetMapping("/services")
	public String services() {
		return "services";
	}

	@GetMapping("/info")
	public String info() {
		return "/info";
	}

	@GetMapping("/artists")
	public String artists() {
		return "/artists";
	}
}
