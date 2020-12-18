package com.six.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class GroupMessage {
	int id;
	int groupId;
	String msg;
	String sender;
	String sendDay;
	boolean img;
	public GroupMessage(int id, int groupId, String msg, String sender, String sendDay, boolean img) {
		super();
		this.id = id;
		this.groupId = groupId;
		this.msg = msg;
		this.sender = sender;
		this.sendDay = sendDay;
		this.img = img;
	}
	
	public GroupMessage(int id, int groupId, String msg, String sender, Date sendDay, boolean img) {
		super();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.id = id;
		this.groupId = groupId;
		this.msg = msg;
		this.sender = sender;
		this.sendDay = sdf.format(sendDay);
		this.img = img;
	}
}
