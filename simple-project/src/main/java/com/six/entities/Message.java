package com.six.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;

@Data
public class Message {
	String sendDay;
	String msg;
	String sender;
	String receiver;
	boolean img;
	public Message(String sendDay, String msg, String sender, String receiver, boolean img) {
		super();
		this.sendDay = sendDay;
		this.msg = msg;
		this.sender = sender;
		this.receiver = receiver;
		this.img = img;
	}
	
	public Message(Date sendDay, String msg, String sender, String receiver, boolean img) {
		super();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.sendDay = sdf.format(sendDay);
		this.msg = msg;
		this.sender = sender;
		this.receiver = receiver;
		this.img = img;
	}
}
