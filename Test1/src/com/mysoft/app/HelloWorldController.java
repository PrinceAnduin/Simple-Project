package com.mysoft.app;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.ajaxjs.web.mvc.controller.*;;

@Path("/SayHello") //定义URL路径
public class HelloWorldController implements IController {
	@GET //绑定HTTP GET，用户 GET /Say Hello时，执行该方法
	public String helloWorld() {
		return "html::Hello World!";
	}
}
