package com.six.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.six.entities.Message;
import com.six.entities.User;
import com.six.entities.UserGroup;
import com.six.mapper.FriendshipMapper;
import com.six.mapper.GroupMapper;
import com.six.mapper.MessageMapper;
import com.six.mapper.UserMapper;

@Controller
@RequestMapping("/friend")
public class FriendController {
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	FriendshipMapper friendshipMapper;
	
	@Autowired
	GroupMapper groupMapper;
	
	@Autowired
	MessageMapper messageMapper;
	
	@RequestMapping("/add/{id}")
	public String addFriend(@PathVariable("id") String id, HttpSession session) {
		String nowId = (String) session.getAttribute("loginUser");
		User user = friendshipMapper.getFriend(nowId, id);
		if (user == null) {
			friendshipMapper.addFriend(nowId, id);
		}
		return "redirect:/home";
	}

	
	@GetMapping("/{id}")
	public String friendChat(@PathVariable("id") String id, HttpSession session, Model model) {
		String nowId = (String) session.getAttribute("loginUser");
		List<User> friends = friendshipMapper.getFriends(nowId);
		List<UserGroup> groups = groupMapper.getGroups(nowId);
		model.addAttribute("friends", friends);
		model.addAttribute("groups", groups);
		List<Message> messages = messageMapper.getMessages(id, nowId);
		model.addAttribute("chattingFriend", id);
		model.addAttribute("messages", messages);
		return "/home";
	}
	
	@PostMapping("/{id}")
	public String sendMessage(@PathVariable("id") String id, @RequestParam("msg") String msg, @RequestParam("file") MultipartFile file
					, HttpSession session, Model model) {
		String nowId = (String) session.getAttribute("loginUser");

		String fileName = file.getOriginalFilename();
		if (fileName.indexOf(".") != -1) {
			String type=fileName.substring(fileName.indexOf("."));
			String newName = "/FILE"+new Date().getTime()+type;
			System.out.println(fileName);
			String path = "/asserts/img/" + nowId + newName;
		    File tempFile=new File("D:/git/simple-project/src/main/resources/static" + path);
		    Message message = new Message(new Date(), path, nowId, id, true);
		    messageMapper.sendMessage(message);

		    try{
		        if (!tempFile.getParentFile().exists()){
		            tempFile.getParentFile().mkdirs();//创建父级文件路径
		            tempFile.createNewFile();//创建文件
		        }
		        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
		        file.transferTo(tempFile);
		        System.out.println("1");
		    }catch (IOException e){
		        e.printStackTrace();
		    }
		}
		if (!msg.equals("")) {
			Message message = new Message(new Date(), msg, nowId, id, false);
			messageMapper.sendMessage(message);
		}
		return "redirect:/friend/"+id;
	}
}
