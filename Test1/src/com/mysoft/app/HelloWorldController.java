package com.mysoft.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.ajaxjs.web.mvc.controller.*;;

@Path("/SayHello") //����URL·��
public class HelloWorldController implements IController {
	@GET //��HTTP GET���û� GET /Say Helloʱ��ִ�и÷���
	public String helloWorld() {
		return "html::Hello World!";
	}
}
