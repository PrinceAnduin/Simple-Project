package com.six.controller;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.six.entities.SignUpUser;
import com.six.entities.User;
import com.six.mapper.UserMapper;
import com.sun.mail.util.MailSSLSocketFactory;

import lombok.extern.slf4j.Slf4j;

//QQ邮箱授权码：njtrgaulapzdbeac
@Slf4j
@Controller
@RequestMapping("/signupNow")
public class SignupController {
	SignUpUser signupUser;
	private String captcha;
	@Autowired
	private UserMapper userMapper;
	@GetMapping
	public String getting(Model model) {
		model.addAttribute("signupUser", new SignUpUser());
		return "/signup";
	}
	@PostMapping
	public String signUp(@Valid @ModelAttribute("signupUser")SignUpUser signupUser
						, Errors errors, Map<String, Object> map) throws GeneralSecurityException {
		//If there is an error, return
		if (errors.hasErrors()) {
			return "/signup";
		}
		if (!signupUser.getPassword().equals(signupUser.getComfirmedPassword())) {
			map.put("comfirmPassWordError", "both the input passwords must be consistent");
			return "/signup";
		}
		if(userMapper.getOne(signupUser.getId()) != null) {
			map.put("idError", "ID already exists");
			return "/signup";
		}
		this.signupUser = signupUser;
		//Create a captcha and sent it to user
		StringBuilder sb = new StringBuilder();
		for (int count = 0; count < 5; count ++) {
			int x = (int)((10 + 26) * Math.random());
			if (x < 10) {
				sb.append((char)('0'+x));
			}else {
				x = x - 10;
				sb.append((char)('A'+x));
			}
		}
		//record the email and captcha, used to verify email
		captcha = sb.toString();
		signupUser.setSentCaptcha(captcha);
		System.out.println(captcha);
		//sendMail(signupUser.getEmail());
		System.out.println(signupUser);
		return "/submitCaptcha";
	}
	@PostMapping("/submitCaptcha")
	public String verify(@ModelAttribute("signupUser")SignUpUser s
						,  Map<String, Object> map) {
		if (captcha.equals(s.getCaptcha())) {
			User user = new User();
			user.setId(signupUser.getId());
			user.setName(signupUser.getName());
			user.setEmail(signupUser.getEmail());
			user.setPassword(signupUser.getPassword());
			System.out.println(signupUser.getEmail());
			userMapper.insert(user);
			return "/index";
		}
		//if captcha is not true, put the message in map
		map.put("captchaError", "error in captcha");
		return "/submitCaptcha";
	}
	public void sendMail(String email) throws GeneralSecurityException {
		//reviser and sender
		String to = email;
		String from = "702638473@qq.com";
		//here is host of mail, I use QQ mail
		String host = "smtp.qq.com";
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.put("mail.smtp.auth","true");
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sf);
		
		Session session = Session.getInstance(properties,new Authenticator(){
	        @Override
			public PasswordAuthentication getPasswordAuthentication()
	        {
	        //user and password, remember: DO NOT PUSH PASSWORD TO GITHUB!!!! by zzp
	         return new PasswordAuthentication("702638473@qq.com", ""); //发件人邮件用户名、授权码
	        }
	    });
		
		try {
			//create a message to send HTML doc
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(from));
			
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Welcome To DragonHub!");
			//insert captcha here
			String htmlText = "    <table class=\"body\" width=\"100%\" style=\"background-color:#757070d5; min-width:600px\" bgcolor=\"#757070d5\">\r\n" + 
					"        <tr>\r\n" + 
					"        <td class=\"body\" align=\"center\" valign=\"top\" width=\"100%\" style=\"min-width:600px\">\r\n" + 
					"        <center>\r\n" + 
					"        <table width=\"100%\" style=\"min-width:600px;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\">\r\n" + 
					"        <tr>\r\n" + 
					"        <td align=\"center\">\r\n" + 
					"        <table width=\"560\" class=\"panel-padded\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"min-width:560px\">\r\n" + 
					"        <tr height=\"50\">\r\n" + 
					"        <td width=\"100%\" height=\"50\" style=\"line-height:1px; font-size:1px\"> </td>\r\n" + 
					"        </tr>\r\n" + 
					"\r\n" + 
					"        <tr>\r\n" + 
					"        <td width=\"560\" align=\"center\" style=\"font-family:arial,helvetica,sans-serif; font-weight:bold; mso-line-height-rule: exactly; font-size:40px; color:#313131; text-align:left; line-height:75px\">\r\n" + 
					"        <div style=\"text-align:center;line-height:70px\">Here is your captcha to verify your email address</div>\r\n" + 
					"        </td>\r\n" + 
					"        </tr>\r\n" + 
					"\r\n" + 
					"        <tr height=\"30\">\r\n" + 
					"            <td width=\"100%\" height=\"30\" style=\"line-height:1px; font-size:1px\"> </td>\r\n" + 
					"            </tr>\r\n" + 
					"            </table>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"\r\n" + 
					"        <tr>\r\n" + 
					"            <td width=\"560\" align=\"center\" bgcolor=\"#000000\"; style=\"font-family:arial,helvetica,sans-serif; font-weight:bold; font-size:50px; color:#313131; text-align:left; line-height:75px\">\r\n" + 
					"                <div  style=\"text-align:center; line-height:70px;color:  #c9aa71;\"><i>"+ captcha +"</i></div>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"        <tr>\r\n" + 
					"            <td align=\"center\">\r\n" + 
					"            <table width=\"560\" class=\"panel-padded\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"min-width:560px\">\r\n" + 
					"            <tr height=\"50\">\r\n" + 
					"            <td width=\"100%\" height=\"50\" style=\"line-height:1px; font-size:1px\"> </td>\r\n" + 
					"        </tr>\r\n" + 
					"        <tr>\r\n" + 
					"            <td width=\"560\" align=\"center\" style=\"font-family:arial,helvetica,sans-serif; font-weight:bold; mso-line-height-rule: exactly; font-size: 35px; color:#313131; text-align:left; line-height:75px\">\r\n" + 
					"            <div style=\"text-align:center;line-height:70px\">Thanks for using Dragon Hub</div>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"        <tr>\r\n" + 
					"            <td width=\"560\" align=\"center\" style=\"font-family:arial,helvetica,sans-serif; font-weight:bold; mso-line-height-rule: exactly; font-size: 20px; color:#313131; text-align:left; line-height:75px\">\r\n" + 
					"            <div style=\"text-align:center;line-height:70px\">mail service offerd by zzp</div>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"        </table>\r\n" + 
					"        </center>\r\n" + 
					"    </td>\r\n" + 
					"    </tr>\r\n" + 
					"    </table>";
			message.setContent(htmlText, "text/html; charset=UTF-8");
			Transport.send(message);
			System.out.println("Sent message successfully");
		} catch (MessagingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
