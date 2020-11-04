package com.six.controller;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.mail.util.MailSSLSocketFactory;

//QQ邮箱授权码：njtrgaulapzdbeac
@Controller
@RequestMapping("/signup")
public class SignupController {
	@PostMapping
	public void signUp() throws GeneralSecurityException {
		sendMail();
	}
	
	public void sendMail() throws GeneralSecurityException {
		String to = "2971145543@qq.com";
		String from = "702638473@qq.com";
		String host = "smtp.qq.com";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.auth","true");
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);
		
		Session session = Session.getDefaultInstance(properties,new Authenticator(){
	        @Override
			public PasswordAuthentication getPasswordAuthentication()
	        {
	         return new PasswordAuthentication("702638473@qq.com", "njtrgaulapzdbeac"); //发件人邮件用户名、授权码
	        }
	    });
		
		try {
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(from));
			
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			message.setSubject("点到为止");
			String htmlText = "<h1>点到为止</h1><br><p>  按传统编程的点到为止，这个邮件已经发出去了。</p><br><a href=\"https://www.bilibili.com/bangumi/play/ss25839/?from=search&seid=6207569350688875272\">點硪看高清美女 果 衣视频</a>";
			message.setContent(htmlText, "text/html; charset=UTF-8");
			Transport.send(message);
			System.out.println("Sent message successfully");
		} catch (MessagingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
