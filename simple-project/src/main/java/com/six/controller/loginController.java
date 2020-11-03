package com.six.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class loginController {
	
	@PostMapping
	public String login(@RequestParam("userId") String userId,
						@RequestParam("password") String password,
						Map<String, Object> map, HttpSession session) {
		if (userId.equals("admin") && password.equals("123456")) {
			session.setAttribute("loginUser", "admin");
			return "redirect:home";
		}else {
			return "index";
		}
	}
}
