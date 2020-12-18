package com.six.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.six.entities.User;
import com.six.entities.UserGroup;
import com.six.mapper.FriendshipMapper;
import com.six.mapper.GroupMapper;
import com.six.mapper.MessageMapper;
import com.six.mapper.UserMapper;

@Controller
@RequestMapping("/manager")
public class ManagerController {
	@Autowired
	FriendshipMapper friendshipMapper;
	
	@Autowired
	GroupMapper groupMapper;
	
	@Autowired
	MessageMapper messageMapper;
	
	@Autowired
	UserMapper userMapper;
	@RequestMapping
	String home(HttpSession session, Model model) {
		List<User> users = userMapper.getAll();
		List<UserGroup> groups = groupMapper.getAll();
		model.addAttribute("users", users);
		model.addAttribute("groups", groups);
		return "manager";
	}
	
	@GetMapping("/user/{id}")
	public String UserManage(@PathVariable("id") String id, HttpSession session, Model model) {
		List<User> users = userMapper.getAll();
		List<UserGroup> groups = groupMapper.getAll();
		model.addAttribute("users", users);
		model.addAttribute("groups", groups);
		User user = userMapper.getOne(id);
		model.addAttribute("selectedUser", user);
		return "/manager";
	}
	
	@GetMapping("/user/delete/{id}")
	public String UserDelete(@PathVariable("id") String id, HttpSession session, Model model) {
		List<User> users = userMapper.getAll();
		List<UserGroup> groups = groupMapper.getAll();
		model.addAttribute("users", users);
		model.addAttribute("groups", groups);
		userMapper.delete(id);
		return "/manager";
	}
	
	@GetMapping("/group/{id}")
	public String GroupManager(@PathVariable("id") int id, HttpSession session, Model model) {
		List<User> users = userMapper.getAll();
		List<UserGroup> groups = groupMapper.getAll();
		model.addAttribute("users", users);
		model.addAttribute("groups", groups);
		UserGroup group = null;
		for (int count = 0; count < groups.size();count ++) {
			if (groups.get(count).getId() == id) {
				group = groups.get(count);
			}
		}
		model.addAttribute("selectedGroup", group);
		return "/manager";
	}
}
