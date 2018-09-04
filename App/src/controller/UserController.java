package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	//主页路径
	@RequestMapping("/manager/login")
	public String login(){
		System.out.println(2);
		return "backendlogin";
	}
	//测试
	@RequestMapping("/ceshi")
	public String aaaa(){
		System.out.println(2);
		return "backend/appcheck";
	}
}
