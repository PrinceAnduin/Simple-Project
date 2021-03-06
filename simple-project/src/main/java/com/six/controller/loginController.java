package com.six.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.six.entities.User;
import com.six.mapper.UserMapper;

@Controller
@RequestMapping("/login")
public class loginController {
	@Autowired
	UserMapper userMapper;
	@PostMapping
	public String login(@RequestParam("userId") String userId,
						@RequestParam("password") String password,
						Map<String, Object> map, HttpSession session, Model model) {
		User loginUser = userMapper.getOne(userId);
		if (userId.equals("admin") && password.equals("123456")) {
			session.setAttribute("loginUser", "admin");
			return "redirect:manager";
		} else if (loginUser != null && loginUser.getPassword().equals(password)){
			session.setAttribute("loginUser", loginUser.getId());
			return "redirect:home";
		}
		else {
			return "index";
		}
	}
}
