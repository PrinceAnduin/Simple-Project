package com.six.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.six.entities.User;
import com.six.entities.UserGroup;
import com.six.mapper.FriendshipMapper;
import com.six.mapper.GroupMapper;
import com.six.mapper.MessageMapper;
import com.six.mapper.UserMapper;

@Controller
@RequestMapping("/home")
public class HomeController {
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
		String nowId = (String) session.getAttribute("loginUser");
		List<User> friends = friendshipMapper.getFriends(nowId);
		List<UserGroup> groups = groupMapper.getGroups(nowId);
		model.addAttribute("friends", friends);
		model.addAttribute("groups", groups);
		return "home";
	}
	
	@GetMapping("/setVisiable")
	String visiable(HttpSession session) {
		String nowId = (String) session.getAttribute("loginUser");
		userMapper.setVisiable(nowId);
		return "home";
	}
	
	@PostMapping("/search")
	public String findFriend(@RequestParam("searchString") String id,Model model, HttpSession session) {
		User user = userMapper.searchOne(id);
		String nowId = (String) session.getAttribute("loginUser");
		User friendUser = friendshipMapper.getFriend(nowId, id);
		if (friendUser == null) {
			model.addAttribute("isFriend", false);
		}else {
			model.addAttribute("isFriend", true);
		}
		
		List<UserGroup> groups = groupMapper.searchGroups(id);
		model.addAttribute("searchedUser", user);
		model.addAttribute("searchedGroup", groups);
		return "/home";
	}
}
