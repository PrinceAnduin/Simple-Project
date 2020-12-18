package com.six.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.six.entities.GroupMessage;
import com.six.entities.User;
import com.six.entities.UserGroup;
import com.six.mapper.FriendshipMapper;
import com.six.mapper.GroupMapper;

@Controller
@RequestMapping("/group")
public class UserGroupController {
	@Autowired
	GroupMapper groupMapper;
	
	@Autowired
	FriendshipMapper friendshipMapper;
	
	@GetMapping("/add/{id}")
	public String addGroup(@PathVariable("id")int id, HttpSession session) {
		String nowId = (String) session.getAttribute("loginUser");
		List<UserGroup> groups = groupMapper.getGroups(nowId);
		boolean flag = false;
		for (int count = 0; count < groups.size(); count ++) {
			if (groups.get(count).getId() == id) {
				flag = true;
			}
		}
		if (!flag) {
			groupMapper.addUserToGroup(id, nowId);
		}
		return "redirect:/home";
	}
	
	@GetMapping("/{id}")
	public String friendChat(@PathVariable("id") int id, HttpSession session, Model model) {
		String nowId = (String) session.getAttribute("loginUser");
		groupMapper.cancelEite(id, nowId);
		List<User> friends = friendshipMapper.getFriends(nowId);
		List<UserGroup> groups = groupMapper.getGroups(nowId);
		model.addAttribute("friends", friends);
		model.addAttribute("groups", groups);
		
		List<GroupMessage> messages = groupMapper.getMessages(id);
		model.addAttribute("chattingGroup", id);
		model.addAttribute("messages", messages);
		return "/home";
	}
	
	@PostMapping("/{id}")
	public String sendMessage(@PathVariable("id") int id, @RequestParam("msg") String msg
					, HttpSession session, Model model) {
		String nowId = (String) session.getAttribute("loginUser");
		GroupMessage message = new GroupMessage(0, id, msg, nowId, new Date(), false);
		if (msg.indexOf("@all") != -1) {
			groupMapper.eiteAll(id);
		}
		groupMapper.sendMessage(message);
		return "redirect:/group/"+id;
	}
	
	@PostMapping("/new")
	public String findFriend(@RequestParam("groupName") String groupName,Model model, HttpSession session) {
		UserGroup group = new UserGroup();
		group.setGroupName(groupName);
		groupMapper.addGroup(group);
		int count = groupMapper.count();
		return "redirect:add/" + count;
	}
}
