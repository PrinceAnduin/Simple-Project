package com.six.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpUser {
	@Size(min = 6, max = 10, message = "6-10 Characters")
	private String id;
	@Size(min = 1, max = 10, message = "1-10 Characters")
	private String name;
	@Size(min = 6, max = 15, message = "6-15 Characters")
	private String password;
	private String comfirmedPassword;
	@NotBlank(message = "REQUIRE")
	@Email(message = "error in your email format")
	private String email;
	private String captcha;
	private String sentCaptcha;
}
