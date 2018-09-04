package controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import service.UserService;

import dao.UserDao;
import entity.BackendUser;
import entity.DevUser;


import service.UserService;


@Controller
public class UserController {
	@Autowired
	private UserService userService;
	//主页路径
	@Autowired
	private UserDao dao;
	@RequestMapping("/manager/login")
	public String login(){
		return "backendlogin";	
	}
	@RequestMapping("/dev/login")
	public String login2(){
		return "devlogin";	
	}
	@RequestMapping("/manager/dologin")
	public String dologin(HttpServletRequest request,HttpSession session){
		String userCode=request.getParameter("userCode");	
		String userPassword=request.getParameter("userPassword");
		Map<String, String> map=new HashMap<>();
		map.put("code", userCode);
		//这里执行查询方法
		BackendUser user=dao.findCode(map);
		if(user==null){
			request.setAttribute("error", "用户名错误");
			return "backendlogin"; //返回页面，提示错误信息
		}
		map.put("password", userPassword);
		//这里再次执行查询方法
		user=dao.findCode(map);
		if(user==null){
			request.setAttribute("error", "密码错误");
			return "backendlogin"; //返回页面，提示错误信息
		}
		session.setAttribute("userSession", user);
		return "backend/main";
	}
	@RequestMapping("/dev/dologin2")
	public String dologin2(HttpServletRequest request,HttpSession session){
		String userCode=request.getParameter("devCode");	
		String userPassword=request.getParameter("devPassword");
		Map<String, String> map=new HashMap<>();
		map.put("code", userCode);
		//这里执行查询方法
		DevUser user=dao.findUser(map);
		if(user==null){
			request.setAttribute("error", "用户名错误");
			return "devlogin"; //返回页面，提示错误信息
		}
		map.put("password", userPassword);
		//这里再次执行查询方法
		user=dao.findUser(map);
		if(user==null){
			request.setAttribute("error", "密码错误");
			return "devlogin"; //返回页面，提示错误信息
		}
		session.setAttribute("devUserSession", user);
		return "developer/main";
	}

	//测试
	@RequestMapping("/ceshi")
	public String aaaa(){
		System.out.println(2);
		return "backend/appcheck";
	}

}
